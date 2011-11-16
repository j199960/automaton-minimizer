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
}
