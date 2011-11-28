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
		
		ArrayList<Estado> estadosConVacio = null;
		
		estadosConVacio = obtieneListaVacios (automataConE.DameEstados());
		
		estadosEpsilon = obtieneListaEpsilon (estadosConVacio);
		
		//
		// En este punto sabemos las transiciones vacias y ademas la conversion Epsilon...
 		// ahora sigue identificar de cada estado dentro de Epsilon a que estado 
		// se van con determinado simbolo.
		//
		for (int y = 0 ; y< estadosEpsilon.size(); y++)
		{
			Estado estado = estadosEpsilon.get(y);
			
			for (int x = 0; x < automataConE.DameAlfabeto().size(); x ++)
			{
				//
				// listaEpsilonDestino  guarda todos los estados destinos de las transiciones Epsilon
				//
				ArrayList <Estado> listaEpsilonDestino = null;
				
				ArrayList <Estado> obtenTrancisionesAgregar = null;

				String simbolo = automataConE.DameAlfabeto().get(x);
				
				listaEpsilonDestino= obtenTransicionesPorSimbolo (estado, simbolo, automataConE);
				
				//
				// Ahora que se tiene la lista de transiciones Destino de Epsilon.. a ver a ver
				// sus transiciones originales a donde van... con esto ya se podra ver cuales serian las
				// transiciones adicionales a agregar.
				//
				
				obtenTrancisionesAgregar = obtenTranscionesAgregar (listaEpsilonDestino, estadosEpsilon);
				
				if(obtenTrancisionesAgregar != null )
				{
					modificaTransiciones(estado, obtenTrancisionesAgregar, simbolo, automataConE);
				}

			}
			borraTransicionesE (estado,automataConE);
			
		}
		
		
		return automataConE;
	}
	private static void borraTransicionesE(Estado estado, Automata automata)
	{
		Estado estadoAutomata = automata.dameElEstado(estado.dameNombre());

		ArrayList<Transicion> transiciones = estadoAutomata.dameTransiciones();
		
		for (int x = 0; x< estadoAutomata.dameTransiciones().size(); x++)
		{
			Transicion transicion = estadoAutomata.dameTransiciones().get(x);
			if (transicion.dameEstimulo() == "E")
			{
				estadoAutomata.dameTransiciones().remove(x);
			}
		}
	}
	
	private static void modificaTransiciones(Estado estado, ArrayList<Estado> estadosAgregar, String simbolo, Automata automata)
	{
		
		Estado estadoAutomata = automata.dameElEstado(estado.dameNombre());
		boolean repetido = true;
		
		for (int y = 0; y < estadosAgregar.size() ; y++)
		{
			
			repetido = estadoAutomata.buscaEstadoEnTransiciones (estadosAgregar.get(y), simbolo);
			if (repetido  == false)
			{
				estadoAutomata.agregaTransicion(new Transicion (simbolo, estadosAgregar.get(y)));
		
			}
			else
			{
				System.out.println("ModificaTransiciones : ya se contiene el estado "+ estadosAgregar.get(y).dameNombre());
			}
		}
		
	}
	private static ArrayList<Estado> obtenTranscionesAgregar (ArrayList<Estado> listaEpsilonDestino, ArrayList<Estado> estadosEpsilon)
	{
		ArrayList<Estado> obtenTransicionesAgregar = new ArrayList<Estado> ();
		
		for (int x = 0 ; x < listaEpsilonDestino.size(); x ++)
		{
			Estado estadoEpsilon = listaEpsilonDestino.get(x);
			
			System.out.println("obtenTranscionesAgregar : El nombre del estado en Epsilon es " + estadoEpsilon.dameNombre());
			
			ArrayList <Estado> ListaEstadosDestino  = buscarEstadosDestino (estadoEpsilon.dameNombre(),estadosEpsilon);
			
			//
			// si llegas hasta este punto y no lo encuentras en el destino.. quiere decir
			// que NO hay una transicion vacia.. lo cual lo unico que indica es que
			// tendras que ir a tu mismo estado...
			//
			if(ListaEstadosDestino == null || ListaEstadosDestino.isEmpty())
			{
				System.out.println("obtenTranscionesAgregar : El estado destino tiene transiciones vacias. Agregate a ti mismo");
				if(obtenTransicionesAgregar.contains(estadoEpsilon) == false)
				{
					obtenTransicionesAgregar.add(estadoEpsilon);
				}

			}
			else
			{
				for (int z = 0; z< ListaEstadosDestino.size(); z++)
				{
					obtenTransicionesAgregar.add(ListaEstadosDestino.get(z));
				}
			}
			//obtenTransicionesAgregar. = ListaEstadoDestino;
			
			System.out.println ("haz tiempo");
			
		}
		
		
		return obtenTransicionesAgregar;
	}
	private static ArrayList<Estado> buscarEstadosDestino(String nombre, ArrayList<Estado> estados)
	{
		ArrayList <Estado> estadosDestino = new ArrayList<Estado> ();
		Estado destinoEpsilon = buscarEstadoEnArreglo (nombre, estados);
		
		if (destinoEpsilon !=null )
		{
			Transicion transicion = destinoEpsilon.dameTransiciones().get(0);
			
			
			for ( int x = 0 ; x< transicion.dameEstadosDestinos().size(); x++)
			{
				Estado estado = transicion.dameEstadosDestinos().get(x);
				
				System.out.println("buscarEstadosDestino : nombre estado " + estado.dameNombre());
				
				estadosDestino.add(estado);
			}
		}
		
		return estadosDestino;
	}
	
	private static Estado buscarEstadoEnArreglo(String nombre, ArrayList<Estado> estados)
	{
		Estado estado = null;
		
		for (int x =0; x<estados.size(); x++)
		{
			//
			// primero checar sino esta separado por comas
			// ejem q0,q1
			//
			if (nombre.contentEquals(estados.get(x).dameNombre()))
			{
				System.out.println(" buscarEstadoEnArreglo(): Estado encontrado : " + nombre);
				estado = estados.get(x);
				break;
			}
		
					
		}
		
		return estado;	
	}
	private static ArrayList<Estado> obtenTransicionesPorSimbolo (Estado estadoEpsilon, String simbolo, Automata automata)
	{
		Transicion transicion = estadoEpsilon.dameTransicion("E");
		ArrayList<Estado> listaDeEstadosDestino = new ArrayList <Estado> ();
		
		if(transicion == null)
		{
			System.out.println("obtenTransicionesPorSimbolo : ERROR : La transicion es nula");
		}
		
		for (int x = 0 ; x<transicion.dameEstadosDestinos().size(); x++)
		{
			ArrayList<Estado> estadosDestino = transicion.dameEstadosDestinos();
			
			for (int y = 0 ; y< estadosDestino.size(); y++)
			{
				Estado estado = estadosDestino.get(y);
				
				System.out.println("obtenTransicionesPorSimbolo : El estado destino es : " + estado.dameNombre());
				
				Estado edoDestino = automata.dameEstadoDestino(estado.dameNombre(), simbolo);
				if(edoDestino != null)
				{
					
				
					if (listaDeEstadosDestino.contains(edoDestino) == false)
					{
						listaDeEstadosDestino.add(edoDestino);
					}
					else
					{
						System.out.println("obtenTransicionesPorSimbolo : La lista destino ya contiene : " + edoDestino.dameNombre());
	
					}
				}
				else
				{
					System.out.println("obtenTransicionesPorSimbolo : no se encontro estado destino");

				}
			}
		}
		return listaDeEstadosDestino;
		
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
						System.out.println("obtieneListaVacios:  Estado :  " + nuevoEstado.dameNombre());

						System.out.println("obtieneListaVacios: Estado de transicion de E es " + edoDestinosIter.get(z).dameNombre());
						
						
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
			System.out.println("obtieneListaEpsilon: Estado :  " + estado.dameNombre());
			
			//
			// se usa get(0) porque solo tendira una transicion E
			// a diferentes estados...
			Transicion transicion = estado.dameTransiciones().get(0);
			
			for (int y = 0; y < transicion.dameEstadosDestinos().size(); y ++)
			{
				Estado edoDestino = transicion.dameEstadosDestinos().get(y);
				
				System.out.println("obtieneListaEpsilon: Estado destino :  " + edoDestino.dameNombre());
				
				//
				// Por default en una transicion Epsilon se debe incluir el mismo estado
				//

			}
			
			transicion.dameEstadosDestinos().add(estado);

			
			
			
			//for (int y = 0; )

		}
		return estados;
	}
}
