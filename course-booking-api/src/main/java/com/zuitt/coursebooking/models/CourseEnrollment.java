package com.zuitt.coursebooking.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "course_enrollments")
public class CourseEnrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int courseId;
    private int userId;
    @Column(insertable = false)
    private LocalDateTime datetimeEnrolled;

    public CourseEnrollment() { }

    public CourseEnrollment(
        int courseId,
        int userId
    ) {
        this.courseId = courseId;
        this.userId = userId;
        this.datetimeEnrolled = LocalDateTime.now();
    }

    public int getId() {
        return this.id;
    }

    public int getCourseId() {
        return this.courseId;
    }

    public int getUserId() {
        return this.userId;
    }

    public LocalDateTime getDatetimeEnrolled() {
        return this.datetimeEnrolled;
    }
}
