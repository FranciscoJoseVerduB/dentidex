package com.controladoras;

import java.io.File; 
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate; 
import java.util.ArrayList;
 

import com.Main; 
import com.modelos.hibernate.Diente;
import com.modelos.hibernate.HistorialClinicoDetalle;
import com.modelos.hibernate.HistorialClinicoDetalle_Documento;
import com.modelos.hibernate.HistorialClinicoDetalle_Receta;
import com.modelos.hibernate.HistorialClinicoDetalle_Tratamiento;
import com.modelos.hibernate.Medicamento; 
import com.modelos.hibernate.TipoTratamiento;
import com.modelos.hibernate.Tratamiento;
import com.util.Informes;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML; 
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell; 
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn; 

public class HistorialClinicoDetalleShowController {
	Main main;
	HistorialClinicoDetalle historialClinicoDetalle;
	Diente dienteSeleccionado; 
	Medicamento medicamentoSeleccionado;
	

	@FXML TextField txtId_Visita;
	@FXML TextField txtFechaVisita;
	@FXML TextField txtHoraVisita;
	@FXML TextField txtId_Cita;
	@FXML TextField txtId_Medico;
	@FXML TextField txtMedicoNombre;
	@FXML TextField txtId_Paciente;
	@FXML TextField txtPacienteNombre;
	@FXML TextField txtPacienteEdad;
	@FXML TextField txtPacienteGenero;
	@FXML TextField txtPacienteNif;
	@FXML TextField txtDienteCodigo;
	@FXML TextField txtDienteNombre;
	@FXML TextField txtDienteCuadrante;
	@FXML Button btnReiniciarOdontograma;
	@FXML TextArea txtEnfermedadesFamiliares;
	@FXML TextArea txtAncetedentesPatologicos;
	@FXML TextArea txtAlergias;
	@FXML CheckBox ckSangradoExcesivo;
	@FXML CheckBox ckVih;
	@FXML CheckBox ckProblemaSanguineo;
	@FXML CheckBox ckEmbarazada;
	@FXML CheckBox ckPastillasAnticonceptivas;
	@FXML CheckBox ckDificultadAbrirBoca;
	@FXML CheckBox ckRuidoAbrirCerrarBoca;
	@FXML CheckBox ckMuerdeUnhas;
	@FXML CheckBox ckFuma;
	@FXML CheckBox ckConsumoAlimentosCitricos;
	@FXML TextField txtPesoKg;
	@FXML TextField txtAlturaCm;
	@FXML TextField txtTemperatura;
	@FXML TextField txtFrecuenciaCardiaca;
	@FXML TextArea txtDiagnostico;
	@FXML TextArea txtObservaciones;
	@FXML Button btnAnadirTratamientoSeleccionado;
	@FXML Button btnQuitarTratamientoSeleccionado;
	@FXML TextField txtRecetaMedicamento;
	@FXML Button btnBuscarMedicamento;
	@FXML TextArea txtRecetaIndicaciones;
	@FXML Button btnAnadirReceta;
	@FXML Button btnQuitarReceta; 
	@FXML Button btnBorrarFichero; 
	@FXML Button btnBuscarFichero;
	@FXML TextArea txtHabitosAlimenticios;
	@FXML TextArea txtTratamientosAnteriores;
	@FXML TextArea txtMedicacionActual;
	@FXML Button btnReiniciarDatosVisita1;
	@FXML Button btnVerPresupuesto;
	@FXML Button btnCrearNuevaVisita;
	@FXML Button btnVerPresupuesto1;
	@FXML GridPane dataGridDientesPermanentes;
	@FXML GridPane dataGridDientesTemporales;
	
	ArrayList<Diente> listaDientes;

