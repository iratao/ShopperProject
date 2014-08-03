package com.ira.shopper.ui;

import java.text.NumberFormat;
import java.util.Locale;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.maps.model.LatLng;
import com.ira.shopper.R;
import com.ira.shopper.ShopperApp;
import com.ira.shopper.ShopperApp.TrackerName;
import com.ira.shopper.eval.ResultsActivity;
import com.ira.shopper.eval.Statistics;
import com.ira.shopper.model.Shop;
import com.ira.shopper.provider.ShopperContract.Stats;
import com.ira.shopper.settings.AppSettings;
import com.ira.shopper.utils.ValueConverter;
import com.squareup.picasso.Picasso;
import com.uwetrottmann.shopr.algorithm.model.ClothingType;
import com.uwetrottmann.shopr.algorithm.model.Color;
import com.uwetrottmann.shopr.algorithm.model.Item;
import com.uwetrottmann.shopr.algorithm.model.Sex;

public class ItemDetailFragment extends Fragment{
	public static final String TAG = "Item Detail Fragment";
	
	public static ItemDetailFragment newInstance() {
        return new ItemDetailFragment();
    }
	
  private Item mItem;
  private Shop mShop;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      View v = inflater.inflate(R.layout.fragment_item_details, container, false);
      
      mItem = ((ItemDetailsActivity)this.getActivity()).getCurrentItem();
      mShop = ((ItemDetailsActivity)this.getActivity()).getShop(mItem.shopId());

      setupViews(v);
      return v;
  }
  
  private void setupViews(View v) {
      ImageView image = (ImageView) v.findViewById(R.id.imageViewItemDetails);
      // load picture
      Picasso.with(this.getActivity())
              .load(mItem.image())
              .placeholder(null)
              .error(R.drawable.ic_action_tshirt)
              .resizeDimen(R.dimen.default_image_width, R.dimen.default_image_height)
              .centerCrop()
              .into(image);

      v.findViewById(R.id.buttonItemDetailsFinish).setOnClickListener(new OnClickListener() {
          @Override
          public void onClick(View v) {
              onFinishTask();
          }
      });

      // title
      TextView itemTitle = (TextView) v.findViewById(R.id.textViewItemDetailsTitle);
      itemTitle.setText(getString(R.string.choice_confirmation, mItem.name()));

      // item attributes
      StringBuilder description = new StringBuilder();
      description
              .append(ValueConverter.getLocalizedStringForValue(this.getActivity(), mItem.attributes()
                      .getAttributeById(ClothingType.ID).currentValue().descriptor()))
              .append("\n")
              .append(ValueConverter.getLocalizedStringForValue(this.getActivity(), mItem.attributes()
                      .getAttributeById(Sex.ID).currentValue().descriptor()))
              .append("\n")
              .append(ValueConverter.getLocalizedStringForValue(this.getActivity(), mItem.attributes()
                      .getAttributeById(Color.ID).currentValue().descriptor()))
              .append("\n")
              .append(NumberFormat.getCurrencyInstance(Locale.GERMANY).format(
                      mItem.price().doubleValue()));
      TextView itemDescription = (TextView) v.findViewById(R.id.textViewItemDetailsAttributes);
      itemDescription.setText(description);
      
      LatLng userPosition = MainActivity.getLastLocation();
      TextView locationTextView = (TextView) v.findViewById(R.id.textViewLocation);
      locationTextView.setText(Html.fromHtml(this.getString(R.string.detail_location, 
    		  mShop.name(), String.valueOf((int)(mShop.getDistance(userPosition))))));
      
      if(AppSettings.isUsingContext(this.getActivity())){
      	  TextView recommendReason = (TextView) v.findViewById(R.id.textViewRecommendReason);
          recommendReason.setText(mItem.getContextAwareReason());
          
      }else{
      	  TextView recommendReason = (TextView) v.findViewById(R.id.textViewRecommendReason);
          recommendReason.setVisibility(TextView.INVISIBLE);
          ImageView recommImage = (ImageView)v.findViewById(R.id.recomm_image);
          recommImage.setVisibility(ImageView.INVISIBLE);
      }
      
  }
  


  protected void onFinishTask() {
      // finish task, store stats to database
  	Tracker tracker = ((ShopperApp)this.getActivity().getApplication()).getTracker(
              TrackerName.APP_TRACKER);
  	
  	// Set screen name.
      // Where path is a String representing the screen name.
  	tracker.setScreenName("ItemDetailsActivity");

      // Send a screen view.
  	tracker.send(new HitBuilders.AppViewBuilder().build());
      
      Uri statUri = Statistics.get().finishTask(this.getActivity(),tracker);
      if (statUri == null) {
          Toast.makeText(this.getActivity(), "Task was not started.", Toast.LENGTH_LONG).show();
          return;
      }

      // display results
      Intent intent = new Intent(this.getActivity(), ResultsActivity.class);
      intent.putExtra(ResultsActivity.InitBundle.STATS_ID,
              Integer.valueOf(Stats.getStatId(statUri)));
      intent.putExtra(ResultsActivity.InitBundle.ITEM_ID, mItem.id());
      startActivity(intent);
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
      // Inflate the menu; this adds items to the action bar if it is present.
	  inflater.inflate(R.menu.item_details, menu);
  }
}
