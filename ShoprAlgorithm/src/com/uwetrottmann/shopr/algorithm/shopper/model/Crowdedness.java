package com.uwetrottmann.shopr.algorithm.shopper.model;

import com.uwetrottmann.shopr.algorithm.model.ClothingType;
import com.uwetrottmann.shopr.algorithm.shopper.model.ContextFactors.ContextFactor;
import com.uwetrottmann.shopr.algorithm.shopper.model.ContextFactors.ContextValue;

public class Crowdedness extends GenericContextFactor{
	
    public static final String ID = "crowdedness";
	
	public enum Value implements ContextValue {
		CROWDED("crowded"),
		NOT_CROWDED("not crowded"),
		EMPTY("empty");

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
			return 0.7798;
		case DRESSES:
			return 0.7350;
		case UNDERWEAR:
			return 0.8793;
		case CARDIGANS:
			return 0.8231;
		case TROUSERS:
			return 0.7970;
		case COATS:
			return 0.8237;
		case BLOUSES:
			return 0.7963;
		case JACKETS:
			return 0.8013;
		case SKIRTS:
			return 1;
		case JEANS:
			return 0.8306;
		case SOCKS:
			return 0.9159;
		case SWIMWEAR:
			return 0.8357;
		case SUITS:
			return 0.8358;
		case SHIRTS:
			return 0.8678;
		default:
			return 1;
		}
	}
	
	@Override
	public String getFactorDescription() {
		switch(Crowdedness.Value.values()[getCurrentValue().index()]){
		case CROWDED:
			return "the store is crowded";
		case EMPTY:
			return "the store is empty";
		case NOT_CROWDED:
			return "the store is not crowded";
		default:
			return "";
		
		}
	}

}
