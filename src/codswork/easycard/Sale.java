package codswork.easycard;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Sale {
	
	public static final DateFormat DateStandart = new SimpleDateFormat("dd/MM/yyyy");
	
	protected double value;
	protected Calendar saleDate;
	protected Calendar receiveDate;
	protected int id;
	protected Card card;
	protected Account account;
	protected double valueToEnter;
	
	protected static int id_count = 0;
	
	public Sale(Card card, Account account, double value){
		DatabaseHelper db = DatabaseHelper.getInstance(MainActivity.baseContext);
		this.saleDate = Calendar.getInstance();
		this.receiveDate = saleDate;
		this.receiveDate.add(Calendar.DAY_OF_YEAR, 1);
		this.id = id_count++;
		this.card = card;
		this.account = account;
		this.value = value;
		this.valueToEnter = ((100 - Settings.getInterestDebit()) / 100) * value;
		db.addDebitSale(db.getWritableDatabase(), this);
	}
	
	public Sale(int id, Calendar sale_date, double value){
		this.id = id;
		this.saleDate = sale_date;
		this.receiveDate = saleDate;
		this.receiveDate.add(Calendar.DAY_OF_YEAR, 1);
		this.value = value;
		this.card = new Card("teste");
		this.account = Account.Accounts.get(0);
		this.valueToEnter = ((100 - Settings.getInterestDebit()) / 100) * value;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.account.Sales.remove(this);
		this.value = value;
		this.account.Sales.add(this);
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.account.Sales.remove(this);
		this.card = card;
		this.account.Sales.add(this);
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account.Sales.remove(this);
		this.account = account;
		this.account.Sales.add(this);
	}

	public Calendar getSaleDate() {
		return saleDate;
	}

	public String getReceiveDate() {
		return Sale.DateStandart.format(receiveDate.getTime());
	}

	public int getId() {
		return id;
	}

	public double getValueToEnter() {
		return valueToEnter;
	}
	@Override
	public String toString(){
		return "Debito";
	}
	

}