	@FXML TableView<Tratamiento> tvTratamientosDisponibles;
	@FXML TableColumn<Tratamiento, Integer> col_Tratamiento_Id;
	@FXML TableColumn<Tratamiento, Tratamiento> col_TratamientoNombre;
	@FXML TableColumn<Tratamiento, Double> col_Tratamiento_Precio;
	@FXML TableColumn<Tratamiento, TipoTratamiento> col_Tratamiento_Tipo;
	@FXML TableView<HistorialClinicoDetalle_Tratamiento> tvTratamientosPresupuestados;
	ObservableList<HistorialClinicoDetalle_Tratamiento> listaTratamientosPresupuestados = FXCollections.observableArrayList();
	@FXML TableColumn<HistorialClinicoDetalle_Tratamiento, Tratamiento> col_TratamientoPresupuestado;
	@FXML TableColumn<HistorialClinicoDetalle_Tratamiento, String> col_TratamientoPresupuestado_Observaciones;
	@FXML TableColumn<HistorialClinicoDetalle_Tratamiento, Diente> col_TratamientoPresupuestado_Diente;
	@FXML TableView<HistorialClinicoDetalle_Receta> tvRecetas;
	ObservableList<HistorialClinicoDetalle_Receta> listaRecetas = FXCollections.observableArrayList();
	@FXML TableColumn<HistorialClinicoDetalle_Receta, Integer> col_Id_Receta;
	@FXML TableColumn<HistorialClinicoDetalle_Receta, Medicamento> col_Medicamento;
	@FXML TableColumn<HistorialClinicoDetalle_Receta, String> col_RecetaIndicaciones;
	@FXML TableView<HistorialClinicoDetalle_Documento> tvFicheros;
	ObservableList<HistorialClinicoDetalle_Documento> listaDocumentos = FXCollections.observableArrayList();
	@FXML TableColumn<HistorialClinicoDetalle_Documento, Integer> col_Fichero_Id;
	@FXML TableColumn<HistorialClinicoDetalle_Documento, String> col_FicheroRuta;
	@FXML TableColumn<HistorialClinicoDetalle_Documento, ImageView> col_ObjetoFichero;
	@FXML Button btnGuardar;
	@FXML Button btnEliminar; 
	private Stage stage; 
 
