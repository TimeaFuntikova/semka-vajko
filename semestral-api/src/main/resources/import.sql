-- User Table
CREATE TABLE IF NOT EXISTS app_user (
                                        user_id BIGSERIAL PRIMARY KEY,
                                        username VARCHAR(255) NOT NULL,
    profile_name VARCHAR(255) NOT NULL,
    password VARCHAR(255),
    salt BYTEA NOT NULL,
    hashed_password VARCHAR(255) NOT NULL,
    profile_photo VARCHAR(255),
    date_joined DATE,
    last_login TIMESTAMP,
    user_role VARCHAR(255)
    );

-- Course Table
CREATE TABLE IF NOT EXISTS course (
                                      course_id SERIAL PRIMARY KEY,
                                      title VARCHAR(255) NOT NULL,
    description TEXT,
    category VARCHAR(255),
    difficulty_level VARCHAR(255),
    thumbnail VARCHAR(255),
    date_created DATE,
    last_updated DATE
    );

-- Enrollment Table
CREATE TABLE IF NOT EXISTS enrollment (
                                          enrollment_id SERIAL PRIMARY KEY,
                                          user_id BIGINT REFERENCES app_user(user_id),
    course_id INT REFERENCES course(course_id),
    enrollment_date DATE,
    progress INT,
    completion_status VARCHAR(255)
    );

-- Forum Table
CREATE TABLE IF NOT EXISTS forum (
                                     forum_id SERIAL PRIMARY KEY,
                                     course_id INT REFERENCES course(course_id),
    title VARCHAR(255) NOT NULL,
    description TEXT
    );

-- Post Table
CREATE TABLE IF NOT EXISTS post (
                                    post_id SERIAL PRIMARY KEY,
                                    forum_id INT REFERENCES forum(forum_id),
    user_id BIGINT REFERENCES app_user(user_id),
    content TEXT,
    date_posted DATE
    );

-- Certificate Table
CREATE TABLE IF NOT EXISTS certificate (
                                           certificate_id SERIAL PRIMARY KEY,
                                           user_id BIGINT REFERENCES app_user(user_id),
    course_id INT REFERENCES course(course_id),
    date_issued DATE
    );

-- Lesson Table
CREATE TABLE IF NOT EXISTS lesson (
                                      lesson_id SERIAL PRIMARY KEY,
                                      course_id INT REFERENCES course(course_id),
    title VARCHAR(255) NOT NULL,
    content TEXT,
    video_url VARCHAR(255),
    additional_resources TEXT,
    sequence INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    quiz_question VARCHAR(255),
    answer_1 VARCHAR(255),
                                      answer_2 VARCHAR(255),
                                      answer_3 VARCHAR(255),
                                      correct_answer VARCHAR(255)

    );

ALTER TABLE APP_COURSE
    ADD COLUMN created_by_user_id bigint,
ADD FOREIGN KEY (created_by_user_id) REFERENCES APP_USER(user_id);

-- Image Table
CREATE TABLE IF NOT EXISTS app_image_data (
                                    user_id BIGINT REFERENCES app_user(user_id),
                                    image_id SERIAL PRIMARY KEY,
                                    name VARCHAR(255),
                                    type VARCHAR(255)
);

ALTER TABLE app_image_data
    ADD COLUMN image_data bytea;

DELETE * from app_enrollment where user_id = 1;

ALTER TABLE app_course
    ADD CONSTRAINT fk_user_username
        FOREIGN KEY (created_by_user_id)
            REFERENCES app_user(user_id)
            ON DELETE CASCADE;