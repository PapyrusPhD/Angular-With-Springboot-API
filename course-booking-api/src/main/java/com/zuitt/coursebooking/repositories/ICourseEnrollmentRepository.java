package com.zuitt.coursebooking.repositories;

import com.zuitt.coursebooking.models.CourseEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ICourseEnrollmentRepository extends JpaRepository<CourseEnrollment, Integer> {
    @Query(value = "SELECT * FROM course_enrollments WHERE course_id = ?1 AND user_id = ?2 LIMIT 1", nativeQuery = true)
    CourseEnrollment findPriorEnrollment(int courseId, int userId);
}
