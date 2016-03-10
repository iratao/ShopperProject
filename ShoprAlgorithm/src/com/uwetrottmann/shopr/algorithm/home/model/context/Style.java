package com.uwetrottmann.shopr.algorithm.home.model.context;

import com.uwetrottmann.shopr.algorithm.model.ClothingType;
import com.uwetrottmann.shopr.algorithm.shopper.model.GenericContextFactor;
import com.uwetrottmann.shopr.algorithm.shopper.model.ContextFactors.ContextFactor;
import com.uwetrottmann.shopr.algorithm.shopper.model.ContextFactors.ContextValue;

/*
 * 简约
 * 美式
 * 中式
 * 日式
 * 地中海
 * 混搭
 * 新古典
 * 北欧
 * 田园
 * 东南亚
 * loft
 */
public class Style extends GenericContextFactor{
    public static final String ID = "style";
	
	public enum Value implements ContextValue {
	    Simple("简约"),
		American("美式"),
		Chinese("中式"),
		Japanese("日式"),
		MiddleSea("地中海"),
		Hybrid("混搭"),
		NewClassic("新古典"),
		NorthEuro("北欧"),
		Garden("田园"),
		SouthEastAsia("东南亚"),
		Loft("loft");

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
		switch(Style.Value.values()[getCurrentValue().index()]){
			case American:
				return "they want American style";
			case Chinese:
				return "they want Chinese style";
			case Garden:
				return "they want rural style";
			case Hybrid:
				return "they want hybrid style";
			case Japanese:
				return "they want Japanese style";
			case Loft:
				return "they want loft style";
			case MiddleSea:
				return "they want middle sea style";
			case NewClassic:
				return "they want new classic style";
			case NorthEuro:
				return "they want North Euro style";
			case Simple:
				return "they want simple style";
			case SouthEastAsia:
				return "they want south east asia style";
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
