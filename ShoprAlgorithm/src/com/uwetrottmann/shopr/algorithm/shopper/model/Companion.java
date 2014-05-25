package com.uwetrottmann.shopr.algorithm.shopper.model;

import com.uwetrottmann.shopr.algorithm.model.ClothingType;
import com.uwetrottmann.shopr.algorithm.shopper.model.ContextFactors.ContextFactor;
import com.uwetrottmann.shopr.algorithm.shopper.model.ContextFactors.ContextValue;

public class Companion extends GenericContextFactor{
    public static final String ID = "companion";
	
	public enum Value implements ContextValue {
		GIRL_BOY_FRIEND("with girl-/boy-friend"),
		FAMILY("with family"),
		CHILDREN("with children"),
		ALONE("alone"),
		FRIENDS("with friends");

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
			return 0.7589;
		case DRESSES:
			return 0.9118;
		case UNDERWEAR:
			return 0.7833;
		case CARDIGANS:
			return 0.8199;
		case TROUSERS:
			return 0.8224;
		case COATS:
			return 0.6815;
		case BLOUSES:
			return 0.7491;
		case JACKETS:
			return 0.8973;
		case SKIRTS:
			return 1;
		case JEANS:
			return 0.9075;
		case SOCKS:
			return 0.7881;
		case SWIMWEAR:
			return 0.7591;
		case SUITS:
			return 1;
		case SHIRTS:
			return 0.9009;
		default:
			return 1;
		}
	}
	
	@Override
	public String getFactorDescription() {
		switch(Companion.Value.values()[getCurrentValue().index()]){
		case ALONE:
			return "they are alone";
		case CHILDREN:
			return "they are with children";
		case FAMILY:
			return "they are with family";
		case FRIENDS:
			return "they are with friends";
		case GIRL_BOY_FRIEND:
			return "they are with girl/boy-friend";
		default:
			return "";
		
		}
	}

}
