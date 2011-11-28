package itesm.fundamentos.minimizer.control;

import java.util.ArrayList;
import java.util.Iterator;

import itesm.fundamentos.minimizer.modelo.*;

public class Clasificador {
	
	//
	// Reglas para considerar que un automata es determinista
	//  - tiene una transicion para cada simbolo del alfabeto.
	// 	- no transiciones vacias
	//  - con un mismo simbolo del alfabeto no puede existir dos transiciones
	//
	public static boolean EsDeterminista(Automata automata)
	{
		System.out.println(" >> EsDeterminista ");
		 
		boolean esAutomataDeterminista = true;
		
		// 
		 ArrayList<Estado> estados = new ArrayList<Estado>();
		 int i = 0; 
		 int y = 0 ;
		 int tamanoAlfabeto = automata.DameTamanoAlfateto();
		 estados = automata.DameEstados();
		 
		 System.out.println("tama;o alfabeto " +tamanoAlfabeto);
		 
		 // ya que se obtiene la lista de estados,
		 // ahora hay que verificar que cada estado cumpla
		 // con las reglas de un automata determinista
		 
		 System.out.println(estados.size());
		 
		 for (i=0; i<estados.size(); i++)
		 {
			 
			// System.out.println(i);
			 
			 //
			 // TempEstimuloTrans es una variable que va ir guardando los simbolos
			 // utilizados en las transiciones , para identificar SI existen
			 // mas de una transicion con el mismo estimulo (simbolo)
			 //
			 ArrayList<String> tempEstimuloTrans = new ArrayList <String>();
			 
			 Estado state = estados.get(i);
			 
			 //
			 // Cada estado tiene sus transiciones.. hay que verificar si cumplen con las
			 // reglas establecidas
			 // las siguientes lineas tendran que ser cambiadas a funciones independientes.
			 //
			 
			 ArrayList <Transicion> transiciones = new ArrayList<Transicion>();
			 transiciones = state.dameTransiciones();
			 
			 System.out.println(state.dameNombre() + ": " );
			 
			 for( y = 0; y< transiciones.size(); y++)
			 {
				 Transicion singleTransicion = transiciones.get(y);
				 String estimulo = singleTransicion.dameEstimulo();
				 
				 System.out.println(singleTransicion.dameEstimulo());
				 
				 
				 
				 //
				 // si alguna de las transiciones es vacia "E"
				 // automaticamente el automata es NO determinista
				 //
				 if (estimulo=="E" || estimulo =="e")
				 {
					 //
					 // 
					 //
					 esAutomataDeterminista = false;
					 System.out.println("Este automata tiene una transicion E ... por lo tanto es No determinista ");
					 automata.setTransicionE(true);
					 break;
				 }
				 
				 //
				 // Ahora hay que checar si se repite la transicion... 
				 // si es asi, quiere decir que hubo otra transicion previamente
				 //
				 if ( tempEstimuloTrans.contains(estimulo) )
				 {
					 esAutomataDeterminista = false;
					 System.out.println("Este automata tiene dos transiciones del estimulo  " + estimulo );
					 break;
				 
				 }
				 else
				 {
					 tempEstimuloTrans.add(estimulo);
				 }
				
			 }
			 
			 //
			 // Checar si las transiciones de este estado son MENORES al tama;o
			 // del alfabeto. Esto quisiera decir que el automata no
			 // cumple con la regla de que cada estado debe poder satisfacer a cada
			 // simbolo del alfabeto
 			 //
			 if (y < tamanoAlfabeto)
			 {
				 System.out.println("Estado " + state.dameNombre() + " solamente satisface " + y + " simbolo");
				 esAutomataDeterminista = false;
			 }
			
			 
			 //
			 // si ya se encontro alguna razon por ser NO determinista.. no hay necesidad
			 // seguir recorriendo el automata... 
 			 //
			 if (esAutomataDeterminista == false)
			 {
				 break;
			 }
			 
		 }
		
		 System.out.println("<< EsDeterminista");
		 
		 return esAutomataDeterminista ; 
	}
	
	public static Automata quitaTransicionesE (Automata automataConE)
	{
		
		ArrayList<Estado> estadosEpsilon = null;
		
		obtieneListaVacios (automataConE.DameEstados());
		
		
		
		return automataConE;
	}
	
	private static ArrayList<Estado> obtieneListaVacios (ArrayList<Estado> estados)
	{
		ArrayList<Estado> estadosEpsilon = new ArrayList<Estado>();
		Estado nuevoEstado = null;
		ArrayList <Estado> edoDestinos = null;
		
		for (int x = 0; x< estados.size(); x++)
		{
			Estado estado = estados.get(x);
			nuevoEstado = new Estado(estado.dameNombre());
			boolean existenTransicionesE = false;
		
			
			System.out.println(" Estado :  " + nuevoEstado.dameNombre());

			
			ArrayList<Transicion> transiciones = estado.dameTransiciones();
			
			for (int y = 0; y < transiciones.size(); y++)
			{
				Transicion transicion = transiciones.get(y);
				edoDestinos = new ArrayList<Estado> ();
				
				if (transicion.dameEstimulo() == "E")
				{
					ArrayList<Estado> edoDestinosIter = transicion.dameEstadosDestinos();
					
					for (int z = 0 ; z < edoDestinosIter.size(); z++)
					{
						System.out.println("Estado de transicion de E es " + edoDestinosIter.get(z).dameNombre());
						
						
						edoDestinos.add(edoDestinosIter.get(z));
						existenTransicionesE = true;
					}
				}
				else
				{
					//
					// Este estado no tiene transiciones E
					//
				}
			}
			
			if ( existenTransicionesE== true )
			{
				nuevoEstado.agregaTransicion(new Transicion("E", edoDestinos));
				estadosEpsilon.add(nuevoEstado);
				existenTransicionesE = false;
			}
				
			
		}
		
		return estadosEpsilon;
	}
	
	private static ArrayList <Estado> obtieneListaEpsilon (ArrayList <Estado> estados)
	{
		for (int x = 0 ; x< estados.size() ; x++)
		{
			Estado estado = estados.get(x);
			System.out.println(" Estado :  " + estado.dameNombre());
			
			//
			// se usa get(0) porque solo tendira una transicion E
			// a diferentes estados...
			Transicion transicion = estado.dameTransiciones().get(0);
			
			//for (int y = 0; )

		}
		return estados;
	}
}
