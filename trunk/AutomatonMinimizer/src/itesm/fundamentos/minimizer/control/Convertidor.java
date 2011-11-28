package itesm.fundamentos.minimizer.control;

import java.util.ArrayList;
import itesm.fundamentos.minimizer.modelo.*;

public class Convertidor {
	
	public static Automata ConvierteloADeterminista(Automata automata)
	{
		
		System.out.println(" >> ConvierteDeterminista ");
		
	//	Estado estadoInicial = automata.DameElEstadoInicial();
		
		ArrayList<String> alfabeto = automata.DameAlfabeto();
		
		ArrayList<Estado> estadosEnAutomata = automata.DameEstados();
		
		ArrayList<Estado> estadosEncontrados = new ArrayList<Estado>();
		
		Automata automataND = new Automata(automata.DameElEstadoInicial(),automata.DameAlfabeto(),automata.DameEstadosDeAceptacion());
		
		// 
		// Hay que analizar las transiciones por cada simbolo en el alfabeto
		//
		for(int z = 0 ; z<estadosEnAutomata.size(); z++)
		{
			Estado thisEstado = estadosEnAutomata.get(z);
			Estado newEstado = new Estado(thisEstado.dameNombre());
			
			
			System.out.println(" ==  El nombre de este estado es  " + thisEstado.dameNombre());
			if (automataND.encuentraYAgrega(newEstado))
			{
				System.out.println("  Se agrego este estado a la lista "+ newEstado.dameNombre()) ;

			}
			/**/
		
			for(int x = 0; x<alfabeto.size(); x++)
			{
				String simbolo = alfabeto.get(x);
				String tempNombre = new String();
				ArrayList<Estado> edosDestino = new ArrayList<Estado>();
				
				System.out.println(" El simbolo del alfabeto es  " + simbolo);
		
				for(int y=0; y<thisEstado.dameTransiciones().size() ; y++)
				{
					
					// 
					// Checar hacia que estado van con los estimulos.
					// esta condicion ademas idefntifica cuando con un estimulos se va a
					// dos (o mas) estados diferentes)
					//
					
					if(simbolo==thisEstado.dameTransiciones().get(y).dameEstimulo())
					{
						//System.out.println(" El simbolo de esta transicion es  " + thisEstado.dameTransiciones().get(y).dameEstimulo());
						//System.out.println(" La lista de tansiciones es  " +estadoInicial.dameTransiciones().get(y).dameEstadosDestinos().size());						
						
						// This used to work
						tempNombre = tempNombre+thisEstado.dameTransiciones().get(y).dameEstadosDestinos().get(0).dameNombre() ;
						edosDestino.add(thisEstado.dameTransiciones().get(y).dameEstadosDestinos().get(0));
					}

					
				}
				// sino se encontro alguna transicion con este simboiolo .. se ira a una transicion Z 
				
				// this used to work
				if (tempNombre.isEmpty())
				{
					tempNombre="Z";

					//
					// no existe alguna transicion con este simbolo.. se crea una simbolo Z
					//
					edosDestino.add(new Estado ("Z"));
				}
				
				//System.out.println(" Al estado que se mueve es  " +tempNombre);
				
				//
				// En este punto tenemos todas las transiciones que necesitamos por estado..
				//  Ahora se tienen que agregar..
				//
				
				//
				// aqui se crea el nombre del nuevo estado destino..
				// Tal nombre puede ser compuesto de dos estados
				// ejemplo > q0q1
				//
				Estado estadoDestino = new Estado (tempNombre);
				
				//
				// estado Destino va agregar sus transiciones.. 
				// lo "tricky" es que las transiciones son de un segundo arreglo de estados
				// 
				// ejemplo
				// q0q1(Estado) -> (transicion)
				//				q0 (Estado)
				//				q1 (Estado)
				//
				// En el caso anterior estadoDestino = q0q1
				// y edosDestino = q0, q1
				estadoDestino.agregaTransicion(new Transicion(simbolo,edosDestino));
				
				//
				// new estado es el estado que esta dentro del automata directamente en la lista
				//
				newEstado.agregaTransicion(new Transicion (simbolo,estadoDestino));
				
				if(automataND.dameEstado(estadoDestino.dameNombre())==-1)
				{
					estadosEncontrados.add(estadoDestino);
					System.out.println(" Agregando el estado a la lista temporal  " +estadoDestino.dameNombre());
					
				}
				
			}
			
			
		
		}
		
		automataND.imprimeAutomata();
		
	//	imprimeEstados(estadosEncontrados);


		generarTransiciones(automata,automataND,estadosEncontrados);
		
		automataND.imprimeAutomata();

		//imprimeEstados(estadosEncontrados);
		
		System.out.println(" << ConvierteDeterminista ");
		
		return automata;
	}
	
	
	
