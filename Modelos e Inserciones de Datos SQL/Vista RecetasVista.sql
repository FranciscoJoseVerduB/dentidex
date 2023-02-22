



CREATE VIEW RecetasVista AS 

SELECT  
		HCD.ID					as Id_HistorialClinicoDetalle,
		HCD.Fecha				as FechaVisita,
		HCD_R.Indicaciones		as IndicacionesReceta,
		MED.NombreComercial		as MedicamentoNombreComercial,
		C.ID_Paciente,
		CONCAT(PV.Nombre, ' ', PV.Apellidos) as PacienteNombre,
		PV.Nif				 	as PacienteNif,
		PV.Telefono				as PacienteTelefono,
		PV.Provincia			as PacienteProvincia,
		C.ID_Medico,
		CONCAT(MV.Nombre, ' ', MV.Apellidos) as MedicoNombre,
		MV.Nif				 	as MedicoNif,
		MV.Telefono				as MedicoTelefono,
		MV.Provincia			as MedicoProvincia
	FROM HistorialClinicoDetalle HCD 
		INNER JOIN HistorialClinicoDetalle_Recetas HCD_R on HCD_R.ID_HistorialCLinicoDetalle = HCD.ID
		INNER JOIN Medicamentos MED on MED.ID = HCD_R.ID_Medicamento
		INNER JOIN Citas C on HCD.ID_Cita = C.ID
		INNER JOIN PacientesVista PV on C.ID_Paciente = PV.ID_Paciente
		INNER JOIN MedicosVista MV on C.ID_Medico = MV.ID_Medico
	 