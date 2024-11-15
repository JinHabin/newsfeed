CREATE TABLE USERS {
    userId INT NOT NULL PRIMARY KEY,
    username VARCHAR(20) NOT NULL,
    email VARCHAR(50) NOT NULL,
    password VARCHAR(20) NOT NULL,
    createAt TIMESTAMP NOT NULL,
    updateAt TIMESTAMP
    };

CREATE TABLE SCHEDULES {
    id INT NOT NULL,
    userId INT NOT NULL,
    title VARCHAR(20) NOT NULL,
    contents VARCHAR(50),
    createAt TIMESTAMP NOT NULL,
    updateAt TIMESTAMP,
    password VARCHAR(20) NOT NULL,
    FOREIGN KEY (userId) REFERENCES USERS (userId)
    };

INSERT INTO USERS (username, email, password, createAt) VALUES ("HABIN", "example@gmail.com", "abc123", current_timestamp());

INSERT INTO SCHEDULES (userId, title, contents, createAt, password) VALUES (1, "과제중간점검", "14:00까지", current_timestamp(), '2024-11-01' "abcd1234");
INSERT INTO SCHEDULES (userId, title, contents, createdAt, password) VALUES (1, "과제제출", "12:00까지", current_timestamp(), '2024-11-08', "abcd1234");

SELECT * FROM SCHEDULES;

SELECT * FROM SCHEDULES WHERE id = 1;

UPDATE SCHEDULES SET content = "금 14:00까지", updatedAt = current_timestamp() WHERE id = 2;

DELETE * FROM SCHEDULES WHERE id = 1;