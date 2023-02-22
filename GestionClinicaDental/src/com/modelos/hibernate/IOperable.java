package com.modelos.hibernate;
 

public interface IOperable extends ICargaDatos{

	public boolean guardar();
	public boolean esEliminable();
	public boolean borrar();
	

}
