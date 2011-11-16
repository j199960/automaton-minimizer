package itesm.fundamentos.minimizer.vista;
import java.util.ArrayList;

import itesm.fundamentos.minimizer.modelo.*;
import itesm.fundamentos.minimizer.control.*;

public class AutomatonMinimizer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println("Construyendo automata para expresion ab(a+b)*");
	    ArrayList<Estado> estados = new ArrayList<Estado>();
	    
	    //---------------INICIA CONSTRUCCION DEL AUTOMATA--------//
	    Estado q0 = new Estado("q0");
	    Estado q1 = new Estado("q1");
	    Estado q2 = new Estado("q2");
	    Estado z = new Estado("z");
	    q0.agregaTransicion(new Transicion("a", q1));
	    q0.agregaTransicion(new Transicion("b", z));
	   // q0.agregaTransicion(new Transicion("a", q1));
	   // q0.agregaTransicion(new Transicion("b", q1));
	    
	    q1.agregaTransicion(new Transicion("b", q2));
	    q1.agregaTransicion(new Transicion("a", z));
	    q2.agregaTransicion(new Transicion("a", q2));
	    q2.agregaTransicion(new Transicion("b", q2));
	    
	    z.agregaTransicion(new Transicion("a", z));
	    z.agregaTransicion(new Transicion("b", z));
	    
	    //Listo ya generamos las conexiones, ahora a generar el automata
	    estados.add(q0);
	    estados.add(q1);
	    estados.add(q2);
	    estados.add(z);
	    
	    ArrayList<String> alfabeto = new ArrayList<String>();
	    alfabeto.add("a");
	    alfabeto.add("b");
	    
	    // RJUA
	    // TODO. Crear una funcion cargarAutomata la cual cargara
	    // los valores de automatas desde un archivo y creara el objeto
	    // en tiempo de ejecucion.
	    //
	    
	    
	    Automata automata = new Automata(estados, alfabeto, q0,q2);
	    System.out.println("Automata construido!...");
	    
	  //---------------FIN CONSTRUCCION DEL AUTOMATA--------//

	    //1.- Clasificamos si es determinista
	    boolean esAFD = Clasificador.EsDeterminista(automata);
	    //2.- Si no es AFD tenemos que convertirlo
	    if(!esAFD)
	    {	
	    	System.out.println(" El automata es NO determinista ");
	    	automata = Convertidor.ConvierteloADeterminista(automata);
	    }
	    else
	    {
	    	System.out.println(" El automata es determinista ");
	    }
	    //3.-Ya convertido a AFD tenemos que minimizarlo
	    automata = Minimizador.Minimiza(automata);
	    
	    
	    

	}

}
