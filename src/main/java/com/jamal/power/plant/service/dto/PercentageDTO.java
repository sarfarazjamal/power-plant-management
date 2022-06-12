package com.jamal.power.plant.service.dto;

public class PercentageDTO {

    private String actualValue;
    private String plantOutputPercentage;


    public String getActualValue() {
        return actualValue;
    }

    public void setActualValue(String actualValue) {
        this.actualValue = actualValue;
    }


    public String getPlantOutputPercentage() {
        return plantOutputPercentage;
    }

    public void setPlantOutputPercentage(String plantOutputPercentage) {
        this.plantOutputPercentage = plantOutputPercentage;
    }
}