	public void setMain(Main main) {
		this.main = main;		
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
 
	
	public void setHistorialClinicoDetalle(HistorialClinicoDetalle historialClinicoDetalle) {
		this.historialClinicoDetalle = historialClinicoDetalle;
		this.cargarHistorialClinicoDetalle(historialClinicoDetalle);
	}
	

	@FXML
	private void initialize() { 
		try {
			//Establecemos el limite de caracteres
			com.util.Eventos.setLimiteCaracteres(this.txtEnfermedadesFamiliares, 300);
			com.util.Eventos.setLimiteCaracteres(this.txtAncetedentesPatologicos, 300);
			com.util.Eventos.setLimiteCaracteres(this.txtAlergias, 300);
			com.util.Eventos.setLimiteCaracteres(this.txtHabitosAlimenticios, 100);
			com.util.Eventos.setLimiteCaracteres(this.txtRecetaIndicaciones, 500);
			com.util.Eventos.setLimiteCaracteres(this.txtTratamientosAnteriores, 200);
			com.util.Eventos.setLimiteCaracteres(this.txtObservaciones, 300);
			com.util.Eventos.setLimiteCaracteres(this.txtPesoKg, 5);
			com.util.Eventos.setLimiteCaracteres(this.txtAlturaCm, 5);
			com.util.Eventos.setLimiteCaracteres(this.txtFrecuenciaCardiaca, 3); 
			com.util.Eventos.setMascaraDouble(this.txtPesoKg); 
			com.util.Eventos.setMascaraDouble(this.txtAlturaCm);
			com.util.Eventos.setMascaraInteger(this.txtFrecuenciaCardiaca); 
			com.util.Eventos.setMascaraDouble(this.txtFrecuenciaCardiaca);
			
			// Ininicializamos la tabla de tratamientos
			col_Tratamiento_Id.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
			col_TratamientoNombre.setCellValueFactory(cellData -> new SimpleObjectProperty<Tratamiento>( cellData.getValue()));
			col_Tratamiento_Precio.setCellValueFactory(cellData -> new SimpleDoubleProperty( cellData.getValue().getPrecio()).asObject());
			col_Tratamiento_Tipo.setCellValueFactory(cellData -> new SimpleObjectProperty<TipoTratamiento>(cellData.getValue().getTipoTratamiento()));		
			 
			//Iniciamos el resto de tablas
			col_TratamientoPresupuestado.setCellValueFactory(cellData -> new SimpleObjectProperty<Tratamiento>(cellData.getValue().getTratamiento()));
			col_TratamientoPresupuestado_Diente.setCellValueFactory(cellData -> new SimpleObjectProperty<Diente>(cellData.getValue().getDiente()));
			col_TratamientoPresupuestado_Observaciones.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getObservaciones())); 
			col_Id_Receta.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
			col_Medicamento.setCellValueFactory(cellData -> new SimpleObjectProperty<Medicamento>(cellData.getValue().getMedicamento()));
			col_RecetaIndicaciones.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIndicaciones()));
			col_Fichero_Id.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
			col_FicheroRuta.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFichero()));
			col_ObjetoFichero.setCellValueFactory(cellData -> new SimpleObjectProperty<ImageView>(com.util.Imagenes.getImageViewFromFileSistemaIcono(cellData.getValue().getFichero(), 100)));
			
			this.tvTratamientosPresupuestados.setEditable(true);
			this.col_TratamientoPresupuestado_Observaciones.setCellFactory(TextFieldTableCell.forTableColumn());
			//Indicamos que se guarde el registro de la observacion cuando se confirme su edicion
			this.col_TratamientoPresupuestado_Observaciones.setOnEditCommit(t -> {
				t.getTableView().getItems().get(t.getTablePosition().getRow()).setObservaciones(t.getNewValue());
				
			});
					
			
			
			
			//Suscribimos el tableView al evento para que al hacer doble click sobre algún documento, se abra 
			this.tvFicheros.setOnMousePressed(new EventHandler<MouseEvent>() {
			    @Override 
			    public void handle(MouseEvent event) {
			        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
			        	if(tvFicheros.getSelectionModel() != null)
			        		com.util.Ficheros.abrirFichero(tvFicheros.getSelectionModel().getSelectedItem().getFichero());          
			        }
			    }
			});
			
			
			//Cargamos los datos
			this.listaDientes = new Diente().getLista();
			this.tvTratamientosDisponibles.setItems(FXCollections.observableArrayList(new Tratamiento().getLista())); 
			this.establecerSetImagenesDientes();
		}catch(Exception ex) {
			ex.printStackTrace();
		}          
	}
	 
	private void establecerSetImagenesDientes() {
		//Recorremos los grid  
		ObservableList<Node> lista = FXCollections.observableArrayList(); 	
		lista.addAll(this.dataGridDientesTemporales.getChildren());
		lista.addAll(this.dataGridDientesPermanentes.getChildren());		
		
		for (Node node : lista)
		{
		    if(node instanceof Button) {
		    		ImageView imagen = com.util.Imagenes.getImageViewFrom("Imagen_Diente.png");
		    		imagen.setFitHeight(50);
		    		imagen.setFitWidth(50);
		        	((Button) node).setGraphic(imagen);
		        	//Recorremos la lista de dientes y le asignamos el objeto - Diente al botón
		        	for(Diente d : this.listaDientes){
		        		if(d.getCodigo().equals(((Button) node).getText())) {
		        			((Button) node).setUserData(d);
		        			break;
		        		}
		        	}
		        	//Indicamos que cuando el usuario pulse el botón se establezca el diente como seleccionado
		        	((Button) node).setOnAction(evento -> {
		        		if(((Button)evento.getSource()).getUserData() == null) return;
		        		this.dienteSeleccionado = (Diente)((Button)evento.getSource()).getUserData();
		        		this.txtDienteCodigo.textProperty().setValue(this.dienteSeleccionado.getCodigo());
		        		this.txtDienteNombre.textProperty().setValue(this.dienteSeleccionado.getNombre());
		        		this.txtDienteCuadrante.textProperty().setValue(this.dienteSeleccionado.getCuadrante()); 
		        	});
		        	
		       }
		} 
	}
	 

	@FXML public void btnReiniciarOdontograma() {
		this.dienteSeleccionado = null;
		this.txtDienteCodigo.textProperty().setValue(null);
		this.txtDienteNombre.textProperty().setValue(null);
		this.txtDienteCuadrante.textProperty().setValue(null);
	}

	@FXML public void btnAnadirTratamientoSeleccionado() {
		int indice = this.tvTratamientosDisponibles.getSelectionModel().getSelectedIndex(); 
        if (indice >= 0) {   
        	//Si el usuario no selecciona un diente, no podra continuar
        	if(this.dienteSeleccionado == null) {
        		 com.util.Alertas.alertaNadaSeleccionado(this.main.getStagePrincipal(), "diente");
        		 return;
        	}

            //Añadimos un tratamiento nuevo al paciente
            HistorialClinicoDetalle_Tratamiento historialClinicoTratamiento = new HistorialClinicoDetalle_Tratamiento();
            historialClinicoTratamiento.setTratamiento(this.tvTratamientosDisponibles.getSelectionModel().getSelectedItem());
            historialClinicoTratamiento.setDiente(this.dienteSeleccionado);
            historialClinicoTratamiento.setHistorialClinicoDetalle(historialClinicoDetalle);
            
            //Si el historial ya tiene un tratamiento para ese mismo tratamiento para el diente en cuestion, no se podrá continuar.
        	if(this.listaTratamientosPresupuestados.contains(historialClinicoTratamiento)){
        		com.util.Alertas.alertaListaDatosDuplicados(this.main.getStagePrincipal(), "tratamiento");
        		return;
        	}
            
            //Añadimos el tratamiento a la lista
            this.listaTratamientosPresupuestados.add(historialClinicoTratamiento); 
        } else {
            com.util.Alertas.alertaNadaSeleccionado(this.main.getStagePrincipal(), "tratamiento"); 
        }
	}

	@FXML public void btnQuitarTratamientoSeleccionado() {
		int indice = this.tvTratamientosPresupuestados.getSelectionModel().getSelectedIndex(); 
        if (indice >= 0) {    
        	
        	 if(!com.util.Alertas.alertaConfirmacionUsuario(this.main.getStagePrincipal(), "Va eliminar el tratamiento presupuestado seleccionado, ¿continuar?").equals(ButtonType.OK)) 
             	return;	//Preguntamos al usuario si desea eliminar el registro seleccionado 
        	 
        	 if(!tvTratamientosPresupuestados.getSelectionModel().getSelectedItem().esEliminable()) {
             	com.util.Alertas.alertaDatosErroneos(this.main.getStagePrincipal(), "El tratamiento presupuestado no se puede eliminar, tiene facturas asociadas");
             	return;
             }
            this.listaTratamientosPresupuestados.remove(this.tvTratamientosPresupuestados.getSelectionModel().getSelectedItem()); 
        } else {
            com.util.Alertas.alertaNadaSeleccionado(this.main.getStagePrincipal(), "tratamiento presupuestado"); 
        }
         
	}

	@FXML public void btnAnadirReceta() { 
		//Si el usuario no selecciona un medicamento, no podra continuar
    	if(this.medicamentoSeleccionado == null) {
    		 com.util.Alertas.alertaNadaSeleccionado(this.main.getStagePrincipal(), "medicamento"); return;
    	} 
        //Añadimos una receta nueva al paciente
        HistorialClinicoDetalle_Receta historialClinicoReceta = new HistorialClinicoDetalle_Receta();
        historialClinicoReceta.setMedicamento(this.medicamentoSeleccionado);
        historialClinicoReceta.setIndicaciones(this.txtRecetaIndicaciones.getText());
        historialClinicoReceta.setHistorialClinicoDetalle(historialClinicoDetalle);
        
        //Si el historial ya tiene un tratamiento para ese mismo tratamiento para el diente en cuestion, no se podrá continuar.
    	if(this.listaRecetas.contains(historialClinicoReceta)){
    		com.util.Alertas.alertaListaDatosDuplicados(this.main.getStagePrincipal(), "medicamento");
    		return;
    	} 
        //Añadimos el medicamento a la lista
        this.listaRecetas.add(historialClinicoReceta);
        //Eliminamos los datos temporales
        this.medicamentoSeleccionado = null;
        this.txtRecetaIndicaciones.textProperty().setValue(null);
        this.txtRecetaMedicamento.textProperty().setValue(null);
	}

	@FXML public void btnQuitarReceta() {
		int indice = this.tvRecetas.getSelectionModel().getSelectedIndex(); 
        if (indice >= 0) {   
        	 if(!com.util.Alertas.alertaConfirmacionUsuario(this.main.getStagePrincipal(), "Va eliminar la receta seleccionada, ¿continuar?").equals(ButtonType.OK)) 
             	return;	//Preguntamos al usuario si desea eliminar el registro seleccionado 
            this.listaRecetas.remove(this.tvRecetas.getSelectionModel().getSelectedItem()); 
        } else {
            com.util.Alertas.alertaNadaSeleccionado(this.main.getStagePrincipal(), "receta"); 
        }
	}
 
	
	private void cargarHistorialClinicoDetalle(HistorialClinicoDetalle historialClinicoDetalle) { 
		//Establecemos los datos de la cita
		if(this.historialClinicoDetalle.getCita() != null) {
			this.txtId_Cita.textProperty().setValue(String.valueOf(this.historialClinicoDetalle.getCita().getId()));
			this.txtHoraVisita.textProperty().setValue(this.historialClinicoDetalle.getCita().getHora()); 
			this.txtId_Medico.textProperty().setValue(String.valueOf(this.historialClinicoDetalle.getCita().getMedico().getId()));
			this.txtMedicoNombre.textProperty().setValue(this.historialClinicoDetalle.getCita().getMedico().toString()); 
			this.txtId_Paciente.textProperty().setValue(String.valueOf(this.historialClinicoDetalle.getCita().getPaciente().getId()));
			this.txtPacienteNombre.textProperty().setValue(this.historialClinicoDetalle.getCita().getPaciente().toString());
			this.txtPacienteGenero.textProperty().setValue(this.historialClinicoDetalle.getCita().getPaciente().getGenero().toString());
			this.txtPacienteNif.textProperty().setValue(this.historialClinicoDetalle.getCita().getPaciente().getSujeto().getNif());
			this.txtPacienteEdad.textProperty().setValue(String.valueOf(this.historialClinicoDetalle.getCita().getPaciente().getSujeto().getEdad()));
		}
		else {
			this.txtId_Cita.textProperty().setValue(null);
			this.txtHoraVisita.textProperty().setValue(null); 
			this.txtId_Medico.textProperty().setValue(null);
			this.txtMedicoNombre.textProperty().setValue(null); 
			this.txtId_Paciente.textProperty().setValue(null);
			this.txtPacienteNombre.textProperty().setValue(null); 
			this.txtPacienteGenero.textProperty().setValue(null);
			this.txtPacienteNif.textProperty().setValue(null);
			this.txtPacienteEdad.textProperty().setValue(null);
			this.txtPacienteEdad.textProperty().setValue(null);
		}
		if(this.historialClinicoDetalle.getId() != 0) this.txtId_Visita.textProperty().setValue(String.valueOf(this.historialClinicoDetalle.getId()));
		
		//Establecemos los datos actuales en los gridpanels  
		this.listaTratamientosPresupuestados.clear();
		this.listaTratamientosPresupuestados.addAll(this.historialClinicoDetalle.getHistorialClinicoDetalle_Tratamiento());
		this.tvTratamientosPresupuestados.setItems(this.listaTratamientosPresupuestados); 
		this.listaRecetas.clear();
		this.listaRecetas.setAll(this.historialClinicoDetalle.getHistorialClinicoDetalle_Receta());
		this.tvRecetas.setItems(this.listaRecetas);
		this.listaDocumentos.clear();
		this.listaDocumentos.setAll(this.historialClinicoDetalle.getHistorialClinicoDetalle_Documento());
		this.tvFicheros.setItems(this.listaDocumentos);
		
		 
		
		//Establecemos los datos de los textos 
		this.txtAncetedentesPatologicos.textProperty().setValue(this.historialClinicoDetalle.getHistorialClinico().getAntecedentesPatologicos());
		this.txtEnfermedadesFamiliares.textProperty().setValue(this.historialClinicoDetalle.getHistorialClinico().getEnfermedadesFamiliares());
		this.txtAlergias.textProperty().setValue(this.historialClinicoDetalle.getHistorialClinico().getAlergias());
		  
		if(this.historialClinicoDetalle.getFecha() != null) this.txtFechaVisita.textProperty().setValue(new SimpleDateFormat("dd/MM/yyyy").format(this.historialClinicoDetalle.getFecha()));
		else this.txtFechaVisita.textProperty().setValue(String.valueOf(LocalDate.now())); 
		this.txtHabitosAlimenticios.textProperty().setValue(this.historialClinicoDetalle.getHabitosAlimenticios() != null? this.historialClinicoDetalle.getHabitosAlimenticios(): null);
		this.txtMedicacionActual.textProperty().setValue(this.historialClinicoDetalle.getMedicacionActual() != null? this.historialClinicoDetalle.getMedicacionActual():null);
		this.txtTratamientosAnteriores.textProperty().setValue(this.historialClinicoDetalle.getTratamientosAnteriores() != null? this.historialClinicoDetalle.getTratamientosAnteriores(): null);
		this.txtObservaciones.textProperty().setValue(this.historialClinicoDetalle.getObservaciones() != null? this.historialClinicoDetalle.getObservaciones(): null);
		this.ckSangradoExcesivo.setSelected(this.historialClinicoDetalle.getSangradoExcesivo() != null? this.historialClinicoDetalle.getSangradoExcesivo(): false);
		this.ckProblemaSanguineo.setSelected(this.historialClinicoDetalle.getProblemaSanguineo() != null? this.historialClinicoDetalle.getProblemaSanguineo(): false);
		this.ckVih.setSelected(this.historialClinicoDetalle.getVih() != null? this.historialClinicoDetalle.getVih(): false);
		this.ckEmbarazada.setSelected(this.historialClinicoDetalle.getEmbarazada() != null? this.historialClinicoDetalle.getEmbarazada(): false);
		this.ckPastillasAnticonceptivas.setSelected(this.historialClinicoDetalle.getPastillasAnticonceptivas() != null? this.historialClinicoDetalle.getPastillasAnticonceptivas(): false);
		this.ckDificultadAbrirBoca.setSelected(this.historialClinicoDetalle.getDificultadAbrirBoca() != null? this.historialClinicoDetalle.getDificultadAbrirBoca(): false);
		this.ckRuidoAbrirCerrarBoca.setSelected(this.historialClinicoDetalle.getRuidoAbrirCerrarBoca() != null? this.historialClinicoDetalle.getRuidoAbrirCerrarBoca(): false);
		this.ckMuerdeUnhas.setSelected(this.historialClinicoDetalle.getMuerdeUnhas() != null? this.historialClinicoDetalle.getMuerdeUnhas(): false);
		this.ckFuma.setSelected(this.historialClinicoDetalle.getFuma() != null? this.historialClinicoDetalle.getFuma(): false);
		this.ckConsumoAlimentosCitricos.setSelected(this.historialClinicoDetalle.getConsumoAlimentosCitricos() != null? this.historialClinicoDetalle.getConsumoAlimentosCitricos(): false);
		this.txtPesoKg.textProperty().setValue(this.historialClinicoDetalle.getPesoKg() != null? String.valueOf(this.historialClinicoDetalle.getPesoKg()): null); 
		this.txtAlturaCm.textProperty().setValue(this.historialClinicoDetalle.getAlturaCm() != null? String.valueOf(this.historialClinicoDetalle.getAlturaCm()): null);
		this.txtTemperatura.textProperty().setValue(this.historialClinicoDetalle.getTemperaturaGrados() != null? String.valueOf(this.historialClinicoDetalle.getTemperaturaGrados()): null); 
		this.txtFrecuenciaCardiaca.textProperty().setValue(this.historialClinicoDetalle.getFrecuenciaCardiaca() != null? String.valueOf(this.historialClinicoDetalle.getFrecuenciaCardiaca()): null);
		 
	}

 
	@FXML public void btnBuscarMedicamento() {
		//Buscamos un medicamento
		Medicamento medicamento = com.util.DialogBox.seleccionarMedicamento(this.main);
		if(medicamento == null) return;
		//Asociamos el medico
		this.medicamentoSeleccionado = medicamento;
		this.txtRecetaMedicamento.textProperty().setValue(medicamentoSeleccionado.toString());
		this.txtRecetaIndicaciones.textProperty().setValue(medicamento.getViaAdministracion());
	}
 

	@FXML public void btnBuscarFichero() {
		//Buscamos un fichero y la añadimos
		File fichero = com.util.Ficheros.buscarFichero(this.main.getStagePrincipal());
		if(fichero != null) {
			HistorialClinicoDetalle_Documento historialClinicoDocumento = new HistorialClinicoDetalle_Documento();
			historialClinicoDocumento.setHistorialClinicoDetalle(this.historialClinicoDetalle);
			historialClinicoDocumento.setFichero(fichero.getPath());

			this.listaDocumentos.add(historialClinicoDocumento);
		}
	}

	@FXML public void btnBorrarFichero() {
		int indice = this.tvFicheros.getSelectionModel().getSelectedIndex(); 
        if (indice >= 0) {   
        	 if(!com.util.Alertas.alertaConfirmacionUsuario(this.main.getStagePrincipal(), "Va eliminar el fichero seleccionado, ¿continuar?").equals(ButtonType.OK)) 
             	return;	//Preguntamos al usuario si desea eliminar el registro seleccionado 
            this.listaDocumentos.remove(this.tvFicheros.getSelectionModel().getSelectedItem()); 
        } else {
            com.util.Alertas.alertaNadaSeleccionado(this.main.getStagePrincipal(), "receta"); 
        }
	}
	
	private boolean isValido() {
		String errorMessage = "";
		  
		if(this.listaTratamientosPresupuestados.size() == 0)
			 errorMessage += "Debe tener al menos un tratamiento para el paciente";
		 	              
        if (errorMessage.length() == 0) return true;
        else {
            // Mostramos el mensaje de error
            com.util.Alertas.alertaDatosInvalidos(this.main.getStagePrincipal(), errorMessage);
            return false;
        } 
	}

	@FXML public void btnGuardar() {
		//Comprobamos que los datos son válidos.
		if(!isValido()) return;
		 
		HistorialClinicoDetalle p = this.historialClinicoDetalle;
		//Establecemos los datos
		SimpleDateFormat formatoFecha=new SimpleDateFormat("dd/MM/yyyy");   
		try {
			p.setFecha(formatoFecha.parse(this.txtFechaVisita.getText()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		p.setHabitosAlimenticios(this.txtHabitosAlimenticios.getText());
		p.setMedicacionActual(this.txtMedicacionActual.getText());
		p.setTratamientosAnteriores(this.txtTratamientosAnteriores.getText());
		p.setObservaciones(this.txtObservaciones.getText());
		p.setProblemaSanguineo(this.ckProblemaSanguineo.isSelected());
		p.setVih(this.ckVih.isSelected());
		p.setEmbarazada(this.ckEmbarazada.isSelected());
		p.setSangradoExcesivo(this.ckSangradoExcesivo.isSelected());
		p.setPastillasAnticonceptivas(this.ckPastillasAnticonceptivas.isSelected());
		p.setDificultadAbrirBoca(this.ckDificultadAbrirBoca.isSelected());
		p.setRuidoAbrirCerrarBoca(this.ckRuidoAbrirCerrarBoca.isSelected());
		p.setMuerdeUnhas(this.ckMuerdeUnhas.isSelected());
		p.setFuma(this.ckFuma.isSelected());
		p.setConsumoAlimentosCitricos(this.ckConsumoAlimentosCitricos.isSelected());
		p.setPesoKg(this.txtPesoKg.getText() == null? null : Double.valueOf(this.txtPesoKg.getText()));
		p.setAlturaCm(this.txtAlturaCm.getText() == null? null : Double.valueOf(this.txtAlturaCm.getText()));
		p.setTemperaturaGrados(this.txtTemperatura.getText() == null? null : Double.valueOf(this.txtTemperatura.getText()));
		p.setFrecuenciaCardiaca(this.txtFrecuenciaCardiaca.getText() == null? null : Integer.valueOf(this.txtFrecuenciaCardiaca.getText()));
		
		//HistorialClinico
		p.getHistorialClinico().setAntecedentesPatologicos(this.txtAncetedentesPatologicos.getText());
		p.getHistorialClinico().setEnfermedadesFamiliares(this.txtEnfermedadesFamiliares.getText());
		p.getHistorialClinico().setAlergias(this.txtAlergias.getText()); 
		p.setHistorialClinicoDetalle_Tratamiento(new ArrayList<HistorialClinicoDetalle_Tratamiento>(this.tvTratamientosPresupuestados.getItems()));
		p.setHistorialClinicoDetalle_Receta(new ArrayList<HistorialClinicoDetalle_Receta>(this.tvRecetas.getItems()));
		p.setHistorialClinicoDetalle_Documento(new ArrayList<HistorialClinicoDetalle_Documento>(this.tvFicheros.getItems()));
		
		
		//Guardamos los datos del paciente y recargamos los datos
		if(!p.esModificable()) {
			com.util.Alertas.alertaDatosErroneos(this.main.getStagePrincipal(), "No se puede modificar. Se ha generado una factura."); 
	        return;
		}; 
		 		 
		
		//Guardamos los datos del paciente y recargamos los datos
		if(!p.guardar()) {
			com.util.Alertas.alertaDatosErroneos(this.main.getStagePrincipal(), "Ha ocurrido un error al guardar los cambios."); 
	        return;
		}; 
 		 
		//Avisamos al usuario del proceso
        if(p.getId() > 0) com.util.Alertas.alertaDatosCorrectos(this.main.getStagePrincipal(), "La visita ha sido modificada exitosamente.");  
        else  com.util.Alertas.alertaDatosCorrectos(this.main.getStagePrincipal(), "La visita ha sido creada exitosamente.");
        
        //Recargamos el documento actual
        recargarHistorialClinicoDetalle();
	}
	
	/**
	 * Recargamos los datos
	 */
	private void recargarHistorialClinicoDetalle() {
		this.historialClinicoDetalle =  new HistorialClinicoDetalle().cargar(this.historialClinicoDetalle.getCita());
		this.cargarHistorialClinicoDetalle(this.historialClinicoDetalle);
	}

	@FXML public void btnVerPresupuesto() {
		if(this.historialClinicoDetalle.getId() == 0) { 
			com.util.Alertas.alertaDatosErroneos(this.main.getStagePrincipal(), "Debe guardar los datos para visualizar el presupuesto");	
       } else {
    	   Informes.mostrarInformeHistorialClinicoDetalle(this.main, this.historialClinicoDetalle); 
       }
	}
 

	@FXML public void btnEliminar() {
		if(this.historialClinicoDetalle.getId() == 0) { 
			com.util.Alertas.alertaDatosErroneos(this.main.getStagePrincipal(), "El presupuesto aún no se ha guardado.");
			return;
       }    
       if(com.util.Alertas.alertaConfirmacionUsuario(this.main.getStagePrincipal(), "Va eliminar la visita actual, ¿continuar?").equals(ButtonType.OK)) { 
    	   if(!this.historialClinicoDetalle.esEliminable()) {
            	com.util.Alertas.alertaDatosErroneos(this.main.getStagePrincipal(), "La visita actual no se puede eliminar, está asociado a una factura.");
            	return;
            }
    	   //Eliminamos el documento en curso
    	   if(this.historialClinicoDetalle.borrar()) {
    		   com.util.Alertas.alertaDatosCorrectos(this.main.getStagePrincipal(), "La visita actual ha sido eliminada exitosamente.");
    		   this.stage.close();
    	   }
    	   else com.util.Alertas.alertaDatosCorrectos(this.main.getStagePrincipal(), "Ha ocurrido un error al eliminar la visita actual.");
    	   
       } 
	}
 
 
 
 
}
