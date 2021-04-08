DROP DATABASE IF EXISTS mediscreen_patientadmin_oc_mc;

/** PRODUCTION DB **/

-- CREATE Database
    CREATE DATABASE IF NOT EXISTS mediscreen_patientadmin_oc_mc;
    USE mediscreen_patientadmin_oc_mc;
    SET autocommit=1;

-- CREATE Tables
    -- Users Table
        CREATE TABLE patient (
            id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
            firstname VARCHAR(60),
            lastname VARCHAR(60),
            sexe CHAR(1) NOT NULL,
            birthday DATE,
            address VARCHAR(255),
            email VARCHAR(100),
            phone VARCHAR(50),
            country VARCHAR(100)
        )
        ENGINE=INNODB
        AUTO_INCREMENT=0;

-- INSERT data
    LOCK TABLES patient WRITE;

    INSERT INTO patient(firstname, lastname, sexe, birthday, address, email, phone, country)
    VALUES
        ('juanita', 'emard', 'F', '1995-01-06', '1 Brookside St', 'juanita.emard@email.com', '100-222-3333', 'France'),
        ('alexane', 'collins', 'F', '1989-11-22', '2 High St','alexane.collins@email.com', '200-333-4444', 'United Kingdom of Great Britain and Northern Ireland'),
        ('ford', 'bashirian', 'M', '1997-09-13', '3 Club Road', 'ford.bashirian@email.com', '300-444-5555', 'United States of America'),
        ('katrine', 'lehner', 'F', '2000-05-05', '4 Valley Dr', 'katrine.lehner@email.com', '400-555-6666', 'Finland');

  UNLOCK TABLES;