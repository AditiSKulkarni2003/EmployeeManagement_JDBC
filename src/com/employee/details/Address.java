package com.employee.details;

public class Address {

    private int id; // Add this field
    private String buildingName;
    private String PinCode;
    private String city;
    private String mobNo;

    // Constructor
    public Address(int id, String buildingName, String city, String PinCode, String mobNo) {
        this.id = id;
        this.buildingName = buildingName;
        this.city = city;
        this.PinCode = PinCode;
        this.mobNo = mobNo;
    }

    // Getters and Setters for the id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Other Getters and Setters
    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getPinCode() {
        return PinCode;
    }

    public void setPinCode(String PinCode) {
        this.PinCode = PinCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMobNo() {
        return mobNo;
    }

    public void setMobNo(String mobNo) {
        this.mobNo = mobNo;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", buildingName='" + buildingName + '\'' +
                ", PinCode='" + PinCode + '\'' +
                ", city='" + city + '\'' +
                ", mobNo='" + mobNo + '\'' +
                '}';
    }
    }
