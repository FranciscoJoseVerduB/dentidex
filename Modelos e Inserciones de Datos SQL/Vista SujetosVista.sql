



CREATE VIEW SujetosVista AS
SELECT 
		S.Id 				as Id_Sujeto,
		S.FechaCreacion,
		S.FechaNacimiento,
		S.Nombre,
		S.Apellidos,
		S.Nif,
		S.Email,
		S.Telefono,
		S.Foto,
		S.Id_Direccion, 
		DIR.Direccion,
		DIR.Poblacion,
		DIR.CodigoPostal,
		DIR.Provincia,
		DIR.Pais,
		USU.ID				as Id_Usuario,
		USU.Codigo			as UsuarioClave,
		USU.Contrasenia
	FROM Sujetos S
	LEFT JOIN Direcciones DIR ON DIR.Id = S.ID_Direccion
	LEFT JOIN Usuarios USU ON USU.ID_Sujeto = S.ID
	
	
	 