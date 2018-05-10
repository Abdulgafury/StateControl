package com.abdulgafur.bersugir.statecontrol.Products;



public class MonitoringProduct {
    private String monitoringName;
    private String monitoringMoisture;
    private String monitoringMoistureState;

    private String monitoringTemperature;
    private String monitoringTemperatureState;

    private String monitoringHumidity;
    private String monitoringHumidityState;




    public MonitoringProduct(String monitoringName, String monitoringMoisture,String monitoringMoistureState, String monitoringTemperature, String monitoringTemperatureState, String monitoringHumidity, String monitoringHumidityState) {
        this.monitoringName = monitoringName;
        this.monitoringMoisture = monitoringMoisture;
        this.monitoringMoistureState = monitoringMoistureState;
        this.monitoringTemperature = monitoringTemperature;
        this.monitoringTemperatureState = monitoringTemperatureState;
        this.monitoringHumidity = monitoringHumidity;
        this.monitoringHumidityState = monitoringHumidityState;
    }

    public String getMonitoringMoisture() {
        return monitoringMoisture;
    }

    public void setMonitoringMoisture(String monitoringMoisture) {
        this.monitoringMoisture = monitoringMoisture;
    }

    public String getMonitoringTemperature() {
        return monitoringTemperature;
    }

    public void setMonitoringTemperature(String monitoringTemperature) {
        this.monitoringTemperature = monitoringTemperature;
    }

    public String getMonitoringHumidity() {
        return monitoringHumidity;
    }

    public void setMonitoringHumidity(String monitoringHumidity) {
        this.monitoringHumidity = monitoringHumidity;
    }

    public String getMonitoringName() {
        return monitoringName;
    }

    public void setMonitoringName(String monitoringName) {
        this.monitoringName = monitoringName;
    }

    public String getMonitoringMoistureState() {
        return monitoringMoistureState;
    }

    public void setMonitoringMoistureState(String monitoringMoistureState) {
        this.monitoringMoistureState = monitoringMoistureState;
    }

    public String getMonitoringTemperatureState() {
        return monitoringTemperatureState;
    }

    public void setMonitoringTemperatureState(String monitoringTemperatureState) {
        this.monitoringTemperatureState = monitoringTemperatureState;
    }

    public String getMonitoringHumidityState() {
        return monitoringHumidityState;
    }

    public void setMonitoringHumidityState(String monitoringHumidityState) {
        this.monitoringHumidityState = monitoringHumidityState;
    }
}
