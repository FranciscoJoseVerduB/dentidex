package com.modelos.hibernate;

import java.util.ArrayList;

public interface ICargaDatos {
	public boolean cargar();
	public ArrayList<?> getLista();
	public String toString();

}
