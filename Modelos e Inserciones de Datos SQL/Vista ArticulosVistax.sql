

CREATE VIEW ArticulosVista AS
SELECT 
		A.ID 			as Id_Articulo,
		A.Codigo		as ArticuloCodigo,
		A.Nombre		as ArticuloNombre,
		A.PrecioCompra,
		A.CantidadExistencia,
		A.ID_FamiliaArticulo,
		FA.Codigo		as FamiliaArticuloCodigo,
		FA.Nombre		as FamiliaArticuloNombre,
		A.ID_Medicamento,
		M.Farmaco,
		M.NombreComercial,
		M.Presentacion,
		M.Indicacion,
		M.ViaAdministracion

	FROM Articulos A
	LEFT JOIN FamiliasArticulo FA on FA.ID = A.ID_FamiliaArticulo
	LEFT JOIN Medicamentos M on M.ID = A.ID_Medicamento