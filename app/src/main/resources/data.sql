
INSERT INTO TEAM (TEAM_NAME, PRINCIPAL_NAME, HEADQUARTERS, SPONSOR_NAME)
VALUES
    ('Mercedes-AMG Petronas Formula One Team', 'Toto Wolff', 'Brackley, United Kingdom', 'Petronas'),
    ('Red Bull Racing Honda', 'Christian Horner', 'Milton Keynes, United Kingdom', 'Red Bull'),
    ('Scuderia Ferrari', 'Mattia Binotto', 'Maranello, Italy', 'Mission Winnow');


INSERT INTO DRIVER (NAME, NATIONALITY, DATE_OF_BIRTH, NUMBER, TEAM_ID)
VALUES
    ('Lewis Hamilton', 'British', 19850107, 44, 1),
    ('Max Verstappen', 'Dutch', 19970930, 33, 2),
    ('Charles Leclerc', 'Monégasque', 19971016, 16, 3);


INSERT INTO RACE_RESULT (RACE_ID, DRIVER_ID, POSITION, FASTEST_LAP_TIME, POINTS_EARNED)
VALUES
    (1, 1, 1, '1:32.123', 25),
    (1, 2, 2, '1:32.567', 18),
    (1, 3, 3, '1:33.045', 15);


INSERT INTO CAR (MODEL, ENGINE, CHASSIS, TEAM_ID)
VALUES
    ('Mercedes W12', 'Mercedes-AMG F1 M13 E Performance', 'Carbon-fibre', 1),
    ('Red Bull RBPT2', 'Honda RA621H', 'Carbon-fibre', 2),
    ('Ferrari SF21', 'Ferrari 065/6', 'Carbon-fibre', 3);

INSERT INTO SPONSOR (SPONSOR_NAME, COUNTRY, NUMBER, SPONSOR_TYPE)
VALUES
    ('Petronas', 'Malaysia', 44, 'Title Sponsor'),
    ('Red Bull', 'Austria', 33, 'Title Sponsor'),
    ('Mission Winnow', 'Switzerland', 16, 'Principal Partner');

INSERT INTO TEAM_SPONSOR (TEAM_ID, SPONSOR_ID)
VALUES
    (1, 1),
    (2, 2),
    (3, 3);
