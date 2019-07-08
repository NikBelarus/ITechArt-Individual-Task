DROP DATABASE IF EXISTS daschynski;
CREATE DATABASE daschynski CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE daschynski;

/*Таблица Contact*/
CREATE TABLE contact(id INT UNSIGNED UNIQUE PRIMARY KEY NOT NULL AUTO_INCREMENT, name VARCHAR(20) NOT NULL,
                     surname VARCHAR(20) NOT NULL, fathers_name VARCHAR(20) NOT NULL, birthdate DATE NOT NULL, sex ENUM('man', 'woman') NOT NULL,
                     citizenship VARCHAR(20) NOT NULL, family_Status ENUM('married', 'not married', 'diverced') NOT NULL,
                     web_site VARCHAR(40) UNIQUE NOT NULL, email VARCHAR(40) UNIQUE NOT NULL, current_workspace VARCHAR(200) NOT NULL, photo VARCHAR(20)) ENGINE=InnoDB;

/*Таблица Phone*/
CREATE TABLE phone(id INT UNSIGNED UNIQUE PRIMARY KEY NOT NULL AUTO_INCREMENT, contact_id INT UNSIGNED NOT NULL,
                   country_code SMALLINT NOT NULL, operator_code SMALLINT NOT NULL, number VARCHAR(7) NOT NULL,
                   type ENUM('mobile', 'home number') NOT NULL, comment VARCHAR(1000), FOREIGN KEY (contact_id) REFERENCES Contact(id) ON DELETE CASCADE) ENGINE=InnoDB;

/*Таблица Address*/
CREATE TABLE address(id INT UNSIGNED UNIQUE PRIMARY KEY NOT NULL AUTO_INCREMENT, contact_id INT UNSIGNED UNIQUE NOT NULL,
                     country VARCHAR(40) NOT NULL, city VARCHAR(40) NOT NULL, street VARCHAR(40) NOT NULL,
                     home_num VARCHAR(10) NOT NULL, apartment VARCHAR(10), postcode VARCHAR(20) NOT NULL, FOREIGN KEY (contact_id)
REFERENCES Contact(id) ON DELETE CASCADE) ENGINE=InnoDB;

/*Таблица Attachment*/
CREATE TABLE attachment(id INT UNSIGNED UNIQUE PRIMARY KEY NOT NULL AUTO_INCREMENT, contact_id INT UNSIGNED NOT NULL,
                        name VARCHAR(100) NOT NULL, upload_date VARCHAR(20) NOT NULL, comment VARCHAR(1000), FOREIGN KEY (contact_id)
REFERENCES Contact(id) ON DELETE CASCADE) ENGINE=InnoDB;

/*------------------------------------------------------------------------------------------------------------------*/

INSERT INTO contact (name, surname, fathers_name, birthdate, sex, citizenship, family_Status,
                     web_site, email, current_workspace, photo) VALUES ('Никита', 'Дащинский', 'Олегович',
                      '19990416', 'man', 'belarusian', 'not married', 'vk1', 'dashchynskinikita@gmail.com', 'ITechArt', NULL);
INSERT INTO contact (name, surname, fathers_name, birthdate, sex, citizenship, family_Status,
                     web_site, email, current_workspace, photo) VALUES ('Katsiaryna', 'Patsiuk', 'Jurevna',
                      '19980915', 'woman', 'belarusian', 'not married', 'facebook', 'katepatsiuk@gmail.com', 'Sosedi', NULL);
INSERT INTO contact (name, surname, fathers_name, birthdate, sex, citizenship, family_Status,
                     web_site, email, current_workspace, photo) VALUES ('Антон', 'Бакатович', 'Олегович',
                      '20000915', 'man', 'belarusian', 'married', 'vk2', 'anton@gmail.com', 'Exadel', NULL);
INSERT INTO contact (name, surname, fathers_name, birthdate, sex, citizenship, family_Status,
                     web_site, email, current_workspace, photo) VALUES ('Геннадий', 'Богданович', 'Юрьевич',
                      '19990715', 'man', 'belarusian', 'not married', 'vk3', 'gena@gmail.com', 'IT', NULL);
INSERT INTO contact (name, surname, fathers_name, birthdate, sex, citizenship, family_Status,
                     web_site, email, current_workspace, photo) VALUES ('Дарья', 'Седнева', 'Юрьевна',
                      '19970919', 'woman', 'belarusian', 'diverced', 'vk4', 'dasha@gmail.com', 'Sosedi', NULL);
INSERT INTO contact (name, surname, fathers_name, birthdate, sex, citizenship, family_Status,
                     web_site, email, current_workspace, photo) VALUES ('Николай', 'Бадылевич', 'Иванович',
                      '19991019', 'man', 'belarusian', 'not married', 'vk5', 'kolya@gmail.com', 'Atlant', NULL);
INSERT INTO contact (name, surname, fathers_name, birthdate, sex, citizenship, family_Status,
                     web_site, email, current_workspace, photo) VALUES ('Максим', 'Палецкий', 'Юрьевич',
                      '19980319', 'man', 'belarusian', 'not married', 'vk6', 'maks@gmail.com', 'Univer', NULL);
