package com.uwetrottmann.shopr.algorithm.shopper.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.uwetrottmann.shopr.algorithm.model.ClothingType;

public class ContextFactors {
	public interface ContextFactor {
		public String id();
		public ContextValue getCurrentValue();
		public String getFactorValueString();
		public double getSimilarity(ContextFactor factor);
		public double getWeight(ClothingType type);
		public String getFactorDescription();
	}
	
	public interface ContextValue {
		public int index();
		public String valueName();
	}
	
	private HashMap<String, ContextFactor> contextFactors = 
			new HashMap<String, ContextFactors.ContextFactor>();
	
	public ContextFactor getContextFactorById(String id){
		return contextFactors.get(id);
	}
	
	/**
     * Adds the new context factor mapped by its {@link ContextFactor#id()} or replaces
     * an existing one.
     */
    public ContextFactors putContextFactor(ContextFactor factor) {
    	contextFactors.put(factor.id(), factor);
        return this;
    }
    
    public List<ContextFactor> getAllContextFactors() {
        List<ContextFactor> contextFactorList = new ArrayList<ContextFactor>();
        contextFactorList.addAll(contextFactors.values());

        return contextFactorList;
    }
    
    public String getAllContextFactorsString() {
        StringBuilder factorsStr = new StringBuilder("");

        List<ContextFactor> allContextFactors = getAllContextFactors();
        for (int i = 0; i < allContextFactors.size(); i++) {
        	factorsStr.append(allContextFactors.get(i).getFactorValueString());
            if (i != allContextFactors.size() - 1) {
            	factorsStr.append(" ");
            }
        }

        return factorsStr.toString();
    }
    
    /**
     * Calls {@link #putAttribute(Attribute)} with a {@link GenericAttribute}
     * implementation matching the given id.
     */
    public void initializeContextFactor(ContextFactor factor) {
        try {
            Class<?> factorClass = Class.forName(factor.getClass().getCanonicalName());
            ContextFactor newFactor = (ContextFactor) factorClass.newInstance();
            putContextFactor(newFactor);
        } catch (ClassNotFoundException ex) {
            System.err.println(ex + " Interpreter class must be in class path.");
        } catch (InstantiationException ex) {
            System.err.println(ex + " Interpreter class must be concrete.");
        } catch (IllegalAccessException ex) {
            System.err.println(ex + " Interpreter class must have a no-arg constructor.");
        }
    }

}