	private static Automata generarTransiciones(Automata automataOriginal, Automata automataND, ArrayList<Estado> estadosEncontrados )
	{
		//ArrayList<Estado> listaEstados = automataND.DameEstados();
		
		System.out.println(" >> Generar TRansiciones ");
		
		for (int x = 0 ; x < estadosEncontrados.size(); x++)
		{
			Estado estado = estadosEncontrados.get(x);
			ArrayList <Transicion> transiciones = estado.dameTransiciones();
			ArrayList <String> alfabeto = automataND.DameAlfabeto();
			ArrayList <Transicion> nuevasTransiciones = new ArrayList<Transicion>();
			boolean transicionNula = false;
			
		
			// primero lo primero... agrega el estado temporal al automata.. 
			// eventualmente lo vas a buscar y modificar... 
			// pero por el momento hay que agregarlo.
			//
			automataND.encuentraYAgrega(estado);
			
			System.out.println(" ---  Estado encontrado en lista temporal " + estado.dameNombre());

			for (int w= 0 ; w < alfabeto.size(); w++)
			{
				String simbolo = alfabeto.get(w);
				String tempNombre = new String();
				Estado edoGenerado = null;
				Estado tempEstado = null;
				Estado edoEnAutomata = null;
				
				System.out.println("  Iniciando ciclo del alfabeto  "+ simbolo);

				//
				// Si por alguna razon no existen transiciones quiere decir que aun no se calculan....
				// hay que sacarlas basado en el nombre del estado...
				// 
				if (transiciones.size() == 0 || transicionNula == true)
				{
					
					if ( transicionNula == false )
					{
							automataND.dameTransicionesPorEstado(estado);
							estado.imprimeEstado();
							transicionNula = true;
					}
					
					//
					// Debido a que esta parte del codigo es "diferente"
					// tendremos que iterar de nuevo.. parecido que la parte de abajo
					// pero diferente ...  definitivamente mala practica de programacion 
					// 
					for (int y = 0; y<transiciones.size(); y++ )
					{
						
						Transicion transicion = transiciones.get(y);
						
						ArrayList<Estado> estadosDestino = transicion.dameEstadosDestinos();
						
						System.out.println( transicion.dameEstimulo() + " -> ");
						
						if (simbolo == transicion.dameEstimulo())
						{
						
							for (int z = 0; z< estadosDestino.size(); z++)
							{
								Estado edoDestino = estadosDestino.get(z);
								System.out.println("Estado destino  " + edoDestino.dameNombre());
								
								if (edoDestino != null)
								{
								
								
									if (edoDestino.dameNombre().equals("Z"))
									{
										System.out.println("El estado destino es Z ... se debe ignorar  " );
										//
										// Agregar el estado vacio
										//
										if(tempNombre.isEmpty())
										{
											tempNombre = "Z";
										}
				
									}
									else
									{
										if (! tempNombre.contains("Z"))
										{
											tempNombre = generaNombre(tempNombre, edoDestino.dameNombre());
										}
										else
										{
											tempNombre = removerEstadoZ(tempNombre);
											System.out.println("sales de funion remover");
											tempNombre = generaNombre(tempNombre, edoDestino.dameNombre());
										}
										
									}
									
								}
						
							} // ciclo for
							
							System.out.println(" >> Transicion del estado "+estado.dameNombre() + " con simbolo  " + transicion.dameEstimulo()+ "  va hacia el estado " + tempNombre + " <<");

							//
							// Importante : solia generar un estado nuevo.. ahora hay que checar si existe uno previamente creado..
							//
							tempEstado = automataND.dameElEstado(tempNombre);
							
							//
							// si se regresa null quiere decir que NO se encuentra en el automata.. 
							// asi que se tendra que hacer el trabajo sucio para agregarlo ;)
							if(tempEstado == null)
							{
								//tempEstado = new Estado(tempNombre);
								
								tempEstado = buscarEstadoEnArreglo(tempNombre, estadosEncontrados);
								
								if (tempEstado == null)
								{
									//
									// Esto NO es esperado... solo crea el estado.. imprime el error para que no truene 
									//
									tempEstado = new Estado(tempNombre);
									System.out.println("  PELIGRO PELIGRO : no se encuentra EStado ni en automata, ni en la lista temporal ");
									
									//automataND.dameTransicionesPorEstado(tempEstado);
									
									//
									// En este caso tmb se debe agregar en la lista temporal...
									//
									estadosEncontrados.add(tempEstado);
		
								}
								//
								// Este es un caso que puede ocurrir cuando la transicion destino
								// aun no se ha sido encontrado previamente
								// en este caso se debe agregar al automata y como todo..
								// eventualmente se asignara su salida
								// 
								automataND.encuentraYAgrega(tempEstado);
							} // temp == null
									
						}// if simbolo == transicion.dameEstimulo()
						
						if (simbolo == transicion.dameEstimulo())
						{
							System.out.println(" El estado " +estado.dameNombre()+ " tiene estas transiciones "+estado.dameTransiciones().size());
							nuevasTransiciones.add(new Transicion (simbolo, tempEstado));
						}
						
					} // for transiciones
					
				} // transicion == nula 
				else
				{
					transicionNula = false;
					
					for (int y = 0; y<transiciones.size(); y++ )
					{
						
						Transicion transicion = transiciones.get(y);
						
						ArrayList<Estado> estadosDestino = transicion.dameEstadosDestinos();
						
						System.out.println( simbolo + " -> ");
						
						for (int z = 0; z< estadosDestino.size(); z++)
						{
							
							Estado edoDestino = estadosDestino.get(z);
	
							System.out.println("Estado destino  " + edoDestino.dameNombre());
							
							edoGenerado = automataND.dameEstadoDestino(edoDestino.dameNombre(), simbolo);
							//edoGenerado = edoDestino;
							// 
							// En este punto se tiene la lista de estados destino.
							// Ahora lo que sigue es identificar cada uno hacia que transicion van
							// 
							// ejemplo:
							// q0 : 
							// a -> q1, q2
							// b -> q3
							// Este loop itera con los estados q1, q2 y q3.
							//
							if (edoGenerado != null)
							{
							
							
								if (edoGenerado.dameNombre().equals("Z"))
								{
									System.out.println("El estado destino es Z ... se debe ignorar  " );
									//
									// Agregar el estado vacio
									//
									if(tempNombre.isEmpty())
									{
										tempNombre = "Z";
									}
			
								}
								else
								{
									if (! tempNombre.contains("Z"))
									{
										tempNombre = generaNombre(tempNombre, edoGenerado.dameNombre());
										//tempNombre = tempNombre + edoGenerado.dameNombre();
									}
									else
									{
										tempNombre = removerEstadoZ(tempNombre);
										System.out.println("sales de funion remover");
										tempNombre = generaNombre(tempNombre, edoGenerado.dameNombre());
									}
									
									
								}
								
							}
							else
							{
		
								System.out.println("El estado generado es Z osea nulo... se debe ignorar  " );
								//
								// Agregar el estado vacio
								//
								tempNombre = "Z";
								
							}
		
						}
						System.out.println(" >> Transicion del estado "+estado.dameNombre() + " con simbolo  " + simbolo+ "  va hacia el estado " + tempNombre + " <<");
						
	
						//
						// Importante : solia generar un estado nuevo.. ahora hay que checar si existe uno previamente creado..
						//
						tempEstado = automataND.dameElEstado(tempNombre);
						
						//
						// si se regresa null quiere decir que NO se encuentra en el automata.. 
						// asi que se tendra que hacer el trabajo sucio para agregarlo ;)
						if(tempEstado == null)
						{
							//tempEstado = new Estado(tempNombre);
							
							tempEstado = buscarEstadoEnArreglo(tempNombre, estadosEncontrados);
							
							if (tempEstado == null)
							{
								//
								// Esto NO es esperado... solo crea el estado.. imprime el error para que no truene 
								//
								tempEstado = new Estado(tempNombre);
								System.out.println("  PELIGRO PELIGRO : no se encuentra EStado ni en automata, ni en la lista temporal ");
								
								//automataND.dameTransicionesPorEstado(tempEstado);
								
								//
								// En este caso tmb se debe agregar en la lista temporal...
								//
								estadosEncontrados.add(tempEstado);
	
							}
							//
							// Este es un caso que puede ocurrir cuando la transicion destino
							// aun no se ha sido encontrado previamente
							// en este caso se debe agregar al automata y como todo..
							// eventualmente se asignara su salida
							// 
							automataND.encuentraYAgrega(tempEstado);
						}
						
						edoEnAutomata = automataND.dameElEstado(estado.dameNombre());
						
						if (edoEnAutomata == null)
						{
							
							//
							// En teoria este caso no debe pasar, aun asi imprime un mensaje de error 
							//  y continua como "deberia" ser
							//
							System.out.println("ERROR : este estado no deberia ser nulo");
							edoEnAutomata = new Estado(estado.dameNombre());
						}
						
	
						edoEnAutomata.imprimeEstado();
						
						nuevasTransiciones.add(new Transicion (simbolo, tempEstado));
						
									
					} // for transiciones
				}
				
			}// for alfabeto
			
			//
			// borrar transiciones?
			//
			//imprimeEstado(estado);
			removerTransiciones(estado);
			agregarTransiciones(estado,nuevasTransiciones);
			transicionNula = false;
			
		//	imprimeEstado(estado);

			
		} //estados temporales encontrados
		
		//automataND.imprimeAutomata();
		
		System.out.println(" << Generar transiciones  ");
		return automataND;
	}
	
	
	private static void removerTransiciones(Estado estado)
	{
		ArrayList <Transicion> transiciones = estado.dameTransiciones();
		System.out.println("RemoverTransiciones () "+ estado.dameNombre()+ " - num. de transiciones " + transiciones.size());
		
		transiciones.clear();
		System.out.println("RemoverTransiciones () - num. de transiciones " + transiciones.size());

		
	}
	private static void agregarTransiciones(Estado estado, ArrayList<Transicion> nuevasTransiciones)
	{
		ArrayList <Transicion> transiciones = estado.dameTransiciones();
		//System.out.println("agregarTransiciones () - num. de transiciones " + transiciones.size());
		//System.out.println("agregarTransiciones () "+ estado.dameNombre() +" - nuevas transiciones " + nuevasTransiciones.size());
	
		for(int x = 0 ; x< nuevasTransiciones.size(); x++)
		{
			System.out.println(" nombre destino transicion" + nuevasTransiciones.get(x).dameEstadosDestinos().size());

			transiciones.add(nuevasTransiciones.get(x));
		}
		
	
		System.out.println("agregarTransiciones () - num. de transiciones " + transiciones.size());
		
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
			
			else if (nombre.length() == estados.get(x).dameNombre().length())
			{
				if ( encuentraSplitNombre(nombre, estados.get(x).dameNombre() ) == true )
				{
					System.out.println(" buscarEstadoEnArreglo() : Se encontro que el estado "+ nombre + " es igual que "+estados.get(x).dameNombre());
					
					estado = estados.get(x);
					break;
				}
				
				
			}
			
					
		}
		
