package com.util;

 
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Date;

import javax.imageio.ImageIO;

import com.Main;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Imagenes { 
	
	
	public static ImageView buscarImagen(Stage stage) {
		//Preguntamos al usuario que seleccione alguna imagen
				FileChooser file = new FileChooser();
				file.getExtensionFilters().addAll(
							new FileChooser.ExtensionFilter("Archivos de imagen", "*.png","*.jpg", "*.jpeg")); 
				File fichero = file.showOpenDialog(stage);
				ImageView imageView = null;
				if(fichero != null) {
					FileInputStream input = null;
					try {
						//Cargamos la imagen seleccionada por el usuario 
						input = new FileInputStream(fichero.getPath());
						Image image = new Image(input);	
						String extensionFichero = Ficheros.getExtension(fichero.getPath());
						imageView = new ImageView(image);
						imageView.setUserData(guardarImagen(image, extensionFichero));
						//imageView = new ImageView(image); 
						//imageView.setUserData(fichero.getPath());
					} catch (FileNotFoundException e1) { 
						System.out.println(e1.toString());
					}
					finally {
						if (input != null)
							try {
								input.close();
							} catch (IOException e) { 
								e.printStackTrace();
							}
					}
				}
				return imageView;
	}
	 
	public static String guardarImagen(Image image, String formato) {
		if(image == null) return "";
		
		//Obtenemos la ruta donde se guardará el fichero
		Date date = new Date();
		@SuppressWarnings("deprecation")
		String rutaImagen = Main.getRutaGuardadoImagenes() + 
								date.getYear() + "_" + date.getMonth() + "_" + date.getDay() +
								date.getHours() + date.getMinutes() + date.getSeconds() + date.getTime() +
								"." + formato;
		File outputFile = new File(rutaImagen);
		
		//Si no existe el directorio se creará
		if(!outputFile.getParentFile().exists())
			outputFile.getParentFile().mkdirs();
		
		//Guardamos la imagen como fichero
	    BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
	    try {
	      ImageIO.write(bImage, "png", outputFile);
	    } catch (IOException e) {
	      throw new RuntimeException(e);
	    } 
	    //Devolvemos la ruta de la imagen.
		return rutaImagen; 
	}
	
	
	
	public static ImageView getImageViewFrom(String path){
	    URL imagePath = com.util.Ficheros.getResourceFrom(path);
	    
	    if(imagePath != null) {
	        return new ImageView(imagePath.toString());
	    }else{
	        return null;
	    }
	}
	public static ImageView getImageViewFromFileSistema(String path){
	    URL imagePath = com.util.Ficheros.getFileSistemaFrom(path);
	    
	    if(imagePath != null) {
	        return new ImageView(imagePath.toString());
	    }else{
	        return null;
	    }
	}
	public static ImageView getImageViewFromFileSistemaIcono(String path, int tamanyo) {
		ImageView imageView = getImageViewFromFileSistema(path);
	    if(imageView == null) return null;
		imageView.setFitHeight(tamanyo);
	    imageView.setFitWidth(tamanyo);
	    return imageView;
	}
	

}
