package com.uwetrottmann.shopr.algorithm.shopper.model;

import com.uwetrottmann.shopr.algorithm.model.ClothingType;
import com.uwetrottmann.shopr.algorithm.shopper.model.ContextFactors.ContextFactor;
import com.uwetrottmann.shopr.algorithm.shopper.model.ContextFactors.ContextValue;

public class Temperature extends GenericContextFactor{
    public static final String ID = "temperature";
	
	public enum Value implements ContextValue {
		WARM("warm"),
		COLD("cold"),
		HOT("hot");

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
			return 0.9095;
		case UNDERWEAR:
			return 0.8093;
		case CARDIGANS:
			return 0.9166;
		case TROUSERS:
			return 0.8013;
		case COATS:
			return 1;
		case BLOUSES:
			return 0.8217;
		case JACKETS:
			return 0.8816;
		case SKIRTS:
			return 0.8743;
		case JEANS:
			return 0.9036;
		case SOCKS:
			return 0.8042;
		case SWIMWEAR:
			return 0.9174;
		case SUITS:
			return 0.8876;
		case SHIRTS:
			return 1;
		default:
			return 1;
		}
	}
	
	@Override
	public String getFactorDescription() {
		switch(Temperature.Value.values()[getCurrentValue().index()]){
		case COLD:
			return "the temperature is cold";
		case HOT:
			return "the temperature is hot";
		case WARM:
			return "the temperature is warm";
		default:
			return "";
		
		}
	}
}
