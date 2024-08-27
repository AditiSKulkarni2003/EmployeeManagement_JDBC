package com.employee.details;

public class Manager extends Employee {
    private String teamSize;
    private String location;



    public String getTeamSize() {
        return teamSize;
    }

    public void setTeamSize(String teamSize) {
        this.teamSize = teamSize;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public Manager(int empId, String empName, String empCompanyName, String empBloodGroup, Address address, String GCMLevel, String dassId, String location, String teamSize) {
        super(empId, empName, empCompanyName, empBloodGroup, address, GCMLevel, dassId);
        this.location = location;
        this.teamSize = teamSize;
    }

    @Override
    public String toString() {
        return "Manager{" +


                "teamSize='" + teamSize + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
