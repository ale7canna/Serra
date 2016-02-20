package it.polimi.serra;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import it.polimi.serra.Client.Scelta;
import it.polimi.serra.utils.SerraException;
import it.polimi.serra.utils.Utils.ModificaTemperatura;
import it.polimi.serra.utils.Utils.ModificaUmidita;
import it.polimi.serra.utils.Utils.OccupazionePianta;

public class Front {
		
	private static Serra serra = Serra.getInstance(); 
	private static BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
	private static int contatorePiante = 0;

	public static void addPianta()
	{
		boolean sceltaGiusta = false;
		int index = 0;
		while (!sceltaGiusta)
		{
			println("Quale pianta vuoi aggiungere?");
			println("1) Pianta Tropicale");
			println("2) Pianta Temperata");
			println("3) Pianta Invernale");
			println("4) Esci\n");
			
			String risposta = "";
			try {
				risposta = readLine();
			} catch (Exception e)
			{ println("Errore di lettura");}
			
			try 
			{
				index = Integer.parseInt(risposta);
				if (index >= 1 && index <= 4)
					sceltaGiusta = true;
				else
				{
					println("Scelta sbagliata");
					sceltaGiusta = false;
				}
			}
			catch (NumberFormatException e)
			{
				println("Non hai inserito un numero");
				sceltaGiusta = false;
			}
		}
		
		if (index != 4)
			InstanziaPianta(index);
	}
	
	public static void rimuoviPianta()
	{
		stampaPiante();
		int id = requireInputInteger("Inserire il codice della pianta: ");
		try
		{
			serra.rimuoviPianta(id);
		}
		catch (SerraException exc)
		{
			println(exc.getMessage());
		}
	}
	
	public static void getInfoPianta()
	{
		stampaPiante();
		int id = requireInputInteger("Inserire il codice della pianta: ");
		try
		{
			println(serra.getPiantaInfo(id));
		}
		catch (SerraException e)
		{
			println(e.getMessage());
		}
	}
	
	public static void stampaPiante()
	{
		println("Ecco la situazione della serra:");
		println(serra.MostraSuperficie());
	}
	
	public static void modificaStatoSerra() throws SerraException
	{
		int scelta = -1;
		do
		{
			String req = "Scegliere cosa modificare: \n" +
					"1) Temperatura \n" +
					"2) Umidità \n" +
					"3) Esci \n";
								
			scelta = requireInputInteger(req);
		}while (scelta < 1 || scelta > 3 );
		
		if (scelta != 3)
		{
			int operazione = -1;
			do
			{
				String req = "Aumentare o diminuire? \n" +
						"1) Aumentare \n" +
						"2) Diminuire \n" +
						"3) Esci \n";
										
				operazione = requireInputInteger(req);
			}while (operazione < 1 || operazione > 3 );
			
			if (operazione != 3)
			{
				ModificaUmidita umidita = ModificaUmidita.Mantieni;
				ModificaTemperatura temperatura = ModificaTemperatura.Mantieni;
				
				if (scelta == 1)
					if (operazione == 1)
						temperatura = ModificaTemperatura.Alza;
					else
						temperatura = ModificaTemperatura.Abbassa;
				else
					if (operazione == 1)
						umidita = ModificaUmidita.Alza;
					else
						umidita = ModificaUmidita.Abbassa;
						
				serra.modificaStato(temperatura, umidita);
				visualizzaStatoSerra();
			}
		}
		
		
	}
	
	public static void visualizzaStatoSerra()
	{
		println("Stato attuale della serra:");
		println("Temperatura: " + String.format("%.2f", serra.getStato().getTemperatura()));
		println("Umidità: " + String.format("%.2f", serra.getStato().getUmidita()) + "%\n");
	}
	
	private static void InstanziaPianta(int index)
	{
		String specie = requireInputString("Inserire specie: ");
		
		int eta = requireInputInteger("Inserire eta: ");
		int altezza = requireInputInteger("Inserire altezza: ");
		
		int occupazione = 0;
		String s = "";
		OccupazionePianta[] occs = OccupazionePianta.values();
		for (int k = 0; k < occs.length; k ++)
			s += String.valueOf(k + 1) + ") " + occs[k] + "\n"; 
		while (occupazione <= 0 || occupazione > occs.length)
			occupazione = requireInputInteger(s);
		
		occupazione --;
		
		int x = requireInputInteger("Inserire posizione pianta x: ");
		int y = requireInputInteger("Inserire posizione pianta y: ");
		Point posizione = new Point(x, y);
		
		Pianta p = null;
		switch (index)
		{
			case 1:
				p = new PiantaTropicale(serra, contatorePiante++, specie, eta, altezza, occs[occupazione]);
				break;
			case 2:
				p = new PiantaTemperata(serra, contatorePiante++, specie, eta, altezza, occs[occupazione]);
				break;
			case 3:
				p = new PiantaInvernale(serra, contatorePiante++, specie, eta, altezza, occs[occupazione]);
				break;
		}
		
		try {
			serra.registra(p, posizione);
			stampaPiante();
		}
		catch (SerraException e)
		{
			println(e.getMessage());
		}

	}
	
	private static void println(String s)
	{
		System.out.println(s);
	}
	
	private static String readLine() throws Exception
	{
		return inputReader.readLine();
	}
	
	private static String requireInputString(String request)
	{
		boolean rightInput = false;
		String result = "";
		while (!rightInput)
		{
			try
			{
				println(request);
				result = readLine();
				rightInput = true;
			}
			catch (Exception e)
			{
				println("Errore");
			}
		}
		return result;
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
	
}
