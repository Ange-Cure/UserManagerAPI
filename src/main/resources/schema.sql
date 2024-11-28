CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(255) NOT NULL,
    birthdate DATE NOT NULL,
    country_of_residence VARCHAR(255) NOT NULL,
    phone_number VARCHAR(15) DEFAULT NULL,
    gender ENUM('Male', 'Female', 'Other', 'Prefer not to say') DEFAULT NULL
);
