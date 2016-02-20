package it.polimi.serra;

import java.util.Timer;
import java.util.TimerTask;

import it.polimi.serra.utils.DoubleRange;
import it.polimi.serra.utils.StatoSerra;
import it.polimi.serra.utils.Utils.ModificaStatoPianta;
import it.polimi.serra.utils.Utils.OccupazionePianta;
import it.polimi.serra.utils.Utils.StatoPianta;

public abstract class Pianta implements IPianta {
	
	protected class Caratteristiche
	{
		public String specie;
		public int eta, altezza;
		public OccupazionePianta occupazione;
		public int id;
	} 
	
	Caratteristiche caratteristiche = new Caratteristiche();
	
	//TODO: Sistemare tempi
	private final static long INTERVAL = 1000 * 6; //* 60 * 24;
	private final static long GIORNI_AGGIORNAMENTO = 10;
	
	protected DoubleRange temperatureRange;
	protected String CategoriaPianta = "";
	
	private boolean changed = false;
	private Serra serra;
	private StatoSerra statoSerra;
	private StatoPianta stato;
	private Timer timer;
	private int giorniPassati;
	
	public Pianta(Serra _serra, int id, String specie, int eta, int altezza, OccupazionePianta occ)
	{
		serra = _serra;
		statoSerra = serra.getStato();
		stato = StatoPianta.Buono;
		giorniPassati = 0;
		init();
		timer = new Timer();
		timer.schedule(new ControllaStato(), 0, INTERVAL);
		
		caratteristiche.id 				= id;
		caratteristiche.specie 			= specie;
		caratteristiche.eta 			= eta;
		caratteristiche.altezza 		= altezza;
		caratteristiche.occupazione 	= occ;
	}
	
	protected abstract void init();
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		changed = true;
	}
	
	public int getId()
	{
		return caratteristiche.id;
	}
	
	public StatoPianta getStato()
	{
		return stato;
	}
	
	private class ControllaStato extends TimerTask
	{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			if (changed)
				statoSerra = serra.getStato();
			
			boolean tempOttimale = temperatureRange.contains(statoSerra.getTemperatura());
			if (giorniPassati == 0)
			{
				giorniPassati = 1;
				if (!tempOttimale)
					giorniPassati = -1;	
			}
			
			if (giorniPassati > 0 && tempOttimale)
				giorniPassati ++;
			else if (giorniPassati > 0 && !tempOttimale)
				giorniPassati = 0;
			else if (giorniPassati < 0 && !tempOttimale)
				giorniPassati --;
			else if (giorniPassati < 0 && tempOttimale)
				giorniPassati = 0;
			
			if (giorniPassati > GIORNI_AGGIORNAMENTO)
			{
				AggiornaStato(ModificaStatoPianta.Upgrade);
				giorniPassati = 0;
			}
			else if (giorniPassati < - GIORNI_AGGIORNAMENTO)
			{
				AggiornaStato(ModificaStatoPianta.Downgrade);
				giorniPassati = 0;
			}
			//Problemi. Statica?
			changed = false;
		}
		
		private void AggiornaStato(ModificaStatoPianta _modifica)
		{
			
			if (_modifica == ModificaStatoPianta.Upgrade)
			{
				if (stato.ordinal() > 0)
				{
					stato = StatoPianta.values()[stato.ordinal() - 1];
					System.out.println("La pianta " + caratteristiche.specie + " è migliorata allo stato " + stato.toString());
				}
				
			}
			else
			{
				if (stato.ordinal() < StatoPianta.values().length - 1)
				{
					stato = StatoPianta.values()[stato.ordinal() + 1];
					System.out.println("La pianta " + caratteristiche.specie + " è peggiorata allo stato " + stato.toString());
				}
			}
		}
		
	}

}
