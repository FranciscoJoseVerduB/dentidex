package com.controladoras;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
 

import com.Main; 
import com.modelos.hibernate.Diente;
import com.modelos.hibernate.Factura;
import com.modelos.hibernate.FacturaDetalle; 
import com.modelos.hibernate.TipoTratamiento;
import com.modelos.hibernate.Tratamiento;
import com.util.Informes;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList; 
import javafx.fxml.FXML; 
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea; 
import javafx.scene.control.TableView; 
import javafx.util.converter.DoubleStringConverter;
import javafx.scene.control.TableColumn; 
public class FacturasShowController {
	Main main;
	Factura factura; 
 
	
	
	ObservableList<FacturaDetalle> listaFacturaDetalle = FXCollections.observableArrayList(); 
	@FXML Button btnGuardar;
	@FXML TextField txtSerie;
	@FXML TextField txtNumero;
	@FXML TextField txtFecha;
	@FXML TextField txtId_Medico;
	@FXML TextField txtMedicoNombre;
	@FXML TextField txtId_Paciente;
	@FXML TextField txtPacienteNombre;
	@FXML TextField txtPacienteProvincia;
	@FXML TextField txtPacientePoblacion;
	@FXML TextField txtPacienteNif;
	@FXML TextArea txtObservaciones;
	@FXML Button btnVerFactura;
	@FXML Button btnEliminar;
	@FXML TableView<FacturaDetalle> tvFacturaDetalle;
	@FXML TableColumn<FacturaDetalle, Integer> col_Id_FacturaDetalle;
	@FXML TableColumn<FacturaDetalle, Diente> col_Diente;
	@FXML TableColumn<FacturaDetalle, String> col_Observaciones;
	@FXML TableColumn<FacturaDetalle, Tratamiento> col_TratamientoNombre;
	@FXML TableColumn<FacturaDetalle, TipoTratamiento> col_TratamientoTipo;
	@FXML TableColumn<FacturaDetalle, Double> col_Tratamiento_Precio;
	@FXML TableColumn<FacturaDetalle, Double> col_Tratamiento_Cobrado;  
	@FXML TextField txtPorcentajeDescuento;
	@FXML TextField txtPorcentajeIva;
	@FXML TextField txtBaseImponible;
	@FXML TextField txtTotalFactura;
	private Stage stage; 
	
 
	public void setMain(Main main) {
		this.main = main;		
	}

	public void setFactura(Factura factura) {
		this.factura = factura;
		this.cargarFactura(factura);
	}
	

	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	private void recargarFactura() {
		Factura factura = new Factura().getFactura(this.factura.getHistorialClinicoDetalle());
		//Si tras recargar los datos, el dato es NULL, se cerrará la ventana
		if(factura == null) this.stage.close();
		//Establecemos la factura y la recargamos
		this.setFactura(factura);
		this.cargarFactura(factura); 
	}
	
