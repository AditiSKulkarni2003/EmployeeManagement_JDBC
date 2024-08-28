package com.employee.details;

import java.sql.*;
import java.util.Scanner;

public class EmployeeDao {

    private static final String URL = "jdbc:mysql://localhost:3307/EmployeeDetails";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        EmployeeDao dao = new EmployeeDao();
        dao.menu();
    }

    private void menu() {
        boolean running = true;

        while (running) {
            System.out.println("Choose an operation:");
            System.out.println("1. Create Employee");
            System.out.println("2. Read Employee");
            System.out.println("3. Update Employee");
            System.out.println("4. Delete Employee");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            String input = scanner.nextLine();

            try {
                int choice = Integer.parseInt(input);

                switch (choice) {
                    case 1:
                        createEmployee();
                        break;
                    case 2:
                        readEmployee();
                        break;
                    case 3:
                        updateEmployee();
                        break;
                    case 4:
                        deleteEmployee();
                        break;
                    case 5:
                        System.out.println("Exiting the program...");
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        scanner.close();
    }

    private void createEmployee() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

            Address address = getAddressFromUser(conn);

            System.out.print("Enter Employee ID (leave blank for auto-generated): ");
            String empIdInput = scanner.nextLine();
            Integer empId = empIdInput.isEmpty() ? null : Integer.parseInt(empIdInput);

            System.out.print("Enter Employee Name: ");
            String empName = scanner.nextLine();

            System.out.print("Enter Employee Company Name: ");
            String empCompanyName = scanner.nextLine();

            System.out.print("Enter Employee Blood Group: ");
            String empBloodGroup = scanner.nextLine();

            System.out.print("Enter GCM Level: ");
            String gcmLevel = scanner.nextLine();

            System.out.print("Enter Dass ID: ");
            String dassId = scanner.nextLine();

            System.out.print("Enter Employee Type (AssociateConsultantDelivery / ConsultantDelivery / Manager): ");
            String empType = scanner.nextLine();

            String insertEmployeeSQL = "INSERT INTO employee (emp_id, emp_name, emp_company_name, emp_blood_group, gcm_level, dass_id, address_id, emp_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertEmployeeSQL, Statement.RETURN_GENERATED_KEYS)) {
                if (empId == null) {
                    pstmt.setNull(1, Types.INTEGER);
                } else {
                    pstmt.setInt(1, empId);
                }
                pstmt.setString(2, empName);
                pstmt.setString(3, empCompanyName);
                pstmt.setString(4, empBloodGroup);
                pstmt.setString(5, gcmLevel);
                pstmt.setString(6, dassId);
                pstmt.setInt(7, address.getId());
                pstmt.setString(8, empType);
                pstmt.executeUpdate();


                if (empId == null) {
                    try (ResultSet rs = pstmt.getGeneratedKeys()) {
                        if (rs.next()) {
                            empId = rs.getInt(1);
                        }
                    }
                }
            }


            String insertSpecificSQL;
            if ("AssociateConsultantDelivery".equalsIgnoreCase(empType)) {
                System.out.print("Enter Skill Set (comma separated): ");
                String skillSet = scanner.nextLine();

                System.out.print("Enter Reports To: ");
                String reportsTo = scanner.nextLine();

                System.out.print("Enter Project Role: ");
                String projectRole = scanner.nextLine();

                insertSpecificSQL = "UPDATE employee SET skill_set = ?, project_role = ?, reports_to = ? WHERE emp_id = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(insertSpecificSQL)) {
                    pstmt.setString(1, skillSet);
                    pstmt.setString(2, projectRole);
                    pstmt.setString(3, reportsTo);
                    pstmt.setInt(4, empId);
                    pstmt.executeUpdate();
                }
            } else if ("ConsultantDelivery".equalsIgnoreCase(empType)) {
                System.out.print("Enter Consulting Level: ");
                String consultingLevel = scanner.nextLine();

                System.out.print("Enter Lead Projects (comma separated): ");
                String leadProjects = scanner.nextLine();

                insertSpecificSQL = "UPDATE employee SET consulting_level = ?, lead_projects = ? WHERE emp_id = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(insertSpecificSQL)) {
                    pstmt.setString(1, consultingLevel);
                    pstmt.setString(2, leadProjects);
                    pstmt.setInt(3, empId);
                    pstmt.executeUpdate();
                }
            } else if ("Manager".equalsIgnoreCase(empType)) {
                System.out.print("Enter Team Size: ");
                String teamSize = scanner.nextLine();

                System.out.print("Enter Location: ");
                String location = scanner.nextLine();

                insertSpecificSQL = "UPDATE employee SET team_size = ?, location = ? WHERE emp_id = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(insertSpecificSQL)) {
                    pstmt.setString(1, teamSize);
                    pstmt.setString(2, location);
                    pstmt.setInt(3, empId);
                    pstmt.executeUpdate();
                }
            } else {
                throw new IllegalArgumentException("Invalid Employee Type");
            }

            System.out.println("Employee created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format for employee ID.");
        }
    }

    private Address getAddressFromUser(Connection conn) throws SQLException {
        System.out.print("Enter Building Name: ");
        String buildingName = scanner.nextLine();

        System.out.print("Enter City: ");
        String city = scanner.nextLine();

        System.out.print("Enter PinCode: ");
        String pinCode = scanner.nextLine();

        System.out.print("Enter Mobile No: ");
        String mobileNo = scanner.nextLine();

        String insertAddressSQL = "INSERT INTO address (building_name, city, pin_code, mob_no) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(insertAddressSQL, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, buildingName);
            pstmt.setString(2, city);
            pstmt.setString(3, pinCode);
            pstmt.setString(4, mobileNo);
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return new Address(rs.getInt(1), buildingName, city, pinCode, mobileNo);
                } else {
                    throw new SQLException("Failed to retrieve address ID.");
                }
            }
        }
    }

    private void readEmployee() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

            System.out.print("Enter Employee ID to view: ");
            int empId = Integer.parseInt(scanner.nextLine());

            String querySQL = "SELECT * FROM employee WHERE emp_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(querySQL)) {
                pstmt.setInt(1, empId);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        // Fetch general employee info
                        System.out.println("ID: " + rs.getInt("emp_id"));
                        System.out.println("Name: " + rs.getString("emp_name"));
                        System.out.println("Company Name: " + rs.getString("emp_company_name"));
                        System.out.println("Blood Group: " + rs.getString("emp_blood_group"));
                        System.out.println("GCM Level: " + rs.getString("gcm_level"));
                        System.out.println("Dass ID: " + rs.getString("dass_id"));
                        System.out.println("Employee Type: " + rs.getString("emp_type"));


                        String empType = rs.getString("emp_type");
                        if ("AssociateConsultantDelivery".equalsIgnoreCase(empType)) {
                            System.out.println("Skill Set: " + rs.getString("skill_set"));
                            System.out.println("Project Role: " + rs.getString("project_role"));
                            System.out.println("Reports To: " + rs.getString("reports_to"));
                        } else if ("ConsultantDelivery".equalsIgnoreCase(empType)) {
                            System.out.println("Consulting Level: " + rs.getString("consulting_level"));
                            System.out.println("Lead Projects: " + rs.getString("lead_projects"));
                        } else if ("Manager".equalsIgnoreCase(empType)) {
                            System.out.println("Team Size: " + rs.getString("team_size"));
                            System.out.println("Location: " + rs.getString("location"));
                        }
                    } else {
                        System.out.println("Employee not found.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid employee ID.");
        }
    }

    private void updateEmployee() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

            System.out.print("Enter Employee ID to update: ");
            int empId = Integer.parseInt(scanner.nextLine());

            // Check if the employee exists
            String checkSQL = "SELECT * FROM employee WHERE emp_id = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSQL)) {
                checkStmt.setInt(1, empId);
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (!rs.next()) {
                        System.out.println("Employee not found.");
                        return;
                    }

                    // Display current details
                    System.out.println("Current details of employee ID " + empId + ":");
                    System.out.println("Name: " + rs.getString("emp_name"));
                    System.out.println("Company Name: " + rs.getString("emp_company_name"));
                    System.out.println("Blood Group: " + rs.getString("emp_blood_group"));
                    System.out.println("GCM Level: " + rs.getString("gcm_level"));
                    System.out.println("Dass ID: " + rs.getString("dass_id"));
                    String empType = rs.getString("emp_type");
                    System.out.println("Employee Type: " + empType);

                    if ("AssociateConsultantDelivery".equalsIgnoreCase(empType)) {
                        System.out.println("Skill Set: " + rs.getString("skill_set"));
                        System.out.println("Project Role: " + rs.getString("project_role"));
                        System.out.println("Reports To: " + rs.getString("reports_to"));
                    } else if ("ConsultantDelivery".equalsIgnoreCase(empType)) {
                        System.out.println("Consulting Level: " + rs.getString("consulting_level"));
                        System.out.println("Lead Projects: " + rs.getString("lead_projects"));
                    } else if ("Manager".equalsIgnoreCase(empType)) {
                        System.out.println("Team Size: " + rs.getString("team_size"));
                        System.out.println("Location: " + rs.getString("location"));
                    }

                    // Prompt for new values
                    System.out.print("Enter new Employee Name (or press Enter to keep current): ");
                    String newName = scanner.nextLine();
                    if (newName.isEmpty()) newName = rs.getString("emp_name");

                    System.out.print("Enter new Company Name (or press Enter to keep current): ");
                    String newCompanyName = scanner.nextLine();
                    if (newCompanyName.isEmpty()) newCompanyName = rs.getString("emp_company_name");

                    System.out.print("Enter new Blood Group (or press Enter to keep current): ");
                    String newBloodGroup = scanner.nextLine();
                    if (newBloodGroup.isEmpty()) newBloodGroup = rs.getString("emp_blood_group");

                    System.out.print("Enter new GCM Level (or press Enter to keep current): ");
                    String newGCMLevel = scanner.nextLine();
                    if (newGCMLevel.isEmpty()) newGCMLevel = rs.getString("gcm_level");

                    System.out.print("Enter new Dass ID (or press Enter to keep current): ");
                    String newDassId = scanner.nextLine();
                    if (newDassId.isEmpty()) newDassId = rs.getString("dass_id");

                    // Update common employee details
                    String updateSQL = "UPDATE employee SET emp_name = ?, emp_company_name = ?, emp_blood_group = ?, gcm_level = ?, dass_id = ? WHERE emp_id = ?";
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateSQL)) {
                        updateStmt.setString(1, newName);
                        updateStmt.setString(2, newCompanyName);
                        updateStmt.setString(3, newBloodGroup);
                        updateStmt.setString(4, newGCMLevel);
                        updateStmt.setString(5, newDassId);
                        updateStmt.setInt(6, empId);
                        updateStmt.executeUpdate();
                    }

                    // Update type-specific details
                    if ("AssociateConsultantDelivery".equalsIgnoreCase(empType)) {
                        System.out.print("Enter new Skill Set (or press Enter to keep current): ");
                        String newSkillSet = scanner.nextLine();
                        if (newSkillSet.isEmpty()) newSkillSet = rs.getString("skill_set");

                        System.out.print("Enter new Project Role (or press Enter to keep current): ");
                        String newProjectRole = scanner.nextLine();
                        if (newProjectRole.isEmpty()) newProjectRole = rs.getString("project_role");

                        System.out.print("Enter new Reports To (or press Enter to keep current): ");
                        String newReportsTo = scanner.nextLine();
                        if (newReportsTo.isEmpty()) newReportsTo = rs.getString("reports_to");

                        String updateSpecificSQL = "UPDATE employee SET skill_set = ?, project_role = ?, reports_to = ? WHERE emp_id = ?";
                        try (PreparedStatement pstmt = conn.prepareStatement(updateSpecificSQL)) {
                            pstmt.setString(1, newSkillSet);
                            pstmt.setString(2, newProjectRole);
                            pstmt.setString(3, newReportsTo);
                            pstmt.setInt(4, empId);
                            pstmt.executeUpdate();
                        }

                    } else if ("ConsultantDelivery".equalsIgnoreCase(empType)) {
                        System.out.print("Enter new Consulting Level (or press Enter to keep current): ");
                        String newConsultingLevel = scanner.nextLine();
                        if (newConsultingLevel.isEmpty()) newConsultingLevel = rs.getString("consulting_level");

                        System.out.print("Enter new Lead Projects (or press Enter to keep current): ");
                        String newLeadProjects = scanner.nextLine();
                        if (newLeadProjects.isEmpty()) newLeadProjects = rs.getString("lead_projects");

                        String updateSpecificSQL = "UPDATE employee SET consulting_level = ?, lead_projects = ? WHERE emp_id = ?";
                        try (PreparedStatement pstmt = conn.prepareStatement(updateSpecificSQL)) {
                            pstmt.setString(1, newConsultingLevel);
                            pstmt.setString(2, newLeadProjects);
                            pstmt.setInt(3, empId);
                            pstmt.executeUpdate();
                        }

                    } else if ("Manager".equalsIgnoreCase(empType)) {
                        System.out.print("Enter new Team Size (or press Enter to keep current): ");
                        String newTeamSize = scanner.nextLine();
                        if (newTeamSize.isEmpty()) newTeamSize = rs.getString("team_size");

                        System.out.print("Enter new Location (or press Enter to keep current): ");
                        String newLocation = scanner.nextLine();
                        if (newLocation.isEmpty()) newLocation = rs.getString("location");

                        String updateSpecificSQL = "UPDATE employee SET team_size = ?, location = ? WHERE emp_id = ?";
                        try (PreparedStatement pstmt = conn.prepareStatement(updateSpecificSQL)) {
                            pstmt.setString(1, newTeamSize);
                            pstmt.setString(2, newLocation);
                            pstmt.setInt(3, empId);
                            pstmt.executeUpdate();
                        }

                    } else {
                        System.out.println("Invalid employee type, unable to update specific fields.");
                    }

                    System.out.println("Employee updated successfully.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid employee ID.");
        }
    }

    private void deleteEmployee() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

            System.out.print("Enter Employee ID to delete: ");
            int empId = Integer.parseInt(scanner.nextLine());

            String deleteSQL = "DELETE FROM employee WHERE emp_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {
                pstmt.setInt(1, empId);
                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("Employee deleted successfully.");
                } else {
                    System.out.println("Employee not found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid employee ID.");
        }
    }
}