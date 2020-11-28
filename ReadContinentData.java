package TravelPlanAlgorithm;

import java.io.*;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
 

public class ReadContinentData {
	
	private String JSON_DATA_PATH = "src/TravelPlanAlgorithm/cities.json";
	
	public JSONObject startingCity;
	
	public HashMap<String , JSONObject> continentCoordinates = new HashMap<>();
	
	public JSONObject allCitiesData;
	
	public JSONObject readData() throws Exception {
		
		String jsonString;
		try {
			jsonString = new Scanner(new File(JSON_DATA_PATH)).useDelimiter("\\Z").next();
			JSONObject jsonData = new JSONObject(jsonString);
			this.allCitiesData = jsonData;
			
			return groupCityByContinent(jsonData);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		throw new Exception("Unable to Read Data File.Please check the Class Path Variable.");
	}
	
	public JSONArray readByContinentId(JSONObject allCitiesData, String continentId) throws Exception {
		
		JSONArray allCityCode = allCitiesData.names();
		JSONArray continentCity = new JSONArray(); 
	
		double continentAverageX = 0;
		double continentAverageY = 0;
		int cityCount = 0;

		for(Object cityCode: allCityCode) {
			
			JSONObject city = allCitiesData.getJSONObject(cityCode.toString());
			
			if(city.get("contId").toString().equals(continentId)) {

				// remove unnecessary data not needed for computation
				JSONObject customCityData = new JSONObject();
				customCityData.put("id" , city.get("id"));
				customCityData.put("name" , city.get("name"));
				customCityData.put("location" , city.get("location"));
				customCityData.put("countryName" , city.get("countryName"));
				customCityData.put("contId" , city.get("contId"));

				continentCity.put(customCityData);
				
				continentAverageX += city.getJSONObject("location").getDouble("lat");
				continentAverageY += city.getJSONObject("location").getDouble("lon");
				cityCount++;
			}
			
		}
		
		//set average coordinates of continent
		continentCoordinates.put(continentId, new JSONObject().put("lat", continentAverageX / cityCount).put("lon", continentAverageY / cityCount));
		
		return continentCity;
	}
	
	public JSONObject groupCityByContinent(JSONObject allCitiesData) throws JSONException, Exception {
		
		JSONArray allCityCode = allCitiesData.names();
		ArrayList<String> continentList = new ArrayList<String>(); 
		JSONObject continentGroupedCity = new JSONObject();
		
		continentList.add("south-america");
		continentList.add("europe");
		continentList.add("asia");
		continentList.add("north-america");
		continentList.add("africa");
		continentList.add("oceania");
				
		for(String continentName: continentList) {
			continentGroupedCity.put(continentName, readByContinentId(allCitiesData, continentName));
		}
		
		return continentGroupedCity;
	}
	
	public JSONObject removeContinentFromCityId(JSONObject allCitiesData , String cityId) throws Exception {
		
		JSONArray allContinentCode = allCitiesData.names();
		
		for(Object contCode: allContinentCode) {
			
			boolean continentStartingPoint = false;
			
			JSONArray continentCity = allCitiesData.getJSONArray(contCode.toString());
			for(int i = 0; i < continentCity.length() ; i++) {
				
				if(continentCity.getJSONObject(i).get("id").equals(cityId)) {
					this.startingCity = continentCity.getJSONObject(i);
					continentStartingPoint = true;
					break;
				}
			}
			
			if(continentStartingPoint) {
				allCitiesData.remove(contCode.toString());
				break;
			}
		}

		return allCitiesData;
	}
	
}
