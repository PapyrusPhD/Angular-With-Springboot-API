package com.zuitt.coursebooking.controllers;

import com.zuitt.coursebooking.Constants;
import com.zuitt.coursebooking.models.CourseEnrollment;
import com.zuitt.coursebooking.repositories.ICourseEnrollmentRepository;
import com.zuitt.coursebooking.repositories.IUserRepository;
import com.zuitt.coursebooking.models.User;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;

@RestController
public class UserController extends AppController {
    @Autowired
    private IUserRepository user;

    @Autowired
    private ICourseEnrollmentRepository courseEnrollment;

    @PostMapping("/api/users/register")
    public ResponseEntity<Object> register(@RequestBody User newUser) {
        HashMap<String, String> response = new HashMap<>();
        String encodedPassword = new BCryptPasswordEncoder().encode(newUser.getPassword());

        user.save(new User(
            newUser.getFirstName(),
            newUser.getLastName(),
            newUser.getEmail(),
            encodedPassword
        ));
        response.put("result", "added");

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/api/users/login")
    public ResponseEntity<Object> login(@RequestBody HashMap<String, Object> credentials) {
        HashMap<String, String> response = new HashMap<>();
        User matchedUser = user.findByEmail(credentials.get("email").toString());

        if (matchedUser != null) {
            String enteredPasword = credentials.get("password").toString();
            boolean isPasswordMatched = new BCryptPasswordEncoder().matches(enteredPasword, matchedUser.getPassword());

            if (isPasswordMatched) {
                response.put("result", "successful");
                response.put("isAdmin", Boolean.toString(matchedUser.getIsAdmin()));
                response.put("email", matchedUser.getEmail());
                response.put("token", generateToken(matchedUser.getId(), matchedUser.getEmail()));
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.put("result", "incorrect_credentials");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } else {
            response.put("result", "user_not_found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/api/users/enroll")
    public ResponseEntity<Object> enroll(@RequestBody HashMap<String, Object> data, HttpServletRequest request) {
        HashMap<String, String> response = new HashMap<>();
        int courseId = Integer.parseInt(data.get("courseId").toString());
        int userId = Integer.parseInt(request.getAttribute("id").toString());
        CourseEnrollment priorEnrollment = courseEnrollment.findPriorEnrollment(courseId, userId);

        if (priorEnrollment == null) {
            courseEnrollment.save(new CourseEnrollment(courseId, userId));
            response.put("result", "added");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            response.put("result", "already_enrolled");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    private String generateToken(int id, String email) {
        long timestamp = System.currentTimeMillis();
        JwtBuilder builder = Jwts.builder().signWith(SignatureAlgorithm.HS256, Constants.API_SECRET_KEY);

        builder = builder.setIssuedAt(new Date(timestamp));
        builder = builder.setExpiration(new Date(timestamp + Constants.TOKEN_VALIDITY));

        builder = builder.claim("id", id);
        builder = builder.claim("email", email);

        return builder.compact();
    }
}