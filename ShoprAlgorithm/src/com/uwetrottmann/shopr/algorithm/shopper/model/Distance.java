package com.uwetrottmann.shopr.algorithm.shopper.model;

import com.uwetrottmann.shopr.algorithm.model.ClothingType;
import com.uwetrottmann.shopr.algorithm.shopper.model.ContextFactors.ContextFactor;
import com.uwetrottmann.shopr.algorithm.shopper.model.ContextFactors.ContextValue;

public class Distance extends GenericContextFactor{
	
	public static final String ID = "distance";
	
	public enum Value implements ContextValue {
		NEAR_BY("near by"),
		FAR_AWAY("far away");

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
			return 1;
		case DRESSES:
			return 0.9213;
		case UNDERWEAR:
			return 1;
		case CARDIGANS:
			return 1;
		case TROUSERS:
			return 1;
		case COATS:
			return 1;
		case BLOUSES:
			return 0.8966;
		case JACKETS:
			return 0.9094;
		case SKIRTS:
			return 0;
		case JEANS:
			return 0.9423;
		case SOCKS:
			return 1;
		case SWIMWEAR:
			return 0.8456;
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
		switch(Distance.Value.values()[getCurrentValue().index()]){
		case FAR_AWAY:
			return "the clothes is far away";
		case NEAR_BY:
			return "the clothes is near by";
		default:
			return "";
		}
	}

}
