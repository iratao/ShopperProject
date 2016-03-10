package com.uwetrottmann.shopr.algorithm.home.model.context;

import com.uwetrottmann.shopr.algorithm.home.model.attributes.ContentType;
import com.uwetrottmann.shopr.algorithm.model.ClothingType;
import com.uwetrottmann.shopr.algorithm.shopper.model.Companion;
import com.uwetrottmann.shopr.algorithm.shopper.model.ContextFactors.ContextFactor;
import com.uwetrottmann.shopr.algorithm.shopper.model.ContextFactors.ContextValue;
import com.uwetrottmann.shopr.algorithm.shopper.model.GenericContextFactor;

/**
 * < 20
 * 20 <= age < 30
 * 30 <= age < 40
 * 40 <= age < 50
 * 50 <= age
 * @author iratao
 *
 */
public class Age extends GenericContextFactor{
	public static final String ID = "age";
	
	public enum Value implements ContextValue {
		SUB_20("younger than 20 years old"),
		BETWEEN_20_30("between 20 to 30 years old"),
		BETWEEN_30_40("between 30 to 40 years old"),
		BETWEEN_40_50("between 40 to 50 years old"),
		ABOVE_50("above 50 years old");

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
	public String getFactorDescription() {
		switch(Age.Value.values()[getCurrentValue().index()]){
			case SUB_20:
				return "they are less than 20 years old";
			case BETWEEN_20_30:
				return "they are from 20 to 30 years old";
			case BETWEEN_30_40:
				return "they are from 30 to 40 years old";
			case BETWEEN_40_50:
				return "they are from 40 to 50 years old";
			case ABOVE_50:
				return "they are above 50 years old";
			default:
				return "";
		}
	}

	@Override
	public double getWeight(ClothingType type) {
		// TODO Auto-generated method stub
		return 1;
	}

}
