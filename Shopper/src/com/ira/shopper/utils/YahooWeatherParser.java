package com.ira.shopper.utils;


import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

public class YahooWeatherParser {
	private static final String ns = null;
	/**
	 * Get temperature in celsis.
	 * @param in
	 * @return
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	public int getTemperature(InputStream in) throws XmlPullParserException, IOException{
		try{
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(in, null);
			parser.nextTag();
			Item item = readRss(parser);
			if(item != null){
				return item.temperature;
			}else{
				return 0;
			}
		}finally{
			in.close();
		}
	}
	
	public int getCondition(InputStream in)throws XmlPullParserException, IOException{
		try{
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(in, null);
			parser.nextTag();
			Item item = readRss(parser);
			if(item != null){
				return item.condition;
			}else{
				return 0;
			}
		}finally{
			in.close();
		}
	}
	
	private Item readRss(XmlPullParser parser) throws XmlPullParserException, IOException{
		parser.require(XmlPullParser.START_TAG, ns, "rss");
		while(parser.next() != XmlPullParser.END_TAG){
			if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
			String name = parser.getName();
			if (name.equals("channel")) {
				return readChannel(parser);
	        } else {
	            skip(parser);
	        }
		}
		return null;
	}

	
	private Item readChannel(XmlPullParser parser) throws XmlPullParserException, IOException{
		parser.require(XmlPullParser.START_TAG, ns, "channel");
		while(parser.next() != XmlPullParser.END_TAG){
			if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
			String name = parser.getName();
			if (name.equals("item")) {
				return readItem(parser);
	        } else {
	            skip(parser);
	        }
		}
		return null;
	}
	
	private Item readItem(XmlPullParser parser) throws XmlPullParserException, IOException{
		parser.require(XmlPullParser.START_TAG, ns, "item");
		int condition = 0;
		int temperature = 0;
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
			String name = parser.getName();
			if (name.equals("yweather:condition")) {
				return readCondition(parser);
	        }else{
	        	skip(parser);
	        }
		}
		return new Item(condition, temperature);
	}
	
	private Item readCondition(XmlPullParser parser) throws XmlPullParserException, IOException{
		int condition,temperature;
		parser.require(XmlPullParser.START_TAG, ns, "yweather:condition");
		String conditionStr = parser.getAttributeValue(null, "code");  
		condition = Integer.parseInt(conditionStr);
		String temperatureStr = parser.getAttributeValue(null, "temp");  
		temperature = Integer.parseInt(temperatureStr);
		parser.nextTag();
		parser.require(XmlPullParser.END_TAG, ns, "yweather:condition");
		return new Item(condition, temperature);
	}
	
	
	
	public static class Item {
		public final int condition;
		public final int temperature;
		private Item(int condition, int temperature){
			this.condition = condition;
			this.temperature = temperature;
		}
	}
	
	private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
	    if (parser.getEventType() != XmlPullParser.START_TAG) {
	        throw new IllegalStateException();
	    }
	    int depth = 1;
	    while (depth != 0) {
	        switch (parser.next()) {
	        case XmlPullParser.END_TAG:
	            depth--;
	            break;
	        case XmlPullParser.START_TAG:
	            depth++;
	            break;
	        }
	    }
	 }
}
