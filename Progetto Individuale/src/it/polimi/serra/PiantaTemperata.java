package it.polimi.serra;

import it.polimi.serra.utils.DoubleRange;
import it.polimi.serra.utils.Utils.OccupazionePianta;

public class PiantaTemperata extends Pianta{

	private final static double MIN_TEMP = 14, MAX_TEMP = 28; 
	
	public PiantaTemperata(Serra _serra, int id, String specie, Integer eta, Integer altezza, OccupazionePianta occs) {
		super(_serra, id, specie, eta, altezza, occs);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		temperatureRange = new DoubleRange(MIN_TEMP, MAX_TEMP);
		CategoriaPianta = "Pianta Temperata";
	}

}
