package com.uwetrottmann.shopr.algorithm.shopper.model;

import com.uwetrottmann.shopr.algorithm.model.ClothingType;
import com.uwetrottmann.shopr.algorithm.shopper.model.ContextFactors.ContextFactor;
import com.uwetrottmann.shopr.algorithm.shopper.model.ContextFactors.ContextValue;

public class Transport extends GenericContextFactor{
    public static final String ID = "transport";
	
	public enum Value implements ContextValue {
		WALKING("walking"),
		PUBLIC_TRANSPORT("public transport"),
		BICYCLE("bicycle"),
		CAR("car");

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
			return 0.8130;
		case DRESSES:
			return 0.7819;
		case UNDERWEAR:
			return 0.8119;
		case CARDIGANS:
			return 0.728;
		case TROUSERS:
			return 0.8437;
		case COATS:
			return 0.8898;
		case BLOUSES:
			return 1;
		case JACKETS:
			return 0.8857;
		case SKIRTS:
			return 0.7353;
		case JEANS:
			return 0.8148;
		case SOCKS:
			return 0.6838;
		case SWIMWEAR:
			return 0.7851;
		case SUITS:
			return 0.7207;
		case SHIRTS:
			return 0.8477;
		default:
			return 1;
		}
	}
	
	@Override
	public String getFactorDescription() {
		switch(Transport.Value.values()[getCurrentValue().index()]){
		case BICYCLE:
			return "they went shopping by bicycle";
		case CAR:
			return "they went shopping by car";
		case PUBLIC_TRANSPORT:
			return "they went shopping by public transport";
		case WALKING:
			return "they went shopping by walking";
		default:
			return "";
		
		}
	}
}
