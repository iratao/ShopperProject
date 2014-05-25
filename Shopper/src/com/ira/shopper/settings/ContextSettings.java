package com.ira.shopper.settings;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;

import com.ira.shopper.utils.YahooWeatherParser;
import com.uwetrottmann.shopr.algorithm.shopper.model.Budget;
import com.uwetrottmann.shopr.algorithm.shopper.model.Companion;
import com.uwetrottmann.shopr.algorithm.shopper.model.ContextFactors;
import com.uwetrottmann.shopr.algorithm.shopper.model.ContextFactors.ContextFactor;
import com.uwetrottmann.shopr.algorithm.shopper.model.Crowdedness;
import com.uwetrottmann.shopr.algorithm.shopper.model.DayOfTheWeek;
import com.uwetrottmann.shopr.algorithm.shopper.model.Distance;
import com.uwetrottmann.shopr.algorithm.shopper.model.Intent;
import com.uwetrottmann.shopr.algorithm.shopper.model.Mood;
import com.uwetrottmann.shopr.algorithm.shopper.model.Season;
import com.uwetrottmann.shopr.algorithm.shopper.model.Temperature;
import com.uwetrottmann.shopr.algorithm.shopper.model.TimeAvailable;
import com.uwetrottmann.shopr.algorithm.shopper.model.TimeOfTheDay;
import com.uwetrottmann.shopr.algorithm.shopper.model.Transport;
import com.uwetrottmann.shopr.algorithm.shopper.model.Weather;

public class ContextSettings {
	public static final String KEY_CONTEXT_DAY_OF_THE_WEEK = "com.ira.shopper.day_of_the_week";
	public static final String KEY_CONTEXT_TEMPERATURE = "com.ira.shopper.temperature";
	public static final String KEY_CONTEXT_TIME_OF_THE_DAY = "com.ira.shopper.time_of_the_day";
	public static final String KEY_CONTEXT_WEATHER = "com.ira.shopper.weather";
	public static final String KEY_CONTEXT_CROWDEDNESS = "com.ira.shopper.crowdedness";
	public static final String KEY_CONTEXT_SEASON = "com.ira.shopper.season";
	public static final String KEY_CONTEXT_DISTANCE = "com.ira.shopper.distance";
	
	public static final String KEY_CONTEXT_TIME_AVAILABLE = "com.ira.shopper.time_available";
	public static final String KEY_CONTEXT_BUDGET = "com.ira.shopper.budget";
	public static final String KEY_CONTEXT_INTENT = "com.ira.shopper.intent";
	public static final String KEY_CONTEXT_COMPANION = "com.ira.shopper.companion";
	public static final String KEY_CONTEXT_TRANSPORT = "com.ira.shopper.transport";
	public static final String KEY_CONTEXT_MOOD = "com.ira.shopper.mood";
	
	public static final String WEATHER_URL = "http://weather.yahooapis.com/forecastrss?w=676757&u=c";
	
	public static final String TAG = "ContextSettings";
	
	public static boolean useRealContext = true;
	
	public static ContextFactors currentContext = null;
	
	public static void setUseRealContext(boolean useReal){
		useRealContext = useReal;
	}
	public static void setCurrentContext(Context context, ContextFactors factors){
		currentContext = factors;
	}
	
	public static ContextFactors getCurrentContext(Context context){
		
		if(useRealContext || currentContext == null){
			List<ContextFactor> factors = new ArrayList<ContextFactor>();
			
			SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
			
			factors.add(getDayOfWeek(sp));
			if(isOnline(context)){
				try {
					factors.add(getTemperature(sp));
					factors.add(getWeather(sp));
				} catch (IOException e) {
					e.printStackTrace();
				} catch (XmlPullParserException e) {
					e.printStackTrace();
				}
				
			}
			
			factors.add(getTimeOfTheDay(sp));
			
			factors.add(getCrowdedness(sp));
			factors.add(getSeason(sp));
			// Do not consider knowledge in this app
//			factors.add(getKnowledge(sp));
			
			factors.add(getTimeAvailable(sp));
			factors.add(getBudget(sp));
			factors.add(getIntent(sp));
			factors.add(getCompanion(sp));
			factors.add(getTransport(sp));
			factors.add(getMood(sp));
			
			currentContext = new ContextFactors();
			for(ContextFactor factor: factors){
				if(factor != null)
					currentContext.putContextFactor(factor);
			}
		}

		return currentContext;

	}
	
	  
	
