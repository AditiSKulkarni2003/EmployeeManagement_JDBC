package com.employee.details;

import java.util.List;

public class AssociateConsultantDelivery extends Employee {

    private List<String> skillSet;
    private String reportsTo;
    private String projectRole;

    public List<String> getSkillSet() {
        return skillSet;
    }

    public void setSkillSet(List<String> skillSet) {
        this.skillSet = skillSet;
    }

    public String getReportsTo() {
        return reportsTo;
    }

    public void setReportsTo(String reportsTo) {
        this.reportsTo = reportsTo;
    }

    public String getProjectRole() {
        return projectRole;
    }

    public void setProjectRole(String projectRole) {
        this.projectRole = projectRole;
    }

    public AssociateConsultantDelivery(int empId, String empName, String empCompanyName, String empBloodGroup, Address address, String GCMLevel, String dassId, List<String> skillSet, String reportsTo, String projectRole) {
        super(empId, empName, empCompanyName, empBloodGroup, address, GCMLevel, dassId);
        this.skillSet = skillSet;
        this.reportsTo = reportsTo;
        this.projectRole = projectRole;
    }

    @Override
    public String toString() {
        return "AssociateConsultantDelivery{" +
                "skillSet=" + skillSet +
                ", reportsTo='" + reportsTo + '\'' +
                ", projectRole='" + projectRole + '\'' +
                '}';
    }
}