		return estado;	
	}
	public static boolean encuentraSplitNombre (String nomOrigen, String nomDestino)
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
	private static void imprimeEstados(ArrayList<Estado> estados)
	{
		System.out.println("Estados dentro del arreglo temporal ");
		for (int y = 0 ; y< estados.size(); y ++)
		{
			Estado estado = estados.get(y);
			 imprimeEstado(estado);
			
		}
		
	}
	
	//
	// El objetivo de esta funcion es NO repetir estados que ya estan registrados... 
	// ejemplo si el estado es ACF y se recibe otra F
	// que el estado final NO sea ACFF
	//
	private static String generaNombre(String nombreOriginal, String nombreEstadoNuevo)
	{
		String result = new String();
	
	
		System.out.println("generaNombre");
		if (nombreOriginal.contains(nombreEstadoNuevo))
		{
			System.out.println("generaNombre - if");

			//
			// no  hya necesicidad de hacer concatenacion...
			//
			result = nombreOriginal;
		}
		else
		{
			System.out.println("generaNombre - else");

			result = nombreOriginal + nombreEstadoNuevo;
		}
		
		return result;
	} 
	//
	// Esta funcion recibe u string que contiene una "Z"
	// la cual se debe remover ya que no se debe de contar
	//
	private static String removerEstadoZ (String nombre)
	{
		String result = new String();
		
		System.out.println("removerEstadoZ : nombre recibido " + nombre);

		int index = nombre.indexOf("Z");
		
		System.out.println("removerEstadoZ : index encontrado " + index);

		if (index >0 )
		{
		
			result = nombre.substring(0,index-1);
			result= result+nombre.substring(index+1,nombre.length());
		}
		else
		{
			//
			// nothing to do
			//
		}
		System.out.println("removerEstadoZ : nombre a regresar " + result);

		
		return result;
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
				for (int z =0 ; z< transicion.dameEstadosDestinos().size(); z++)
				{
					System.out.print(transicion.dameEstadosDestinos().get(z).dameNombre() + " ");
					
				}
				System.out.println(" "); 
				
			}
		
	}


}
