package com.uwetrottmann.shopr.algorithm.shopper.model;

import com.uwetrottmann.shopr.algorithm.model.ClothingType;
import com.uwetrottmann.shopr.algorithm.shopper.model.ContextFactors.ContextFactor;
import com.uwetrottmann.shopr.algorithm.shopper.model.ContextFactors.ContextValue;

public class DayOfTheWeek extends GenericContextFactor{
	public static final String ID = "day of the week";
	
	public enum Value implements ContextValue {
		WEEKEND("weekend"),
		WORKING_DAY("working day");

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
		// TODO Auto-generated method stub
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
			return 0.8502;
		case DRESSES:
			return 1;
		case UNDERWEAR:
			return 0.9038;
		case CARDIGANS:
			return 1;
		case TROUSERS:
			return 0.8914;
		case COATS:
			return 0.8582;
		case BLOUSES:
			return 1;
		case JACKETS:
			return 0.8910;
		case SKIRTS:
			return 1;
		case JEANS:
			return 0.8690;
		case SOCKS:
			return 0.8600;
		case SWIMWEAR:
			return 0.9039;
		case SUITS:
			return 0.8237;
		case SHIRTS:
			return 1;
		default:
			return 1;
		}
	}
	
	@Override
	public String getFactorDescription() {
		switch(DayOfTheWeek.Value.values()[getCurrentValue().index()]){
		case WEEKEND:
			return "it is weekend";
		case WORKING_DAY:
			return "it is working day";
		default:
			return "";
		
		
		}
	}

}
