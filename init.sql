CREATE DATABASE db_recruitment;

USE db_recruitment;

CREATE TABLE users (
  id BIGINT AUTO_INCREMENT NOT NULL,
  username VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  number VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  active BIT(1) NOT NULL,
  `role` VARCHAR(255) NOT NULL,
  CONSTRAINT pk_users PRIMARY KEY (id)
);


CREATE TABLE resumes (
  id BIGINT AUTO_INCREMENT NOT NULL,
  fullName VARCHAR(255) NULL,
  birthDate date NULL,
  age INT NULL,
  city VARCHAR(255) NULL,
  skills VARCHAR(255) NULL,
  education VARCHAR(255) NULL,
  otherInfo VARCHAR(255) NULL,
  statusResume VARCHAR(255) NULL,
  user_id BIGINT NULL,
  CONSTRAINT pk_resumes PRIMARY KEY (id),
  CONSTRAINT FK_RESUMES_ON_USER FOREIGN KEY (user_id) REFERENCES users(id)
);


CREATE TABLE responsesToResume (
  id BIGINT AUTO_INCREMENT NOT NULL,
  user_id BIGINT NOT NULL,
  resume_id BIGINT NOT NULL,
  responseDate DATE NOT NULL,
  CONSTRAINT pk_responsestoresume PRIMARY KEY (id),
  CONSTRAINT FK_RESPONSESTORESUME_ON_RESUME FOREIGN KEY (resume_id) REFERENCES resumes(id),
  CONSTRAINT FK_RESPONSESTORESUME_ON_USER FOREIGN KEY (user_id) REFERENCES users(id)
);


CREATE TABLE responses (
  id BIGINT AUTO_INCREMENT NOT NULL,
  vacancy_id BIGINT NOT NULL,
  user_id BIGINT NOT NULL,
  currentDateTime DATETIME NOT NULL,
  statusResponse VARCHAR(255) NOT NULL,
  CONSTRAINT pk_responses PRIMARY KEY (id),
  CONSTRAINT FK_RESPONSES_ON_USER FOREIGN KEY (user_id) REFERENCES users(id),
  CONSTRAINT FK_RESPONSES_ON_VACANCY FOREIGN KEY (vacancy_id) REFERENCES vacancy(id)
);


CREATE TABLE metricsReportingHistory (
  id BIGINT AUTO_INCREMENT NOT NULL,
  startDate DATE NOT NULL,
  endDate DATE NOT NULL,
  countVacancies INT NOT NULL,
  countResponses INT NOT NULL,
  countSelfDanial INT NOT NULL,
  countRelevantResponse INT NOT NULL,
  countRefusalEmployer INT NOT NULL,
  countInvitation INT NOT NULL,
  countFoundResume INT NOT NULL,
  userId BIGINT NOT NULL,
  CONSTRAINT pk_metricsreportinghistory PRIMARY KEY (id),
  CONSTRAINT FK_METRICSREPORTINGHISTORY_ON_USER FOREIGN KEY (userId) REFERENCES users(id)
);

CREATE TABLE messages (
  id BIGINT AUTO_INCREMENT NOT NULL,
   content VARCHAR(255) NULL,
   sender BIGINT NULL,
   receiver BIGINT NULL,
   date datetime NULL,
   CONSTRAINT pk_messages PRIMARY KEY (id)
);

CREATE TABLE vacancy (
  id BIGINT AUTO_INCREMENT NOT NULL,
  name_vacancy VARCHAR(255) NOT NULL,
  description_vacancy VARCHAR(255) NOT NULL,
  conditions_and_requirements VARCHAR(255) NOT NULL,
  wage INT NULL,
  schedule VARCHAR(255) NULL,
  status_vacancy VARCHAR(255) NOT NULL,
  createDateTime DATETIME NOT NULL,
  user_id BIGINT NOT NULL,
  CONSTRAINT pk_vacancy PRIMARY KEY (id),
  CONSTRAINT FK_VACANCY_ON_USER FOREIGN KEY (user_id) REFERENCES users(id)
);

INSERT INTO users (username, email, number, password, active, role) VALUES
  ('Admin', 'admin@mail.ru', '89209568731', 'root', 1, 'ROLE_ADMIN'),
  ('Moder', 'moder@mail.ru', '89206295073', 'root', 1, 'ROLE_MODER');
