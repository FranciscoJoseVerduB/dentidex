

CREATE VIEW TratamientosVista AS
select  T.Id				as Id_Tratamiento,
		T.Nombre			as Tratamiento,
		T.Precio			as Precio,
		T.ID_TipoTratamiento,
		TT.Nombre			as TipoTratamiento
	FROM Tratamientos T
	LEFT JOIN TiposTratamiento TT on TT.ID = T.ID_TipoTratamiento 
	
	 
	 
	  