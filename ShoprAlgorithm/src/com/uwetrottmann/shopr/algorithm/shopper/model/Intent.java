package com.uwetrottmann.shopr.algorithm.shopper.model;

import com.uwetrottmann.shopr.algorithm.model.ClothingType;
import com.uwetrottmann.shopr.algorithm.shopper.model.ContextFactors.ContextFactor;
import com.uwetrottmann.shopr.algorithm.shopper.model.ContextFactors.ContextValue;

public class Intent extends GenericContextFactor{
	
    public static final String ID = "intent of purchasing";
	
	public enum Value implements ContextValue {
		WORK("work"),
		DAILY_WEAR("daily wear"),
		PARTY("party"),
		SPORTS("sports"),
		NO_SPECIAL("no special");

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
			return 0.7759;
		case DRESSES:
			return 0.7589;
		case UNDERWEAR:
			return 0.6926;
		case CARDIGANS:
			return 0.7593;
		case TROUSERS:
			return 1;
		case COATS:
			return 0.8019;
		case BLOUSES:
			return 0.8909;
		case JACKETS:
			return 0.8106;
		case SKIRTS:
			return 0.8806;
		case JEANS:
			return 0.7260;
		case SOCKS:
			return 0.8778;
		case SWIMWEAR:
			return 0.8;
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
		switch(Intent.Value.values()[getCurrentValue().index()]){
		case DAILY_WEAR:
			return "the purpose is for daily wear";
		case NO_SPECIAL:
			return "";
		case PARTY:
			return "the purpose is for attending parties";
		case SPORTS:
			return "the purpose is for sports";
		case WORK:
			return "the purpose is for work";
		default:
			return "";

		}
	}

}
