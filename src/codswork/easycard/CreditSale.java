package codswork.easycard;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class CreditSale extends Sale{
	
	private int parcels;
	public ArrayList<Double> values = new ArrayList<Double>();
	public LinkedList<Calendar> days = new LinkedList<Calendar>();

	public CreditSale(Card card, Account account, double value, int parcels) {
		super(card, account, value);
		this.saleDate = Calendar.getInstance();
		this.id = id_count++;
		this.card = card;
		this.account = account;
		this.value = value;
		this.parcels = parcels;
		this.valueToEnter = ((100 - Settings.getInterestCredit(parcels, null)) / 100) * value;
		calculateCreditReturn(saleDate);
		DatabaseHelper db = DatabaseHelper.getInstance(MainActivity.baseContext);
		db.addDebitSale(db.getWritableDatabase(), this);
	}
	
	public CreditSale(int id, double value, int parcels, Calendar sale_date){
		super(new Card("teste"), MainActivity.testAccount, value);
		this.saleDate = sale_date;
		this.id = id;
		this.card = new Card("teste");
		this.account = MainActivity.testAccount;
		this.value = value;
		this.parcels = parcels;
		this.valueToEnter = ((100 - Settings.getInterestCredit(parcels, null))/100) * value;
		calculateCreditReturn(saleDate);
	}

	public int getParcels() {
		return parcels;
	}

	public void setParcels(int parcels) {
		this.parcels = parcels;
	}
	
	private void calculateCreditReturn(Calendar c){
		Calendar tempC = (Calendar) c.clone();
		for(int x=0; x<parcels; x++){
			tempC.add(Calendar.DAY_OF_YEAR, 30);
			values.add(valueToEnter/parcels);
			days.add((Calendar) tempC.clone());
		}
	}
	
	public String getReceiveDate(int i){
		return Sale.DateStandart.format(days.get(i).getTime());
	}
	
	@Override
	public String toString(){
		return "Credito";
	}
	

}
