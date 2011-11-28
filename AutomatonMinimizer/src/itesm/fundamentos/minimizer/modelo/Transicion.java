package itesm.fundamentos.minimizer.modelo;

import java.util.ArrayList;

public class Transicion {
	private String estimulo;
	private ArrayList<Estado> estadosDestino;
	
	/*
	 *  Constructor para el caso de un solo estado destino
	 */
	public Transicion(String estimulo, Estado estadoDestino)
	{
		
		this.estimulo = estimulo;
		this.estadosDestino = new ArrayList<Estado>();
		this.estadosDestino.add(estadoDestino);
	}
	
	/*
	 *  Constructor para el caso de varios casos destino
	 */
	public Transicion(String estimulo, ArrayList<Estado> estadosDestino)
	{
		this.estimulo = estimulo;
		this.estadosDestino = estadosDestino;
	}
	
	
	
	/*
	 * Obtener todos los estados para iterar sobre ellos
	 */
	public ArrayList<Estado> dameEstadosDestinos()
	{
		return this.estadosDestino;
	}
	
	public String dameEstimulo()
	{
		return this.estimulo;
	}
	
	
	

}