	@FXML
	private void initialize() { 
		try {
			//Establecemos el limite de caracteres
			com.util.Eventos.setLimiteCaracteres(this.txtSerie, 5);
			com.util.Eventos.setLimiteCaracteres(this.txtNumero, 5);
			com.util.Eventos.setLimiteCaracteres(this.txtPorcentajeIva, 5);
			com.util.Eventos.setLimiteCaracteres(this.txtPorcentajeDescuento, 5);
			com.util.Eventos.setLimiteCaracteres(this.txtBaseImponible, 7);
			com.util.Eventos.setLimiteCaracteres(this.txtTotalFactura, 7);
			com.util.Eventos.setLimiteCaracteres(this.txtObservaciones, 200); 
			com.util.Eventos.setMascaraDouble(this.txtPorcentajeDescuento);
			com.util.Eventos.setMascaraDouble(this.txtPorcentajeIva);
			com.util.Eventos.setMascaraDouble(this.txtBaseImponible);
			com.util.Eventos.setMascaraDouble(this.txtTotalFactura);
			com.util.Eventos.setMascaraInteger(this.txtNumero); 
			
			//Evento si el usuario hace uso del descuento
			this.txtPorcentajeDescuento.setOnAction(t ->{ 
				this.factura.setPorcentajeDescuento(this.txtPorcentajeDescuento.getText() == null || this.txtPorcentajeDescuento.getText().length() == 0? 0 : Double.valueOf(this.txtPorcentajeDescuento.getText()));
				this.recalcularDatos();
			}); 
			//Evento si el usuario hace uso del iva
			this.txtPorcentajeIva.setOnAction(t ->{
				this.factura.setPorcentajeIva(this.txtPorcentajeIva.getText() == null || this.txtPorcentajeIva.getText().length() == 0 ? 0 : Double.valueOf(this.txtPorcentajeIva.getText()));
				this.recalcularDatos();
			});
			
			
			// Ininicializamos la tabla de tratamientos
			col_Id_FacturaDetalle.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
			col_Diente.setCellValueFactory(cellData -> new SimpleObjectProperty<Diente>( cellData.getValue().getHistorialClinicoDetalle_Tratamiento().getDiente()));
			col_Observaciones.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getHistorialClinicoDetalle_Tratamiento().getObservaciones()));
			col_Tratamiento_Precio.setCellValueFactory(cellData -> new SimpleDoubleProperty( cellData.getValue().getPrecio()).asObject());
			col_TratamientoNombre.setCellValueFactory(cellData -> new SimpleObjectProperty<Tratamiento>( cellData.getValue().getHistorialClinicoDetalle_Tratamiento().getTratamiento()));
			col_TratamientoTipo.setCellValueFactory(cellData -> new SimpleObjectProperty<TipoTratamiento>( cellData.getValue().getHistorialClinicoDetalle_Tratamiento().getTratamiento().getTipoTratamiento()));
			col_Tratamiento_Precio.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrecio()).asObject());
			col_Tratamiento_Cobrado.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getCobrado()).asObject());
			 
			this.tvFacturaDetalle.setEditable(true);
			this.col_Tratamiento_Precio.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
			this.col_Tratamiento_Cobrado.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
			//Indicamos que se guarde el registro de la observacion cuando se confirme su edicion
			this.col_Tratamiento_Precio.setOnEditCommit(t -> {
				t.getTableView().getItems().get(t.getTablePosition().getRow()).setPrecio(t.getNewValue());
				this.recalcularDatos();
			});
			this.col_Tratamiento_Cobrado.setOnEditCommit(t -> {
				t.getTableView().getItems().get(t.getTablePosition().getRow()).setCobrado(t.getNewValue()); 
			});		
			 
		}catch(Exception ex) {
			ex.printStackTrace();
		}          
	} 
	
	 
	private void cargarFactura(Factura factura) { 
		//Establecemos los datos de la cita
		if(this.factura != null) { 
			this.txtTotalFactura.textProperty().setValue(String.valueOf(this.factura.getImporteFactura() == null? 0 : this.factura.getImporteFactura()));
			this.txtBaseImponible.textProperty().setValue(String.valueOf(this.factura.getBaseImponible() == null? 0 : this.factura.getBaseImponible()));
			this.txtPorcentajeDescuento.textProperty().setValue(String.valueOf(this.factura.getPorcentajeDescuento() == null? 0 : this.factura.getPorcentajeDescuento())); 
			this.txtPorcentajeIva.textProperty().setValue(String.valueOf(this.factura.getPorcentajeIva() == null? 0 : this.factura.getPorcentajeIva()));
			this.txtSerie.textProperty().setValue(this.factura.getSerie());
			this.txtNumero.textProperty().setValue(String.valueOf(this.factura.getNumero()));
			this.txtId_Medico.textProperty().setValue(String.valueOf(this.factura.getMedico().getId()));
			this.txtId_Paciente.textProperty().setValue(String.valueOf(this.factura.getPaciente().getId()));
			this.txtMedicoNombre.textProperty().setValue(this.factura.getMedico().toString());
			this.txtPacienteNombre.textProperty().setValue(this.factura.getPaciente().toString());
			this.txtPacienteNif.textProperty().setValue(this.factura.getPaciente().getSujeto().getNif());
			this.txtPacientePoblacion.textProperty().setValue(this.factura.getPaciente().getSujeto().getDireccion().getPoblacion());
			this.txtPacienteProvincia.textProperty().setValue(this.factura.getPaciente().getSujeto().getDireccion().getProvincia());  
		}
		else { 
			this.txtTotalFactura.textProperty().setValue("0");
			this.txtBaseImponible.textProperty().setValue("0");
			this.txtPorcentajeDescuento.textProperty().setValue("0"); 
			this.txtPorcentajeIva.textProperty().setValue("0");
			this.txtSerie.textProperty().setValue(null);
			this.txtNumero.textProperty().setValue(null);
			this.txtId_Medico.textProperty().setValue(null);
			this.txtId_Paciente.textProperty().setValue(null);
			this.txtMedicoNombre.textProperty().setValue(null);
			this.txtPacienteNombre.textProperty().setValue(null);
			this.txtPacienteNif.textProperty().setValue(null);
			this.txtPacientePoblacion.textProperty().setValue(null);
			this.txtPacienteProvincia.textProperty().setValue(null); 
		} 
		
		//Establecemos los datos actuales en el tableview
		this.listaFacturaDetalle.clear();
		this.listaFacturaDetalle.addAll(this.factura.getFacturaDetalle());
		this.tvFacturaDetalle.setItems(this.listaFacturaDetalle);  
		 
		 
		  
		if(this.factura.getFecha() != null) this.txtFecha.textProperty().setValue(new SimpleDateFormat("dd/MM/yyyy").format(this.factura.getFecha()));
		else this.txtFecha.textProperty().setValue(String.valueOf(LocalDate.now()));  
		 
	}
 
	private boolean isValido() {
		String errorMessage = "";
		  
		if(this.listaFacturaDetalle.size() == 0)
			 errorMessage += "Debe tener al menos un detalle de linea en la factura\n";
	 
        if(this.txtPorcentajeIva.getText() == null || this.txtPorcentajeIva.getText().trim().length() == 0)
        	errorMessage += "El campo de Porcentaje de IVA debe estar relleno\n";
        
        if(this.txtSerie.getText() == null || this.txtSerie.getText().trim().length() == 0)
        	errorMessage += "El campo Serie debe estar relleno\n";
        
        if(this.txtBaseImponible.getText() == null || this.txtBaseImponible.getText().trim().length() == 0)
        	errorMessage += "El campo de Base Imponible debe estar relleno\n";
        
		 	              
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
		 
		Factura p = this.factura;
		//Establecemos los datos    
		try {
			p.setFecha(new SimpleDateFormat("dd/MM/yyyy").parse(this.txtFecha.getText()));
		} catch (ParseException e) { 
			e.printStackTrace();
		}
		
		this.recalcularDatos();
		p.setSerie(this.txtSerie.getText());
		p.setObservaciones(this.txtObservaciones.getText());
		p.setImporteFactura(Double.valueOf(this.txtTotalFactura.getText()));
		p.setBaseImponible(Double.valueOf(this.txtBaseImponible.getText()));
		p.setPorcentajeDescuento(Double.valueOf(this.txtPorcentajeDescuento.getText()));
		p.setPorcentajeIva(Double.valueOf(this.txtPorcentajeIva.getText()));
		  
		
		//Guardamos los datos del paciente y recargamos los datos
		if(!p.guardar()) {
			com.util.Alertas.alertaDatosErroneos(this.main.getStagePrincipal(), "Ha ocurrido un error al guardar los cambios."); 
	        return;
		}; 
 		 
		//Avisamos al usuario del proceso
        if(p.getId() > 0) com.util.Alertas.alertaDatosCorrectos(this.main.getStagePrincipal(), "La factura ha sido modificada exitosamente.");  
        else  com.util.Alertas.alertaDatosCorrectos(this.main.getStagePrincipal(), "La factura ha sido creada exitosamente.");

		this.recargarFactura();
	}

	@FXML public void btnVerFactura() {
		if(this.factura.getId() == 0) { 
			com.util.Alertas.alertaDatosErroneos(this.main.getStagePrincipal(), "Debe guardar los datos para visualizar el presupuesto");	
       } else {
    	   Informes.mostrarInformeFactura(this.main, this.factura); 
       }
	}

	@FXML public void btnEliminar() {
		 if(!com.util.Alertas.alertaConfirmacionUsuario(this.main.getStagePrincipal(), "Va eliminar la factura actual, ¿continuar?").equals(ButtonType.OK)) 
          	return;	//Preguntamos al usuario si desea eliminar el registro seleccionado 
     	 
		//Eliminamos la factura 
		if(!this.factura.esEliminable()) {
			com.util.Alertas.alertaDatosErroneos(this.main.getStagePrincipal(), "La factura no se puede eliminar."); 
	        return;
		}; 
		//Borramos la factura y cerramos la ventana actual
		if(this.factura.borrar()) {
			com.util.Alertas.alertaDatosCorrectos(this.main.getStagePrincipal(), "La factura ha sido eliminada exitosamente.");
			this.stage.close();
		}
		else com.util.Alertas.alertaDatosCorrectos(this.main.getStagePrincipal(), "Ha ocurrido un error al eliminar la factura."); 
		
	}
 
 
	private void recalcularDatos() {
		this.factura.calcularPie();
		this.txtBaseImponible.textProperty().setValue(String.valueOf(this.factura.getBaseImponible()));
		this.txtTotalFactura.textProperty().setValue(String.valueOf(this.factura.getImporteFactura()));
 
	}

 
 
}
