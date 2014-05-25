package com.uwetrottmann.shopr.algorithm.shopper.model;

import com.uwetrottmann.shopr.algorithm.model.ClothingType;
import com.uwetrottmann.shopr.algorithm.shopper.model.ContextFactors.ContextFactor;
import com.uwetrottmann.shopr.algorithm.shopper.model.ContextFactors.ContextValue;

public class Season extends GenericContextFactor{
    public static final String ID = "season";
	
	public enum Value implements ContextValue {
		SPRING("spring"),
		SUMMER("summer"),
		AUTUMN("autumn"),
		WINTER("winter");

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
			return 0.7577;
		case DRESSES:
			return 0.8331;
		case UNDERWEAR:
			return 0.8317;
		case CARDIGANS:
			return 0.7287;
		case TROUSERS:
			return 0.7468;
		case COATS:
			return 0.8136;
		case BLOUSES:
			return 0.739;
		case JACKETS:
			return 0.7726;
		case SKIRTS:
			return 0.8517;
		case JEANS:
			return 0.8046;
		case SOCKS:
			return 1;
		case SWIMWEAR:
			return 0.7986;
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
		switch(Season.Value.values()[getCurrentValue().index()]){
		case AUTUMN:
			return "the season is autumn";
		case SPRING:
			return "the season is spring";
		case SUMMER:
			return "the season is summer";
		case WINTER:
			return "the season is winter";
		default:
			return "";
		
		}
	}
}
