package com.zuitt.coursebooking.repositories;

import com.zuitt.coursebooking.models.Course;
import com.zuitt.coursebooking.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICourseRepository extends JpaRepository<Course, Integer> {
    @Query(value = "SELECT * FROM courses WHERE is_active = 1", nativeQuery = true)
    List<Course> findAllActive();
}
