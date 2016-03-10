package com.uwetrottmann.shopr.algorithm.home.model.context;

import com.uwetrottmann.shopr.algorithm.model.ClothingType;
import com.uwetrottmann.shopr.algorithm.shopper.model.ContextFactors.ContextFactor;
import com.uwetrottmann.shopr.algorithm.shopper.model.ContextFactors.ContextValue;
import com.uwetrottmann.shopr.algorithm.shopper.model.GenericContextFactor;

/*
 * Bathroom
 * Bedroom
 * DiningRoom
 * Kids
 * Kitchen
 * LivingRoom
 * LivingDiningRoom
 * Balcony               阳台
 * LaundryRoom           洗衣房
 * CloakRoom             衣帽间
 * Study                 书房
 * Corridor              走廊
 * Entry                 玄关
 */
public class RoomType extends GenericContextFactor{
    public static final String ID = "roomtype";
	
	public enum Value implements ContextValue {
		Bathroom("Bathroom"),
		Bedroom("Bedroom"),
		DiningRoom("DiningRoom"),
		Kids("Kids"),
		Kitchen("Kitchen"),
		LivingRoom("LivingRoom"),
		LivingDiningRoom("LivingDiningRoom"),
		Balcony("Balcony"),
		LaundryRoom("LaundryRoom"),
		CloakRoom("CloakRoom"),
		Study("Study"),
		Corridor("Corridor"),
		Entry("Entry");

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
		switch(RoomType.Value.values()[getCurrentValue().index()]){
			case Bathroom:
				return "their room is bathroom";
			case Bedroom:
				return "their room is bedroom";
			case DiningRoom:
				return "their room is dining room";
			case Kids:
				return "their room is kids' room";
			case Kitchen:
				return "their room is kitchen";
			case LivingRoom:
				return "their room is living room";
			case LivingDiningRoom:
				return "their room is living dining room";
			case Balcony:
				return "their room is balcony";
			case LaundryRoom:
				return "their room is laundry room";
			case CloakRoom:
				return "their room is cloak room";
			case Study:
				return "their room is study room";
			case Corridor:
				return "their room is corridor";
			case Entry:
				return "their room is entry";
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
