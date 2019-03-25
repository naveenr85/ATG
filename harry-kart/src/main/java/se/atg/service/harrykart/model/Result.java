package se.atg.service.harrykart.model;

import java.util.ArrayList;
import java.util.List;

public class Result {
	
	private String message;
	private List<HorseDetails> ranking = new ArrayList<HorseDetails>();

	public List<HorseDetails> getRanking() {
		return ranking;
	}

	public void setRanking(List<HorseDetails> ranking) {
		this.ranking = ranking;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
}