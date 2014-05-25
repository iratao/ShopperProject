package com.uwetrottmann.shopr.algorithm.shopper.model;

import com.uwetrottmann.shopr.algorithm.shopper.model.ContextFactors.ContextFactor;
import com.uwetrottmann.shopr.algorithm.shopper.model.ContextFactors.ContextValue;

public abstract class GenericContextFactor implements ContextFactor{

	private ContextValue currentValue;

	@Override
	public ContextValue getCurrentValue() {
		return currentValue;
	}
	
	public GenericContextFactor setCurrentValue(ContextValue currentValue) {
        this.currentValue = currentValue;
        return this;
    }

	@Override
	public String getFactorValueString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[" + this.id() + ":" + this.getCurrentValue() + "]");
		
		return builder.toString();
	}
	
	

	

}
