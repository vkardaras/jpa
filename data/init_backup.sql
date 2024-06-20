CREATE TABLE IF NOT EXISTS student(
 id SERIAL NOT NULL PRIMARY KEY,
 name TEXT
 );

 CREATE TABLE IF NOT EXISTS course(
  id SERIAL NOT NULL PRIMARY KEY,
  name TEXT
  );

 CREATE TABLE IF NOT EXISTS enrollment(
  id SERIAL NOT NULL PRIMARY KEY,
  enrollmentDate DATE NOT NULL,
  student_id BIGINT REFERENCES student(id),
  course_id BIGINT REFERENCES student(id)
  );

  INSERT INTO student (id, name) VALUES (1, 'Alice');
  INSERT INTO student (id, name) VALUES (2, 'Bob');
  INSERT INTO student (id, name) VALUES (3, 'Charlie');
  INSERT INTO student (id, name) VALUES (4, 'Josh');

  INSERT INTO course (id, title) VALUES (1, 'Mathematics');
  INSERT INTO course (id, title) VALUES (2, 'Physics');
  INSERT INTO course (id, title) VALUES (3, 'Chemistry');

  -- Alice enrolls in Mathematics and Physics
  INSERT INTO enrollment (id, enrollmentDate, student_id, course_id) VALUES (1, '2023-10-10', 1, 1);
  INSERT INTO enrollment (id, enrollmentDate, student_id, course_id) VALUES (2, '2023-10-09', 1, 2);

  -- Bob enrolls in Physics
  INSERT INTO enrollment (id, enrollmentDate, student_id, course_id) VALUES (3, '2023-09-15', 2, 2);

  -- Charlie enrolls in Chemistry
  INSERT INTO enrollment (id, enrollmentDate, student_id, course_id) VALUES (4, '2023-08-20', 3, 3);