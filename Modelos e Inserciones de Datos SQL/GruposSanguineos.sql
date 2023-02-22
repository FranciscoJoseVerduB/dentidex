
DELETE FROM GruposSanguineo;

INSERT INTO GruposSanguineo (Codigo, PuedeRecibirDe, PuedeDonarA)
VALUES  ('A+', 'A+|AB+','O+|O-|A+|A-'),
		('A-', 'A+|AB+|A-|AB-', 'O-|A-'),
		('B+', 'B+|AB+', 'O+|O-|B+|B-'),
		('B-', 'B+|B-|AB+|AB-', 'O-|B-'),
		
		('AB+', 'AB+', 'Todos'),
		('AB-', 'AB+|AB-', 'AB-|A-|B-|O-'),
		('O+', 'A+|B+|AB+|O+', 'O+|O-'),
		('O-', 'A+|B+|AB+|O+', 'O+|O-');






SELECT * FROM GruposSanguineo;