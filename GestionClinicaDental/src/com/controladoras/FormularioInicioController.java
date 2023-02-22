package com.controladoras;

import com.Main; 
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView; 

public class FormularioInicioController {

	Main main;
	@FXML ImageView imgLogoInicio; 
	
	public FormularioInicioController() {
		// TODO Auto-generated constructor stub
	}

	public void setMain(Main main) {
		this.main = main;		
	}

	
	@FXML
	private void initialize() { 
		this.imgLogoInicio.setImage(new Image(Main.class.getResource("resources/icono3.jpeg").toExternalForm()));
	}
}
