package com.uwetrottmann.shopr.algorithm.shopper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.uwetrottmann.shopr.algorithm.AdaptiveSelection;
import com.uwetrottmann.shopr.algorithm.BoundedGreedySelection;
import com.uwetrottmann.shopr.algorithm.Critique;
import com.uwetrottmann.shopr.algorithm.Feedback;
import com.uwetrottmann.shopr.algorithm.model.Attributes.Attribute;
import com.uwetrottmann.shopr.algorithm.model.ClothingType;
import com.uwetrottmann.shopr.algorithm.model.Item;
import com.uwetrottmann.shopr.algorithm.shopper.model.Budget;
import com.uwetrottmann.shopr.algorithm.shopper.model.ContextCase;
import com.uwetrottmann.shopr.algorithm.shopper.model.ContextFactors;
import com.uwetrottmann.shopr.algorithm.shopper.model.ContextFactors.ContextFactor;



public class ContextAwareRecommendation {
//	private static final int NUM_SIM_CASES = 10;

	private static ContextAwareRecommendation _instance;
	public static synchronized ContextAwareRecommendation get() {
        if (_instance == null) {
            _instance = new ContextAwareRecommendation();
        }
        return _instance;
    }
	
	private ContextFactors mCurrentContext;
	private List<ContextCase> mContextCaseBase;
//	private int mNumSimCases;
	
	private ContextAwareRecommendation() {
		mCurrentContext = new ContextFactors();
//		mNumSimCases = NUM_SIM_CASES;
		mContextCaseBase = new ArrayList<ContextCase>();
    }
	
	public void setInitialContextCaseBase(List<ContextCase> caseBase) {
        mContextCaseBase = caseBase;
    }
	
	public void setCurrentContext(ContextFactors currentContext){
		this.mCurrentContext = currentContext;
	}
	
	public void setMaxSimReturn(int limit){
//		this.mNumSimCases = Math.max(1, limit);
	}
	
	/**
	 * Find the most similar items in caseBase to the items that are contained 
	 * in the similar context cases.
	 * @param items - items that are contained in the similar context cases
	 * @param caseBase - the initial case base from AdaptiveSelection
	 * @return
	 */
	public List<Item> getInitialRecommendation(){
		// list items contains items in similar context cases
//		List<ContextCase> cases = this.getSimilarContextCases();
		List<ContextCase> recomCases = this.getSimilarDiverseContextCases();
		List<Item> recommendations = new ArrayList<Item>();
		
		for(ContextCase mCase: recomCases){
			mCase.getItem().setSimilarContext(mCase.getContextFactors());
			recommendations.add(mCase.getItem());
		}
			
//		List<Item> caseBase = AdaptiveSelection.get().getInitialCaseBase();
//		if(caseBase.size() == 0 )
//			return recommendations;
//		for(Item item: caseBase){
//			
//			List<Similarity> similarities = new ArrayList<Similarity>(cases.size()) ;
//			Feedback feedback = new Feedback();
//	        feedback.isPositiveFeedback(true);
//	        Critique critique = new Critique();
//			for(ContextCase mcase: cases){
//				Item sampleItem = mcase.getItem();
//				for(Attribute attr: sampleItem.attributes().getAllAttributes()){
//					feedback.addAttributes(attr);
//				}
//		        
//		        critique.item(sampleItem);
//		        critique.feedback(feedback);
//		        
//		        AdaptiveSelection.get().submitCritique(critique);
//		        /*
//		         * Calculate similarity of item in the case base to each item in the 
//		         * similar context case base. 
//		         * Final item similarity =  item similarity * (1-contextSimilarity). 
//		         * Because for context similarity, the smaller the value, the more similar
//		         * the context.
//		         */
//		        similarities.add(new Similarity(
//		        		AdaptiveSelection.get().calculateSimilarityOfItemToQuery(item)*(1-mcase.getContextSimilarity()),
//		        		mcase.getContextFactors()));
//		        AdaptiveSelection.get().resetQuery();
//			}
//			
//			Collections.sort(similarities, new Comparator<Similarity>(){
//
//				@Override
//				public int compare(Similarity sim1, Similarity sim2) {
//					if(sim1.getSim() > sim2.getSim()){
//						return 1;
//					}else if(sim1.getSim() < sim2.getSim()){
//						return -1;
//					}else{
//						return 0;
//					}
//				}
//				
//			});
////			Arrays.sort(sims);
//			// Store the largest similarity for each item for later use in sorting.
//			item.querySimilarity(similarities.get(similarities.size() - 1).getSim());
//			item.setSimilarContext(similarities.get(similarities.size() - 1).getContext());
//		}
//		
//		// Sort in descending order.
//		Collections.sort(caseBase, new Comparator<Item>() {
//            @Override
//            public int compare(Item o1, Item o2) {
//                // is o2 smaller?
//                if (o1.querySimilarity() > o2.querySimilarity()) {
//                    return -1;
//                }
//                // is o2 bigger?
//                if (o1.querySimilarity() < o2.querySimilarity()) {
//                    return 1;
//                }
//                // they are equal!
//                return 0;
//            }
//        });
//
////		for(int i=0; i<AdaptiveSelection.get().getMaxRecommendations(); i++){
////			recommendations.add(caseBase.remove(0));
////		}
//		
//		recommendations = BoundedGreedySelection
//				.boundedGreedySelectionForContext(
//						AdaptiveSelection.get().getCurrentQuery(), 
//						caseBase, 
//						AdaptiveSelection.get().getMaxRecommendations(), 
//						AdaptiveSelection.getBoundDefault());
		
		return recommendations;
	}
	
