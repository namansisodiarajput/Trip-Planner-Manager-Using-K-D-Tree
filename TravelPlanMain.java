package TravelPlanAlgorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;


public class TravelPlanMain {
	
	public static void main(String[] args) throws Exception {
		
		// fetch all cities data
		ReadContinentData readData = new ReadContinentData();
		JSONObject allCitiesData = readData.readData();
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter Your Starting City Code - ");
		String inputCity = sc.nextLine();
		if(!readData.allCitiesData.has(inputCity)) {
			throw new Exception("Invalid cityId " + inputCity + ".Enter valid cityID");
		}

		//starting city is already visited removed the continent not needed for computation
		allCitiesData = readData.removeContinentFromCityId(allCitiesData, inputCity);
		JSONObject sourceCity = readData.startingCity;
		
		//find shorted path to travel between continents
		TravelPlanMain travelPlanManager = new TravelPlanMain();
		List<String> continentPath = travelPlanManager.findShortestPathContinents(sourceCity.getString("contId"), readData);
				
		//find shortest cities in other continent from starting city
		String finalPath = sourceCity.getString("id") + " ( " + sourceCity.getString("name") + "," + sourceCity.getString("contId") + " ) ";
		double totalDistanceTravelled = 0;
		double prevLatX = sourceCity.getJSONObject("location").getDouble("lat");
		double prevLatY = sourceCity.getJSONObject("location").getDouble("lon");

		for(String continentCode: continentPath) {
			//finding shortest city in given continent
			finalPath += " --> ";
			KDNode shortestDistanceCity = travelPlanManager.findShortestCityInContinentFromCoordinate(sourceCity, allCitiesData.getJSONArray(continentCode));
			finalPath += shortestDistanceCity.cityCode + " ( " + shortestDistanceCity.cityName + ","  + shortestDistanceCity.continentCode + " ) ";
		
			//calculating total distance
			double[] coordinates = shortestDistanceCity.x;
			totalDistanceTravelled += Utils.distanceBetweenCoordinates(prevLatX, prevLatY, coordinates[0], coordinates[1]);
			prevLatX = coordinates[0];
			prevLatY = coordinates[1];
		}
		finalPath += " --> Back To " +  sourceCity.getString("id") + " ( " + sourceCity.getString("name") + "," +  sourceCity.getString("contId") + " ) ";
		System.out.println("Shortest Path Recommended From System - \n" + finalPath);
		
		//distance from end to source
		totalDistanceTravelled += Utils.distanceBetweenCoordinates(prevLatX, prevLatY,  
				sourceCity.getJSONObject("location").getDouble("lat"),  sourceCity.getJSONObject("location").getDouble("lon"));
		System.out.printf("\nTotal Distance Travelled in Journey = %.2f" + " KM ", totalDistanceTravelled);
		
	}
	
	private KDNode findShortestCityInContinentFromCoordinate(JSONObject sourceCity, JSONArray citiesList) {
		
        int numPoint = citiesList.length();
		KDTree kdt = new KDTree(numPoint);

		for(int i = 0; i < numPoint ; i++) {
			
			JSONObject city = citiesList.getJSONObject(i);

			double coordinates[] = new double[2];
	        coordinates[0] = city.getJSONObject("location").getDouble("lat");
	        coordinates[1] = city.getJSONObject("location").getDouble("lon");
			kdt.add(coordinates , city.getString("contId") , city.getString("id") , city.getString("name"));
			
		}
		
		double sourceCoordinates[] = new double[2];
		sourceCoordinates[0] = sourceCity.getJSONObject("location").getDouble("lat");
		sourceCoordinates[1] = sourceCity.getJSONObject("location").getDouble("lon");
        KDNode kdn = kdt.find_nearest(sourceCoordinates);
        return kdn;
	}
	
	private List<String> findShortestPathContinents(String sourceContId , ReadContinentData readData) {
		
		HashMap<String , JSONObject> tempContinentCoord = readData.continentCoordinates;
		
		ArrayList<String> contiShortestPath = new ArrayList<>();
				
		double shortestDistance = Double.MAX_VALUE;
		double prevLat = tempContinentCoord.get(sourceContId).getDouble("lat");
		double prevLon = tempContinentCoord.get(sourceContId).getDouble("lon");
		tempContinentCoord.remove(sourceContId);
		String nearbyContId = sourceContId;
		
		while(tempContinentCoord.size() > 0) {
						
			for (Map.Entry continent : tempContinentCoord.entrySet()) { 
		           
			 	String contId = (String)continent.getKey(); 
	            JSONObject location = (JSONObject) continent.getValue(); 
	            
	            double distanceFromSource = Utils.distanceBetweenCoordinates(prevLat, prevLon, location.getDouble("lat"), location.getDouble("lon"));
	            if(distanceFromSource <  shortestDistance) {
	            	shortestDistance = distanceFromSource;
	            	nearbyContId = contId;
	            }
		     }
			
			contiShortestPath.add(nearbyContId);
			prevLat = tempContinentCoord.get(nearbyContId).getDouble("lat");
			prevLon = tempContinentCoord.get(nearbyContId).getDouble("lon");
			tempContinentCoord.remove(nearbyContId);
			shortestDistance = Double.MAX_VALUE;
		}
		
		return contiShortestPath;
	}

}
