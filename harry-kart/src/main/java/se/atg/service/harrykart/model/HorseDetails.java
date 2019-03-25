package se.atg.service.harrykart.model;

public class HorseDetails {

	private static int counter;
	
	private int position;
	
	private String horse;
	
	public HorseDetails(String horse) {
		this.position = ++counter;
		this.horse = horse;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String getHorse() {
		return horse;
	}

	public void setHorse(String horse) {
		this.horse = horse;
	}
	
	public static void resetCounter() {
		counter = 0;
	}
}
