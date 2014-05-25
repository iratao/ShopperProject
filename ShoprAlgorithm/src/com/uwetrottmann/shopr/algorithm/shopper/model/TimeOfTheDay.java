package com.uwetrottmann.shopr.algorithm.shopper.model;

import com.uwetrottmann.shopr.algorithm.model.ClothingType;
import com.uwetrottmann.shopr.algorithm.shopper.model.ContextFactors.ContextFactor;
import com.uwetrottmann.shopr.algorithm.shopper.model.ContextFactors.ContextValue;

public class TimeOfTheDay extends GenericContextFactor{
	public static final String ID = "time of the day";
	
	public enum Value implements ContextValue {
		MORNING_TIME("morning time"),
		AFTERNOON("afternoon"),
		NIGHT_TIME("night time");

        String valueName;

        Value(String name) {
        	valueName = name;
        }

		@Override
		public int index() {
			return ordinal();
		}

		@Override
		public String valueName() {
			return valueName;
		}

    }
	
	@Override
	public String id() {
		return ID;
	}
	
	@Override
	public double getSimilarity(ContextFactor factor) {
		if(factor.getCurrentValue() == this.getCurrentValue()){
			return 0;
		}else{
			return 1;
		}
	}
	
	@Override
	public double getWeight(ClothingType type) {
		switch(ClothingType.Value.values()[type.currentValue().index()]){
		case TOPS:
			return 0.7958;
		case DRESSES:
			return 1;
		case UNDERWEAR:
			return 0.8997;
		case CARDIGANS:
			return 0.7858;
		case TROUSERS:
			return 0.7388;
		case COATS:
			return 0.7639;
		case BLOUSES:
			return 1;
		case JACKETS:
			return 0.7866;
		case SKIRTS:
			return 0.7632;
		case JEANS:
			return 0.865;
		case SOCKS:
			return 0.7741;
		case SWIMWEAR:
			return 0.7531;
		case SUITS:
			return 1;
		case SHIRTS:
			return 1;
		default:
			return 1;
		}
	}
	
	@Override
	public String getFactorDescription() {
		switch(TimeOfTheDay.Value.values()[getCurrentValue().index()]){
		case AFTERNOON:
			return "it is afternoon";
		case MORNING_TIME:
			return "it is morning time";
		case NIGHT_TIME:
			return "it is night time";
		default:
			return "";
		
		}
	}

}
