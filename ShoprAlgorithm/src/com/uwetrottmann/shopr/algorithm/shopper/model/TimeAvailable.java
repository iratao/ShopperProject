package com.uwetrottmann.shopr.algorithm.shopper.model;

import com.uwetrottmann.shopr.algorithm.model.ClothingType;
import com.uwetrottmann.shopr.algorithm.shopper.model.ContextFactors.ContextFactor;
import com.uwetrottmann.shopr.algorithm.shopper.model.ContextFactors.ContextValue;

public class TimeAvailable extends GenericContextFactor{

    public static final String ID = "time available";
	
	public enum Value implements ContextValue {
		HALF_DAY("half day"),
		ONE_DAY("one day");

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
			return 0.8369;
		case DRESSES:
			return 1;
		case UNDERWEAR:
			return 0.9214;
		case CARDIGANS:
			return 0.8667;
		case TROUSERS:
			return 0.869;
		case COATS:
			return 0.9104;
		case BLOUSES:
			return 1;
		case JACKETS:
			return 0.871;
		case SKIRTS:
			return 1;
		case JEANS:
			return 1;
		case SOCKS:
			return 0.8918;
		case SWIMWEAR:
			return 0.8846;
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
		switch(TimeAvailable.Value.values()[getCurrentValue().index()]){
		case HALF_DAY:
			return "they have half day available";
		case ONE_DAY:
			return "they have one day available";
		default:
			return "";
		
		}
	}

}
