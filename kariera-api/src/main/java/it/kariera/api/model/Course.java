package it.kariera.api.model;

public class Course {
    private int id;
    private String name;
    private int study_area_id;

    public Course(){}

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public int getStudy_area_id() {return study_area_id;}
    public void setStudy_area_id(int study_area_id) {this.study_area_id = study_area_id;}
}
