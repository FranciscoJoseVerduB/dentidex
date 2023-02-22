package com.util;
   
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField; 

public class Eventos {

 
	public static void setLimiteCaracteres(TextField textField, int longitud) {
		textField.setOnKeyTyped(evento ->{
			if(textField.getText() == null) return;	//Si la caja de texto no tiene datos no hará nada
			
			//Obtenemos los datos de la caja de texto e imponemos un limite de carácteres
			String texto = textField.getText(); 
			if(texto.length() > longitud) {
				textField.textProperty().setValue(texto.substring(0, longitud));
				textField.positionCaret(texto.length());
			}
		});
	} 
	
	public static void setLimiteCaracteres(TextArea textArea, int longitud) {
		textArea.setOnKeyTyped(evento ->{
			if(textArea.getText() == null ||
					textArea.getLength() == 0) return;	//Si la caja de texto no tiene datos no hará nada
			
			//Obtenemos los datos de la caja de texto e imponemos un limite de carácteres
			String texto = textArea.getText(); 
			if(texto.length() > longitud) {
				textArea.textProperty().setValue(texto.substring(0, longitud));
				textArea.positionCaret(texto.length());
			}
        });
	} 
	
	public static void setMascaraDouble(TextField textField) {
		textField.textProperty().addListener((observable, oldValue, newValue) -> {
			if(textField.getText() == null || textField.getLength() == 0) return;	//Si la caja de texto no tiene datos no hará nada
			//Intentamos convertirlo a Double
			try { 
				Double.parseDouble(textField.getText());
			}catch(Exception ex) {
				textField.textProperty().setValue(oldValue);
			} 
		}); 
	} 
	public static void setMascaraInteger(TextField textField) {
		textField.textProperty().addListener((observable, oldValue, newValue) -> {
			if(textField.getText() == null || textField.getLength() == 0) return;	//Si la caja de texto no tiene datos no hará nada
			//Intentamos convertirlo a Double
			try { 
				Integer.parseInt(textField.getText());
			}catch(Exception ex) {
				textField.textProperty().setValue(oldValue);
			} 
		}); 
	} 
	 
	
}
