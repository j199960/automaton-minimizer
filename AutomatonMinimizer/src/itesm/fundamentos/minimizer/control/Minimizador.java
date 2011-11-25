package itesm.fundamentos.minimizer.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import itesm.fundamentos.minimizer.modelo.*;

public class Minimizador {
	
	Automata automata = null;
	Map<String, ArrayList<String>> tablaInicial = null, tablaFinal = null;
	ArrayList<String> estadosDeEliminacion = null;
	
	public Minimizador(Automata automata)
	{
		this.automata = automata;
		this.estadosDeEliminacion = new ArrayList<String>();
		this.tablaInicial = new HashMap<String, ArrayList<String>>();
		this.tablaFinal = new HashMap<String, ArrayList<String>>();
	}
	
	public  Automata Minimiza() 
	{
		CreaLaTabla();
		
		EliminaEstados();
		
		ConstruyeAutomata();
			
		return automata;
	}

	
	private void CreaLaTabla()
	{
		ArrayList<String> temporal;
		
		//Creacion de la tabla
		for(int apuntador1 = 0; apuntador1<automata.DameEstados().size(); apuntador1++)
		{
			for(int apuntador2 = apuntador1+1; apuntador2<automata.DameEstados().size(); apuntador2++)
			{
				Estado estado1 = this.automata.DameEstados().get(apuntador1);
				Estado estado2 = this.automata.DameEstados().get(apuntador2);
				
				String estado = String.valueOf(apuntador1) + "," + String.valueOf(apuntador2);
				
				//Caso1: estado1 SI es de aceptacion Y estado2 NO es de aceptacion
				//Caso2: estado1 NO es de aceptacion Y estado2 SI es de aceptacion
				if((this.automata.DameEstadosDeAceptacion().indexOf(estado1)!= -1)&&(this.automata.DameEstadosDeAceptacion().indexOf(estado2)== -1))
				{
					this.estadosDeEliminacion.add(estado);
				}
				else if((this.automata.DameEstadosDeAceptacion().indexOf(estado1)== -1)&&(this.automata.DameEstadosDeAceptacion().indexOf(estado2)!= -1))
				{
					this.estadosDeEliminacion.add(estado);
				}
				
				
				temporal = new ArrayList<String>();
				for(int alfabeto = 0; alfabeto < automata.DameTamanoAlfateto(); alfabeto++)
				{
					ArrayList<Estado> estadosDestino1 = estado1.dameElSiguientesEstados(this.automata.DameAlfabeto().get(alfabeto));
					
					//if(estadosDestino1 == null || estadosDestino1.size() != 1)
					//	throw new Exception("Automata tiene varios estados destino o es nulo");
					
					ArrayList<Estado> estadosDestino2 = estado2.dameElSiguientesEstados(this.automata.DameAlfabeto().get(alfabeto));
					
					//if(estadosDestino1 == null || estadosDestino1.size() != 1)
					//	throw new Exception("Automata tiene varios estados destino o es nulo");
					
					//A este punto tenemos el estado de cada uno
					String destino1 = String.valueOf(this.automata.DameEstados().indexOf(estadosDestino1.get(0)));
					String destino2 = String.valueOf(this.automata.DameEstados().indexOf(estadosDestino2.get(0)));
					temporal.add(destino1 + "," + destino2);
				}
				this.tablaInicial.put(estado, temporal);
			}
		}
	}
	
	private void EliminaEstados()
	{
		this.tablaFinal.putAll(this.tablaInicial);
		
		for (Map.Entry<String, ArrayList<String>> elemento : tablaInicial.entrySet()) 
		{
			ArrayList<String> valores = elemento.getValue();
			
			for(int i = 0; i < valores.size(); i++)
			{
				String par = valores.get(i);
				for(int j = 0; j < this.estadosDeEliminacion.size(); j++)
				{
					String valorDerecho = this.estadosDeEliminacion.get(j);
					String[] temp  = valorDerecho.split(",");
					String valorReves = temp[1] + "," + temp[0];
					
					if(par.equalsIgnoreCase(valorDerecho)||par.equalsIgnoreCase(valorReves))
					{
						this.tablaFinal.remove(elemento.getKey());	
					}
				}
			}
		}
	}
	
	private void ConstruyeAutomata()
	{
		
	}
}
