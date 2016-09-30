package com.uwetrottmann.shopr.algorithm.home.model.attributes;

import java.util.Arrays;

import com.uwetrottmann.shopr.algorithm.home.model.attributes.Brand.Value;
import com.uwetrottmann.shopr.algorithm.model.GenericAttribute;
import com.uwetrottmann.shopr.algorithm.model.Attributes.AttributeValue;

public class ContentType extends GenericAttribute{
	public static final String ID = "contenttype";

    public enum Value implements AttributeValue {
    	A1("bed/king-size bed"),
    	A2("chair/chair"),
    	A3("bed/matress"),
    	A4("storage unit/dresser"),
    	A5("storage unit/floor-based storage unit"),
    	A6("table/night table"),
    	A7("media unit/floor-based media unit"),
    	A8("storage unit/armoire"),
    	A9("cabinet/hutch&buffet"),
    	A10("table/side table"),
    	A11("door/single swing door"),
    	A12("mirror/wall-attached mirror"),
    	A13("200 - on the floor"),
    	A14("cabinet/floor-based cabinet"),
    	A15("storage unit/wall-attached storage unit"),
    	A16("sofa/double seat sofa"),
    	A17("sofa/single seat sofa"),
    	A18("sofa/right corner sofa"),
    	A19("table/dining table"),
    	A20("table/table"),
    	A21("table/coffee table"),
    	A22("table/console table"),
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
