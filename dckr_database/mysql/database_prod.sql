DROP DATABASE IF EXISTS mediscreen_patientadmin_prod_oc_mc;

/** PRODUCTION DB **/

-- CREATE Database
    CREATE DATABASE IF NOT EXISTS mediscreen_patientadmin_prod_oc_mc;
    USE mediscreen_patientadmin_prod_oc_mc;
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

    INSERT INTO patient(id, firstname, lastname, sexe, birthday, address, email, phone, country)
    VALUES
        ('1', 'lucas', 'ferguson', 'M', '1968-06-22', '2 Warren Street', 'ferguson.lucas@email.com', '387-866-1399', 'Italy'),
        ('2', 'pippa', 'rees', 'F', '1952-09-27', '745 West Valley Farms Drive', 'rees.pippa@email.com', '628-423-0993', 'Spain'),
        ('3', 'edward', 'arnold', 'M', '1952-11-11', '599 East Garden Ave', 'arnold.edward@email.com', '123-727-2779', 'Italie'),
        ('4', 'anthony', 'sharp', 'M', '1946-11-26', '894 Hall Street', 'sharp.anthony@email.com', '451-761-8383', 'France'),
        ('5', 'wendy', 'ince', 'F', '1958-06-29', '4 Southampton Road', 'ince.wendy@email.com', '802-911-9975', 'Italy'),
        ('6', 'tracey', 'ross', 'F', '1949-12-07', '40 Sulphur Springs Dr', 'ross.tracey@email.com', '131-396-5049', 'Spain'),
        ('7', 'claire', 'wilson', 'F', '1966-12-31', '12 Cobblestone St', 'wilson.claire@email.com', '300-452-1091', 'France'),
        ('8', 'max', 'buckland', 'M', '1945-06-24', '193 Vale St', 'buckland.max@email.com', '833-534-0864', 'United Kingdom of Great Britain and Northern Ireland'),
        ('9', 'natalie', 'clark', 'F', '1964-06-18', '12 Beechwood Road', 'clark.natalie@email.com', '241-467-9197', 'United Kingdom of Great Britain and Northern Ireland'),
        ('10', 'piers', 'bailey', 'M', '1959-06-28', '1202 Bumble Dr', 'bailey.piers@email.com', '747-815-0557', 'France'),
        ('11', 'juanita', 'emard', 'F', '1995-01-06', '1 Brookside St', 'juanita.emard@email.com', '100-222-3333', 'France'),
        ('12', 'alexane', 'collins', 'F', '1989-11-22', '2 High St','alexane.collins@email.com', '200-333-4444', 'United Kingdom of Great Britain and Northern Ireland'),
        ('13', 'ford', 'bashirian', 'M', '1997-09-13', '3 Club Road', 'ford.bashirian@email.com', '300-444-5555', 'United States of America'),
        ('14', 'katrine', 'lehner', 'F', '2000-05-05', '4 Valley Dr', 'katrine.lehner@email.com', '400-555-6666', 'Finland');

  UNLOCK TABLES;