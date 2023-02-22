

 
 
 CREATE VIEW ArticulosVista as 
 
 SELECT 
 		A.Id		as Id_Articulo,
		A.Codigo	as ArticuloCodigo,
		A.Nombre	as ArticuloNombre,
		A.PrecioCompra,
		A.CantidadExistencia,
		A.Id_FamiliaArticulo,
		FA.Codigo	as FamiliaCodigo,
		FA.Nombre	as FamiliaNombre,
		A.Id_Medicamento,
		M.Farmaco,
		M.NombreComercial,
		M.Presentacion,
		M.Indicacion,
		M.ViaAdministracion
	FROM Articulos A
	INNER JOIN FamiliasArticulo FA on A.Id_FamiliaArticulo = A.ID
	LEFT JOIN Medicamentos M on M.ID = A.Id_Medicamento