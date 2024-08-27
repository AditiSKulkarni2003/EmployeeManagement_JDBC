package com.employee.details;

public class Employee {

    private int empId;
    private String empName;
    private String empCompanyName;
    private String empBloodGroup;
    private Address address;
    private String GCMLevel;
    private String dassId;

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpCompanyName() {
        return empCompanyName;
    }

    public void setEmpCompanyName(String empCompanyName) {
        this.empCompanyName = empCompanyName;
    }

    public String getEmpBloodGroup() {
        return empBloodGroup;
    }

    public void setEmpBloodGroup(String empBloodGroup) {
        this.empBloodGroup = empBloodGroup;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;

    }

    public String getGCMLevel() {
        return GCMLevel;
    }

    public void setGCMLevel(String GCMLevel) {
        this.GCMLevel = GCMLevel;
    }

    public String getDassId() {
        return dassId;
    }

    public void setDassId(String dassId) {
        this.dassId = dassId;
    }

    public Employee(int empId, String empName, String empCompanyName, String empBloodGroup, Address address, String GCMLevel, String dassId) {
        this.empId = empId;
        this.empName = empName;
        this.empCompanyName = empCompanyName;
        this.empBloodGroup = empBloodGroup;
        this.address = address;
        this.GCMLevel = GCMLevel;
        this.dassId = dassId;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empId=" + empId +
                ", empName='" + empName + '\'' +
                ", empCompanyName='" + empCompanyName + '\'' +
                ", empBloodGroup='" + empBloodGroup + '\'' +
                ", address=" + address +
                ", GCMLevel='" + GCMLevel + '\'' +
                ", dassId='" + dassId + '\'' +
                '}';
    }
}