INSERT INTO contact (name, surname, fathers_name, birthdate, sex, citizenship, family_Status,
                     web_site, email, current_workspace, photo) VALUES ('Илья', 'Бакатович', 'Иванович',
                      '19990319', 'man', 'belarusian', 'diverced', 'vk7', 'ilya@gmail.com', 'Epam', NULL);
INSERT INTO contact (name, surname, fathers_name, birthdate, sex, citizenship, family_Status,
                     web_site, email, current_workspace, photo) VALUES ('Роман', 'Коба', 'Юрьевич',
                      '19991119', 'man', 'belarusian', 'married', 'vk8', 'roman@gmail.com', 'Epam', NULL);
INSERT INTO contact (name, surname, fathers_name, birthdate, sex, citizenship, family_Status,
                     web_site, email, current_workspace, photo) VALUES ('Вадим', 'Ермаков', 'Олегович',
                      '19990510', 'man', 'belarusian', 'not married', 'vk9', 'vadim@gmail.com', 'Yandex', NULL);


INSERT INTO address (contact_id, country, city, street, home_num, apartment, postcode) VALUES
  (1, 'Belarus', 'Zaslavl', 'Gonoles', '8', NULL, 223036);
INSERT INTO address (contact_id, country, city, street, home_num, apartment, postcode) VALUES
  (2, 'Belarus', 'Minsk', 'Zhukovskaya', '8/1', '43', 220007);
INSERT INTO address (contact_id, country, city, street, home_num, apartment, postcode) VALUES
  (3, 'Belarus', 'Minsk', 'str', '8a', '43', 220010);
INSERT INTO address (contact_id, country, city, street, home_num, apartment, postcode) VALUES
  (4, 'Belarus', 'Minsk', 'Zhukovskaya', '10', '45', 220015);
INSERT INTO address (contact_id, country, city, street, home_num, apartment, postcode) VALUES
  (5, 'Belarus', 'Minsk', 'Zhukovskaya', '8', NULL, 220040);
INSERT INTO address (contact_id, country, city, street, home_num, apartment, postcode) VALUES
  (6, 'Belarus', 'Minsk', 'str2', '100a', '40', 220300);
INSERT INTO address (contact_id, country, city, street, home_num, apartment, postcode) VALUES
  (7, 'Belarus', 'Minsk', 'str3', '10a', '12', 220007);
INSERT INTO address (contact_id, country, city, street, home_num, apartment, postcode) VALUES
  (8, 'Belarus', 'Gomel', 'str4', '1', NULL , 223040);
INSERT INTO address (contact_id, country, city, street, home_num, apartment, postcode) VALUES
  (9, 'Belarus', 'Minsk', 'str5', '15', '56', 220048);
INSERT INTO address (contact_id, country, city, street, home_num, apartment, postcode) VALUES
  (10, 'Belarus', 'Minsk', 'str6', '16', '48', 220039);

INSERT INTO phone (contact_id, country_code, operator_code, number, type, comment) VALUES
  (1, 375, 29, '3457284', 'mobile', 'This is my phone!');
INSERT INTO phone (contact_id, country_code, operator_code, number, type, comment) VALUES
  (2, 375, 44, '7247770', 'mobile', 'I am Kate');
INSERT INTO phone (contact_id, country_code, operator_code, number, type, comment) VALUES
  (2, 375, 25, '7090883', 'mobile', 'This is my mobile phone!');
INSERT INTO phone (contact_id, country_code, operator_code, number, type, comment) VALUES
  (3, 375, 25, '1111111', 'mobile', 'This is my mobile phone!');
INSERT INTO phone (contact_id, country_code, operator_code, number, type, comment) VALUES
  (4, 375, 25, '2222222', 'mobile', 'This is my mobile phone!');
INSERT INTO phone (contact_id, country_code, operator_code, number, type, comment) VALUES
  (5, 375, 17, '3333333', 'home number', 'This is my home number phone!');
INSERT INTO phone (contact_id, country_code, operator_code, number, type, comment) VALUES
  (6, 375, 44, '4444444', 'mobile', 'This is my mobile phone!');
INSERT INTO phone (contact_id, country_code, operator_code, number, type, comment) VALUES
  (7, 375, 25, '5555555', 'mobile', 'This is my mobile phone!');
INSERT INTO phone (contact_id, country_code, operator_code, number, type, comment) VALUES
  (8, 375, 25, '6666666', 'mobile', 'This is my mobile phone!');
INSERT INTO phone (contact_id, country_code, operator_code, number, type, comment) VALUES
  (9, 375, 25, '7777777', 'mobile', 'This is my mobile phone!');
INSERT INTO phone (contact_id, country_code, operator_code, number, type, comment) VALUES
  (10, 375, 25, '8888888', 'mobile', 'This is my mobile phone!');

/*------------------------------------------------------------------------------------------------------------------*/

CREATE USER IF NOT EXISTS 'daschynski'@'localhost' IDENTIFIED BY '123';
GRANT ALL PRIVILEGES ON daschynski . * TO 'daschynski'@'localhost';
FLUSH PRIVILEGES;