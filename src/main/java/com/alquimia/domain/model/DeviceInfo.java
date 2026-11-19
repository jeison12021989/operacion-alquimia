package com.alquimia.domain.model;
public class DeviceInfo {
    private String deviceId;
    private double distance;
    private String messageFragment;
    public DeviceInfo(String deviceId, double distance, String messageFragment) {
        this.deviceId = deviceId;
        this.distance = distance;
        this.messageFragment = messageFragment != null ? messageFragment : "";
    }
    public String getDeviceId() { return deviceId; }
    public void setDeviceId(String deviceId) { this.deviceId = deviceId; }
    public double getDistance() { return distance; }
    public void setDistance(double distance) { this.distance = distance; }
    public String getMessageFragment() { return messageFragment; }
    public void setMessageFragment(String messageFragment) { 
        this.messageFragment = messageFragment != null ? messageFragment : ""; 
    }
}