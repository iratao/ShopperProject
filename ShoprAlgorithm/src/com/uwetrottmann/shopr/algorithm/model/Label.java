
package com.uwetrottmann.shopr.algorithm.model;

import com.uwetrottmann.shopr.algorithm.model.Attributes.AttributeValue;

import java.util.Arrays;

public class Label extends GenericAttribute {

    public static final String ID = "label";

    public enum Value implements AttributeValue {
//        ARMANI("Armani"),
//        HUGO_BOSS("Hugo Boss"),
//        CHANEL("Chanel"),
//        DOLCE_AND_GABBANA("Dolce & Gabbana"),
//        KARL_LAGERFELD("Karl Lagerfeld");
    	EDC_ESPRIT("edc by Esprit"),
    	ESPRIT_COLLECTION("ESPRIT Collection"),
    	ESPRIT("ESPRIT"),
    	SOLIVER("s.Oliver"),
    	QS_SOLIVER("QS by s.Oliver"),
    	SIR_OLIVER("Sir Oliver"),
    	TRIANGLE_SOLIVER("Triangle by s.Oliver"),
    	ESPRIT_SPORTS("Triangle by s.Oliver"),
    	ESPRIT_MATERNITY("Esprit Maternity"),
    	ESPRIT_HOME("Esprit Home"),
    	SOLIVER_SELECTION("s.Oliver Selection"),
    	TOM_TAILOR("Tom Tailor"),
    	TOM_TAILOR_POLO_TEAM("Tom Tailor Polo Team"),
    	TOM_TAILOR_DENIM("Tom Tailor Denim"),
    	MARC_OPOLO("Marc O'Polo"),
    	BENETTON("Benetton");

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

    public Label() {
        int numValues = Value.values().length;
        mValueWeights = new double[numValues];
        Arrays.fill(mValueWeights, 1.0 / numValues);
    }

    public Label(Value value) {
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
