
DELETE FROM Dientes;

/* INSERTAMOS LOS DIENTES DE LECHE */
INSERT INTO Dientes(Codigo, Cuadrante, Nombre, TipoDenticion)
VALUES  ('51', '5', 'Incisivo Central', 'Temporal'), 
		('52', '5', 'Incisivo Lateral', 'Temporal'),
		('53', '5', 'Canino', 'Temporal'),
		('54', '5', 'Primer Molar', 'Temporal'),
		('55', '5', 'Segundo Molar', 'Temporal'),
		
		('61', '6', 'Incisivo Central', 'Temporal'), 
		('62', '6', 'Incisivo Lateral', 'Temporal'),
		('63', '6', 'Canino', 'Temporal'),
		('64', '6', 'Primer Molar', 'Temporal'),
		('65', '6', 'Segundo Molar', 'Temporal'), 

		('71', '7', 'Incisivo Central', 'Temporal'),
		('72', '7', 'Incisivo Lateral', 'Temporal'),
		('73', '7', 'Canino', 'Temporal'),
		('74', '7', 'Primer Molar', 'Temporal'),
		('75', '7', 'Segundo Molar', 'Temporal'), 

		('81', '8', 'Incisivo Central', 'Temporal'),
		('82', '8', 'Incisivo Lateral', 'Temporal'),
		('83', '8', 'Canino', 'Temporal'),
		('84', '8', 'Primer Molar', 'Temporal'),
		('85', '8', 'Segundo Molar', 'Temporal');
		
		
/* INSERTAMOS LOS DIENTES DEFINITIVOS */
INSERT INTO Dientes(Codigo, Cuadrante, Nombre, TipoDenticion)
VALUES  ('11', '1', 'Incisivo Central', 'Permanente'), 
		('12', '1', 'Incisivo Lateral', 'Permanente'), 
		('13', '1', 'Canino', 'Permanente'), 
		('14', '1', 'Primer Premolar', 'Permanente'), 
		('15', '1', 'Segundo Premolar', 'Permanente'), 
		('16', '1', 'Primer Molar', 'Permanente'), 
		('17', '1', 'Segundo Molar', 'Permanente'), 
		('18', '1', 'Tercer Molar (Muela del Juicio)', 'Permanente'), 

		('21', '2', 'Incisivo Central', 'Permanente'), 
		('22', '2', 'Incisivo Lateral', 'Permanente'), 
		('23', '2', 'Canino', 'Permanente'), 
		('24', '2', 'Primer Premolar', 'Permanente'), 
		('25', '2', 'Segundo Premolar', 'Permanente'), 
		('26', '2', 'Primer Molar', 'Permanente'), 
		('27', '2', 'Segundo Molar', 'Permanente'), 
		('28', '2', 'Tercer Molar (Muela del Juicio)', 'Permanente'), 

		('31', '3', 'Incisivo Central', 'Permanente'), 
		('32', '3', 'Incisivo Lateral', 'Permanente'), 
		('33', '3', 'Canino', 'Permanente'), 
		('34', '3', 'Primer Premolar', 'Permanente'), 
		('35', '3', 'Segundo Premolar', 'Permanente'), 
		('36', '3', 'Primer Molar', 'Permanente'), 
		('37', '3', 'Segundo Molar', 'Permanente'), 
		('38', '3', 'Tercer Molar (Muela del Juicio)', 'Permanente'), 

		('41', '4', 'Incisivo Central', 'Permanente'), 
		('42', '4', 'Incisivo Lateral', 'Permanente'), 
		('43', '4', 'Canino', 'Permanente'), 
		('44', '4', 'Primer Premolar', 'Permanente'), 
		('45', '4', 'Segundo Premolar', 'Permanente'), 
		('46', '4', 'Primer Molar', 'Permanente'), 
		('47', '4', 'Segundo Molar', 'Permanente'), 
		('48', '4', 'Tercer Molar (Muela del Juicio)', 'Permanente');
		
SELECT * FROM Dientes		
		

