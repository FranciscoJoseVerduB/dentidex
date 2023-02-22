  


CREATE TABLE IF NOT EXISTS Direcciones
(
	Id 					SERIAL PRIMARY KEY,
	Direccion			varchar(50) NOT NULL,
	Poblacion			varchar(20) NOT NULL,
	CodigoPostal		varchar(5) NOT NULL,
	Provincia			varchar(20) NOT NULL,
	Pais				varchar(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS Sujetos
(
	Id					SERIAL PRIMARY KEY,
	FechaCreacion		TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
	FechaNacimiento		DATE			 NULL,
	Nombre				varchar(100) NOT NULL,
	Apellidos			varchar(50) NOT NULL, 
	NIF					varchar(20) NOT NULL,
	Email				varchar(100) NULL,
	Telefono			varchar(30) NULL,
	Foto				varchar(200) NULL,
	Id_Direccion	 	INT	NOT NULL,
	FOREIGN KEY (Id_Direccion) REFERENCES Direcciones(ID)  ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS Generos
(
	Id					SERIAL PRIMARY KEY,
	Codigo				varchar(5) NOT NULL	UNIQUE,			/* H, M, O */
	Nombre				varchar(20) NOT NULL				/* Hombre, Mujer, Otro */ 
);

CREATE TABLE IF NOT EXISTS EstadosCivil
(
	Id					SERIAL PRIMARY KEY,
	Nombre				varchar(20) NOT NULL	UNIQUE /* Casado, Soltero, Pareja de Hecho, Viudo ... */ 
);

CREATE TABLE IF NOT EXISTS GruposSanguineo
(
	Id					SERIAL PRIMARY KEY,
	Codigo				varchar(5) NOT NULL	UNIQUE,  /* ABO, RH+- No Sabe*/
	PuedeRecibirDe		varchar(50) NOT NULL,		 /* Puede recibir sangre de... */
	PuedeDonarA			varchar(50) NOT NULL		 /* Puede donar sangre a ...*/ 
);



CREATE TABLE IF NOT EXISTS Pacientes
(
	Id					SERIAL PRIMARY KEY,
	Profesion			VARCHAR(50) NOT NULL,  
	Id_Sujeto			INT	NOT NULL,
	Id_GrupoSanguineo	INT	NOT NULL,
	Id_Genero			INT	NOT NULL,
	Id_EstadoCivil		INT	NOT NULL,
	FOREIGN KEY (Id_Sujeto) REFERENCES Sujetos(ID)  ON DELETE CASCADE,
	FOREIGN KEY (Id_GrupoSanguineo) REFERENCES GruposSanguineo(ID),
	FOREIGN KEY (Id_Genero) REFERENCES Generos(ID),
	FOREIGN KEY (Id_EstadoCivil) REFERENCES EstadosCivil(ID) 
);

CREATE TABLE IF NOT EXISTS Usuarios
(
	Id				SERIAL PRIMARY KEY, 
	Codigo			varchar(20)		NULL,
	Contrasenia		varchar(50)		NULL,
	Id_Sujeto		INT	NOT NULL,
	FOREIGN KEY (Id_Sujeto) REFERENCES Sujetos(ID) ON DELETE CASCADE
);
 
 
CREATE TABLE IF NOT EXISTS Especialidades
(
	Id					SERIAL PRIMARY KEY,
	Nombre				varchar(20) NOT NULL	UNIQUE 
);

CREATE TABLE IF NOT EXISTS Medicos
(
	Id				SERIAL PRIMARY KEY,
	NumeroColegiado	varchar(20) NOT NULL, 
	Id_Sujeto		INT	NOT NULL,
	Id_Especialidad	INT	NOT NULL,
	FOREIGN KEY (Id_Sujeto) REFERENCES Sujetos(ID)  ON DELETE CASCADE, 
	FOREIGN KEY (Id_Especialidad) REFERENCES Especialidades(ID)
);

CREATE TABLE IF NOT EXISTS Proveedores
(
	Id				SERIAL PRIMARY KEY,
	PersonaContacto	varchar(20) NULL, 
	PaginaWeb	varchar(20) NULL, 
	Id_Sujeto		INT	NOT NULL, 
	FOREIGN KEY (Id_Sujeto) REFERENCES Sujetos(ID)  ON DELETE CASCADE  
);



CREATE TABLE IF NOT EXISTS FamiliasArticulo
(
	Id			SERIAL PRIMARY KEY,
	Codigo		varchar(20) NOT NULL	UNIQUE,
	Nombre		varchar(50) NOT NULL
);
 
CREATE TABLE IF NOT EXISTS Articulos
(
	Id					SERIAL PRIMARY KEY,
	Codigo				varchar(20) NOT NULL UNIQUE,
	Nombre				varchar(50) NOT NULL,
	PrecioCompra		Decimal(6,3) NULL,
	CantidadExistencia	INT		NULL,
	Id_FamiliaArticulo  INT	NOT NULL,
	Id_Medicamento		INT		NULL,
	FOREIGN KEY (Id_Medicamento) REFERENCES Medicamentos(ID),
	FOREIGN KEY (Id_FamiliaArticulo) REFERENCES FamiliasArticulo(ID)
);



CREATE TABLE IF NOT EXISTS Medicamentos
(
	Id					SERIAL PRIMARY KEY,
	Farmaco				varchar(200) NOT NULL,
	NombreComercial		varchar(200) NOT NULL,
	Presentacion		varchar(300) NOT NULL,
	Indicacion			varchar(500) NOT NULL,		/* Esto se propondrá por defecto cuando se cree una receta en un detalle de historial clinico */
	ViaAdministracion	varchar(500) NOT NULL 
);


 
 

CREATE TABLE IF NOT EXISTS CitasHora
(
	Id					SERIAL PRIMARY KEY,
	Hora				Time NOT NULL	UNIQUE /* 9:00 , 12:25... */ 
);

CREATE TABLE IF NOT EXISTS CitasEstado
(
	Id					SERIAL PRIMARY KEY,
	Nombre				varchar(20) NOT NULL	UNIQUE /* Sin Confirmar, Confirmado, Anulado, Completado, Expirado ... */ 
);

CREATE TABLE IF NOT EXISTS Citas
(
	Id					SERIAL PRIMARY KEY,
	Fecha				Date NOT NULL,
	Hora				Time NOT NULL,
	Observaciones 		VARCHAR(200) NULL,
	Id_Medico			INT NOT NULL,
	Id_Paciente			INT NOT NULL, 
	Id_EstadoCita				INT NOT NULL,
	FOREIGN KEY (Id_Medico) 	REFERENCES Medicos(ID),
	FOREIGN KEY (Id_Paciente) 	REFERENCES Pacientes(ID),
	FOREIGN KEY (Id_EstadoCita) REFERENCES CitasEstado(ID) 
);




CREATE TABLE IF NOT EXISTS HistorialClinico
(
	Id						SERIAL PRIMARY KEY,
	Id_Paciente				INT NOT NULL,
	EnfermedadesFamiliares	varchar(300)	NULL,
	AntecedentesPatologicos	varchar(300)	NULL,
	Alergias				varchar(300)	NULL,
	FOREIGN KEY (Id_Paciente) REFERENCES Pacientes(ID) ON DELETE CASCADE
);




CREATE TABLE IF NOT EXISTS HistorialClinicoDetalle
(
	Id							SERIAL PRIMARY KEY,
	Id_Cita						INT NOT NULL,
	Id_HistorialClinico			INT NOT NULL,
	Fecha						Date NOT NULL,
	HabitosAlimenticios			VARCHAR(100) NULL,
	MedicacionActual			VARCHAR(100) NULL,
	TratamientosAnteriores		VARCHAR(200) NULL,
	Observaciones				VARCHAR(300) NULL,
	SangradoExcesivo			BOOLEAN		 NULL,
	ProblemaSanguineo			BOOLEAN		 NULL,
	VIH							BOOLEAN		 NULL,
	Embarazada					BOOLEAN		 NULL,
	PastillasAnticonceptivas	BOOLEAN		 NULL,
	DificultadAbrirBoca			BOOLEAN		 NULL,
	RuidoAbrirCerrarBoca		BOOLEAN 	 NULL,
	MuerdeUnhas					BOOLEAN		 NULL,
	Fuma						BOOLEAN 	 NULL,
	ConsumoAlimentosCitricos	BOOLEAN 	 NULL,
	PesoKg						DECIMAL(5,2) NULL,
	AlturaCm					DECIMAL(5,2) NULL,
	TemperaturaGrados			DECIMAL(5,2) NULL,
	FrecuenciaCardiaca			INT			NULL,  
	FOREIGN KEY (Id_HistorialClinico) REFERENCES HistorialClinico(ID),
	FOREIGN KEY (Id_Cita) REFERENCES Citas(ID) ON DELETE CASCADE
);




CREATE TABLE IF NOT EXISTS HistorialClinicoDetalle_Documentos
(
	Id							SERIAL PRIMARY KEY,
	Id_HistorialClinicoDetalle	INT NOT NULL,
	Fichero						varchar(100) NULL, 
	FOREIGN KEY (Id_HistorialClinicoDetalle) REFERENCES HistorialClinicoDetalle(ID) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS HistorialClinicoDetalle_Recetas
(
	Id							SERIAL PRIMARY KEY,
	Id_HistorialClinicoDetalle	INT NOT NULL,
	Id_Medicamento				INT NOT NULL,
	Indicaciones				varchar(500) NULL,
	FOREIGN KEY (Id_HistorialClinicoDetalle) REFERENCES HistorialClinicoDetalle(ID) ON DELETE CASCADE,
	FOREIGN KEY (Id_Medicamento) REFERENCES Medicamentos(ID)
);
 

CREATE TABLE IF NOT EXISTS Dientes
(
	Id					SERIAL PRIMARY KEY,
	Codigo				varchar(3) NOT NULL	UNIQUE, /* Código de Diente */
	Cuadrante			varchar(1) NOT NULL,  /* Del 1 al 8 */
	Nombre				varchar(50) NOT NULL,
	TipoDenticion		varchar(10) NOT NULL,	
); 

CREATE TABLE IF NOT EXISTS TiposTratamiento
(
	Id					SERIAL PRIMARY KEY,
	Nombre				VARCHAR(30)	NOT NULL UNIQUE  /* Diagnostico y Prevencion, Ortodoncia, Implantes, Odontopediatria, Estetica Dental, Periodoncia, Protesis, Caries, Bruxismo, Sensibilidad Dental, Halitosis, Antirronquidos, Endodoncias  */
);

CREATE TABLE IF NOT EXISTS Tratamientos
(
	Id					SERIAL PRIMARY KEY,
	Nombre				VARCHAR(30) 	NOT NULL UNIQUE,
	Precio				DECIMAL (6,2)	NOT NULL,
	Id_TipoTratamiento	INT NOT NULL,
	FOREIGN KEY (Id_TipoTratamiento) REFERENCES TiposTratamiento(Id)
);


CREATE TABLE IF NOT EXISTS HistorialClinicoDetalle_Tratamientos
(
	Id					SERIAL PRIMARY KEY, 
	Observaciones 		Varchar(200) NULL,
	Id_Diente			INT NOT NULL,
	Id_HistorialClinicoDetalle INT NOT NULL,
	Id_Tratamiento		INT NOT NULL,
	FOREIGN KEY (Id_Diente) REFERENCES Dientes(Id),
	FOREIGN KEY (Id_HistorialClinicoDetalle) REFERENCES HistorialClinicoDetalle(Id) ON DELETE CASCADE,
	FOREIGN KEY (Id_Tratamiento) REFERENCES Tratamientos(Id)
);

 

CREATE TABLE IF NOT EXISTS Facturas
(
	Id					SERIAL PRIMARY KEY,
	Fecha				DATE 	NOT NULL,
	Serie				VARCHAR(5) NOT NULL,
	Numero				INT		NOT NULL,
	PorcentajeIVA		DECIMAL(5,2) NOT NULL,
	PorcentajeDescuento	DECIMAL(5,2) NOT NULL,
	BaseImponible		DECIMAL(7,2) NOT NULL,
	ImporteFactura		DECIMAL(7,2) NOT NULL, 
	Observaciones		VARCHAR(200) NOT NULL,
	Id_Medico			INT 	NOT NULL,
	Id_Paciente			INT 	NOT NULL,
	FOREIGN KEY (Id_Medico) REFERENCES Medicos(ID),
	FOREIGN KEY (Id_Paciente) REFERENCES Pacientes(ID)
);



CREATE TABLE IF NOT EXISTS FacturaDetalle
(
	Id					SERIAL PRIMARY KEY,
	Precio				DECIMAL(6,2) NOT NULL,
	Cobrado				DECIMAL(6,2) NOT NULL, 
	Id_Factura			INT NOT NULL,
	Id_HistorialClinicoDetalle_Tratamiento		INT NOT NULL,
	FOREIGN KEY (Id_Factura) REFERENCES Facturas(Id) ON DELETE CASCADE,
	FOREIGN KEY (Id_HistorialClinicoDetalle_Tratamiento) REFERENCES HistorialClinicoDetalle_Tratamientos(Id)
);


