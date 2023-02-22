



CREATE VIEW PacientesVista AS

SELECT 
		P.Id				as Id_Paciente,
		P.Profesion,
		S.*,
		GP.Id				as Id_GrupoSanguineo,
		GP.Codigo			as GrupoSanguineo,
		GP.PuedeRecibirDe	as PuedeRecibirDe,
		GP.PuedeDonarA		as PuedeDonarA,
		G.Id				as Id_Genero,
		G.Codigo			as Genero,
		G.Nombre			as GeneroNombre,
		EC.Id				as Id_EstadoCivil,
		EC.Nombre			as EstadoCivil,
		HC.Id				as Id_HistorialClinico,
		HC.EnfermedadesFamiliares,
		HC.AntecedentesPatologicos,
		HC.Alergias
	FROM Pacientes P
	LEFT JOIN SujetosVista S on S.ID_Sujeto = P.Id_Sujeto
	LEFT JOIN GruposSanguineo GP on GP.ID = P.ID_GrupoSanguineo
	LEFT JOIN Generos G on G.ID = P.ID_Genero
	LEFT JOIN EstadosCivil EC on EC.ID = P.Id_EstadoCivil
	LEFT JOIN HistorialClinico HC on HC.Id_Paciente = P.ID