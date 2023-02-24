CREATE SCHEMA IF NOT EXISTS testdb DEFAULT CHARACTER SET utf8 ;
USE testdb;
CREATE TABLE IF NOT EXISTS testdb.users(id INT NOT NULL AUTO_INCREMENT,
                      name VARCHAR(100) NOT NULL,
                      email VARCHAR(40),
                      age int,
                      PRIMARY KEY (id));
INSERT INTO testdb.users(name, email, age) values('Andre', 'meirezende@hotmail.com', 33),
                                           ('Test', 'test@zip.com', 0);
