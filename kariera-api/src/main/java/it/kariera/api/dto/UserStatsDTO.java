package it.kariera.api.dto;

public class UserStatsDTO {
    private Double arithmeticAverage;
    private Double weightedAverage;
    private Integer acquiredCFU;
    private Integer totalCFU;
    private Double degreeGradePrediction;
    private Integer passedExams;
    private Integer totalExams;

    public UserStatsDTO() {}

    public Double getArithmeticAverage() {return arithmeticAverage;}
    public void setArithmeticAverage(Double arithmeticAverage) {this.arithmeticAverage = arithmeticAverage;}

    public Double getWeightedAverage() {return weightedAverage;}
    public void setWeightedAverage(Double weightedAverage) {this.weightedAverage = weightedAverage;}

    public Integer getAcquiredCFU() {return acquiredCFU;}
    public void setAcquiredCFU(Integer acquiredCFU) {this.acquiredCFU = acquiredCFU;}

    public Integer getTotalCFU() {return totalCFU;}
    public void setTotalCFU(Integer totalCFU) {this.totalCFU = totalCFU;}

    public Double getDegreeGradePrediction() {return degreeGradePrediction;}
    public void setDegreeGradePrediction(Double degreeGradePrediction) {this.degreeGradePrediction = degreeGradePrediction;}

    public Integer getPassedExams() {return passedExams;}
    public void setPassedExams(Integer passedExams) {this.passedExams = passedExams;}

    public Integer getTotalExams() {return totalExams;}
    public void setTotalExams(Integer totalExams) {this.totalExams = totalExams;}

}
