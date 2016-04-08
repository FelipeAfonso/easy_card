package codswork.easycard;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Comparator;

public class Entry {
	private String type;
	private Calendar day;
	private Double value;
	private Sale sale;
	
	public static ArrayList<Entry> entries = new ArrayList<Entry>();
	
	public Entry(String type, Calendar day, Double value, Sale sale){
		this.type = type;
		this.day = day;
		this.value = value;
		this.sale = sale;
	}
	
	public String getType() {
		return type;
	}
	public Calendar getDay() {
		return day;
	}
	public Double getValue() {
		return value;
	}
	
	public static ArrayList<Entry> getSalesByDates(){
		@SuppressWarnings("unchecked")
		final ArrayList<Entry> temp = (ArrayList<Entry>) Entry.entries.clone();
		Collections.sort(temp, new Comparator<Entry>(){
			@Override
			public int compare(Entry lhs, Entry rhs) {
				return lhs.getDay().compareTo(rhs.getDay());
			}
		});
		return temp;
	}
	
	public String getReceiveDate(){
		return Sale.DateStandart.format(this.day.getTime());
	}

}
