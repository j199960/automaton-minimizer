package itesm.fundamentos.minimizer.tests;

import itesm.fundamentos.minimizer.modelo.*;

import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;


public class AutomataBuilder {
	
	private transient Collection<Object[]> data = null;
	

	public AutomataBuilder(final String FileName) throws IOException {
        this.data = loadFromFile(FileName);
    }
	
	private Collection<Object[]> loadFromFile(String FileName)
	{
		File file = new File(FileName);
		List listaDeAutomatas = new ArrayList();
		List rowdata;
		boolean expected = false;
		
		try
		{
			BufferedReader input =  new BufferedReader(new FileReader(file));
			String[] simbolos, estadosFinales = null, transiciones;
			String numeroDeSimbolos, numeroDeEstados, estadoInicial = null, numeroDeEstadosFinales, numeroDeTransiciones = null;
			String line = null;
			ArrayList<String> alfabeto = null;
			ArrayList<Estado> estados = null;
			Estado tempEstado = null;
			Automata tempAutomata = null;
			int contadorDeTransiciones = 0;
			
			
			int state = 0;
			//1  = Primer simbolo de start input se ha encontrado siguiente paso es contar simbolos
			//2  = Tiempo de obtener los simbolos siguiente paso es obtener numero de estados
			
			while((line = input.readLine()) != null)
			{
				if(!line.equals(""))
				{
					if(line.equalsIgnoreCase("start input"))
					{
						alfabeto = new ArrayList<String>();
						estados = new ArrayList<Estado>();
						//ArrayList<String> alfabeto = new ArrayList<String>();
					    state = 1;
					}
					else if(state == 1)
					{
						numeroDeSimbolos = line;
						state = 2;
					}
					else if(state == 2)
					{
						simbolos = line.split(" ");
						for(int i = 0; i<simbolos.length; i++)
						{
							alfabeto.add(simbolos[i]);
						}
						state = 3;
					}
					else if(state == 3)
					{
						numeroDeEstados = line;
						for(int i = 0; i<Integer.parseInt(numeroDeEstados); i++)
						{
							estados.add(new Estado(String.valueOf(i)));
						}
						state = 4;
					}
					else if(state == 4)
					{
						estadoInicial = line;
						state = 5;
					}
					else if(state == 5)
					{
						numeroDeEstadosFinales = line;
						state = 6;
					}
					else if(state == 6)
					{
						estadosFinales = line.split(" ");
						state = 7;
					}
					else if(state == 7)
					{
						numeroDeTransiciones = line;
						contadorDeTransiciones = 0;
						state = 8;
					}
					else if(state == 8)
					{
						transiciones = line.split(" ");
						estados.get(Integer.parseInt(transiciones[0])).agregaTransicion(new Transicion(transiciones[2], estados.get(Integer.parseInt(transiciones[1]))));
						contadorDeTransiciones++;
						if(contadorDeTransiciones == Integer.valueOf(numeroDeTransiciones))
						{
							state = 9;
						}
					}
					else if((state == 9)&&(line.equalsIgnoreCase("end input")))
					{
						//The automata has been completed
						ArrayList<Estado> tempEstadosFinales = new ArrayList();
						for(int i=0; i<estadosFinales.length; i++)
						{
							tempEstadosFinales.add(estados.get(Integer.parseInt(estadosFinales[i])));
						}
						tempAutomata = new Automata(estados, alfabeto, estados.get(Integer.valueOf(estadoInicial)), tempEstadosFinales);
						state = 10;
					}
					else if((state == 10)&&(line.equalsIgnoreCase("start expected")))
					{
						state = 11;
					}
					else if(state == 11)
					{
						if(line.equalsIgnoreCase("true"))
						{
							expected = true;
						}
						else
						{
							expected = false;
						}
						state = 12;
					}
					else if(state == 12)
					{
						rowdata = new ArrayList();
						rowdata.add(tempAutomata);
					    rowdata.add(expected);
					    listaDeAutomatas.add(rowdata.toArray());
					    state = 0;
					}
				}
			}
			input.close();
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
		
		/*
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
	    */

		return listaDeAutomatas;
	}

	public Collection<Object[]> getData() {
        return data;
    }
	
	
	

}
