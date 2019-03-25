package se.atg.service.harrykart.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

import org.apache.log4j.Logger;

import se.atg.service.harrykart.model.HorseDetails;
import se.atg.service.harrykart.model.Result;
import se.atg.service.harrykart.exception.HarryKartException;
import se.atg.service.harrykart.model.HarryKartType;
import se.atg.service.harrykart.model.LoopType;
import se.atg.service.harrykart.model.ParticipantType;

/*
 * 
 * Utility class to perform the business logic and validation of the input.
 * 
 */
public class HarryKartUtils {
	
	private static String LOOP_LENGTH = "loop.length";
	
	private static String TOP_RANKINGS = "top.rankings";
	
	private static List<ParticipantType> participants;
	
	private static Map<Integer, Map<Integer, Integer>> loops;
	
	private static int numOfLoops;
	
	private static HarryKartProperties prop = new HarryKartProperties();
	
	final static Logger logger = Logger.getLogger(HarryKartUtils.class);

	/*
	 * 
	 * Main method to calculate the rankings based on the input 
	 * @return - returns the final object to convert into json
	 * 
	 */
	public static Result calculateRankings(HarryKartType kart) {
		
		Result result = new Result();
		
		try {
			
			validateInput(kart);
			
			// Getting the top three winners in the race
			int limit = Integer.valueOf(prop.getProperty(TOP_RANKINGS));
			Stream<String> topRankings = getTimings().values().stream().limit(limit);
			
			HorseDetails.resetCounter();
			topRankings.forEach(key -> result.getRanking().add(new HorseDetails(key)));
			result.setMessage("Congratulations!!!");
			
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			logger.error(e.getMessage(), e);
		}
		
		return result;
	}
	
	/*
	 * Method to validate the input
	 * 
	 */
	private static void validateInput(HarryKartType kart) throws HarryKartException {
		
		boolean invalidInput = false;
		
		if(kart.getStartList() != null && kart.getStartList().getParticipant() != null)
			participants = kart.getStartList().getParticipant();
		else
			invalidInput = true;
		
		if(kart.getPowerUps() != null && kart.getPowerUps().getLoop() != null)
			loops = groupLoops(kart.getPowerUps().getLoop());
		else
			invalidInput = true;
		
		if(invalidInput) {
			throw new HarryKartException("Input xml is not valid");
		}
		
		numOfLoops = kart.getNumberOfLoops().intValue();
				
		if(numOfLoops <= 0) {
			throw new HarryKartException("There should be at least one loop");
		}
		
		
		
	}


	/**
	 * 
	 * Method to get the time required by each horse to complete all the loops
	 * @return map with time in sorted order
	 * @throws HarryKartException 
	 * @throws NumberFormatException 
	 */
	private static Map<Double, String> getTimings() throws NumberFormatException, HarryKartException {
		
		Map<Double, String> timeMap = new TreeMap<Double, String>();
		
		int loopLength = Integer.valueOf(prop.getProperty(LOOP_LENGTH));
		
		for(ParticipantType participant : participants) {
			
			int baseSpeed = participant.getBaseSpeed().intValue();
			double timing = (double)loopLength/baseSpeed;
			if(numOfLoops > 1) {
				if(loops.size() != numOfLoops- 1) {
					throw new HarryKartException("Number of Powerups is more/less than expected");
				}
				for(Integer key : loops.keySet()) {
					baseSpeed += loops.get(key).get(participant.getLane().intValue());
					timing += (double)loopLength/(baseSpeed);
				}
			}
			timeMap.put(timing, participant.getName());
		}
		
		return timeMap;
	}
	
	/*
	 * 
	 * Grouping of lanes for each loop in a way to use it easily while 
	 * calculating the time required by each horse
	 * 
	 */
	private static Map<Integer, Map<Integer, Integer>> groupLoops(List<LoopType> loops) {
		
		Map<Integer, Map<Integer, Integer>> grpedLoop = new TreeMap<Integer,Map<Integer, Integer>>();
		for(LoopType loop : loops) {
			Map<Integer, Integer> lanes = new HashMap<Integer, Integer>();
			loop.getLane().forEach( lane -> lanes.put(lane.getNumber().intValue(), lane.getValue().intValue()));
			grpedLoop.put(loop.getNumber().intValue(), lanes);
		}
		return grpedLoop;
	}
}

