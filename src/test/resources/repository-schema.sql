DROP TABLE IF EXISTS students_courses;
DROP TABLE IF EXISTS students;

CREATE TABLE students (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  kana_name VARCHAR(50),
  nickname VARCHAR(50),
  email VARCHAR(255) NOT NULL,
  area VARCHAR(50) NOT NULL,
  age INT NOT NULL,
  sex VARCHAR(20) NOT NULL,
  remark VARCHAR(255),
  isDeleted BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE students_courses (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  student_id BIGINT NOT NULL,
  course_name VARCHAR(100) NOT NULL,
  course_start_at DATE,
  course_end_at DATE,
  CONSTRAINT fk_students_courses_student
    FOREIGN KEY (student_id) REFERENCES students(id)
);

