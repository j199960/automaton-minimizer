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

public class AutomatonMinimizer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
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
		try {

            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse (new File("C:\\temp\\Ejemplo.xml"));
            ArrayList<Estado> estados = new ArrayList<Estado>();
            String nombreEstadoInicial = null;
            ArrayList<String> nombresEstadosFinales = new ArrayList<String>();

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
            	Node nombre = atributos.getNamedItem("name");
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
            		String strTo = nodoEstadoOrigen.item(0).getNodeValue();
            		
            		Estado  e1  = DameEstadoConNombre(estados, strFrom);
            		Estado e2 = DameEstadoConNombre(estados, strTo);
            		
            		
            		
            		
            		int i = t+2;
            		
            		
            	}
            }
            
            
            
            
            NodeList listOfPersons = doc.getElementsByTagName("person");
            int totalPersons = listOfPersons.getLength();
            System.out.println("Total no of people : " + totalPersons);

            for(int s=0; s<listOfPersons.getLength() ; s++){


                Node firstPersonNode = listOfPersons.item(s);
                if(firstPersonNode.getNodeType() == Node.ELEMENT_NODE){


                    Element firstPersonElement = (Element)firstPersonNode;

                    //-------
                    NodeList firstNameList = firstPersonElement.getElementsByTagName("first");
                    Element firstNameElement = (Element)firstNameList.item(0);

                    NodeList textFNList = firstNameElement.getChildNodes();
                    System.out.println("First Name : " + 
                           ((Node)textFNList.item(0)).getNodeValue().trim());

                    //-------
                    NodeList lastNameList = firstPersonElement.getElementsByTagName("last");
                    Element lastNameElement = (Element)lastNameList.item(0);

                    NodeList textLNList = lastNameElement.getChildNodes();
                    System.out.println("Last Name : " + 
                           ((Node)textLNList.item(0)).getNodeValue().trim());

                    //----
                    NodeList ageList = firstPersonElement.getElementsByTagName("age");
                    Element ageElement = (Element)ageList.item(0);

                    NodeList textAgeList = ageElement.getChildNodes();
                    System.out.println("Age : " + 
                           ((Node)textAgeList.item(0)).getNodeValue().trim());

                    //------


                }//end of if clause


            }//end of for loop with s var


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

}
