package itesm.fundamentos.minimizer.modelo;

import java.util.ArrayList;

public class Estado {
	private String nombre;
	private ArrayList<Transicion> transiciones;
	
	/*
	 * Constructor proporcionando el nombre del estado
	 */
	public Estado(String nombre)
	{
		this.nombre = nombre;
		this.transiciones = new ArrayList<Transicion>();
	}
	
	/*
	 * Metodo para agregar transiciones a un estado construido
	 */
	public void agregaTransicion(Transicion transicion)
	{
		this.transiciones.add(transicion);
	}
	
	
	/*
	 * Metodo para obtener todas las transiciones e iterar sobre ellas
	 */
	public ArrayList<Transicion> dameTransiciones()
	{
		return this.transiciones;
	}
	
	public String dameNombre()
	{
		return this.nombre;
	}
	


}
