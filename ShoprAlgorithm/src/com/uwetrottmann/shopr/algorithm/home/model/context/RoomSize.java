package com.uwetrottmann.shopr.algorithm.home.model.context;

import com.uwetrottmann.shopr.algorithm.model.ClothingType;
import com.uwetrottmann.shopr.algorithm.shopper.model.ContextFactors.ContextFactor;
import com.uwetrottmann.shopr.algorithm.shopper.model.ContextFactors.ContextValue;
import com.uwetrottmann.shopr.algorithm.shopper.model.GenericContextFactor;

/**
 * size < 6
 * 6 <= size < 10
 * 10 <= size < 20
 * 20 <= size < 30
 * 30 <= size
 * @author iratao
 *
 */
public class RoomSize extends GenericContextFactor{
	public static final String ID = "size";
	
	public enum Value implements ContextValue {
		SUB_6("smaller than 6m"),
		BETWEEN_6_10("from 6 to 10m"),
		BETWEEN_10_20("from 10 to 20m"),
		BETWEEN_20_40("from 20 to 30m"),
		ABOVE_40("above 40m");

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
		switch(RoomSize.Value.values()[getCurrentValue().index()]){
			case SUB_6:
				return "their room is less than 6m";
			case BETWEEN_6_10:
				return "their room size is between 6 and 10m";
			case BETWEEN_10_20:
				return "their room size is between 10 and 20m";
			case BETWEEN_20_40:
				return "their room size is between 20 and 40m";
			case ABOVE_40:
				return "their room size is above 40m";
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
