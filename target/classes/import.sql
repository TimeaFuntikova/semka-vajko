CREATE TABLE IF NOT EXISTS users (
                                     user_id SERIAL PRIMARY KEY,
                                     username VARCHAR(255) NOT NULL,
                                     password VARCHAR(255) NOT NULL,
                                     hashedPassword VARCHAR(255) NOT NULL,
                                     email VARCHAR(255) NOT NULL,
                                     role_name varchar(50)
);

CREATE TABLE IF NOT EXISTS enrollments (
                                           enrollment_id SERIAL PRIMARY KEY,
                                           user_id INT,
                                           course_id INT,
                                           enroll_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS courses (
                                       course_id SERIAL PRIMARY KEY,
                                       title VARCHAR(255) NOT NULL,
                                       description TEXT,
                                       instructor_id INT
);

CREATE TABLE IF NOT EXISTS lessons (
                                       lesson_id SERIAL PRIMARY KEY,
                                       title VARCHAR(255) NOT NULL,
                                       content TEXT,
                                       course_id INT
);

ALTER TABLE enrollments
    ADD FOREIGN KEY (user_id) REFERENCES users(user_id),
    ADD FOREIGN KEY (course_id) REFERENCES courses(course_id);

ALTER TABLE courses
    ADD FOREIGN KEY (instructor_id) REFERENCES users(user_id);

ALTER TABLE lessons
    ADD FOREIGN KEY (course_id) REFERENCES courses(course_id);




