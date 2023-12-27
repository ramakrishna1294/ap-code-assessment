-- User creation 

CREATE USER 'apAssessmentUser'@'localhost' IDENTIFIED BY '@ssessment';

-- Createing Database or Schema

CREATE DATABASE IF NOT EXISTS sport_management;

-- The below Commands is to Grant all PRIVILEGES to sport_management

GRANT ALL PRIVILEGES ON sport_management.* TO 'apAssessmentUser'@'localhost';


-- Creating a tables;


CREATE TABLE players (
     id int NOT NULL AUTO_INCREMENT,
     email CHAR(250) NOT NULL ,
     level int check (level between 1 and 10 ) NOT NULL,
     age int NOT NULL,  
     gender Varchar(10) check (gender in ('Female','Male')) NOT NULL,
     UNIQUE (email),
     PRIMARY KEY (id)
);


CREATE TABLE sports (
     id int NOT NULL AUTO_INCREMENT,
     name CHAR(20) NOT NULL,
     UNIQUE (name),
     PRIMARY KEY (id)
);


CREATE TABLE relations (
     id int NOT NULL AUTO_INCREMENT,
     sport_Id int NOT NULL,
     player_Id int  NOT NULL,
     PRIMARY KEY (id),
     FOREIGN KEY (sport_Id) REFERENCES sports(id),
     FOREIGN KEY (player_Id) REFERENCES players(id)
);


--insert into players;
insert into players (email, level, age, gender) values ('rama@test.com', 9, 25, 'male');
insert into players (email, level, age, gender) values ('sample@test.com', 8, 25, 'Female');
insert into players (email, level, age, gender) values ('fun@test.com', 6, 22, 'male');
insert into players (email, level, age, gender) values ('test@test.com', 7, 29, 'Female');
insert into players (email, level, age, gender) values ('player@test.com', 5, 21, 'male');
insert into players (email, level, age, gender) values ('player1@test.com', 3, 21, 'male');
insert into players (email, level, age, gender) values ('player2@test.com', 4, 21, 'Female');
insert into players (email, level, age, gender) values ('player3@test.com', 5, 21, 'Female');
insert into players (email, level, age, gender) values ('player4@test.com', 7, 21, 'male');
insert into players (email, level, age, gender) values ('player5@test.com', 2, 21, 'Female');
insert into players (email, level, age, gender) values ('player6@test.com', 4, 21, 'Female');
insert into players (email, level, age, gender) values ('player7@test.com', 5, 21, 'Female');
insert into players (email, level, age, gender) values ('player8@test.com', 6, 21, 'Female');
insert into players (email, level, age, gender) values ('player9@test.com', 6, 21, 'Female');
insert into players (email, level, age, gender) values ('player10@test.com', 7, 21, 'Female');
insert into players (email, level, age, gender) values ('player11test.com', 5, 21, 'Female');
insert into players (email, level, age, gender) values ('player12test.com', 6, 21, 'Female');
insert into players (email, level, age, gender) values ('player13@test.com', 9, 21, 'Female');



--Sample to check the conditions on the column working 

--level should only accept 1~10
insert into players (email, level, age, gender) values ('rama@test.com', 12, 25, 'male');

--gender should only accept 'Female','Male'

insert into players (email, level, age, gender) values ('rama@test.com', 9, 25, 'notmale');

--insert into sports;

insert into sports (name) values ('tennis');
insert into sports (name) values ('soccer');
insert into sports (name) values ('basketball');
insert into sports (name) values ('Volleyball');
insert into sports (name) values ('Golf');
insert into sports (name) values ('Hockey');
insert into sports (name) values ('Volleyball');
insert into sports (name) values ('Football');
insert into sports (name) values ('NFL');
insert into sports (name) values ('Table Tennis');


