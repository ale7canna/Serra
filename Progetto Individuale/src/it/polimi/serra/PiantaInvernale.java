package it.polimi.serra;

import it.polimi.serra.utils.DoubleRange;
import it.polimi.serra.utils.Utils.OccupazionePianta;

public class PiantaInvernale extends Pianta{

	private final static double MIN_TEMP = Double.NEGATIVE_INFINITY, MAX_TEMP = 16; 
	
	public PiantaInvernale(Serra _serra, int id, String specie, Integer eta, Integer altezza, OccupazionePianta occs) {
		super(_serra, id, specie, eta, altezza, occs);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		temperatureRange = new DoubleRange(MIN_TEMP, MAX_TEMP);
		CategoriaPianta = "Pianta Invernale";
	}

}
