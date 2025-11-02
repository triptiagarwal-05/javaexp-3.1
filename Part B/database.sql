CREATE DATABASE companydb;

USE companydb;

CREATE TABLE Employee (
    EmpID INT PRIMARY KEY,
    Name VARCHAR(50),
    Salary DOUBLE
);

INSERT INTO Employee VALUES
(101, 'Nandini', 55000),
(102, 'Rahul', 60000),
(103, 'Priya', 50000);
