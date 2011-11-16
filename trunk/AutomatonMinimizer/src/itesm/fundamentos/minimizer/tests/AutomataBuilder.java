package itesm.fundamentos.minimizer.tests;

import itesm.fundamentos.minimizer.modelo.*;

import java.util.List;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;


public class AutomataBuilder {
	
	private transient Collection<Object[]> data = null;

	public AutomataBuilder(final String FileName) throws IOException {
        this.data = loadFromFile(FileName);
    }
	
	private Collection<Object[]> loadFromFile(String FileName)
	{
		List listaDeAutomatas = new ArrayList();
		List rowdata = new ArrayList();
		
		
		//-------
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
	    boolean result = true;
	    //------
	    
	    rowdata.add(automata);
	    rowdata.add(result);
	    
	    listaDeAutomatas.add(rowdata.toArray());
	    
		return listaDeAutomatas;
	}

	public Collection<Object[]> getData() {
        return data;
    }
	
	
	

}
