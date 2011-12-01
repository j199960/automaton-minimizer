package itesm.fundamentos.minimizer.modelo;

import java.util.ArrayList;

public class Automata {
	
	private ArrayList<Estado> estados;
	private ArrayList<String> alfabeto;
	private Estado estadoInicial;
	private ArrayList<Estado> estadosDeAceptacion;
	private boolean transicionE;

	 
	public Automata(ArrayList<Estado> estados,
			ArrayList<String> alfabeto,
			Estado estadoInicial,
			ArrayList<Estado> estadosDeAceptacion)
	{
		this.estados = estados;
		this.alfabeto = alfabeto;
		this.estadoInicial = estadoInicial;
		this.estadosDeAceptacion = estadosDeAceptacion;	
		this.transicionE = false;
	}

	
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
		this.transicionE = false;

	}
	public Automata(Estado edoInicial, ArrayList<String> alfabeto, ArrayList<Estado> edosAceptacion)
	{
		this.estadoInicial = edoInicial;
		this.alfabeto = alfabeto;
		this.estadosDeAceptacion = edosAceptacion;
		this.estados = new ArrayList<Estado>();
		this.transicionE = false;

		
	}
	
	public ArrayList<Estado> DameEstadosDeAceptacion()
	{
		return this.estadosDeAceptacion;
	}
	
	public Estado DameElSiguienteEstado(Estado estadoActual, String estimulo)
	{
		return estadoActual;
	}

	public Estado DameElEstadoInicial()
	{
		return estadoInicial;
	}
	public boolean dameTransicionE()
	{
		return transicionE;
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
	public void setTransicionE(boolean transicionE)
	{
		this.transicionE = transicionE;
	}
	
	public boolean esUnEstadoFinal(String nombre)
	{
		boolean esEstadoFinal = false;
		
		for (int x = 0; x < this.estadosDeAceptacion.size(); x++)
		{
			String nombreEdoFinal = this.estadosDeAceptacion.get(x).dameNombre();
			if (nombreEdoFinal == nombre)
			{
				esEstadoFinal = true; 
				break;
				
			}
		}
		
		return esEstadoFinal;
				
	}
	
	public int dameEstado(String nombre)
	{
		int temp = -1;
		
		for (int x =0; x<this.estados.size(); x++)
		{
				if (nombre.contentEquals(this.estados.get(x).dameNombre()))
				{
					System.out.println(" nombre encontrado : " + nombre);
					temp = x;
					break;
				}
				else if (nombre.length() == this.estados.get(x).dameNombre().length())
				{
					if ( encuentraSplitNombre(nombre, this.estados.get(x).dameNombre() ) == true )
					{
						System.out.println("dameEstado: Se encontro que el estado "+ nombre + " es igual que "+this.estados.get(x).dameNombre());

						temp =x;
						break;
					}
					
					
				}
					
		}
		
		return temp;
	}
	
	public Estado dameElEstado(String nombre)
	{
		Estado estado = null;
				
		for (int x =0; x<this.estados.size(); x++)
		{
			//
			// primero checar sino esta separado por comas
			// ejem q0,q1
			//
			if (nombre.contentEquals(this.estados.get(x).dameNombre()))
			{
				System.out.println(" Estado encontrado : " + nombre);
				estado = this.estados.get(x);
				break;
			}
			
			else if (nombre.length() == this.estados.get(x).dameNombre().length())
			{
				if ( encuentraSplitNombre(nombre, this.estados.get(x).dameNombre() ) == true )
				{
					System.out.println("dameElEstado: Se encontro que el estado "+ nombre + " es igual que "+this.estados.get(x).dameNombre());
					
					estado = this.estados.get(x);
					break;
				}
				
				
			}
			
					
		}
		
		return estado;
	}
	
	public void dameTransicionesPorEstado(Estado estado)
	{
		//ArrayList <Transicion> transiciones = estado.dameTransiciones();
		System.out.println("dameTransicionesPorNombre () "+ estado.dameNombre());
		
		
		
		//
		// Basado en el nombre, obtener los estados destino.
		//
		
		ArrayList <Estado> estados = dameEstadosPorNombre(estado.dameNombre());
		
		
		for (int x = 0 ;x <this.DameAlfabeto().size(); x++)
		{
		
			String simbolo = this.DameAlfabeto().get(x);
			ArrayList<Estado> resultEstados = new ArrayList<Estado>();

			
			for (int y = 0; y<estados.size(); y++)
			{
				System.out.println(" dameTransicionesPorNombre () "+ estados.get(y).dameNombre());

				
				Estado estadoDestino = dameEstadoDestino (estados.get(y).dameNombre(), simbolo);
				
				if (estadoDestino == null)
				{
					System.out.println("dameTransicionesPorNombre : esto es nulo.. muy muy mal") ; 
					
					//
					// chequemos si es una transicion nulla... en ocasiones puede psar y su destion seria el mismo
					//
					if (estado.dameNombre().equals("Z"))
					{
						
						estadoDestino = estado;
					}
				}
				
				System.out.println("  dameTransicionesPorNombre () : con simbolo : "+ simbolo +" el estado destino es "+estadoDestino.dameNombre() );
				
				//Estado estadoEnAutomata = dameElEstado(estadoDestino.dameNombre());
				
				/*if (estadoEnAutomata == null)
				{
					System.out.println(" RJUA : dameTransicionesPorNombre () : no se encontro en el automata...." );

				}
				*/
				//estado.agregaTransicion(new Transicion (simbolo, estadoEnAutomata));
				
				resultEstados.add(estadoDestino);
			
				
			}
			
			estado.agregaTransicion(new Transicion (simbolo, resultEstados));
			
		}
		
		//System.out.println("RemoverTransiciones () - num. de transiciones " + transiciones.size());

		
	}
	
	private static void imprimeEstado(Estado estado)
	{

			ArrayList <Transicion> transiciones = estado.dameTransiciones();
			
			
			System.out.println(estado.dameNombre() + " : ");
			
			for (int x = 0; x<transiciones.size(); x++ )
			{
				Transicion transicion = transiciones.get(x);
				
				System.out.print(transicion.dameEstimulo() + " ->");
				
				//System.out.println(transicion.dameEstadosDestinos().size());
				int size = transicion.dameEstadosDestinos().size();
				for (int z =0 ; z< size ; z++)
				{
					System.out.print(transicion.dameEstadosDestinos().get(z).dameNombre() + " ");
					
				}
				System.out.println(" "); 
				
			}
		
	}
	
	public ArrayList<Estado> dameEstadosPorNombre(String nombreOriginal)
	{
	//	System.out.println("dameEstadosPorNombre () ");
		
		ArrayList<Estado> estados = new ArrayList<Estado> ();
		
		for (int x = 0 ; x<nombreOriginal.length(); x++)
		{
			String nombre = nombreOriginal.substring(x,x+1);
			
			//System.out.println("dameEstadosPorNombre () : Nombre en substring : "+ nombre);

			Estado estado = dameElEstado(nombre);
			
			estados.add(estado);

		}
		
		
		
		
		return estados;
		

	}
	public boolean encuentraSplitNombre (String nomOrigen, String nomDestino)
	{
		boolean result = false;
		

		
		for(int x = 0; x<nomOrigen.length(); x++)
		{
			String temp = nomOrigen.substring(x,x+1);
			//temp.ind
			if (nomDestino.contains(temp))
			{
				//System.out.println("$ el nombre contiene :  " + temp);
				result =true;
			}
			else
			{
				//System.out.println("$ el nombre NO contiene :  " + temp);
				result=false;
				break;
			}
		}
		
		
		
		return result;
		
	}
	
	//
	// Esta funcion va a buscar si el elemento ya se encuentra previamente agregado
	// sino lo encuentra, lo agregara..
	//
	
	public boolean encuentraYAgrega(Estado estado)
	{
		
		int temp = -1;
		boolean result = false;
		
		// 
		// si dameEstados regresa -1 quiere decir que este estado NO se encuentra en la lista
		// de estados.. por lo tanto se debe agregar...
		// si ya se encontrara en la lista de estados ya no se deberia agregar doble
		//
		temp = this.dameEstado(estado.dameNombre());
		
		//System.out.println(" Se encontro el valor en la lista de estados ? " + temp );
		
		//
		// si temp es -1 entonces tenemos que agregar el nuevo estado a la lista 
		//
		if (temp == -1 )
		{
			System.out.println(" Agregar estado  " + estado.dameNombre() + " al automata " );
			
			this.estados.add(estado);
			
			actualizaEstadosAceptacion(estado);
			
			result = true;
		}
		else
		{
			System.out.println(" El estado " +estado.dameNombre() + " fue encontrado en el automata... NO se agregara de nuevo");
		}
		return result;
		
	}
	
	//
	// Esta funcion va a checar si el estado ya se encuentra previamente agregado en el automata..
	// si lo encuentra dentro del automata va a regresar los estados  destinos 
	//
	
	public Estado  dameEstadoDestino(String  nombreEstado, String simbolo)
	{
		Estado estadoDestino = null;
				
		ArrayList <Estado> estados = this.DameEstados();
		
		for (int x = 0; x < estados.size(); x++)
		{
			Estado estado = estados.get(x);
			
			if(nombreEstado.equals(estado.dameNombre()))
			{
				//
				// Se ha encontrado el estado buscado.. ahora 
				// se tiene que ver cual es su estado destino..
				//
				//System.out.println("Se encontro estado " + estado.dameNombre());
				//System.out.println(" Contiene   " + estado.dameTransiciones().size() + " transiciones ");
				for (int y = 0; y < estado.dameTransiciones().size(); y ++)
				{
					Transicion transicion = estado.dameTransiciones().get(y);
					if (transicion.dameEstimulo().equals( simbolo))
					{
						//System.out.println("Se encontro transicion con estimulo " + transicion.dameEstimulo());
						System.out.println(" **** nombre destino  " + transicion.dameEstadosDestinos().get(0).dameNombre());
						
						//
						// TODO : quitar este get(0)
						// quiza esta funcion deber regresar un arraylist
						//
						estadoDestino = transicion.dameEstadosDestinos().get(0);
						//System.out.println(" RJUA size of dameEstadosDestinos  " + transicion.dameEstadosDestinos().size());
						
						break;
						
					}
				}
				
				
			}
		}
		
		if (estadoDestino== null)
		{
			System.out.println("No se encontro ningun estado.. se regresara nulo... favor de verificar? ");
		}
		
		
		
		return estadoDestino;
	}
	
	//
	// Esta funcion imprime todos los estados de un autmata.
	// De primera entrada se imprimen los varloes en la pantalla
	//
	
	public void imprimeAutomata()
	{
		System.out.println(" Imprime automata ");
		Estado edoInicial = this.estadoInicial;
		int y = 0;
		
		ArrayList <Estado> listaEstados = this.estados;
		
		
		for ( y = 0 ; y< listaEstados.size(); y ++)
		{
			Estado estado = listaEstados.get(y);
			ArrayList <Transicion> transiciones = estado.dameTransiciones();
			
			
			if (estado.dameNombre()== edoInicial.dameNombre())
			{
				System.out.print(" >> ");
			}
			 if (esUnEstadoFinal(estado.dameNombre()))
			{
				System.out.print(" EdoFinal : ");
			}
			
			
			
			System.out.println(estado.dameNombre() + " : ");
			//System.out.println( estado.dameTransiciones());
			
			for (int x = 0; x<transiciones.size(); x++ )
			{
				Transicion transicion = transiciones.get(x);
				
				System.out.print(transicion.dameEstimulo() + " ->");
				
				for (int z = 0 ; z<transicion.dameEstadosDestinos().size() ; z++)
				{
				
					System.out.println(transicion.dameEstadosDestinos().get(z).dameNombre());
					//
					
					//descomentar este ciclo jodido si tienes duda de las transiciones internas...
					/*System.out.println(" Dentro de transiciones ");
					
					Estado est = transicion.dameEstadosDestinos().get(z);
					
					for (int w = 0 ; w < est.dameTransiciones().size(); w++)
					{
						Transicion tran = est.dameTransiciones().get(w);
						for (int u = 0 ; u< tran.dameEstadosDestinos().size(); u++)
						{
							System.out.println("Nombre estado destino "+ tran.dameEstadosDestinos().get(u).dameNombre());
						}
						
					}*/
					
				}
				
			}
			
		}
		
		System.out.println("Numero total de estados en el automata es " + y );
	
	}
	
	//
	// Esta funcion se encarga de acutalizar los estados de aceptacion
	// Basicamente se fija en el nombre del estado y Si hace "match"
	// con los estados de aceptacion
	//
	private void actualizaEstadosAceptacion(Estado estado)
	{
		ArrayList<Estado> estadosAceptacion = this.DameEstadosDeAceptacion();
		
		for (int x = 0; x < estadosAceptacion.size(); x++)
		{
			Estado estadoAceptacion = estadosAceptacion.get(x);
			if (  	(estado.dameNombre().equals(estadoAceptacion.dameNombre() ) == false) &&
					(estado.dameNombre().contains(estadoAceptacion.dameNombre()) ) )
			{
				this.estadosDeAceptacion.add(estado);
			}
		}
	}
	
}
