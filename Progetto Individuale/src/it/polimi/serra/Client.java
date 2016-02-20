package it.polimi.serra;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import it.polimi.serra.utils.SerraException;

public class Client {
	private static Front frontPanel;
	private static BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));

	public enum Scelta {
		Aggiungi,
		Rimuovi,
		Informazioni,
		Stampa,
		Modifica,
		StatoSerra,
		Esci
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		frontPanel = new Front();
		
		Scelta scelta;
		do {
			int index = 0;
			do 
			{
				String richiesta = "Inserire l'operazione da compiere: \n";
					richiesta += "1) Aggiungi pianta\n";
					richiesta += "2) Rimuovi pianta\n";
					richiesta += "3) Stampa informazioni pianta\n";
					richiesta += "4) Stampa superficie serra\n";
					richiesta += "5) Modifica stato serra\n";
					richiesta += "6) Visualizza stato serra\n";
					richiesta += "7) Esci\n";

				index = requireInputInteger(richiesta);
				
				if (!(index > 0 && index <= Scelta.values().length))
					println("Inserimento non valido");
					
			} while (!(index > 0 && index <= Scelta.values().length));
			
			scelta = Scelta.values()[index - 1];
			
			SceltaOperazione(scelta);
		}
		while (scelta != Scelta.Esci);
		println("Applicazione terminata");
	}
	

	private static void SceltaOperazione(Scelta scelta)
	{
		switch (scelta)
		{
			case Aggiungi:
				Front.addPianta();
				break;
			case Rimuovi:
				Front.rimuoviPianta();
				break;
			case Informazioni:
				Front.getInfoPianta();
				break;
			case Stampa:
				Front.stampaPiante();
				break;
			case Modifica:
				try {
				Front.modificaStatoSerra();
				} catch (SerraException e) { println(e.getMessage()); }
				break;
			case StatoSerra:
				Front.visualizzaStatoSerra();
				break;
			case Esci:
				break;
			default:
				break;
		}
	}


	private static int requireInputInteger(String request)
	{
		boolean rightInput = false;
		int result = 0;
		while (!rightInput)
		{
			try
			{
				println(request);
				result = Integer.parseInt(readLine());
				rightInput = true;
			}
			catch (Exception e)
			{
				println("Errore");
			}
		}
		return result;
	}
	
	private static void println(String s)
	{
		System.out.println(s);
	}
	

	private static String readLine() throws Exception
	{
		return inputReader.readLine();
	}
	

}
