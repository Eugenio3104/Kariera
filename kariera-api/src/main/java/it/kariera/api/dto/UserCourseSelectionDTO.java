package it.kariera.api.dto;

import java.util.List;

public class UserCourseSelectionDTO {
    private List<Integer> courses;

    public UserCourseSelectionDTO(){}

    public List<Integer> getCourses() {return courses;}
    public void setCourses(List<Integer> courses) {this.courses = courses;}
}
