Application Overview: the application demonstrates a Jave JDBC application that connects to PostgreSQL through pgAdmin. It connects to the database to perform CRUD (create, read, update, delete) 
operations. The database contains information about student enrolment in a university.

How to Compile and Run the Application: Create a new database on PgAdmin and run the following SQL commands:

    CREATE TABLE students (
        student_id SERIAL PRIMARY KEY,
        first_name TEXT NOT NULL,
        last_name TEXT NOT NULL,
        email TEXT NOT NULL UNIQUE,
        enrollment_date DATE
    );
    
    INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES
    ('John', 'Doe', 'john.doe@example.com', '2023-09-01'),
    ('Jane', 'Smith', 'jane.smith@example.com', '2023-09-01'),
    ('Jim', 'Beam', 'jim.beam@example.com', '2023-09-02');

Open IntelliJ and create a new Java project, ensuring that Maven is selected as your build system. You can connect to COOMP3005Assignment3Q1 repository and create a pull request or follow the
instructions outlined below.

In the pom.xml file, paste the following:

<dependencies>

        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <version>42.7.8</version>
        </dependency>

  </dependencies>

Lastly, create a Java class in src > main > java folder named 'StudentApp' and paste the StudentApp.java file. 

Before compiling and running the code please remember to update the following lines (lines 8-10):

    private static final String URL = "jdbc:postgresql://localhost:5432/database";
    private static final String USER = "username";
    private static final String PASSWORD = "password";

Replace database, username, and password with your actual database name, username, and password.

You will now be able to run the code.


Link to Documentation Video: https://1drv.ms/v/c/671b7aa3b3cef99e/EdZ3ri9fzs5Mo9jqloF1Ia0BiV8QdYV1qHmrRwUDtRdaog?e=Z7hZcO 
