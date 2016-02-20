package it.polimi.serra.utils;

import java.util.Random;

import it.polimi.serra.utils.Utils.Errors;

public class StatoSerra {
	
	public StatoSerra()
	{
		Temperatura 	= 20; // GRADI
		Umidita 		= 60; // PERCENTUALE UMIDITA	
	}
	public double getTemperatura() {
		return Temperatura;
	}
	public void setTemperatura(double temperatura) {
		Temperatura = temperatura;
	}
	public double getUmidita() {
		return Umidita;
	}
	public void setUmidita(double umidita) {
		Umidita = umidita;
	}
	public void incrementTemperature() {
		Temperatura += 0.5;
	}
	public void decrementTemperature() {
		Temperatura -= 0.5;
	}
	public void incrementHumidity() throws SerraException{
		if (Umidita >= 100)
			throw new SerraException(Errors.MaxHumidity);
		
		Umidita += 0.5;
		if ((new Random()).nextFloat() < 0.2)
			incrementTemperature();
	}
	public void decrementHumidity() throws SerraException{
		if (Umidita <= 0)
			throw new SerraException(Errors.MinHumidity);
		
		Umidita -= 0.5;
		if ((new Random()).nextFloat() < 0.2)
			decrementTemperature();
	}
	private double Temperatura;
	private double Umidita;

}