--insert into Relations;
insert into Relations (sport_Id, player_Id) values ((select id from sports where name = 'tennis'), (select id from players where email = 'rama@test.com'));
insert into Relations (sport_Id, player_Id) values ((select id from sports where name = 'tennis'), (select id from players where email = 'sample@test.com'));
insert into Relations (sport_Id, player_Id) values ((select id from sports where name = 'soccer'), (select id from players where email = 'sample@test.com'));
insert into Relations (sport_Id, player_Id) values ((select id from sports where name = 'basketball'), (select id from players where email = 'sample@test.com'));
insert into Relations (sport_Id, player_Id) values ((select id from sports where name = 'cricket'), (select id from players where email = 'rama@test.com'));
insert into Relations (sport_Id, player_Id) values ((select id from sports where name = 'Golf'), (select id from players where email = 'rama@test.com'));
insert into Relations (sport_Id, player_Id) values ((select id from sports where name = 'NFL'), (select id from players where email = 'player13@test.com'));
insert into Relations (sport_Id, player_Id) values ((select id from sports where name = 'Golf'), (select id from players where email = 'player13@test.com'));
insert into Relations (sport_Id, player_Id) values ((select id from sports where name = 'NFL'), (select id from players where email = 'player12@test.com'));
insert into Relations (sport_Id, player_Id) values ((select id from sports where name = 'NFL'), (select id from players where email = 'player11@test.com'));
insert into Relations (sport_Id, player_Id) values ((select id from sports where name = 'NFL'), (select id from players where email = 'player10@test.com'));
insert into Relations (sport_Id, player_Id) values ((select id from sports where name = 'NFL'), (select id from players where email = 'player9@test.com'));
insert into Relations (sport_Id, player_Id) values ((select id from sports where name = 'NFL'), (select id from players where email = 'player8@test.com'));
insert into Relations (sport_Id, player_Id) values ((select id from sports where name = 'NFL'), (select id from players where email = 'player7@test.com'));
insert into Relations (sport_Id, player_Id) values ((select id from sports where name = 'NFL'), (select id from players where email = 'player6@test.com'));
insert into Relations (sport_Id, player_Id) values ((select id from sports where name = 'NFL'), (select id from players where email = 'player5@test.com'));
insert into Relations (sport_Id, player_Id) values ((select id from sports where name = 'NFL'), (select id from players where email = 'player4@test.com'));
insert into Relations (sport_Id, player_Id) values ((select id from sports where name = 'NFL'), (select id from players where email = 'player3@test.com'));
insert into Relations (sport_Id, player_Id) values ((select id from sports where name = 'NFL'), (select id from players where email = 'player2@test.com'));
insert into Relations (sport_Id, player_Id) values ((select id from sports where name = 'cricket'), (select id from players where email = 'player2@test.com'));
insert into Relations (sport_Id, player_Id) values ((select id from sports where name = 'soccer'), (select id from players where email = 'player2@test.com'));
insert into Relations (sport_Id, player_Id) values ((select id from sports where name = 'Golf'), (select id from players where email = 'player2@test.com'));
insert into Relations (sport_Id, player_Id) values ((select id from sports where name = 'Volleyball'), (select id from players where email = 'player2@test.com'));
insert into Relations (sport_Id, player_Id) values ((select id from sports where name = 'Football'), (select id from players where email = 'player2@test.com'));
insert into Relations (sport_Id, player_Id) values ((select id from sports where name = 'Hockey'), (select id from players where email = 'player2@test.com'));
insert into Relations (sport_Id, player_Id) values ((select id from sports where name = 'Hockey'), (select id from players where email = 'player3@test.com'));
insert into Relations (sport_Id, player_Id) values ((select id from sports where name = 'Hockey'), (select id from players where email = 'player4@test.com'));
insert into Relations (sport_Id, player_Id) values ((select id from sports where name = 'Hockey'), (select id from players where email = 'player5@test.com'));
insert into Relations (sport_Id, player_Id) values ((select id from sports where name = 'Hockey'), (select id from players where email = 'player6@test.com'));


