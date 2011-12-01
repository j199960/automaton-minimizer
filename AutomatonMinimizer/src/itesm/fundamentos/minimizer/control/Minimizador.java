package itesm.fundamentos.minimizer.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import itesm.fundamentos.minimizer.modelo.*;
import itesm.fundamentos.minimizer.utils.Utils;

public class Minimizador {
	
	Automata automata = null;
	Automata automataFinal = null;
	Map<String, ArrayList<String>> tablaInicial = null, tablaFinal = null, tablaMini = null;
	ArrayList<String> estadosDeEliminacion = null;
	
	public Minimizador(Automata automata)
	{
		this.automata = automata;
		this.estadosDeEliminacion = new ArrayList<String>();
		this.tablaInicial = new HashMap<String, ArrayList<String>>();
		this.tablaFinal = new HashMap<String, ArrayList<String>>();
		this.tablaMini = new HashMap<String, ArrayList<String>>();
		
		
	}
	
	public  Automata Minimiza() 
	{
		try
		{
			CreaLaTabla();
			
			EliminaEstados();
			
			ConstruyeAutomata();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return automataFinal;
	}

	
	private void CreaLaTabla() throws Exception
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
					continue;
				}
				else if((this.automata.DameEstadosDeAceptacion().indexOf(estado1)== -1)&&(this.automata.DameEstadosDeAceptacion().indexOf(estado2)!= -1))
				{
					this.estadosDeEliminacion.add(estado);
					continue;
				}
				
				
				temporal = new ArrayList<String>();
				for(int alfabeto = 0; alfabeto < automata.DameTamanoAlfateto(); alfabeto++)
				{
					ArrayList<Estado> estadosDestino1 = estado1.dameElSiguientesEstados(this.automata.DameAlfabeto().get(alfabeto));
					
					//if(estadosDestino1 == null || estadosDestino1.size() != 1)
					//	throw new Exception("Automata tiene varios estados destino o es nulo");
					
					ArrayList<Estado> estadosDestino2 = estado2.dameElSiguientesEstados(this.automata.DameAlfabeto().get(alfabeto));
					
					if(estadosDestino1 == null)
					{
						throw new Exception("El estado " + estado1.dameNombre() + " no tiene estado destino para la transicion " + this.automata.DameAlfabeto().get(alfabeto));
					}
					if(estadosDestino2 == null)
					{
						throw new Exception("El estado " + estado2.dameNombre() + " no tiene estado destino para la transicion " + this.automata.DameAlfabeto().get(alfabeto));
					}
					
					//if(estadosDestino1 == null || estadosDestino1.size() != 1)
					//	throw new Exception("Automata tiene varios estados destino o es nulo");
					
					//A este punto tenemos el estado de cada uno
					String destino1 = String.valueOf(this.automata.DameEstados().indexOf(estadosDestino1.get(0)));
					if(destino1.equals("-1"))
					{
						System.out.println("El estado destino " + estadosDestino1.get(0).dameNombre() + " no se encontro en el arreglo");
						//throw new Exception("El estado destino " + estadosDestino1.get(0) + " no se encontro en el arreglo");
					}
					String destino2 = String.valueOf(this.automata.DameEstados().indexOf(estadosDestino2.get(0)));
					if(destino2.equals("-1"))
					{
						System.out.println("El estado destino " + estadosDestino2.get(0).dameNombre() + " no se encontro en el arreglo");
						//throw new Exception("El estado destino " + estadosDestino2.get(0) + " no se encontro en el arreglo");
					}
					temporal.add(destino1 + "," + destino2);
				}
				this.tablaInicial.put(estado, temporal);
			}
		}
	}
	
	private void EliminaEstados()
	{
		this.tablaFinal.putAll(this.tablaInicial);
		ArrayList<String> tempEliminacion = new ArrayList<String>();
		boolean again = false;
		int tries = 0;
		int tablaFinalSize = tablaFinal.size();
		int tempSize = 0;
		//do
		//{
			again = true;
			while(again == true)
			{
				//Obtengo el tamano de la tabla final antes de realizar eliminacion
				tempSize = tablaFinal.size();
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
							if(!tempEliminacion.contains(elemento.getKey()))
							{
								tempEliminacion.add(elemento.getKey());
							}
							//again = true;
						}
					}
				}
				for(int t = 0; t < tempEliminacion.size(); t++)
				{
					if(!estadosDeEliminacion.contains(tempEliminacion.get(t)))
					{
						estadosDeEliminacion.add(tempEliminacion.get(t));
					}
				}
			}
			if(tablaFinal.size() == tempSize)
			{
				again = false;
			}
			tries++;
			}
		//}while(again == true);
	}
	
	private void ConstruyeAutomata()
	{
		ArrayList<String> nombresEstadosNuevos = new ArrayList<String>();
		int[] arregloDeEnteros = new int[this.automata.DameEstados().size()];
		ArrayList<String> listaDeEstadosAEliminar = new ArrayList<String>();
		ArrayList<String> estadosOriginalesADesarrollar = new ArrayList<String>();
		int id = 1;
		Map <String, ArrayList<String>> tablaTemp = new HashMap<String, ArrayList<String>>();
		boolean match = false;
		
		tablaTemp.putAll(tablaFinal);
		
		//OK, a construir el automata...
		
		
		
		//Tengo que ubicar los estados comunes y agruparlos
		Iterator it = tablaFinal.keySet().iterator();
		
		
		//Se utiliza un arreglo de enteros para poder agrupar
		//los estados QUE SON COMPUESTOS y son resultado de la eliminacion
		while(it.hasNext()) 
		{ 
			String key = (String) it.next(); 
			String[] splittedKey = key.split(",");
			while(it.hasNext())
			{
				String nextKey = (String) it.next();
				String[] nextSplittedKey = nextKey.split(",");
				for(int i = 0; i < splittedKey.length; i++)
				{
					for(int j = 0; j < nextSplittedKey.length; j++)
					{
						//Checamos si existe un Estado en comun
						if(splittedKey[i].equalsIgnoreCase(nextSplittedKey[j]))
						{
							match = true;
							listaDeEstadosAEliminar.add(nextKey);
							
							//OK tenemos un estado en comun, tenemos que "taggear" los elementos encontrados
							for(int m = 0; m < splittedKey.length; m++)
							{
								int temp = Integer.parseInt(splittedKey[m]);
								if(arregloDeEnteros[temp] == 0)
								{
									arregloDeEnteros[temp] = id;
								}
							}
							for(int n = 0; n < nextSplittedKey.length; n++)
							{
								int temp = Integer.parseInt(nextSplittedKey[n]);
								if(arregloDeEnteros[temp] == 0)
								{
									arregloDeEnteros[temp] = id;
								}
							}
						}
					}
				}
			}
			if(match == false)
			{
				for(int m = 0; m < splittedKey.length; m++)
				{
					int temp = Integer.parseInt(splittedKey[m]);
					if(arregloDeEnteros[temp] == 0)
					{
						arregloDeEnteros[temp] = id;
					}
				}
			}
			else
			{
				match = false;
			}
			tablaFinal.remove(key);
			for(int h = 0; h < listaDeEstadosAEliminar.size(); h++)
			{
				tablaFinal.remove(listaDeEstadosAEliminar.get(h));
			}
			it = tablaFinal.keySet().iterator(); 
			id++;
		}
		System.out.println("Se encontraron " + --id + " agrupaciones...");
		
		
		//Ya teniendo los estados cumpuestos agrupados, se procede 
		//a buscar aquellos estados que no son parte de la tabla y por lo tanto
		//son parte del AUTOMATA ORIGINAL
		
		for (Map.Entry<String, ArrayList<String>> elemento : tablaTemp.entrySet()) 
		{
			String str = elemento.getKey();
			String result = DameElId(arregloDeEnteros, str);
			
			ArrayList<String> valores = elemento.getValue();
			
			if(tablaMini.containsKey(result))
			{
				//Este elemento ya esta en la tabla minimizada
				//Checamos que los valores son correctos
				for(int i = 0; i < valores.size(); i++)
				{
					String val = DameElId(arregloDeEnteros, valores.get(i));
					if(!val.equals(tablaMini.get(result).get(i))&& !val.startsWith("or"))
					{
						System.out.println("ERROR Minimizacion fallo!...");
					}
				}
			}
			else
			{
				ArrayList<String> temp = new ArrayList<String>();
				//Este elemento ya esta en la tabla minimizada
				//Checamos que los valores son correctos
				for(int i = 0; i < valores.size(); i++)
				{
					String val = DameElId(arregloDeEnteros, valores.get(i));
					if(val.startsWith("or"))
					{
						//val = val.substring(val.lastIndexOf('r')+1);
						//if(Integer.valueOf(val) <= id)
						//{
					//		arregloDeEnteros[Integer.valueOf(val)] = ++id;
							//val = String.valueOf(id);
				//		}
						
						estadosOriginalesADesarrollar.add(val.substring(val.lastIndexOf('r')+1));
//						id++;
//						val = String.valueOf(id);
					}
					temp.add(val);
				}
				tablaMini.put(result, temp);
			}
		}
		int tamanoEstadosOr = estadosOriginalesADesarrollar.size();
		ArrayList<String> nuevosADesarrollar = new ArrayList<String>();
		
		int eInit = automata.DameEstados().indexOf(automata.DameElEstadoInicial());
		if(arregloDeEnteros[eInit] == 0)
		{
			if(!estadosOriginalesADesarrollar.contains(String.valueOf(eInit)))
			{
				estadosOriginalesADesarrollar.add(String.valueOf(eInit));
				
			}
		}
		
		boolean again = true;
		while(again == true)
		{	
			tamanoEstadosOr = estadosOriginalesADesarrollar.size();
			//Si hubo estados originales a desarrollar...
			if(estadosOriginalesADesarrollar.size() > 0)
			{
				for(int ori = 0; ori < estadosOriginalesADesarrollar.size(); ori++)
				{
					Estado e = automata.DameEstados().get(Integer.valueOf(estadosOriginalesADesarrollar.get(ori)));
					ArrayList<String> tp = new ArrayList<String>();
					for(int alf = 0; alf < automata.DameTamanoAlfateto(); alf++)
					{
						Transicion t = e.dameTransicion(automata.DameAlfabeto().get(alf));
						if(t.dameEstadosDestinos().size() == 1)
						{
							int ir = automata.DameEstados().indexOf(t.dameEstadosDestinos().get(0));
							String strid = DameElId(arregloDeEnteros, String.valueOf(ir));
							if(strid.startsWith("or"))
							{
								if(!nuevosADesarrollar.contains(strid.substring(strid.lastIndexOf('r')+1)))
								{
									String var = strid.substring(strid.lastIndexOf('r')+1);
									nuevosADesarrollar.add(var);
								}
							}
							tp.add(strid);
						}
						
					}
					tablaMini.put("or"+estadosOriginalesADesarrollar.get(ori), tp);
				}
				
				for(int n = 0; n<nuevosADesarrollar.size(); n++)
				{
					if(!estadosOriginalesADesarrollar.contains(nuevosADesarrollar.get(n)))
					{
						estadosOriginalesADesarrollar.add(nuevosADesarrollar.get(n));
					}
				}
				//Estado e = automata.DameEstados().indexOf(o)
			}
			if(tamanoEstadosOr == estadosOriginalesADesarrollar.size())
			{
				again = false;
			}
			
		}
		
		
		//Finalmente a crear el automata
		ArrayList<Estado> estados = new ArrayList<Estado>();
		Estado tempE = null;
		//Comenzando por lo facil, el alfabeto es el mismo que el automata inicial
				ArrayList<String> alfabeto = automata.DameAlfabeto();
				
				
		//Primero lleno estados		
		for (Map.Entry<String, ArrayList<String>> elemento : tablaMini.entrySet()) 
		{
			tempE = new Estado(elemento.getKey());
			estados.add(tempE);
			
			//ArrayList<String> estadoDestino = elemento.getValue();
			/*for(int iDestino = 0; iDestino < estadoDestino.size(); iDestino++)
			{
				String idDest = DameElId(arregloDeEnteros, estadoDestino.get(iDestino));
				Estado tmp = Utils.DameEstadoConNombre(estados, idDest);
				tempE.agregaTransicion(new Transicion (alfabeto.get(iDestino), tmp));
				
			}*/
		}
		
		//Luego lleno transiciones		
		for (Map.Entry<String, ArrayList<String>> elemento : tablaMini.entrySet()) 
		{
			tempE = Utils.DameEstadoConNombre(estados, elemento.getKey());
			ArrayList<String> estadoDestino = elemento.getValue();
			for(int iDestino = 0; iDestino < estadoDestino.size(); iDestino++)
			{
				String idDest = null;
				if(estadoDestino.get(iDestino).startsWith("or"))
				{
					idDest = estadoDestino.get(iDestino);
				}
				else
				{
					//NO ES UN ESTADO ORIGINAL, ENTONES PODEMOS USAR EL ID DIRECTAMENTE
					idDest = estadoDestino.get(iDestino);
					//idDest = DameElId(arregloDeEnteros, estadoDestino.get(iDestino));
				}
				Estado tmp = Utils.DameEstadoConNombre(estados, idDest);
				tempE.agregaTransicion(new Transicion (alfabeto.get(iDestino), tmp));
				
			}
		}
		
		String nombreEstadoInicial = automata.DameElEstadoInicial().dameNombre();
		
		String strId = DameElId(arregloDeEnteros, nombreEstadoInicial);
		Estado inicial = Utils.DameEstadoConNombre(estados, strId);
		
		ArrayList<Estado> estadosFinales = new ArrayList<Estado>();
        
        for(int indiceEstadosFinales = 0; indiceEstadosFinales<automata.DameEstadosDeAceptacion().size(); indiceEstadosFinales++)
        {
        	Estado aceptacion = automata.DameEstadosDeAceptacion().get(indiceEstadosFinales);
        	strId = String.valueOf(automata.DameEstados().indexOf(aceptacion));
        	strId = DameElId(arregloDeEnteros, strId);
        	estadosFinales.add(Utils.DameEstadoConNombre(estados, strId));
        }
        automataFinal = new Automata(estados, alfabeto, inicial, estadosFinales);
        
        Utils.ImprimeAutomata(automataFinal);
		
		
		int x = 0 + 3;
		
	}
	
	private boolean EncuentraEstadosComunes()
	{
		
		Iterator it = tablaFinal.keySet().iterator();
		
		while(it.hasNext()) 
		{ 
			Object key = it.next(); 
			Object val = tablaFinal.get(key); 
		}
		
		
		for (Map.Entry<String, ArrayList<String>> elemento : tablaFinal.entrySet()) 
		{
			String key = elemento.getKey();
			String[] splittedKeyArray = key.split(",");
			
			for(Map.Entry<String, ArrayList<String>> elemento2 : tablaFinal.entrySet())
			{
				
			}
			
		}
		return false;
	}
	
	
	private String DameElId(int[] arregloDeEnteros, String match)
	{
		String[] split = match.split(",");
		String resultado1 = "0";
		String resultado2 = "0";
		
		if(split.length == 1)
		{
			int result = Integer.valueOf(split[0]);
			resultado1 = String.valueOf(arregloDeEnteros[result]);
			if(Integer.valueOf(resultado1) == 0)
			{
				resultado1 = "or" + split[0];
			}
			return resultado1;
		}
	
		int cmp1 = Integer.valueOf(split[0]);
		int cmp2 = Integer.valueOf(split[1]);
		
		resultado1 = String.valueOf(arregloDeEnteros[cmp1]);
		resultado2 = String.valueOf(arregloDeEnteros[cmp2]);
		
		cmp1 = arregloDeEnteros[cmp1];
		cmp2 = arregloDeEnteros[cmp2];
		
		if(cmp1 != cmp2)
		{
			resultado1 = "0";
		}
		else
		{
			if(cmp1 == 0)
			{
				resultado1 = "or" + split[0];
				//resultado1 = "id";
			}
		}
		
		
		return resultado1;
	}
}
