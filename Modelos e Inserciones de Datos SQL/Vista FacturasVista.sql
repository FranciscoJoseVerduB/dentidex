
  
 
CREATE VIEW FacturasVista AS 
SELECT 
		F.ID				as Id_Factura,
		F.Fecha				as FacturaFecha,
		F.Serie				as FacturaSerie,
		F.Numero			as FacturaNumero,
		F.PorcentajeIva		as FacturaPorcentajeIva,
		F.PorcentajeDescuento		as FacturaPorcentajeDescuento,
		F.BaseImponible		as FacturaBaseImponible,
		F.ImporteFactura	as ImporteFactura,
		F.Observaciones		as FacturaObservaciones,
		F.Id_Medico			as Id_Medico,
		F.Id_Paciente		as Id_Paciente,
		FD.Id				as Id_FacturaDetalle,
		FD.Precio			as FacturaDetallePrecio,
		FD.Cobrado			as FacturaDetalleCobrado,
		FD.Precio - FD.Cobrado as FacturaDetalleDeuda,
		FD.Id_HistorialClinicoDetalle_Tratamiento,
		hcd.id_HistorialClinicoDetalle, 
		HCD.Observaciones 	as TratamientoRealizado_Observaciones,
		HCD.Id_Diente		as Id_Diente,
		D.Codigo			as DienteCodigo,
		D.Nombre			as DienteNombre,
		D.Cuadrante		as DienteCuadrante,
		D.TipoDenticion	as DienteTipoDenticion,
		TV.ID_Tratamiento,
		TV.Tratamiento,
		TV.Precio			as TratamientoPrecioDefecto,
		TV.ID_TipoTratamiento,
		TV.TipoTratamiento
		
	FROM Facturas F
	INNER JOIN FacturaDetalle FD on FD.ID_Factura = F.ID
	INNER JOIN HistorialClinicoDetalle_Tratamientos HCD on HCD.ID = FD.ID_HistorialClinicoDetalle_Tratamiento
	INNER JOIN TratamientosVista TV on TV.ID_Tratamiento = HCD.ID_Tratamiento
	INNER JOIN Dientes D on D.ID = HCD.ID_Diente