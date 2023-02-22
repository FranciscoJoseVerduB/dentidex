
CREATE VIEW CitasVista AS 
select 
		C.ID				as Id_Cita,
		C.Fecha 			as CitaFecha,
		C.Hora				as CitaHora,
		C.Observaciones 	as CitaObservaciones,
		CE.Nombre			as CitaEstado,
		C.Id_Paciente,
		CONCAT(PA.Nombre, ' ', PA.Apellidos) as PacienteNombre,
		PA.NIF				as PacienteNif,
		PA.Telefono			as PacienteTelefono,
		PA.Foto				as PacienteFoto,
		PA.Genero			as PacienteGenero,
		C.Id_Medico,
		CONCAT(ME.Nombre, ' ', ME.Apellidos) as MedicoNombre,
		ME.NIF				as MedicoNif,
		ME.Telefono			as MedicoTelefono,
		ME.Especialidad 	as MedicoEspecialidad

	FROM Citas C
	INNER JOIN CitasEstado CE ON CE.Id = C.ID_EstadoCIta
	INNER JOIN MedicosVista ME ON ME.ID_Medico = C.ID_Medico
	INNER JOIN PacientesVista PA on PA.ID_Paciente = C.ID_Paciente
	
	
	 


 