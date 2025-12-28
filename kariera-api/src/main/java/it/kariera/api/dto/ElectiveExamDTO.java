package it.kariera.api.dto;

import java.util.List;


public class ElectiveExamDTO {
    private Integer userId;
    private List<Integer> electiveExamIds;

    public ElectiveExamDTO() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<Integer> getElectiveExamIds() {
        return electiveExamIds;
    }

    public void setElectiveExamIds(List<Integer> electiveExamIds) {
        this.electiveExamIds = electiveExamIds;
    }
}
