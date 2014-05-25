
package com.ira.shopper.utils;

import java.util.List;

import com.google.android.gms.maps.model.LatLng;
import com.ira.shopper.model.Shop;

public class ShopUtils {

    public static List<Shop> getShopsSamples() {
        List<Shop> shops = Lists.newArrayList();

        shops.add(new Shop().id(0).name("Armani Shop").location(new LatLng(48.249246, 11.64988)));
        shops.add(new Shop().id(1).name("Hugo Shop").location(new LatLng(48.250878, 11.651548)));
        shops.add(new Shop().id(2).name("Chanel Shop").location(new LatLng(48.249346, 11.651854)));
        shops.add(new Shop().id(3).name("Dolce Shop").location(new LatLng(48.249403, 11.648378)));
        shops.add(new Shop().id(4).name("Karl Shop").location(new LatLng(48.250353, 11.645679)));
        shops.add(new Shop().id(5).name("Empty Shop").location(new LatLng(48.250403, 11.655056)));

        return shops;
    }

}
