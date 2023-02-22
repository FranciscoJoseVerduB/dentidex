package com.controladoras;

import com.Main;
import com.util.Informes;

import javafx.application.Platform;
import javafx.fxml.FXML; 

public class FormularioBaseController {

	Main main; 
	
	public FormularioBaseController() {
		// TODO Auto-generated constructor stub
	}

	public void setMain(Main main) {
		this.main = main;		
	}
  

	@FXML public void menuCerrarAplicacion() {
		Platform.exit();
		System.exit(0);
	}

	@FXML public void menuAyuda() {}

	@FXML public void btnPacientes() {main.iniciarFormularioPacientes();}

	@FXML public void btnReservarCita() {main.iniciarFormularioReservarCitas();}

	@FXML public void btnTratamientos() {main.iniciarFormularioTratamientos();}

	@FXML public void btnMedicos() {main.iniciarFormularioMedicos();}

	@FXML public void gestionarTiposTratamiento() {main.iniciarFormularioTiposDeTratamiento();}

	@FXML public void gestionarTratamientos() {main.iniciarFormularioTratamientos();}

	@FXML public void gestionarEspecialidades() {main.iniciarFormularioEspecialidades();}

	@FXML public void gestionarMedicos() {main.iniciarFormularioMedicos();}

	@FXML public void gestionarPacientes() {main.iniciarFormularioPacientes();}

	@FXML public void gestionarFamiliasArticulo() {main.iniciarFormularioFamiliasArticulo();}

	@FXML public void gestionarArticulos() {main.iniciarFormularioArticulos();}

	@FXML public void gestionarMedicamentos() {main.iniciarFormularioMedicamentos();}
 
	@FXML public void btnArticulos() {main.iniciarFormularioArticulos();}

	@FXML public void btnHistorialClinico() {main.iniciarFormularioHistorialClinico();}
 
 
	@FXML public void mostrarListaDientes() {main.mostrarListaDientes();}

	@FXML public void mostrarListaGruposSanguineo() {main.mostrarListaGruposSanguineo();}

	@FXML public void gestionarCitas() {main.iniciarFormularioGestionarCitas();}

	@FXML public void reservarCitas() {main.iniciarFormularioReservarCitas();}

	@FXML public void gestionarProveedores() {main.iniciarFormularioProveedores();}

	@FXML public void btnCitasPendientes() {main.iniciarFormularioCitasPendientes();}

	@FXML public void btnVisitasPendientes() {main.iniciarFormularioVisitasPendientesFacturar();}
 
	@FXML public void btnGestionarFacturas() {main.iniciarFormularioSeleccionFacturas();}


	@FXML public void informePacientesDeudores() {Informes.mostrarListadoPacientesDeudores(main);}

	@FXML public void informePacientePorTipoSanguineo() { Informes.mostrarListadoPacientesPorTipoSanguineo(main);}

	@FXML public void informePacientes() {Informes.mostrarListadoPacientes(main);}

	@FXML public void informePacientesPorProvincia() {Informes.mostrarListadoPacientesPorProvincia(main);}

	@FXML public void informePacientePorGenero() {Informes.mostrarListadoPacientesPorGenero(main);}

	@FXML public void informePacientesCitasPorMedico() {Informes.mostrarListadoPacientesCitasPorMedico(main);}

	@FXML public void informeMedicos() {Informes.mostrarListadoMedicos(main);}

	@FXML public void informeMedicosPorEspecialidad() {Informes.mostrarListadoMedicosPorEspecialidad(main);}

	@FXML public void informeMedicosPorPoblacion() {Informes.mostrarListadoMedicosPorPoblacion(main);}

	@FXML public void informeTratamientos() {Informes.mostrarListadoTratamientos(main);}

	@FXML public void informeTratamientosTipoTratamiento() {Informes.mostrarListadoTiposDeTratamiento(main);}

	@FXML public void informeRecetasMedicasPorMedico() {Informes.mostrarListadoRecetasMedicasPorMedico(main);}

	@FXML public void informeRecetasMedicasPorPaciente() {Informes.mostrarListadoRecetasMedicasPorPaciente(main);}


	@FXML public void informeMedicamentos() {Informes.mostrarListadoMedicamentos(main);}

	@FXML public void informeFacturas() {Informes.mostrarListadoFacturas(main);}

	@FXML public void informeHistorialesClinicos() {Informes.mostrarListadoHistorialesClinicos(main);}

	@FXML public void gestionarHistorialClinico() {main.iniciarFormularioHistorialClinico();}

	@FXML public void citasPendientesTratar() {main.iniciarFormularioCitasPendientes();}

	@FXML public void gestionarVisitasPendientesFacturar(){main.iniciarFormularioVisitasPendientesFacturar();}
	 

	@FXML public void gestionarFacturas() {main.iniciarFormularioSeleccionFacturas();}

	@FXML public void btnFormularioInicio() {main.iniciarFormularioInicio();}

 

}
