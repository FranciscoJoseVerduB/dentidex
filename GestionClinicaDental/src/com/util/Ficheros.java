package com.util;

import java.awt.Desktop;
import java.io.File; 
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.Main;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Ficheros {

	public static URL getResourceFrom(String path){
	    URL urlPath = Main.class.getResource("resources/" + path);
	    return urlPath;
	}
	public static URL getFileSistemaFrom(String path){
	    URL urlPath = null;
		try {
			urlPath = new File(path).toURI().toURL();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return urlPath;
	}
	
	
	public static String getExtension(String fileName) { 
	    char ch;
	    int len;
	    if(fileName==null || 
	            (len = fileName.length())==0 || 
	            (ch = fileName.charAt(len-1))=='/' || ch=='\\' || //in the case of a directory
	             ch=='.' ) //in the case of . or ..
	        return "";
	    int dotInd = fileName.lastIndexOf('.'),
	        sepInd = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));
	    if( dotInd<=sepInd )
	        return "";
	    else return fileName.substring(dotInd+1).toLowerCase();
	}
	
	public static File buscarFichero(Stage stage) {
		//Preguntamos al usuario que seleccione alguna imagen
		FileChooser file = new FileChooser();
		file.getExtensionFilters().addAll(
					new FileChooser.ExtensionFilter("Archivos de imagen", "*.png","*.jpg", "*.jpeg", "*.pdf", "*.zip")); 
		File fichero = file.showOpenDialog(stage);
		return fichero;
	}
	
	public static void abrirFichero(String fichero) {
		//Creamos un file
        File file = new File(fichero);
        
        //Miramos si soporta desktop
        if(!Desktop.isDesktopSupported()){
            System.out.println("Desktop no se soporta");
            return;
        }
        //Abrimos el fichero
        Desktop desktop = Desktop.getDesktop();
        if(file.exists())
			try {
				desktop.open(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	}
}
