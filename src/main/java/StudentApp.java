import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;


public class StudentApp {

    // Database connection parameters
    private static final String URL = "jdbc:postgresql://localhost:5432/Assignment 3";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Ottawa*s8072731687";

    // Set up the connection to PostgreSQL database
    private Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    //Display all students
    public void getAllStudents() {
        String query = "SELECT * FROM students";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("\n--- All Students ---");
            while (rs.next()) { //loop through each row
                System.out.printf("ID: %d | %s %s | Email: %s | Enrolled: %s%n",
                        rs.getInt("student_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getDate("enrollment_date"));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving students: " + e.getMessage());
        }
    }

    //Add a new student to the database
    public void addStudent(String firstName, String lastName, String email, LocalDate enrollmentDate) {
        String query = "INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES (?, ?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            //Set up the parameters for the new student
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, email);
            pstmt.setDate(4, Date.valueOf(enrollmentDate));

            int rows = pstmt.executeUpdate();
            if (rows > 0) System.out.println("✅ Student added successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding student: " + e.getMessage());
        }
    }

    //Update a student's email using their id
    public void updateStudentEmail(int studentId, String newEmail) {
        String query = "UPDATE students SET email = ? WHERE student_id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            //Set up the parameters for updating the email
            pstmt.setString(1, newEmail);
            pstmt.setInt(2, studentId);

            int rows = pstmt.executeUpdate();
            if (rows > 0) System.out.println("✅ Student email updated successfully!");
            else System.out.println("⚠️ Student ID not found.");
        } catch (SQLException e) {
            System.out.println("Error updating email: " + e.getMessage());
        }
    }

    //Delete a student using their id
    public void deleteStudent(int studentId) {
        String query = "DELETE FROM students WHERE student_id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            //Set the student to be deleted using their id
            pstmt.setInt(1, studentId);
            int rows = pstmt.executeUpdate();
            if (rows > 0) System.out.println("🗑️ Student deleted successfully!");
            else System.out.println("⚠️ Student ID not found.");
        } catch (SQLException e) {
            System.out.println("Error deleting student: " + e.getMessage());
        }
    }

    /** Main method to demonstrate CRUD operations */
    public static void main(String[] args) {
        StudentApp app = new StudentApp();
        Scanner sc = new Scanner(System.in);

        System.out.println("=== PostgreSQL Student Database ===");

        while (true) {
            //Menu options
            System.out.println("\nChoose an operation:");
            System.out.println("1. View all students");
            System.out.println("2. Add a new student");
            System.out.println("3. Update a student's email");
            System.out.println("4. Delete a student");
            System.out.println("5. Exit");
            System.out.print("Enter choice (1-5): ");

            //Retrieve user input
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1: //print all the students
                    app.getAllStudents();
                    break;

                case 2: //Add a new student
                    System.out.print("Enter first name: ");
                    String first = sc.nextLine();

                    System.out.print("Enter last name: ");
                    String last = sc.nextLine();

                    System.out.print("Enter email: ");
                    String email = sc.nextLine();

                    System.out.print("Enter enrollment date (YYYY-MM-DD): ");
                    String dateInput = sc.nextLine();
                    LocalDate date = LocalDate.parse(dateInput);

                    app.addStudent(first, last, email, date);
                    break;

                case 3: //Update a student's email
                    System.out.print("Enter student ID to update: ");
                    int updateId = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter new email: ");
                    String newEmail = sc.nextLine();

                    app.updateStudentEmail(updateId, newEmail);
                    break;

                case 4: //Delete a student
                    System.out.print("Enter student ID to delete: ");
                    int deleteId = sc.nextInt();
                    sc.nextLine();

                    app.deleteStudent(deleteId);
                    break;

                case 5: //Exit the application
                    System.out.println("Exiting application. Goodbye!");
                    sc.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice. Please select between 1 and 5.");
            }
        }
    }
}
