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
		
		//Automata automataInicial = Utils.ParseFromJflapFormat(args[0]);
		Automata automataInicial = Utils.ParseFromJflapFormatConTransicionesSeparadas(args[0]);
		
		if(!Clasificador.EsDeterminista(automataInicial))
		{
			automataInicial.imprimeAutomata();
			automataInicial = Convertidor.ConvierteloADeterminista(automataInicial);
		}
//		automataInicial.imprimeAutomata();
		
//		Convertidor.ConvierteloADeterminista(automataInicial);
		
		Minimizador m = new Minimizador(automataInicial);
		Automata automatafinal = m.Minimiza();
		
		Utils.CreateJFlapFormat(args[0] + ".min", automatafinal);
		
	}

}
