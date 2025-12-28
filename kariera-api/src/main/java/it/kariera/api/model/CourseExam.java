package it.kariera.api.model;

public class CourseExam {
    private int id;
    private int course_id;
    private String name;
    private int cfu;
    private Integer academic_year;
    private Boolean is_elective; // serve per distinguere tra esami obbligatori e esami a scelta

    public CourseExam(){}

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public int getCourse_id() {return course_id;}
    public void setCourse_id(int course_id) {this.course_id = course_id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public int getCfu() {return cfu;}
    public void setCfu(int cfu) {this.cfu = cfu;}

    public Integer getAcademic_year() {return academic_year;}
    public void setAcademic_year(Integer academic_year) {this.academic_year = academic_year;}

    public Boolean getIs_elective() {return is_elective;}
    public void setIs_elective(Boolean is_elective) {this.is_elective = is_elective;}
}
