
DELETE FROM Tratamientos;
DELETE FROM TiposTratamiento;


/* INSERTAMOS ALGUNOS TIPOS DE TRATAMIENTO POR DEFECTO */
INSERT INTO TiposTratamiento (Nombre)
VALUES  ('Ortodoncia'), 
		('Implantología'),
		('Estética dental'),
		('Periodoncia (encías)'),
		('Odontología conservadora'),
		('Prótesis y rehabilitación oral'),
		('Cirugía menor'),
		('Cirugía ortognática'),
		('Sedación consciente'),
		('Odontopediatría');
		

/* INSERTAMOS ALGUNOS TRATAMIENTOS POR DEFECTO */
INSERT INTO Tratamientos (Precio, Nombre, Id_TipoTratamiento)
VALUES (0, 'Alineadores Invisalign', (SELECT Id FROM TiposTratamiento WHERE Nombre = 'Ortodoncia')),
	   (0, 'Ortodoncia lingual Incognito', (SELECT Id FROM TiposTratamiento WHERE Nombre = 'Ortodoncia')),
	   (0, 'Brackets de zafiro', (SELECT Id FROM TiposTratamiento WHERE Nombre = 'Ortodoncia')),
	   (0, 'Brackets metálicos', (SELECT Id FROM TiposTratamiento WHERE Nombre = 'Ortodoncia')),
	   (0, 'Brackets Damon', (SELECT Id FROM TiposTratamiento WHERE Nombre = 'Ortodoncia')),
	   (0, 'Ortodoncia infantil', (SELECT Id FROM TiposTratamiento WHERE Nombre = 'Ortodoncia')),
	   
	   (0, 'Implantes dentales', (SELECT Id FROM TiposTratamiento WHERE Nombre = 'Implantología')),
	   (0, 'Cirugía guiada', (SELECT Id FROM TiposTratamiento WHERE Nombre = 'Implantología')),
	   (0, 'Sedación consciente', (SELECT Id FROM TiposTratamiento WHERE Nombre = 'Implantología')),
	   (0, 'Injerto de hueso', (SELECT Id FROM TiposTratamiento WHERE Nombre = 'Implantología')),
	   (0, 'Carga inmediata', (SELECT Id FROM TiposTratamiento WHERE Nombre = 'Implantología')),
	   (0, 'Elevación de seno', (SELECT Id FROM TiposTratamiento WHERE Nombre = 'Implantología')),
	   
	   (0, 'Carillas dentales', (SELECT Id FROM TiposTratamiento WHERE Nombre = 'Estética dental')),
	   (0, 'Blanquamiento', (SELECT Id FROM TiposTratamiento WHERE Nombre = 'Estética dental')),
	   (0, 'Coronas de zirconio sin metal', (SELECT Id FROM TiposTratamiento WHERE Nombre = 'Estética dental')),
	   
	   (0, 'Tratamiento de la periodontitis', (SELECT Id FROM TiposTratamiento WHERE Nombre = 'Periodoncia (encías)')),
	   (0, 'Higiene dental', (SELECT Id FROM TiposTratamiento WHERE Nombre = 'Periodoncia (encías)')),

	   (0, 'Endodoncia con microscopio', (SELECT Id FROM TiposTratamiento WHERE Nombre = 'Odontología conservadora')),
	   (0, 'Obturaciones (empastes)', (SELECT Id FROM TiposTratamiento WHERE Nombre = 'Odontología conservadora')),
	   (0, 'Incrustaciones', (SELECT Id FROM TiposTratamiento WHERE Nombre = 'Odontología conservadora')),
	   (0, 'Reconstrucciones', (SELECT Id FROM TiposTratamiento WHERE Nombre = 'Odontología conservadora')),
	   (0, 'Blanqueamiento interno', (SELECT Id FROM TiposTratamiento WHERE Nombre = 'Odontología conservadora')),

	   (0, 'Prótesis completas fijas', (SELECT Id FROM TiposTratamiento WHERE Nombre = 'Prótesis y rehabilitación oral')),
	   (0, 'Bruxismo y ATM', (SELECT Id FROM TiposTratamiento WHERE Nombre = 'Prótesis y rehabilitación oral')),
	   (0, 'Sobredentaduras sobre implantes', (SELECT Id FROM TiposTratamiento WHERE Nombre = 'Prótesis y rehabilitación oral')),
	   (0, 'Prótesis parciales removibles', (SELECT Id FROM TiposTratamiento WHERE Nombre = 'Prótesis y rehabilitación oral')),
	   
	   (0, 'Injerto de encía', (SELECT Id FROM TiposTratamiento WHERE Nombre = 'Cirugía menor')),
	   (0, 'Gingivectomía', (SELECT Id FROM TiposTratamiento WHERE Nombre = 'Cirugía menor')),
	   (0, 'Extracciones', (SELECT Id FROM TiposTratamiento WHERE Nombre = 'Cirugía menor')),
	   (0, 'Fenestración de canino', (SELECT Id FROM TiposTratamiento WHERE Nombre = 'Cirugía menor')),
	   (0, 'Alargamiento coronario', (SELECT Id FROM TiposTratamiento WHERE Nombre = 'Cirugía menor')),
	   (0, 'Apicectomía', (SELECT Id FROM TiposTratamiento WHERE Nombre = 'Cirugía menor')),
	   (0, 'Microtornillos', (SELECT Id FROM TiposTratamiento WHERE Nombre = 'Cirugía menor')),
	   
	   (0, 'Ortodoncia pre-quirúrgica', (SELECT Id FROM TiposTratamiento WHERE Nombre = 'Cirugía ortognática')),
	   (0, 'Planificación quirúrgica', (SELECT Id FROM TiposTratamiento WHERE Nombre = 'Cirugía ortognática')),
	   (0, 'Ortodoncia post-quirúrgica', (SELECT Id FROM TiposTratamiento WHERE Nombre = 'Cirugía ortognática')),
	   
	   (0, 'Presencia de anestesia', (SELECT Id FROM TiposTratamiento WHERE Nombre = 'Sedación consciente')),
	   (0, 'Pruebas pre-operatorias', (SELECT Id FROM TiposTratamiento WHERE Nombre = 'Sedación consciente')),
	   (0, 'Óxido nitroso', (SELECT Id FROM TiposTratamiento WHERE Nombre = 'Sedación consciente')),

	    (0, 'Empastes', (SELECT Id FROM TiposTratamiento WHERE Nombre = 'Odontopediatría')),
		(0, 'Pulpectomías', (SELECT Id FROM TiposTratamiento WHERE Nombre = 'Odontopediatría')),
		(0, 'Selladores', (SELECT Id FROM TiposTratamiento WHERE Nombre = 'Odontopediatría'));

 SELECT * FROM Tratamientos