	public List<Item> getItemInContextCaseBase(List<ContextCase> contextCaseBase){
		List<Item> items = new ArrayList<Item>();
		for(ContextCase contextCase: contextCaseBase){
			items.add(contextCase.getItem());
		}
		return items;
	}
	
	public List<ContextCase> getSimilarDiverseContextCases(){
		List<ContextCase> simContextCases = new ArrayList<ContextCase>();
		
		for(ContextCase contextCase: this.mContextCaseBase){
			contextCase.setContextSimilarity(getContextSimilarity(
					contextCase.getContextFactors(), 
					(ClothingType) contextCase.getItem().attributes().getAttributeById(ClothingType.ID)));
		}
		
		/**
		 * Construct the compare function so that the sort function will put 
		 * the most similar case first in the list.
		 */
		Collections.sort(this.mContextCaseBase, new Comparator<ContextCase>(){

			@Override
			public int compare(ContextCase case1, ContextCase case2) {
				// case1 is more similar 
				if(case1.getContextSimilarity() < case2.getContextSimilarity()){
					return -1;
				}
				if(case1.getContextSimilarity() > case2.getContextSimilarity()){
					return 1;
				}
				return 0;
			}
			
		});
		
		// In case the context case base is not big enough.
		int maxRecommend = Math.min(mContextCaseBase.size(), AdaptiveSelection.get().getMaxRecommendations());
		simContextCases = BoundedGreedySelection
				.boundedGreedySelectionForContext(
						mContextCaseBase, 
						maxRecommend, 
						AdaptiveSelection.getBoundDefaultForContext());
//		for(int i = 0; i < bound; i++){
//			simContextCases.add(this.mContextCaseBase.remove(0));
//		}
		
		for(ContextCase mCase: simContextCases){
			if(!isAvailable(mCase.getItem())){
				mCase.setItem(getSimilarItem(mCase.getItem()));
			}
		}
		return simContextCases;
	}
	
	public boolean isAvailable(Item item){
		List<Item> items = AdaptiveSelection.get().getInitialCaseBase();
		for(Item i: items){
			if(i.id() == item.id())
				return true;
		}
		return false;
	}
	
