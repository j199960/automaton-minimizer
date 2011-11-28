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
	
	
	public ArrayList<Estado> dameElSiguientesEstados(String estimulo)
	{
		for(int i = 0; i < this.transiciones.size(); i++)
		{
			if(transiciones.get(i).dameEstimulo().equalsIgnoreCase(estimulo))
			{
				return transiciones.get(i).dameEstadosDestinos();
			}
		}
		return null;
	}
	
	public Transicion dameTransicion(String estimulo)
	{
		for(int i   = 0; i<transiciones.size(); i++)
		{
			if(transiciones.get(i).dameEstimulo().equalsIgnoreCase(estimulo))
			{
				return transiciones.get(i);
			}
		}
		return null;
	}
	


}
