package com.example.certificate;
import jakarta.persistence.*;
@Entity
public class Certificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;
    private String name;
    private String course;
    private String date;

    public Certificate() {}

    public long getId() {
        return Id;
    }
    public void setId(long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }
    public void setCourse(String course) {
        this.course = course;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
