package com.uwetrottmann.shopr.algorithm.shopper.model;

import com.uwetrottmann.shopr.algorithm.model.Item;


/**
 * Represent one case in the case base
 * @author taoyurong
 *
 */
public class ContextCase {
	private int caseid;
	private Item item;
	private ContextFactors factors;
	private double contextSimilarity;
	
	 public int getCaseId() {
		 return caseid;
	 }
	 
	 public ContextCase setId(int id) {
		 this.caseid = id;
		 return this;
	 }
	 
	 public Item getItem(){
		 return item;
	 }
	 
	 public ContextCase setItem(Item item){
		 this.item = item;
		 return this;
	 }
	 
	 public ContextFactors getContextFactors() {
		 return factors;
	 }

	 public ContextCase setContextFactors(ContextFactors factors) {
		 this.factors = factors;
		 return this;
	 }
	 
	 public double getContextSimilarity() {
		 return contextSimilarity;
     }

	 public ContextCase setContextSimilarity(double contextSimilarity) {
		 this.contextSimilarity = contextSimilarity;
		 return this;
	 }
	 
	 

}
