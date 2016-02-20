package it.polimi.serra;

import java.awt.Point;

import it.polimi.serra.utils.SerraException;

public interface ISerra {

	public void registra(Pianta p, Point pos) throws SerraException;
	public void aggiorna();
	public void rimuovi(Pianta p) throws SerraException;
}
