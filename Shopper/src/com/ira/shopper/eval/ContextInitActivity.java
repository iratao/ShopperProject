package com.ira.shopper.eval;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.google.analytics.tracking.android.EasyTracker;
import com.ira.shopper.R;
import com.ira.shopper.settings.ContextSettings;
import com.ira.shopper.ui.MainActivity;
import com.uwetrottmann.shopr.algorithm.shopper.model.Budget;
import com.uwetrottmann.shopr.algorithm.shopper.model.ContextFactors;
import com.uwetrottmann.shopr.algorithm.shopper.model.DayOfTheWeek;
import com.uwetrottmann.shopr.algorithm.shopper.model.Intent;
import com.uwetrottmann.shopr.algorithm.shopper.model.Mood;
import com.uwetrottmann.shopr.algorithm.shopper.model.Temperature;

/**
 * In this activity, system will select randomly from 5 precreated context set 
 * then show it to the user and the user in the user study will choose ideal 
 * item imaging being under this selected context.
 * @author taoyurong
 *
 */
public class ContextInitActivity extends Activity{

	// The number of selectable context.
	public static final int SET_SIZE = 5;
	
	public static int indexCurrentContext;
	public static boolean USE_REAL_CONTEXT = false;
	
	private static List<ContextFactors> contextSet = new ArrayList<ContextFactors>(SET_SIZE);
	private static List<String> contextText = new ArrayList<String>(SET_SIZE);
	
//	private static ContextFactors mCurrentContext = new ContextFactors();
//	private static String mContextText;
	
	TextView initContextTextView;
	Button readyStartButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_context_init);
		initContextTextView = (TextView)findViewById(R.id.textInitContext);
		readyStartButton = (Button)findViewById(R.id.buttonReadyStart);
		
		ContextSettings.setUseRealContext(USE_REAL_CONTEXT);
		indexCurrentContext = new Random().nextInt(SET_SIZE);
		initContext();
		initContextTextView.setText(contextText.get(indexCurrentContext));
		readyStartButton.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v) {
				onReadStart();
			}
		});
		
	}

	protected void onReadStart(){
		Statistics.get().setContextId(indexCurrentContext);
		setContext();
		startActivity(new android.content.Intent(this, MainActivity.class));
	}
	
	public void setContext(){
		ContextSettings.setCurrentContext(getBaseContext(), contextSet.get(indexCurrentContext));
		if(indexCurrentContext == 0 || indexCurrentContext == 2)
			ContextSettings.setUseDistance(false, this);
		else{
			ContextSettings.setUseDistance(true, this);
		}
	}
	
	public static ContextFactors getContextByIndex(int index){
		return contextSet.get(index);
	}
	
	private void initContext(){

		// Context 1
		ContextFactors mCurrentContext1 = new ContextFactors();
		
		String mContextText1 = "Imagine that you want to buy clothes for daily wear, "
				+ "you are a budget buyer and the temperature is cold. "
				+ "You don't care the exact location of the clothes.";
		
		Budget mBudget1 = new Budget();
		Temperature mTemperature1 = new Temperature();
		Intent mIntent1 = new Intent();
		
		mBudget1.setCurrentValue(Budget.Value.BUDGET_BUYER);
		mTemperature1.setCurrentValue(Temperature.Value.COLD);
		mIntent1.setCurrentValue(Intent.Value.DAILY_WEAR);
		
		mCurrentContext1.putContextFactor(mBudget1);
		mCurrentContext1.putContextFactor(mIntent1);
		mCurrentContext1.putContextFactor(mTemperature1);
		
		// Context 2
		ContextFactors mCurrentContext2 = new ContextFactors();
		
		String mContextText2 = "Imagine that you want to buy clothes for daily wear, "
				+ "you are a budget buyer and the temperature is hot. "
				+ "You want to find only clothes that are near you (within 2km).";
		
		Intent mIntent2 = new Intent();
		Temperature mTemperature2 = new Temperature();
		Budget mBudget2 = new Budget();
		
		mIntent2.setCurrentValue(Intent.Value.DAILY_WEAR);
		mTemperature2.setCurrentValue(Temperature.Value.WARM);
		mBudget2.setCurrentValue(Budget.Value.BUDGET_BUYER);

		mCurrentContext2.putContextFactor(mIntent2);
		mCurrentContext2.putContextFactor(mTemperature2);
		mCurrentContext2.putContextFactor(mBudget2);
		
		// Context 3
		ContextFactors mCurrentContext3 = new ContextFactors();
		
		String mContextText3 = "Imagine that you are feeling like a party animal, "
				+ "it is weekend and you are a budget buyer. "
				+ "You don't care the exact location of the clothes.";
		
		Mood mMood3 = new Mood();
		DayOfTheWeek mDayOfTheWeek3 = new DayOfTheWeek();
		Budget mBudget3 = new Budget();
		
		mMood3.setCurrentValue(Mood.Value.LIKE_A_PARTY_ANIMAL);
		mDayOfTheWeek3.setCurrentValue(DayOfTheWeek.Value.WEEKEND);
		mBudget3.setCurrentValue(Budget.Value.BUDGET_BUYER);
		
		mCurrentContext3.putContextFactor(mMood3);
		mCurrentContext3.putContextFactor(mDayOfTheWeek3);
		mCurrentContext3.putContextFactor(mBudget3);
		
		// Context 4
		ContextFactors mCurrentContext4 = new ContextFactors();
		String mContextText4 = "Imagine that you want to buy clothes for working purpose, "
				+ "you are a high spender and the temperature is warm. "
				+ "You only want to find clothes that are near you (within 2km).";
		
		Intent mIntent4 = new Intent();
		Budget mBudget4 = new Budget();
		Temperature mTemperature4 = new Temperature();
		
		mIntent4.setCurrentValue(Intent.Value.WORK);
		mBudget4.setCurrentValue(Budget.Value.HIGH_SPENDER);
		mTemperature4.setCurrentValue(Temperature.Value.WARM);
		
		mCurrentContext4.putContextFactor(mIntent4);
		mCurrentContext4.putContextFactor(mBudget4);
		mCurrentContext4.putContextFactor(mTemperature4);
		
		// Context 5
		ContextFactors mCurrentContext5 = new ContextFactors();
		
		String mContextText5 = "Imagine that you want to buy clothes for sports purpose, "
				+ "you are feeling outdoorsy and the temperature is hot. "
				+ "You only want to find clothes that are near you (within 2km).";
		
		Intent mIntent5 = new Intent();
		Mood mMood5 = new Mood();
		Temperature mTemperature5 = new Temperature();
		
		mIntent5.setCurrentValue(Intent.Value.SPORTS);
		mMood5.setCurrentValue(Mood.Value.OUTDOORSY);
		mTemperature5.setCurrentValue(Temperature.Value.HOT);
		
		mCurrentContext5.putContextFactor(mIntent5);
		mCurrentContext5.putContextFactor(mMood5);
		mCurrentContext5.putContextFactor(mTemperature5);

		contextSet.add(mCurrentContext1);
		contextSet.add(mCurrentContext2);
		contextSet.add(mCurrentContext3);
		contextSet.add(mCurrentContext4);
		contextSet.add(mCurrentContext5);

		contextText.add(mContextText1);
		contextText.add(mContextText2);
		contextText.add(mContextText3);
		contextText.add(mContextText4);
		contextText.add(mContextText5);

	}

}
