package it.polimi.serra.utils;

import java.awt.Point;
import java.util.ArrayList;

import it.polimi.serra.utils.Utils.Errors;
import it.polimi.serra.utils.Utils.OccupazionePianta;

public class Superficie {
	private final static int LARGHEZZA = 12, LUNGHEZZA = 3;
	private final static String EMPTY_POSITION = " - ";
	private String [][] griglia = new String[LUNGHEZZA][LARGHEZZA];
	private IntegerRange xRange, yRange;
	
	public Superficie()
	{
		for (int k = 0; k < LUNGHEZZA; k++ )
			for (int j = 0; j < LARGHEZZA; j++)
				griglia[k][j] = EMPTY_POSITION;
		
		xRange = new IntegerRange(1,  LARGHEZZA);
		yRange = new IntegerRange(1,  LUNGHEZZA);
	}
	
	public boolean aggiungiPianta(Point posizione, OccupazionePianta occupazione, int id) throws SerraException
	{
		
		boolean spazioDisponibile = false;
		switch (occupazione)
		{
			case unoXuno:
				if (!xRange.contains(posizione.x) || !yRange.contains(posizione.y))
					throw new SerraException(Errors.OutOfBorder); 
				if (isFree(posizione))
					spazioDisponibile = true;
				break;
			case unoXdue:
				if (!xRange.contains(posizione.x) || !yRange.contains(posizione.y) || !xRange.contains(posizione.x + 1))
					throw new SerraException(Errors.OutOfBorder); 
				if (isFree(posizione) && isFree(new Point(posizione.x + 1, posizione.y)))
					spazioDisponibile = true;
				break;			
			case unoXquattro:
				if (!xRange.contains(posizione.x) || !xRange.contains(posizione.x + 1) || !xRange.contains(posizione.x + 2) || !xRange.contains(posizione.x + 3) || !yRange.contains(posizione.y))
					throw new SerraException(Errors.OutOfBorder);
				if (isFree(posizione) && isFree(new Point(posizione.x + 1, posizione.y)) 
						&& isFree(new Point(posizione.x + 2, posizione.y)) && isFree(new Point(posizione.x + 3, posizione.y)))
					spazioDisponibile = true;
				break;
			case dueXuno:
				if (!yRange.contains(posizione.y) || !yRange.contains(posizione.y + 1) || !xRange.contains(posizione.x) )
					throw new SerraException(Errors.OutOfBorder);
				if (isFree(posizione) && isFree(new Point(posizione.x, posizione.y + 1)))
					spazioDisponibile = true;
				break;
			case dueXdue:
				if (!xRange.contains(posizione.x) || !yRange.contains(posizione.y) || !xRange.contains(posizione.x + 1) || !yRange.contains(posizione.y + 1))
					throw new SerraException(Errors.OutOfBorder);
				if (isFree(posizione) && isFree(new Point(posizione.x + 1, posizione.y)) 
						&& isFree(new Point(posizione.x, posizione.y + 1)) && isFree(new Point(posizione.x + 1, posizione.y + 1)))
					spazioDisponibile = true;
				break;
			default:
				break;
		}
		
		if (!spazioDisponibile)
			throw new SerraException(Errors.PositionBusy);

		ArrayList<Point> punti = new ArrayList<Point>();
		switch (occupazione)
		{
			case unoXuno:
				punti.add(posizione);
				break;
			case unoXdue:
				punti.add(posizione); punti.add(new Point(posizione.x + 1, posizione.y));
				break;
			case unoXquattro:
				punti.add(posizione); punti.add(new Point(posizione.x + 1, posizione.y));
				punti.add(new Point(posizione.x + 2, posizione.y)); punti.add(new Point(posizione.x + 3, posizione.y));
				break;
			case dueXuno:
				punti.add(posizione); punti.add(new Point(posizione.x, posizione.y + 1));
				break;
			case dueXdue:
				punti.add(posizione); punti.add(new Point(posizione.x + 1, posizione.y));
				punti.add(new Point(posizione.x, posizione.y + 1)); punti.add(new Point(posizione.x + 1, posizione.y + 1));
				break;
			default:
				break;				
		}
		String nome = String.format("%03d", id);
		markBusy(punti, nome);
			
		return true;
	}
	
	private boolean isFree(Point pos)
	{
		if (griglia[pos.y - 1][pos.x - 1] == EMPTY_POSITION)
			return true;
		else
			return false;
					
	}

	private void markBusy(ArrayList<Point> punti, String name)
	{
		for (Point p : punti)
		{
			griglia[p.y - 1][p.x - 1] = name;
		}
	}
	
	public void rimuovi(int id)
	{
		String toFind = String.format("%03d", id);
		
		for (int k = 0; k < LUNGHEZZA; k++)
			for (int j = 0; j < LARGHEZZA; j++)
				if (griglia[k][j].compareTo(toFind) == 0)
					griglia[k][j] = EMPTY_POSITION;
	}
	
	public String Stampa()
	{
		String result = " C/  ";
		for (int k = 0; k < LARGHEZZA; k++)
			result += String.format("%03d", k+1) + " ";
		result += "\n /R  ";
		for (int k = 0; k < LARGHEZZA; k++)
			result += "____";
		result += "\n";
		for (int k = 0; k < LUNGHEZZA; k++ )
		{
			result += String.format("%03d", k+1) + "| ";
			for (int j = 0; j < LARGHEZZA; j++)
				result += griglia[k][j] + " ";
			result += "\n";
		}		
		return result;
	}
	

}
