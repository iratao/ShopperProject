package com.uwetrottmann.shopr.algorithm.shopper.model;

import com.uwetrottmann.shopr.algorithm.model.ClothingType;
import com.uwetrottmann.shopr.algorithm.shopper.model.ContextFactors.ContextFactor;
import com.uwetrottmann.shopr.algorithm.shopper.model.ContextFactors.ContextValue;

public class Weather extends GenericContextFactor{
    public static final String ID = "weather";
	
	public enum Value implements ContextValue {
		SNOWING("snowing"),
		CLEAR_SKY("clear sky"),
		SUNNY("sunny"),
		RAINING("raining"),
		CLOUDY("cloudy");

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
			return 0.8395;
		case DRESSES:
			return 1;
		case UNDERWEAR:
			return 0.7931;
		case CARDIGANS:
			return 0.9178;
		case TROUSERS:
			return 1;
		case COATS:
			return 0.7454;
		case BLOUSES:
			return 0.8493;
		case JACKETS:
			return 0.7407;
		case SKIRTS:
			return 0.8705;
		case JEANS:
			return 0.7404;
		case SOCKS:
			return 0.7278;
		case SWIMWEAR:
			return 0.8201;
		case SUITS:
			return 0.9153;
		case SHIRTS:
			return 1;
		default:
			return 1;
		}
	}
	
	@Override
	public String getFactorDescription() {
		switch(Weather.Value.values()[getCurrentValue().index()]){
		case CLEAR_SKY:
			return "the weather is clear sky";
		case CLOUDY:
			return "the weather is cloudy";
		case RAINING:
			return "the weather is raining";
		case SNOWING:
			return "the weather is snowing";
		case SUNNY:
			return "the weather is sunny";
		default:
			return "";
		}
	}

}
