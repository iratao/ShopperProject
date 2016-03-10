package com.uwetrottmann.shopr.algorithm.home.model.attributes;

import java.util.Arrays;

import com.uwetrottmann.shopr.algorithm.model.GenericAttribute;
import com.uwetrottmann.shopr.algorithm.model.Attributes.AttributeValue;

public class Style extends GenericAttribute{
	public static final String ID = "style";

    public enum Value implements AttributeValue {
//    	EDC_ESPRIT("edc by Esprit"),
//    	ESPRIT_COLLECTION("ESPRIT Collection"),
//    	ESPRIT("ESPRIT"),
//    	SOLIVER("s.Oliver"),
//    	QS_SOLIVER("QS by s.Oliver"),
//    	SIR_OLIVER("Sir Oliver"),
//    	TRIANGLE_SOLIVER("Triangle by s.Oliver"),
//    	ESPRIT_SPORTS("Triangle by s.Oliver"),
//    	ESPRIT_MATERNITY("Esprit Maternity"),
//    	ESPRIT_HOME("Esprit Home"),
//    	SOLIVER_SELECTION("s.Oliver Selection"),
//    	TOM_TAILOR("Tom Tailor"),
//    	TOM_TAILOR_POLO_TEAM("Tom Tailor Polo Team"),
//    	TOM_TAILOR_DENIM("Tom Tailor Denim"),
//    	MARC_OPOLO("Marc O'Polo"),
//    	BENETTON("Benetton");
    	
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

    @Override
    public String id() {
        return ID;
    }

    @Override
    public Value[] getValueSymbols() {
        return Value.values();
    }

}
