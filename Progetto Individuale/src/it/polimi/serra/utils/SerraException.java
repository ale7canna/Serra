package it.polimi.serra.utils;

import java.util.HashMap;

import it.polimi.serra.utils.Utils.Errors;

public class SerraException extends Exception {
	
	private static HashMap<Errors, String> listaErrori = null;
	
	
	
	public SerraException(Errors err)
	{
		super(getMessageFromError(err));
	}
	
	private static String getMessageFromError(Errors e)
	{		
		if (listaErrori == null)
			loadErrors();		

		return listaErrori.get(e);
	}
	
	private static void loadErrors()
	{
		listaErrori = new HashMap<Errors, String>();
		
		listaErrori.put(Errors.DobuleChangeAttempt, "Non si pu� modificare contemporaneamente temperatura e umidit�");
		listaErrori.put(Errors.MaxHumidity, "L'umidit� si trova gi� al massimo");
		listaErrori.put(Errors.MinHumidity, "L'umidit� si trova gi� al minimo");
		listaErrori.put(Errors.NotFound, "Pianta non trovata");
		listaErrori.put(Errors.OutOfBorder, "Pianta non inserita. La pianta fuoriesce dalla superficie, grazia alla sua posizione o dimensione");
		listaErrori.put(Errors.PositionBusy, "Posizione gi� occupata da un'altra pianta");
		listaErrori.put(Errors.WaitingHalfHour, "Lo stato della serra � gi� stato modificato nell'ultima mezz'ora. Impossibile modificare nuovamente");
		
		
	}

}
