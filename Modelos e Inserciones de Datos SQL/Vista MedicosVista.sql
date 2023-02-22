

 
 
 
 CREATE VIEW MedicosVista AS
 SELECT 
 		M.Id			as Id_Medico,
		M.NumeroColegiado,
		E.Id			as Id_Especialidad,
		E.Nombre		as Especialidad,
		S.*
 	FROM Medicos M	
	LEFT JOIN Especialidades E on E.ID = M.Id_Especialidad
	LEFT JOIN SujetosVista S on S.ID_Sujeto = M.Id_Sujeto
	