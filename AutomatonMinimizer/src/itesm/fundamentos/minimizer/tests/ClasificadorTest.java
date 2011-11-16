package itesm.fundamentos.minimizer.tests;

import itesm.fundamentos.minimizer.control.Clasificador;
import itesm.fundamentos.minimizer.modelo.*;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ClasificadorTest {
	
	private Automata inputAutomata = null;
	private boolean esDeterminista = false;
	
	@Parameters
    public static Collection spreadsheetData() throws IOException {
        return new AutomataBuilder("c:/temp/testdata.xls").getData();
    }

	public ClasificadorTest(Automata inputAutomata, boolean esDeterminista)
	{
		this.inputAutomata = inputAutomata;
		this.esDeterminista = esDeterminista;
	}

	@Test
	public void testEsDeterminista() {
		boolean resultado = Clasificador.EsDeterminista(inputAutomata);
		assertSame(resultado, esDeterminista);
	}
	
	

}
