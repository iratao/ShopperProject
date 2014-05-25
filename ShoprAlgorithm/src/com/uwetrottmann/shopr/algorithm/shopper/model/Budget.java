package com.uwetrottmann.shopr.algorithm.shopper.model;

import com.uwetrottmann.shopr.algorithm.model.ClothingType;
import com.uwetrottmann.shopr.algorithm.shopper.model.ContextFactors.ContextFactor;
import com.uwetrottmann.shopr.algorithm.shopper.model.ContextFactors.ContextValue;

public class Budget extends GenericContextFactor{
	
	public static final String ID = "budget";
	
	public enum Value implements ContextValue {
		BUDGET_BUYER("budget buyer"),
		HIGH_SPENDER("high spender"),
		PRICE_FOR_QUALITY("price-for-quality buyer");

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
			return 0.7548;
		case DRESSES:
			return 0.9125;
		case UNDERWEAR:
			return 0.8119;
		case CARDIGANS:
			return 0.8033;
		case TROUSERS:
			return 0.8143;
		case COATS:
			return 0.8651;
		case BLOUSES:
			return 0.8168;
		case JACKETS:
			return 0.9184;
		case SKIRTS:
			return 1;
		case JEANS:
			return 0.8046;
		case SOCKS:
			return 0.8423;
		case SWIMWEAR:
			return 1;
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
		switch(Budget.Value.values()[getCurrentValue().index()]){
		case BUDGET_BUYER:
			return "they are a budget buyer";
		case HIGH_SPENDER:
			return "they are a high spender";
		case PRICE_FOR_QUALITY:
			return "they are a price-for-quality buyer";
		default:
			return "";
		
		}
	}

}
