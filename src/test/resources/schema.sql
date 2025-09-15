CREATE TABLE IF NOT EXISTS students (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL,
  kana_name VARCHAR(50) NOT NULL,
  nickname VARCHAR(50) ,
  email VARCHAR(50) NOT NULL,
  area VARCHAR(50) ,
  age INT ,
  sex VARCHAR(10) ,
  remark VARCHAR(45) ,
  is_deleted Boolean,
  PRIMARY KEY (`id`)
);

-- students_courses テーブルの修正
CREATE TABLE IF NOT EXISTS students_course (
  id INT NOT NULL  AUTO_INCREMENT,
  student_id VARCHAR(36) NOT NULL,
  course_name VARCHAR(50) NOT NULL,
  course_start_at TIMESTAMP ,
  course_end_at TIMESTAMP,
  PRIMARY KEY (`id`)
);