	public Item getSimilarItem(Item item){
        return AdaptiveSelection.get().getSimilarItem(item);
	}
	
//	public List<ContextCase> getSimilarContextCases(){
//		List<ContextCase> simContextCases = new ArrayList<ContextCase>();
//		
//		for(ContextCase contextCase: this.mContextCaseBase){
//			contextCase.setContextSimilarity(getContextSimilarity(
//					contextCase.getContextFactors(), 
//					(ClothingType) contextCase.getItem().attributes().getAttributeById(ClothingType.ID)));
//		}
//		
//		/**
//		 * Construct the compare function so that the sort function will put 
//		 * the most similar case first in the list.
//		 */
//		Collections.sort(this.mContextCaseBase, new Comparator<ContextCase>(){
//
//			@Override
//			public int compare(ContextCase case1, ContextCase case2) {
//				// case1 is more similar 
//				if(case1.getContextSimilarity() < case2.getContextSimilarity()){
//					return -1;
//				}
//				if(case1.getContextSimilarity() > case2.getContextSimilarity()){
//					return 1;
//				}
//				return 0;
//			}
//			
//		});
//		
//		// In case the context case base is not big enough.
//		int bound = Math.min(mContextCaseBase.size(), this.mNumSimCases);
//		for(int i = 0; i < bound; i++){
//			simContextCases.add(this.mContextCaseBase.remove(0));
//		}
//		
//		return simContextCases;
//	}
	
	/**
	 * The smaller the value the more similar they are.
	 * Ref paper: ITR: a Case-Based Travel Advisory System
	 * @param currentContext
	 * @param targetContext
	 * @return
	 */
	public double getContextSimilarity(ContextFactors targetContext, ClothingType type){
		double sim = 0;
		double weight = 0;
		/*
		 * For all the context factors that are specified by the user, calculate the similarity.
		 * If the targetContext does not contain the context factor in currentContext, the similarity 
		 * will be added by 1*weight(weight shows the level of importance of this factor for the current
		 * item). On the other hand, we don't consider the case when the currentContext does not contain
		 * the context factors that exist in targetContext. Because the user chooses to ignore those factors. 
		 */
		for(ContextFactor factor: this.mCurrentContext.getAllContextFactors()){
			ContextFactor factor2 = targetContext.getContextFactorById(factor.id());
			
			if(factor2 != null){
				sim += factor.getSimilarity(factor2)*factor.getWeight(type);
				weight += factor.getWeight(type);
			}else{
				// Only calculate the similarity between known context factors.
				sim += 1*factor.getWeight(type); 
				weight += factor.getWeight(type);
			}
		}
		
		sim = Math.sqrt(sim/weight);
		
		return sim;
	}
	
	public class Similarity{
		private double sim;
		private ContextFactors factors;
		public Similarity(double sim, ContextFactors factors){
			this.sim = sim;
			this.factors = factors;
		}
		
		public double getSim(){
			return sim;
		}
		
		public ContextFactors getContext(){
			return factors;
		}
		
	}
	
	public static void main(String[] args){
		Budget budget1 = new Budget();
		budget1.setCurrentValue(Budget.Value.BUDGET_BUYER);
		ContextFactors context1 = new ContextFactors();
		context1.putContextFactor(budget1);
		
		Budget budget2 = new Budget();
		budget2.setCurrentValue(Budget.Value.HIGH_SPENDER);
		ContextFactors context2 = new ContextFactors();
		context2.putContextFactor(budget2);
		
		ContextCase contextCase = new ContextCase();
		contextCase.setContextFactors(context2);
		ContextCase contextCase2 = new ContextCase();
		contextCase2.setContextFactors(context1);
		List<ContextCase> casebase = new ArrayList<ContextCase>();
		casebase.add(contextCase);
		casebase.add(contextCase2);
		
		ContextAwareRecommendation recom = ContextAwareRecommendation.get();
		recom.setCurrentContext(context2);
		recom.setInitialContextCaseBase(casebase);
		List<ContextCase> cases = recom.getSimilarDiverseContextCases();
		for(ContextCase mCase: cases){
			System.out.println(mCase.getContextFactors().getAllContextFactorsString());
		}
		
	}
}
