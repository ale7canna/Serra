package it.polimi.serra.utils;

public class Utils {

	public enum ModificaTemperatura{
		Alza,
		Mantieni,
		Abbassa
	}
	
	public enum ModificaUmidita {
		Alza, 
		Mantieni,
		Abbassa
	}
	
	public enum StatoPianta {
		Ottimo,
		Buono,
		Sufficiente,
		Scarso,
		Morto
	}
	
	public enum Errors {
		WaitingHalfHour,
		DobuleChangeAttempt,
		OutOfBorder,
		PositionBusy,
		NotFound,
		MaxHumidity,
		MinHumidity
	}
	
	public enum ModificaStatoPianta {
		Upgrade,
		Downgrade
	}
	
	public enum OccupazionePianta {
		unoXuno,
		unoXdue,
		unoXquattro,
		dueXuno,
		dueXdue
	}
}
