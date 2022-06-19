/* inside connection with root user
DROP USER 'homework'@'localhost';
CREATE USER 'homework'@'localhost' IDENTIFIED BY 'Ir0nh4ck3r!';
GRANT ALL PRIVILEGES ON homework3.* TO 'homework'@'localhost';
GRANT ALL PRIVILEGES ON homework3_test.* TO 'homework'@'localhost';
*/
DROP SCHEMA IF EXISTS homework3;
CREATE SCHEMA homework3;
USE homework3;

DROP TABLE IF EXISTS user;

CREATE TABLE user(
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(255),
	user_type VARCHAR(255),
    password_access VARCHAR(255),
    status VARCHAR(255),
    creation_date DATE,
    modification_date DATE,
    user_creation VARCHAR(255),
    user_modification VARCHAR(255),
	PRIMARY KEY(id)
);

INSERT INTO user (name, user_type, password_access, status, 
creation_date, modification_date, user_creation, user_modification) VALUES
('admin','ADMIN',"$2a$10$MSzkrmfd5ZTipY0XkuCbAejBC9g74MAg2wrkeu8/m1wQGXDihaX3e", "Active",
'2021-10-26','0001-01-01','Query',''),
('DirectorTest','DIRECTOR',"$2a$10$MSzkrmfd5ZTipY0XkuCbAejBC9g74MAg2wrkeu8/m1wQGXDihaX3e", "Active",
'2021-10-26','0001-01-01','Query',''),
('SalesTest','SALESREPRESENTATIVE',"$2a$10$MSzkrmfd5ZTipY0XkuCbAejBC9g74MAg2wrkeu8/m1wQGXDihaX3e", "Active",
'2021-10-26','0001-01-01','Query','');

DROP TABLE IF EXISTS role;

CREATE TABLE role (
    id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    name VARCHAR(255),
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES user (id)
);

INSERT INTO role (name, user_id) VALUES
('ADMIN',1),
('DIRECTOR',2),
('SALESREPRESENTATIVE',3);

DROP TABLE IF EXISTS leader;

CREATE TABLE leader(
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(255),
    user_id INT,
	phone_number VARCHAR(255),
    email VARCHAR(255),
    company_name VARCHAR(255),
    creation_date DATE,
    modification_date DATE,
    user_creation VARCHAR(255),
    user_modification VARCHAR(255),
	PRIMARY KEY(id),
    FOREIGN KEY(user_id) REFERENCES user(id)
);

DROP TABLE IF EXISTS account;

CREATE TABLE account(
	id INT NOT NULL AUTO_INCREMENT,
	industry VARCHAR(255),
	employee_count INT,
    city VARCHAR(255),
    country VARCHAR(255),
    creation_date DATE,
    modification_date DATE,
    user_creation VARCHAR(255),
    user_modification VARCHAR(255),
	PRIMARY KEY(id)
);

DROP TABLE IF EXISTS contact;

CREATE TABLE contact(
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(255),
    user_id INT,
	phone_number VARCHAR(255),
    email VARCHAR(255),
    company_name VARCHAR(255),
    creation_date DATE,
    modification_date DATE,
    user_creation VARCHAR(255),
    user_modification VARCHAR(255),
    account_id INT,
	PRIMARY KEY(id),
    FOREIGN KEY(user_id) REFERENCES user(id),
    FOREIGN KEY(account_id) REFERENCES account(id)
);

DROP TABLE IF EXISTS opportunity;

CREATE TABLE opportunity(
	id INT NOT NULL AUTO_INCREMENT,
    product VARCHAR(255),
    quantity INT,
    status VARCHAR(255),
    user_id INT,
	contact_id INT,
	account_id INT,
    creation_date DATE,
    modification_date DATE,
    user_creation VARCHAR(255),
    user_modification VARCHAR(255),
	PRIMARY KEY(id),
    FOREIGN KEY(user_id) REFERENCES user(id),
    FOREIGN KEY(contact_id) REFERENCES contact(id),
    FOREIGN KEY(account_id) REFERENCES account(id)
);
