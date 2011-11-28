package itesm.fundamentos.minimizer.modelo;

import java.util.ArrayList;

public class Estado {
	private String nombre;
	private ArrayList<Transicion> transiciones;
	
	/*
	 * Constructor proporcionando el nombre del estado
	 */
	public Estado(String nombre)
	{
		this.nombre = nombre;
		this.transiciones = new ArrayList<Transicion>();
	}

	
	/*
	 * Metodo para agregar transiciones a un estado construido
	 */
	public void agregaTransicion(Transicion transicion)
	{
		this.transiciones.add(transicion);
	}
	
	
	/*
	 * Metodo para obtener todas las transiciones e iterar sobre ellas
	 */
	public ArrayList<Transicion> dameTransiciones()
	{
		return this.transiciones;
	}
	
	public String dameNombre()
	{
		return this.nombre;
	}
	
	public void remueveTransiciones(String simbolo)
	{
		ArrayList<Transicion> transiciones = this.dameTransiciones();
		
		//System.out.println(" numero de transiciones " + transiciones.size() );

		
		for (int x = 0 ; x<transiciones.size();x++)
		{
			Transicion transicion = transiciones.get(x);
			
			if (transicion.dameEstimulo()==simbolo)
			{
				//
				//  Se va a remover la transicion con el simbolo enviado
				//
				//System.out.println("Se borrara la transicion con simbolo " + simbolo );
				transiciones.remove(x);
			}
		}
		
	//	System.out.println(" numero de transiciones " + transiciones.size() );


	}

	public ArrayList<Estado> dameElSiguientesEstados(String estimulo)
	{
		for(int i = 0; i < this.transiciones.size(); i++)
		{
			if(transiciones.get(i).dameEstimulo().equalsIgnoreCase(estimulo))
			{
				return transiciones.get(i).dameEstadosDestinos();
			}
		}
		return null;
	}
	
	public Transicion dameTransicion(String estimulo)
	{
		for(int i   = 0; i<transiciones.size(); i++)
		{
			if(transiciones.get(i).dameEstimulo().equalsIgnoreCase(estimulo))
			{
				return transiciones.get(i);
			}
		}
		return null;
	}

	public boolean buscaEstadoEnTransiciones(Estado estadoDestino, String simbolo)
	{
		boolean result = false;
		
		ArrayList<Transicion> listaTransiciones = this.dameTransiciones();
		
		for (int x = 0 ; x< listaTransiciones.size(); x++)
		{
			Transicion transicion = listaTransiciones.get(x);
			
			ArrayList<Estado> estados = transicion.dameEstadosDestinos();
			if (estados.contains(estadoDestino) == true && transicion.dameEstimulo().equals(simbolo))
			{
				result = true;
				break;
			}
		}
		return result;
	}
	
	public void imprimeEstado()
	{
		ArrayList<Transicion> transiciones = this.dameTransiciones();
		
		System.out.println(" ------------------- ");


		
		for (int x = 0 ; x<transiciones.size();x++)
		{
			Transicion transicion = transiciones.get(x);
			
			System.out.println(transicion.dameEstimulo() + " ->");
			
			for (int z = 0 ; z<transicion.dameEstadosDestinos().size() ; z++)
			{
				System.out.println(transicion.dameEstadosDestinos().get(z).dameNombre());
				
				
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
		System.out.println(" ==========  ");

	}
	
	public void renombra(String nuevoNombre)
	{
		this.nombre = nuevoNombre;
	}
	
	
}
