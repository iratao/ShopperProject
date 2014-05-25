package com.uwetrottmann.shopr.algorithm.shopper.model;

import com.uwetrottmann.shopr.algorithm.model.ClothingType;
import com.uwetrottmann.shopr.algorithm.shopper.model.ContextFactors.ContextFactor;
import com.uwetrottmann.shopr.algorithm.shopper.model.ContextFactors.ContextValue;

public class Mood extends GenericContextFactor{
    public static final String ID = "mood";
	
	public enum Value implements ContextValue {
		SHOPAHOLIC("shopaholic"),
		OUTDOORSY("outdoorsy"),
		LIKE_A_PARTY_ANIMAL("like a party animal"),
		NORMAL("normal");

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
			return 0.712;
		case DRESSES:
			return 0.7431;
		case UNDERWEAR:
			return 0.7479;
		case CARDIGANS:
			return 0.8661;
		case TROUSERS:
			return 0.8307;
		case COATS:
			return 0.8504;
		case BLOUSES:
			return 0.8909;
		case JACKETS:
			return 0.7659;
		case SKIRTS:
			return 0.8565;
		case JEANS:
			return 0.7731;
		case SOCKS:
			return 1;
		case SWIMWEAR:
			return 0.8;
		case SUITS:
			return 0.8257;
		case SHIRTS:
			return 0.8484;
		default:
			return 1;
		}
	}
	
	@Override
	public String getFactorDescription() {
		switch(Mood.Value.values()[getCurrentValue().index()]){
		case LIKE_A_PARTY_ANIMAL:
			return "they are feeling like a party animal";
		case NORMAL:
			return "";
		case OUTDOORSY:
			return "they are feeling outdoorsy";
		case SHOPAHOLIC:
			return "they are feeling shopaholic";
		default:
			return "";
		

		}
	}
}
