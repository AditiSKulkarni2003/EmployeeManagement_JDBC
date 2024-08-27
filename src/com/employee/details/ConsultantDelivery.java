package com.employee.details;

import java.util.List;

public class ConsultantDelivery extends Employee {
    private String consultingLevel;
    private List<String> leadProjects;

    public String getConsultingLevel() {
        return consultingLevel;
    }

    public void setConsultingLevel(String consultingLevel) {
        this.consultingLevel = consultingLevel;
    }

    public List<String> getLeadProjects() {
        return leadProjects;
    }

    public void setLeadProjects(List<String> leadProjects) {
        this.leadProjects = leadProjects;
    }

    public ConsultantDelivery(int empId, String empName, String empCompanyName, String empBloodGroup, Address address, String GCMLevel, String dassId, String consultingLevel, List<String> leadProjects) {
        super(empId, empName, empCompanyName, empBloodGroup, address, GCMLevel, dassId);
        this.consultingLevel = consultingLevel;
        this.leadProjects = leadProjects;
    }

    @Override
    public String toString() {
        return "ConsultantDelivery{" +

                "consultingLevel='" + consultingLevel + '\'' +
                ", leadProjects=" + leadProjects +
                '}';
    }
}
