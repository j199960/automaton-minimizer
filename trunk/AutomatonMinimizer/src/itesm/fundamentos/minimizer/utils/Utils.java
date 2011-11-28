package itesm.fundamentos.minimizer.utils;


import java.util.ArrayList;
import java.io.File;
import java.io.StringWriter;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException; 
import itesm.fundamentos.minimizer.modelo.*;

public class Utils {
	
	public static Automata ParseFromJflapFormat(String fileName)
	{
		try {

            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse (new File(fileName));
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
            		
            		
            		/*
            		e1.dameTransicion(estimulo);
            		*/
            		
            		
          
            		 
            		
            		
            		
            		
            		int i = t+2;
            		
            		
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
            
            return automata;
            
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
		return null;

		
	}
	
	public static Estado DameEstadoConNombre(ArrayList<Estado> estados, String nombre)
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
	
	
	public static void CreateJFlapFormat(String fileName, Automata automata)
	{
		try
		{
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.newDocument();
			Element rootElement = document.createElement("structure");
			document.appendChild(rootElement);
			Element em = document.createElement("type");
			rootElement.appendChild(em);
			em.appendChild(document.createTextNode("fa"));
			Node am = document.createElement("automaton");
			rootElement.appendChild(am);
			
			
			//Imprimiendo estados
			for(int i = 0; i < automata.DameEstados().size(); i++)
			{
				String nombre = automata.DameEstados().get(i).dameNombre();
				Node st = document.createElement("state");
				NamedNodeMap  mp = st.getAttributes();
				Attr id = document.createAttribute("id");
				Attr name = document.createAttribute("name");
				id.setValue(nombre);
				name.setValue(nombre);
				mp.setNamedItem(id);
				mp.setNamedItem(name);
				am.appendChild(st);
				if(automata.DameElEstadoInicial().equals(automata.DameEstados().get(i)))
				{
					Node init = document.createElement("initial");
					st.appendChild(init);
				}
				if(automata.DameEstadosDeAceptacion().contains(automata.DameEstados().get(i)))
				{
					Node fin = document.createElement("final");
					st.appendChild(fin);
				}
			}
			
			//Imprimiendo transiciones
			for(int i = 0; i < automata.DameEstados().size(); i++)
			{
				Estado e = automata.DameEstados().get(i);
				for(int j = 0; j< e.dameTransiciones().size(); j++)
				{
					Transicion transicion = e.dameTransiciones().get(j);
					Node tr = document.createElement("transition");
					am.appendChild(tr);
					
					Node from = document.createElement("from");
					from.appendChild(document.createTextNode(e.dameNombre()));
					
					
					Node to = document.createElement("to");
					to.appendChild(document.createTextNode(transicion.dameEstadosDestinos().get(0).dameNombre()));
					
					Node rd = document.createElement("read");
					rd.appendChild(document.createTextNode(transicion.dameEstimulo()));
					
					tr.appendChild(from);
					tr.appendChild(to);
					tr.appendChild(rd);
				}
				
				//String nombre = automata.DameEstados().get(i).dameNombre();
				//Node st = document.createElement("state");
				//NamedNodeMap  mp = st.getAttributes();
				//Attr id = document.createAttribute("id");
				//Attr name = document.createAttribute("name");
			//	id.setValue(nombre);
		//		name.setValue(nombre);
	//			mp.setNamedItem(id);
		//		mp.setNamedItem(name);
			//	am.appendChild(st);
			}
			
			
			
			
			
			
			
			//st.appendChild(document.createAttribute("id"));
			
			//st.appendChild(document.createAttribute("id"));
			//st.appendChild(document.createAttribute("name"));
			
			//Node tr = document.createElement("transition");
			//am.appendChild(tr);
			
			
			
			TransformerFactory transformerFactory = 
			TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(document);
			StreamResult result =  new StreamResult(new StringWriter());
			transformer.transform(source, result);
			
			String xmlString = result.getWriter().toString();
			System.out.println(xmlString);

			
		}catch (Exception err) {
	        System.out.println ("** Parsing error" + ", line " 
	             + ((SAXParseException) err).getLineNumber () + ", uri " + ((SAXParseException) err).getSystemId ());
	        System.out.println(" " + err.getMessage ());
	        }catch (Throwable t) {
	        t.printStackTrace ();
	        }

		
		
	}
	
	
	public static void ImprimeAutomata(Automata automata)
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
	}

}
