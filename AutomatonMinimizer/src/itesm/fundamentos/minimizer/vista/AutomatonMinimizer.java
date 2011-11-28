package itesm.fundamentos.minimizer.vista;
import java.util.ArrayList;
import java.io.File;
import org.w3c.dom.Document;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException; 
import itesm.fundamentos.minimizer.modelo.*;
import itesm.fundamentos.minimizer.control.*;
import itesm.fundamentos.minimizer.utils.*;

public class AutomatonMinimizer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Utils.ParseFromJflapFormat(args[0]);
		
		/*
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
	    
	    Minimizador minimizador = new Minimizador(automata);
	    automata = minimizador.Minimiza();
	    
	    */
	    
	    
	    /*
	     
	     
		try {

            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse (new File("C:\\temp\\gigante.xml"));
            ArrayList<Estado> estados = new ArrayList<Estado>();
            ArrayList<Transicion> transiciones = new ArrayList<Transicion>();
            String nombreEstadoInicial = null;
            ArrayList<String> nombresEstadosFinales = new ArrayList<String>();
            ArrayList<String> alfabeto = new ArrayList<String>();
            

            // normalize text representation
            doc.getDocumentElement ().normalize ();
            System.out.println ("Root element of the doc is " + 
                 doc.getDocumentElement().getNodeName());

            NodeList listaDeAutomatas = doc.getElementsByTagName("automaton");
            System.out.println("Total de automatas : " + listaDeAutomatas.getLength());
            
            NodeList listaDeEstados = doc.getElementsByTagName("state");
            
            NodeList listaDeTransiciones = doc.getElementsByTagName("transition");
            
            System.out.println("Total de estados : " + listaDeEstados.getLength());
            
            for(int s = 0; s<listaDeEstados.getLength(); s++)
            {
            	Node nodo = listaDeEstados.item(s);
            	NamedNodeMap atributos = nodo.getAttributes();
            	Node nombre = atributos.getNamedItem("id");
            	String NombreEstado = nombre.getNodeValue();
            	Estado estado = new Estado(NombreEstado);
            	estados.add(estado);
            	
            	if(nodo.getNodeType() == Node.ELEMENT_NODE)
            	{
            		Element elemento = (Element)nodo;
            		
            		NodeList estadoInicial = elemento.getElementsByTagName("initial");
            		NodeList estadosFinales = elemento.getElementsByTagName("final");
            		if(estadoInicial.getLength() == 1)
            		{
            			nombreEstadoInicial = NombreEstado;
            		}
            		if(estadosFinales.getLength() == 1)
            		{
            			nombresEstadosFinales.add(NombreEstado);
            		}
            	}
            	
            	
            	
            }
            
            for(int t = 0; t < listaDeTransiciones.getLength(); t++)
            {
            	Node nodoTransicion = listaDeTransiciones.item(t);
            	if(nodoTransicion.getNodeType() == Node.ELEMENT_NODE)
            	{
            		Element elementoTransicion = (Element)nodoTransicion;
            		
            		NodeList fromList = elementoTransicion.getElementsByTagName("from");
            		Element estadoOrigen = (Element)fromList.item(0);
            		
            		NodeList nodoEstadoOrigen = estadoOrigen.getChildNodes();
            		String strFrom = nodoEstadoOrigen.item(0).getNodeValue();
            		
            		NodeList toList = elementoTransicion.getElementsByTagName("to");
            		Element estadoDestino = (Element)toList.item(0);
            		
            		NodeList nodoEstadoDestino = estadoDestino.getChildNodes();
            		String strTo = nodoEstadoDestino.item(0).getNodeValue();
            		
            		NodeList readList = elementoTransicion.getElementsByTagName("read");
            		Element estimulos = (Element)readList.item(0);
            		
            		NodeList listaDeEstimulos = estimulos.getChildNodes();
            		
            		String[] arregloDeinput;
            		
            		if(listaDeEstimulos.item(0) == null)
            		{
            			arregloDeinput = new String[1];
            			arregloDeinput[0] = "E";
            		}
            		else
            		{
	            		String strEstimulos = listaDeEstimulos.item(0).getNodeValue();
	            		arregloDeinput = strEstimulos.split(",");
	            	}
            		
            		Estado  e1  = DameEstadoConNombre(estados, strFrom);
            		Estado e2 = DameEstadoConNombre(estados, strTo);
            		for(int index = 0; index<arregloDeinput.length; index++)
            		{
            			if(!alfabeto.contains(arregloDeinput[index]))
            			{
            				alfabeto.add(arregloDeinput[index]);
            				
            			}
            			Transicion tempTransicion = e1.dameTransicion(arregloDeinput[index]);
            			if(tempTransicion == null)
            			{
            				e1.agregaTransicion(new Transicion(arregloDeinput[index], e2));
            				//e1.agregaTransicion(new Transicion(arregloDeinput[index]));
            				int i = index+2;
            				
            			}
            			else
            			{
            				ArrayList<Estado> estadosDestino = tempTransicion.dameEstadosDestinos();
            				Estado e3 = DameEstadoConNombre(estadosDestino, e2.dameNombre());
            				if(e3 == null)
            				{
            					estadosDestino.add(e2);
            				}
            			}
            			
            		}
            		
         */   		
            		/*
            		e1.dameTransicion(estimulo);
            		*/
            		
            		
          
	}
            		
            		
            		
            		
            		//int i = t+2;
            		
           /* 		
            	}
            }
            
            Estado inicial = DameEstadoConNombre(estados, nombreEstadoInicial);
            ArrayList<Estado> estadosFinales = new ArrayList<Estado>();
            
            for(int indiceEstadosFinales = 0; indiceEstadosFinales<nombresEstadosFinales.size(); indiceEstadosFinales++)
            {
            	estadosFinales.add(DameEstadoConNombre(estados, nombresEstadosFinales.get(indiceEstadosFinales)));
            }
            Automata automata = new Automata(estados, alfabeto, inicial, estadosFinales);
            
            ImprimeAutomata(automata);
            
                    }catch (SAXParseException err) {
        System.out.println ("** Parsing error" + ", line " 
             + err.getLineNumber () + ", uri " + err.getSystemId ());
        System.out.println(" " + err.getMessage ());

        }catch (SAXException e) {
        Exception x = e.getException ();
        ((x == null) ? e : x).printStackTrace ();

        }catch (Throwable t) {
        t.printStackTrace ();
        }
        //System.exit (0);
	}
	
	private static Estado DameEstadoConNombre(ArrayList<Estado> estados, String nombre)
	{
		for(int i = 0; i<estados.size(); i++)
		{
			if(estados.get(i).dameNombre().equalsIgnoreCase(nombre))
			{
				return estados.get(i);
			}
			
		}
		return null;
	}
	
	private static void ImprimeAutomata(Automata automata)
	{
		System.out.println("Imprimiendo automata...");
		System.out.println("    Imprimiendo alfabeto...");
		for(int i = 0; i<automata.DameAlfabeto().size(); i++)
		{
			System.out.println("       " + automata.DameAlfabeto().get(i));
		}
		System.out.println("    Imprimiendo estados...");
		for(int j= 0; j<automata.DameEstados().size(); j++)
		{
			System.out.println("       " + "Nombre del estado: " + automata.DameEstados().get(j).dameNombre());
			for(int k = 0; k < automata.DameEstados().get(j).dameTransiciones().size(); k++)
			{
				System.out.println("       " + "   Estimulo: " + automata.DameEstados().get(j).dameTransiciones().get(k).dameEstimulo());
				for(int indiceSig = 0; indiceSig<automata.DameEstados().get(j).dameTransiciones().get(k).dameEstadosDestinos().size(); indiceSig++)
				{
					System.out.println("       " + "   Estados destino: " + automata.DameEstados().get(j).dameTransiciones().get(k).dameEstadosDestinos().get(indiceSig).dameNombre());
				}
			}
		}	
	}*/

}