	public static DayOfTheWeek getDayOfWeek(SharedPreferences sp){
		if(!sp.getBoolean(KEY_CONTEXT_DAY_OF_THE_WEEK, true))
			return null;
		DayOfTheWeek factor = new DayOfTheWeek();
		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DAY_OF_WEEK); 
		switch(day){
		case Calendar.SUNDAY:
			factor.setCurrentValue(DayOfTheWeek.Value.WEEKEND);
			break;
		case Calendar.SATURDAY:
			factor.setCurrentValue(DayOfTheWeek.Value.WEEKEND);
			break;
		case Calendar.MONDAY:
			factor.setCurrentValue(DayOfTheWeek.Value.WORKING_DAY);
			break;
		case Calendar.TUESDAY:
			factor.setCurrentValue(DayOfTheWeek.Value.WORKING_DAY);
			break;
		case Calendar.WEDNESDAY:
			factor.setCurrentValue(DayOfTheWeek.Value.WORKING_DAY);
			break;
		case Calendar.THURSDAY:
			factor.setCurrentValue(DayOfTheWeek.Value.WORKING_DAY);
			break;
		case Calendar.FRIDAY:
			factor.setCurrentValue(DayOfTheWeek.Value.WORKING_DAY);
			break;
		}
		
		return factor;
	}
	
	public static Temperature getTemperature(SharedPreferences sp) throws IOException, XmlPullParserException{
		if(!sp.getBoolean(KEY_CONTEXT_TEMPERATURE, true))
			return null;
		
		Temperature factor = new Temperature();
		
		InputStream stream = null;
    	YahooWeatherParser parser = new YahooWeatherParser();
    	int temperature = 0;
    	try{
    		stream = downloadUrl(WEATHER_URL);        
    		temperature = parser.getTemperature(stream);
    	}finally{
    		if(stream!=null)
    			stream.close();
    	}
    	if(temperature >= 28)
    		factor.setCurrentValue(Temperature.Value.HOT);
    	else if( temperature < 28 && temperature >= 12)
    		factor.setCurrentValue(Temperature.Value.WARM);
    	else
    		factor.setCurrentValue(Temperature.Value.COLD);
		
		return factor;
	}
	
	public static TimeOfTheDay getTimeOfTheDay(SharedPreferences sp){
		if(!sp.getBoolean(KEY_CONTEXT_TIME_OF_THE_DAY, true))
			return null;
		
		TimeOfTheDay factor = new TimeOfTheDay();
		Calendar calendar = Calendar.getInstance(); 
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int[] morningTime = {0,1,2,3,4,5,6,7,8,9,10,11};
		int[] afternoon = {12,13,14,15,16,17,18};
		int[] night = {19,20,21,22,23};
		if(isExist(hour, morningTime))
			factor.setCurrentValue(TimeOfTheDay.Value.MORNING_TIME);
		else if(isExist(hour, afternoon))
			factor.setCurrentValue(TimeOfTheDay.Value.AFTERNOON);
		else
			factor.setCurrentValue(TimeOfTheDay.Value.NIGHT_TIME);

		return factor;
	}
	
	public static Weather getWeather(SharedPreferences sp) throws XmlPullParserException, IOException{
		if(!sp.getBoolean(KEY_CONTEXT_WEATHER, true))
			return null;
		
		// TODO munich-676757: http://weather.yahooapis.com/forecastrss?w=676757&u=c
		Weather factor = new Weather();
		
		InputStream stream = null;
    	YahooWeatherParser parser = new YahooWeatherParser();
    	int condition = 0;
    	try{
    		stream = downloadUrl(WEATHER_URL);        
    		condition = parser.getCondition(stream);
    	}finally{
    		if(stream!=null)
    			stream.close();
    	}
    	int[] clearSky = {31,32,33,34};
    	int[] cloudy = {19,20,21,22,23,24,25,26,27,28,29,30,44,};
    	int[] raining = {0,1,2,3,4,5,6,8,9,10,11,12,35,37,38,39,40,45,47};
    	int[] snowing = {7,13,14,15,16,17,18,41,42,43,46};
    	int[] sunny = {36};
    	
    	if(isExist(condition, clearSky))
    		factor.setCurrentValue(Weather.Value.CLEAR_SKY);
    	else if(isExist(condition, cloudy))
    		factor.setCurrentValue(Weather.Value.CLOUDY);
    	else if(isExist(condition, raining))
    		factor.setCurrentValue(Weather.Value.RAINING);
    	else if(isExist(condition, snowing))
    		factor.setCurrentValue(Weather.Value.SNOWING);
    	else 
    		factor.setCurrentValue(Weather.Value.SUNNY);
		
		return factor;
	}
	
	public static Crowdedness getCrowdedness(SharedPreferences sp){
		if(!sp.getBoolean(KEY_CONTEXT_CROWDEDNESS, true))
			return null;
		
		Crowdedness factor = new Crowdedness();
		Random r = new Random();
		int i1 = r.nextInt(Crowdedness.Value.values().length);
		factor.setCurrentValue(Crowdedness.Value.values()[i1]);
		return factor;
	}
	
	public static Season getSeason(SharedPreferences sp){
		if(!sp.getBoolean(KEY_CONTEXT_SEASON, true))
			return null;
		
		Season factor = new Season();
		Calendar calendar = Calendar.getInstance(); 
		int month = calendar.get(Calendar.MONTH);
		switch(month){
		case Calendar.MARCH:
		case Calendar.APRIL:
		case Calendar.MAY:
			factor.setCurrentValue(Season.Value.SPRING);
			break;
		case Calendar.JUNE:
		case Calendar.JULY:
		case Calendar.AUGUST:
			factor.setCurrentValue(Season.Value.SUMMER);
			break;
		case Calendar.SEPTEMBER:
		case Calendar.OCTOBER:
		case Calendar.NOVEMBER:
			factor.setCurrentValue(Season.Value.AUTUMN);
			break;
		case Calendar.DECEMBER:
		case Calendar.JANUARY:
		case Calendar.FEBRUARY:
			factor.setCurrentValue(Season.Value.WINTER);
			break;
		}
		
		return factor;
	}
	
	/**
	 * Do not include distance factor in the current context. 
	 * Instead use distance as a flag to decide whether or 
	 * not to filter the items by distance.
	 * @param sp
	 * @return
	 */
	public static Distance getDistance(SharedPreferences sp){
		if(!sp.getBoolean(KEY_CONTEXT_DISTANCE, true))
			return null;
		
		Distance factor = new Distance();
		factor.setCurrentValue(Distance.Value.NEAR_BY);
		return factor;
	}
	
	public static boolean useDistance(Context context){
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
		return sp.getBoolean(KEY_CONTEXT_DISTANCE, true);
	}
	
	public static void setUseDistance(boolean useDistance, Context context){
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
		Editor editor = sp.edit();
		editor.putBoolean(KEY_CONTEXT_DISTANCE, useDistance);
		editor.apply();
	}
	

	public static TimeAvailable getTimeAvailable(SharedPreferences sp){
		TimeAvailable factor = new TimeAvailable();
		switch(Integer.parseInt(sp.getString(KEY_CONTEXT_TIME_AVAILABLE, "1"))){
		case 1:
			return null;
		case 2:
			factor.setCurrentValue(TimeAvailable.Value.HALF_DAY);
			return factor;
		case 3:
			factor.setCurrentValue(TimeAvailable.Value.ONE_DAY);
			return factor;
		default:
			return null;
		}
	}
	
	public static Budget getBudget(SharedPreferences sp){
		Budget factor = new Budget();
		switch(Integer.parseInt(sp.getString(KEY_CONTEXT_BUDGET, "1"))){
		case 1:
			return null;
		case 2:
			factor.setCurrentValue(Budget.Value.BUDGET_BUYER);
			return factor;
		case 3:
			factor.setCurrentValue(Budget.Value.HIGH_SPENDER);
			return factor;
		case 4:
			factor.setCurrentValue(Budget.Value.PRICE_FOR_QUALITY);
			return factor;
		default:
			return null;
		}
	}
	
	public static Intent getIntent(SharedPreferences sp){
		Intent factor = new Intent();
		switch(Integer.parseInt(sp.getString(KEY_CONTEXT_INTENT, "1"))){
		case 1:
			return null;
		case 2:
			factor.setCurrentValue(Intent.Value.WORK);
			return factor;
		case 3:
			factor.setCurrentValue(Intent.Value.DAILY_WEAR);
			return factor;
		case 4:
			factor.setCurrentValue(Intent.Value.PARTY);
			return factor;
		case 5:
			factor.setCurrentValue(Intent.Value.SPORTS);
			return factor;
		case 6:
			factor.setCurrentValue(Intent.Value.NO_SPECIAL);
			return factor;
		default:
			return null;
		}
	}
	
	public static Companion getCompanion(SharedPreferences sp){
		Companion factor = new Companion();
		switch(Integer.parseInt(sp.getString(KEY_CONTEXT_COMPANION, "1"))){
		case 1:
			return null;
		case 2:
			factor.setCurrentValue(Companion.Value.GIRL_BOY_FRIEND);
			return factor;
		case 3:
			factor.setCurrentValue(Companion.Value.FAMILY);
			return factor;
		case 4:
			factor.setCurrentValue(Companion.Value.CHILDREN);
			return factor;
		case 5:
			factor.setCurrentValue(Companion.Value.ALONE);
			return factor;
		case 6:
			factor.setCurrentValue(Companion.Value.FRIENDS);
			return factor;
		default:
			return factor;
		}
		
	}
	
	public static Transport getTransport(SharedPreferences sp){
		Transport factor = new Transport();
		switch(Integer.parseInt(sp.getString(KEY_CONTEXT_TRANSPORT, "1"))){
		case 1:
			return null;
		case 2:
			factor.setCurrentValue(Transport.Value.WALKING);
			return factor;
		case 3:
			factor.setCurrentValue(Transport.Value.PUBLIC_TRANSPORT);
			return factor;
		case 4:
			factor.setCurrentValue(Transport.Value.BICYCLE);
			return factor;
		case 5:
			factor.setCurrentValue(Transport.Value.CAR);
			return factor;
		default:
			return null;
		}
	}
	
	public static Mood getMood(SharedPreferences sp){
		Mood factor = new Mood();
		switch(Integer.parseInt(sp.getString(KEY_CONTEXT_MOOD, "1"))){
		case 1:
			return null;
		case 2:
			factor.setCurrentValue(Mood.Value.SHOPAHOLIC);
			return factor;
		case 3:
			factor.setCurrentValue(Mood.Value.OUTDOORSY);
			return factor;
		case 4:
			factor.setCurrentValue(Mood.Value.LIKE_A_PARTY_ANIMAL);
			return factor;
		case 5:
			factor.setCurrentValue(Mood.Value.NORMAL);
			return factor;
		default:
			return null;
		}
	}
	
	private static InputStream downloadUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        // Starts the query
        conn.connect();
        return conn.getInputStream();
    }
	
	public static boolean isOnline(Context context) {
	    ConnectivityManager connMgr = (ConnectivityManager) 
	    		context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
	    return (networkInfo != null && networkInfo.isConnected());
	}
	
	public static boolean isExist(int number, int[] numArray){
		for(int num: numArray){
			if(number == num)
				return true;
		}
		return false;
	}
}









