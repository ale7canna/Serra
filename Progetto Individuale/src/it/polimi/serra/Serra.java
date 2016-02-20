package it.polimi.serra;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import it.polimi.serra.utils.SerraException;
import it.polimi.serra.utils.StatoSerra;
import it.polimi.serra.utils.Superficie;
import it.polimi.serra.utils.Utils.Errors;
import it.polimi.serra.utils.Utils.ModificaTemperatura;
import it.polimi.serra.utils.Utils.ModificaUmidita;

public class Serra implements ISerra{

	// TODO: Sistemare tempi
	private final static long INTERVAL = 1000 * 60;// * 30; // 30 MINUTI

	private boolean waitingHalfHour = false;
	private static Serra instance = null;
	private static StatoSerra statoSerra;
	private Superficie superficie;
	private HashMap<Pianta, Point> listaPiante;
	Timer timer;
	
	//ArrayList<Pianta> listaPiante;
	
	private Serra()
	{
		timer = new Timer();
		statoSerra = new StatoSerra();
		listaPiante = new HashMap<Pianta, Point>();
		superficie = new Superficie();
	}
	
	public static Serra getInstance()
	{
		if (instance == null)
			instance = new Serra();
		
		return instance;
	}
	
	@Override
	public void registra(Pianta p, Point pos) throws SerraException {
		// TODO Auto-generated method stub
		if (superficie.aggiungiPianta(pos, p.caratteristiche.occupazione, p.caratteristiche.id))
			listaPiante.put(p, pos);
	}

	@Override
	public void aggiorna() {
		// TODO Auto-generated method stub
		Set<Pianta> piante = listaPiante.keySet();
		for (Pianta p : piante)
		{
			p.update();
		}
	}

	@Override
	public void rimuovi(Pianta p) throws SerraException {
		// TODO Auto-generated method stub
		listaPiante.remove(p);
	}
	
	public void rimuoviPianta(int id) throws SerraException
	{
		Pianta pianta = null;
		for (Pianta p : listaPiante.keySet())
		{
			if (p.getId() == id)
			{
				pianta = p;
				break;
			}
		}
		if (pianta == null)
			throw new SerraException(Errors.NotFound);
		
		rimuovi(pianta);
		superficie.rimuovi(id);	
	}
	
	public String getPiantaInfo(int id) throws SerraException
	{
		Pianta pianta = null;
		for (Pianta p : listaPiante.keySet())
		{
			if (p.getId() == id)
			{
				pianta = p;
				break;
			}
		}
		if (pianta == null)
			throw new SerraException(Errors.NotFound);
		
		String 	result = "La pianta di id " + String.format("%03d", pianta.caratteristiche.id) + " ha le seguente informazioni:\n";
			   result += "La pianta si trova nello stato: " + pianta.getStato().toString().toUpperCase() + "\n";
			   result += "Categoria: " + pianta.CategoriaPianta + "\n";
			   result += "Specie: " + pianta.caratteristiche.specie + "\n";
			   result += "Eta: " + String.valueOf(pianta.caratteristiche.eta) + "\n";
			   result += "Altezza: " + String.valueOf(pianta.caratteristiche.altezza) + "\n";

		return result;
		
	}
	
	public void modificaStato(ModificaTemperatura modTemp, ModificaUmidita modUm) throws SerraException
	{
		if (waitingHalfHour)
			throw new SerraException(Errors.WaitingHalfHour);
				
		if (modTemp == ModificaTemperatura.Mantieni || modUm == ModificaUmidita.Mantieni)
		{
			switch (modTemp)
			{
				case Alza :
					statoSerra.incrementTemperature();
					avviaTimerHalfHour();
					break;
				case Mantieni:
					break;
				case Abbassa:
					statoSerra.decrementTemperature();
					avviaTimerHalfHour();
					break;
				default:
					break;
			}
			
			switch (modUm)
			{
				case Alza :
					statoSerra.incrementHumidity();
					avviaTimerHalfHour();
					break;
				case Mantieni:
					break;
				case Abbassa:
					statoSerra.incrementHumidity();
					avviaTimerHalfHour();
					break;
				default:
					break;
			}
			aggiorna();
		}
		else
			throw new SerraException(Errors.DobuleChangeAttempt);
	}
	
	private void avviaTimerHalfHour()
	{
		if (!waitingHalfHour)
		{
			timer.schedule(new WaitHalfHourRoutine(), INTERVAL);
			waitingHalfHour = true;
		}
	}

	public StatoSerra getStato()
	{
		return statoSerra;
	}
	
	private class WaitHalfHourRoutine extends TimerTask
	{

		@Override
		public void run() {
			waitingHalfHour = false;
			System.out.println("Passata mezz'ora dall'ultima modifica");
		}
		
	}

	
	public String MostraSuperficie() {
		return superficie.Stampa();
	}
}
