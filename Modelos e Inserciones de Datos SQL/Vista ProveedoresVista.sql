



CREATE VIEW ProveedoresVista AS

SELECT 
		P.Id				as Id_Proveedor,
		P.PaginaWeb,
		P.PersonaContacto,
		S.*
	FROM Proveedores P
	LEFT JOIN SujetosVista S on S.ID_Sujeto = P.Id_Sujeto
	 