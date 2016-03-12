package com.uwetrottmann.shopr.algorithm.home.model.attributes;

import java.util.Arrays;

import com.uwetrottmann.shopr.algorithm.home.model.attributes.Brand.Value;
import com.uwetrottmann.shopr.algorithm.model.GenericAttribute;
import com.uwetrottmann.shopr.algorithm.model.Attributes.AttributeValue;

public class ContentType extends GenericAttribute{
	public static final String ID = "contenttype";

    public enum Value implements AttributeValue {
    	FLOOR_BASED_CABINET("cabinet/floor-based cabinet"),
    	DOUBLE_SEAT_SOFA("sofa/double seat sofa"),
    	FLOOR_BASED_MEDIA_UNIT("media unit/floor-based media unit"),
    	KING_SIZE_BED("bed/king-size bed"),
    	DINING_TABLE("table/dining table"),
    	ON_THE_FLOOR("200 - on the floor"),
    	APPLIANCE("appliance/appliance - floor-based"),
    	CONSOLE_TABLE("table/console table"),
    	SIDE_TABLE("table/side table"),
    	CHAIR("chair/chair"),
    	PEDANT_LIGHT("lighting/pendant light"),
    	COFFEE_TABLE("table/coffee table"),
    	CLOCK_FLOOR_BASED("accessory/clock - floor-based"),
    	APPLIANCE_ON_TOP("appliance/appliance - on top of others"),
    	ARMOIRE("storage unit/armoire"),
    	STORAGE_UNIT("storage unit/floor-based storage unit"),
    	TABLE("table/table"),
    	HUTCH("cabinet/hutch&buffet"),
    	DRESSER("storage unit/dresser"),
    	DESK_LAMP("lighting/desk lamp"),
    	NIGHT_TABLE("table/night table"),
    	KITCHEN_CABINET("cabinet/floor-based kitchen cabinet"),
    	MIRROR("mirror/wall-attached mirror"),
    	SOFA("sofa/single seat sofa"),
    	ATTACH_WALL("400 - attach to wall"),
    	MATRESS("bed/matress"),
    	WALL_LAMP("lighting/wall lamp"),
    	ACCESSORY("accessory/accessory - ceiling-attached"),
    	SINGLE_SPOTLIGHT("lighting/single spotlight - ceiling-attached"),
    	CLOCK("accessory/clock - wall-attached"),
    	APPLIANCE_CEILING("appliance/appliance - ceiling-attached"),
    	CEILING500("500 - attach to ceiling"),
    	KITCHEN_CEILING("build element/kitchen ceiling"),
    	SINGLE_SWING_DOOR("door/single swing door"),
    	WASHER("appliance/washer"),
    	REFRIGERATOR("appliance/refrigerator"),
    	TV("electronics/TV - wall-attached"),
    	AIR_CONDITIONER_WALL("electronics/air-conditioner - wall-attached"),
    	AIR_CONDITIONER_FLOOR("electronics/air-conditioner - floor-based"),
    	ELECTRICAL_WATER_HEATER("appliance/water heater - electrical"),
    	GAS_WATER_HEATER("appliance/water heater - gas"),
    	APPLIANCE_WALL("appliance/appliance - wall-attached"),
    	OTHER("other");

        private String mDescriptor;

        Value(String descriptor) {
            mDescriptor = descriptor;
        }

        @Override
        public String descriptor() {
            return mDescriptor;
        }

        @Override
        public int index() {
            return ordinal();
        }
    }

    public ContentType() {
        int numValues = Value.values().length;
        mValueWeights = new double[numValues];
        Arrays.fill(mValueWeights, 1.0 / numValues);
    }

    public ContentType(Value value) {
        mValueWeights = new double[Value.values().length];
        Arrays.fill(mValueWeights, 0.0);
        mValueWeights[value.ordinal()] = 1.0;
        currentValue(value);
    }
    
    public ContentType(String str) {
    	for (Value value : Value.values()) {
    	    if(value.descriptor().equals(str)){
    	    	mValueWeights = new double[Value.values().length];
    	        Arrays.fill(mValueWeights, 0.0);
    	        mValueWeights[value.ordinal()] = 1.0;
    	        currentValue(value);
    	        return;
    	    } 
    	}
    	mValueWeights = new double[Value.values().length];
        Arrays.fill(mValueWeights, 0.0);
        mValueWeights[Value.OTHER.ordinal()] = 1.0;
        currentValue(Value.OTHER);
    	System.out.println("***********************Value not found!!!!!!!!!!!!!*************");
    }

    @Override
    public String id() {
        return ID;
    }

    @Override
    public Value[] getValueSymbols() {
        return Value.values();
    }
}
