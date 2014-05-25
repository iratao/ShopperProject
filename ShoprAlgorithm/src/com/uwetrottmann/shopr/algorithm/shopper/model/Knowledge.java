package com.uwetrottmann.shopr.algorithm.shopper.model;

import com.uwetrottmann.shopr.algorithm.model.ClothingType;
import com.uwetrottmann.shopr.algorithm.shopper.model.ContextFactors.ContextFactor;
import com.uwetrottmann.shopr.algorithm.shopper.model.ContextFactors.ContextValue;

/**
 * Get rid of this factor.
 * @author taoyurong
 *
 */
public class Knowledge extends GenericContextFactor{
	public static final String ID = "knowledge about the clothes";
	
	public enum Value implements ContextValue {
		BOUGHT("bought a similar clothes before"),
		NEVER_BOUGHT("never bought a similar clothes before");

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
			return 0.8820;
		case DRESSES:
			return 0.8029;
		case UNDERWEAR:
			return 0.8448;
		case CARDIGANS:
			return 0.9283;
		case TROUSERS:
			return 0.7425;
		case COATS:
			return 0.9052;
		case BLOUSES:
			return 1;
		case JACKETS:
			return 0.871;
		case SKIRTS:
			return 1;
		case JEANS:
			return 1;
		case SOCKS:
			return 0.9021;
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
		// TODO Auto-generated method stub
		return "";
	}

}
