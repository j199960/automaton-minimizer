package itesm.fundamentos.minimizer.modelo;

import java.util.ArrayList;

public class Automata {
	
	private ArrayList<Estado> estados;
	private ArrayList<String> alfabeto;
	private Estado estadoInicial;
	private ArrayList<Estado> estadosDeAceptacion;

	 
	public Automata(ArrayList<Estado> estados,
			ArrayList<String> alfabeto,
			Estado estadoInicial,
			ArrayList<Estado> estadosDeAceptacion)
	{
		this.estados = estados;
		this.alfabeto = alfabeto;
		this.estadoInicial = estadoInicial;
		this.estadosDeAceptacion = estadosDeAceptacion;	
	}
	
	// RJUA
	// TODO verificar que se manden varios estados de aceptacion
	//
	
	public Automata(ArrayList<Estado> estados,
			ArrayList<String> alfabeto,
			Estado estadoInicial,
			Estado estadoDeAceptacion)
	{
		this.estados = estados;
		this.alfabeto = alfabeto;
		this.estadoInicial = estadoInicial;
		this.estadosDeAceptacion = new ArrayList<Estado>();
		this.estadosDeAceptacion.add(estadoDeAceptacion);	
	}
	
	public ArrayList<Estado> DameEstadosDeAceptacion()
	{
		return this.estadosDeAceptacion;
	}
	
	public Estado DameElSiguienteEstado(Estado estadoActual, String estimulo)
	{
		return estadoActual;
	}

	public Estado DameElEstado(String estimulo)
	{
		return estadoInicial;
	}
	
	public ArrayList<Estado> DameEstados()
	{
		return this.estados;
	
	}
	public int DameTamanoAlfateto()
	{
		return this.alfabeto.size();
	}
	public ArrayList<String> DameAlfabeto()
	{
		return this.alfabeto;
	}
}
