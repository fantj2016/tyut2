package com.tyut.eureka.core.pojo;

public class Grade {
    private Integer id;

    private String username;

    private String classSchedule;

    private String grade;

    private Integer rank;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getClassSchedule() {
        return classSchedule;
    }

    public void setClassSchedule(String classSchedule) {
        this.classSchedule = classSchedule == null ? null : classSchedule.trim();
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade == null ? null : grade.trim();
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }
}