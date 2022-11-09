package com.zuitt.coursebooking.controllers;

import com.zuitt.coursebooking.models.Course;
import com.zuitt.coursebooking.models.User;
import com.zuitt.coursebooking.repositories.ICourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class CourseController extends AppController {
    @Autowired
    private ICourseRepository course;

    @GetMapping("/api/courses")
    public List<Course> get() {
        return course.findAllActive();
    }

    @GetMapping("/api/courses/{id}")
    public Course getOne(@PathVariable int id) {
        return course.findById(id).get();
    }

    @PostMapping("/api/courses")
    public ResponseEntity<Object> add(@RequestBody Course newCourse) {
        HashMap<String, String> response = new HashMap<>();

        course.save(new Course(
            newCourse.getName(),
            newCourse.getDescription(),
            newCourse.getPrice()
        ));
        response.put("result", "added");

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/api/courses/{id}")
    public ResponseEntity<Object> update(@PathVariable int id, @RequestBody HashMap<String, Object> data) {
        HashMap<String, String> response = new HashMap<>();
        Course selectedCourse = course.findById(id).get();

        selectedCourse.setName(data.get("name").toString());
        selectedCourse.setDescription(data.get("description").toString());
        selectedCourse.setPrice(Double.parseDouble(data.get("price").toString()));

        course.save(selectedCourse);
        response.put("result", "updated");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/api/courses/{id}")
    public ResponseEntity<Object> archive(@PathVariable int id) {
        HashMap<String, String> response = new HashMap<>();
        Course selectedCourse = course.findById(id).get();

        selectedCourse.setIsActive(false);

        course.save(selectedCourse);
        response.put("result", "archived");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}