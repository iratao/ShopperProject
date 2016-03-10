package com.uwetrottmann.shopr.algorithm.home.model.attributes;

import java.util.Arrays;

import com.uwetrottmann.shopr.algorithm.home.model.attributes.ContentType.Value;
import com.uwetrottmann.shopr.algorithm.model.GenericAttribute;
import com.uwetrottmann.shopr.algorithm.model.Attributes.AttributeValue;

public class Style extends GenericAttribute{
	public static final String ID = "style";

    public enum Value implements AttributeValue {
    	SIMPLE_EURO("简欧"),
    	OTHER("其他"),
    	NEW_CLASSIC("新古典"),
    	TRAD_CLASSIC("明清古典"),
    	SIMPLE_MODERN("简约现代"),
    	AMERICAN_COUNTRY("美式乡村"),
    	CHIN_MODERN("现代中式"),
    	NORTH_EURO("北欧"),
    	KOREAN("韩式");

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

    public Style() {
        int numValues = Value.values().length;
        mValueWeights = new double[numValues];
        Arrays.fill(mValueWeights, 1.0 / numValues);
    }

    public Style(Value value) {
        mValueWeights = new double[Value.values().length];
        Arrays.fill(mValueWeights, 0.0);
        mValueWeights[value.ordinal()] = 1.0;
        currentValue(value);
    }
    
    public Style(String str) {
    	for (Value value : Value.values()) {
    	    if(value.toString().equals(str)){
    	    	mValueWeights = new double[Value.values().length];
    	        Arrays.fill(mValueWeights, 0.0);
    	        mValueWeights[value.ordinal()] = 1.0;
    	        currentValue(value);
    	        break;
    	    } 
    	}
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
