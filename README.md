# HRM Application

This is a Human Resource Management (HRM) application built with Java and Maven.

## Prerequisites

- Java 17 or higher
- Maven
- IntelliJ IDEA
- XAMPP

## Steps to Import the SQL File

1. Start the XAMPP control panel and start the Apache and MySQL modules.
2. Open your web browser and navigate to `http://localhost/phpmyadmin`.
3. Click on the `Databases` tab and create a new database. Name it as per your preference.
4. After creating the database, click on it to select it. Then click on the `Import` tab.
5. Click on the `Choose File` button and navigate to the location of the `csd214_lab3_johncarlo.sql` file. Select the file and click `Open`.
6. Click on the `Go` button at the bottom of the page to start the import process.

After the import process is complete, you should see the tables and data from the `csd214_lab3_johncarlo.sql` file in your newly created database.

## Importing to IntelliJ IDEA

1. Open IntelliJ IDEA.
2. Select `File` -> `New` -> `Project from Version Control` -> `Git`.
3. Enter the URL of the repository and click `Clone`.
4. Select `Yes` to open the project.
5. Select `Import project from external model` -> `Maven` and click `Next`.
6. Follow the prompts to finish the import process.

## Running the Application

1. Open the project in IntelliJ IDEA.
2. Navigate to the `src/main/java` directory in the Project Explorer.
3. Find the main application class `HRMgmtApplication.java`.
4. Right-click on the file and select `Run 'HRMgmtApplication.main()'`.

## Running Tests

1. Navigate to the `src/test/java` directory in the Project Explorer.
2. Right-click on the directory and select `Run 'All Tests'`.

## Building the Application

1. Open a terminal in the project root directory.
2. Run `mvn clean install` to build the application. The built JAR file will be located in the `target` directory.
