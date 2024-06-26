USE FORMULA1;
INSERT INTO TEAM VALUES (1, 'RedBull', 'Christian Horner', 'Viena', 'Oracle');

INSERT INTO TEAM (TEAM_ID, TEAM_NAME, PRINCIPAL_NAME, HEADQUARTERS, SPONSOR_NAME)
VALUES (2, 'Ferrari', 'Frederic Vasseur', 'Maranello', 'Santander');

INSERT INTO TEAM (TEAM_ID, TEAM_NAME, PRINCIPAL_NAME, HEADQUARTERS, SPONSOR_NAME)
VALUES (3, 'Mercedes', 'Toto WOlff', 'Brackley', 'Petronas');


INSERT INTO DRIVER (DRIVER_ID, NAME, NATIONALITY, DATE_OF_BIRTH, NUMBER, TEAM_ID)
VALUES (1, 'Max Verstappen', 'Dutch', 1997, 1, 1);

INSERT INTO DRIVER (DRIVER_ID, NAME, NATIONALITY, DATE_OF_BIRTH, NUMBER, TEAM_ID)
VALUES (2, 'Carlos Sainz', 'Spanish', 1994, 55, 2);

INSERT INTO DRIVER (DRIVER_ID, NAME, NATIONALITY, DATE_OF_BIRTH, NUMBER, TEAM_ID)
VALUES (3, 'Lewis Hamilton', 'British', 1985, 44, 3);

INSERT INTO RACE_RESULT (RESULT_ID, RACE_ID, DRIVER_ID, POSITION, FASTEST_LAP_TIME, POINTS_EARNED)
VALUES (1, 101, 1, 2, '00:01:30', 18);

INSERT INTO RACE_RESULT (RESULT_ID, RACE_ID, DRIVER_ID, POSITION, FASTEST_LAP_TIME, POINTS_EARNED)
VALUES (2, 102, 2, 1, '00:01:28', 25);

INSERT INTO RACE_RESULT (RESULT_ID, RACE_ID, DRIVER_ID, POSITION, FASTEST_LAP_TIME, POINTS_EARNED)
VALUES (3, 90, 3, 7, '00:01:10', 8);

INSERT INTO CAR (CAR_ID, MODEL, ENGINE, CHASSIS, TEAM_ID)
VALUES (1, 'RB19', 'Honda', 'RB16B', 1);

INSERT INTO CAR (CAR_ID, MODEL, ENGINE, CHASSIS, TEAM_ID)
VALUES (2, 'SF-24', 'ferrari', 'SF24A', 2);

INSERT INTO CAR (CAR_ID, MODEL, ENGINE, CHASSIS, TEAM_ID)
VALUES (3, 'W14', 'Mercedes', 'W14', 3);

INSERT INTO SPONSOR (SPONSOR_ID, SPONSOR_NAME, COUNTRY, NUMBER, SPONSOR_TYPE)
VALUES (1, 'Oracle', 'USA', 123456789, 'Title Sponsor');

INSERT INTO SPONSOR (SPONSOR_ID, SPONSOR_NAME, COUNTRY, NUMBER, SPONSOR_TYPE)
VALUES (2, 'Santander', 'Spain', 987654321, 'Official Partner');

INSERT INTO SPONSOR (SPONSOR_ID, SPONSOR_NAME, COUNTRY, NUMBER, SPONSOR_TYPE)
VALUES (3, 'Petronas', 'Melaya', 11226789, 'Title Sponsor');

INSERT INTO SPONSOR (SPONSOR_ID, SPONSOR_NAME, COUNTRY, NUMBER, SPONSOR_TYPE)
VALUES (4, 'Petronas', 'Melaya', 11226789, 'Title Sponsor');
