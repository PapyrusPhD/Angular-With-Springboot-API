package com.zuitt.coursebooking.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private double price;
    @Column(insertable = false)
    private boolean isActive;
    @Column(insertable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime datetimeCreated;

    public Course() { }

    public Course(
        String name,
        String description,
        double price
    ) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double value) {
        this.price = value;
    }

    public boolean getIsActive() {
        return this.isActive;
    }

    public void setIsActive(boolean value) {
        this.isActive = value;
    }

    public LocalDateTime getDatetimeCreated() {
        return this.datetimeCreated;
    }
}
