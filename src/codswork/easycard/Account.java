package codswork.easycard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

public class Account {
	
	public static ArrayList<Account> Accounts = new ArrayList<Account>();
	
	public ArrayList<Sale> Sales = new ArrayList<Sale>();
	
	private String name;
	private String description;
	private static int id_count = 0;
	private int id;
	
	public Account(String name, String desc){
		this.name = name;
		this.description = desc;
		this.id = id_count++;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		Account.Accounts.remove(this);
		this.name = name;
		Account.Accounts.add(this);
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		Account.Accounts.remove(this);
		this.description = description;
		Account.Accounts.add(this);
	}
	public int getId() {
		return id;
	}
	
	

}
