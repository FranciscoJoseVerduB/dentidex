package com.modelos.hibernate;

public class Enumerados {

	public enum CitaEstado{ 
		PENDIENTE("Pendiente", 1),
		CONFIRMADO("Confirmado", 2),
		ANULADO("Anulado", 3);
		
		private final String nombre;
		private final int id;
		
		CitaEstado(String nombre, int id){
			this.nombre = nombre;
			this.id = id;
		}

		public String getNombre() {
			return nombre;
		}

		public int getId() {
			return id;
		} 
		public static CitaEstado getCitaEstado(int id) {
			for (CitaEstado estado : CitaEstado.values())
				if(estado.getId() == id) return estado;
			return null;
		}
	}

}
