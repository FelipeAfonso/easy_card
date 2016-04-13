package codswork.easycard;

import java.io.Console;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import android.R.integer;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	// Database Info
	private static final String DATABASE_NAME = "postsDatabase";
	private static final int DATABASE_VERSION = 1;
	
	// Table Names
	private static final String TABLE_SETTINGS = "settings";
	
	// Post Table Columns
	private static final String KEY_SETTINGS_INTEREST_DEBIT = "interest_debit";
	private static final String KEY_SETTINGS_INTEREST_CREDIT = "interest_credit";
	
	private ContentValues values = new ContentValues();
	
	private DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_SETTINGS_TABLE = "CREATE TABLE " + TABLE_SETTINGS +
				"(" + " id INTEGER PRIMARY KEY, " +
				KEY_SETTINGS_INTEREST_DEBIT + " DOUBLE";
		values.put(KEY_SETTINGS_INTEREST_DEBIT, 2.4);
		for (int x=0; x<Settings.getLimit(); x++){
			int y = x;
			CREATE_SETTINGS_TABLE += ", " + KEY_SETTINGS_INTEREST_CREDIT + "_" + (y+1) + " DOUBLE";
			values.put(KEY_SETTINGS_INTEREST_CREDIT + "_" + (y+1), Settings.interest_creditDefault[x]);
		}
		CREATE_SETTINGS_TABLE += ")";
		db.execSQL(CREATE_SETTINGS_TABLE);
		db.insert(TABLE_SETTINGS, null, values);
		
	    String CREATE_DEBIT_TABLE = "CREATE TABLE IF NOT EXISTS debit_sale(id INTEGER PRIMARY KEY, " +
	    		"sale_date DATETIME, value DOUBLE(15))";
	    db.execSQL(CREATE_DEBIT_TABLE);
	    String CREATE_CREDIT_TABLE = "CREATE TABLE IF NOT EXISTS credit_sale(id INTEGER PRIMARY KEY, "
	    		+ "sale_date DATETIME, value DOUBLE(15), parcels INTEGER(2))";
	    db.execSQL(CREATE_CREDIT_TABLE);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (oldVersion != newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_SETTINGS);
			onCreate(db);
		}
	}
	
	private static DatabaseHelper sInstance;
	//SINGLETON PARAMETERS 
	public static synchronized DatabaseHelper getInstance(Context context) {
		if (sInstance == null) {
			sInstance = new DatabaseHelper(context.getApplicationContext());
		}
		return sInstance;
	}
	
	
	public double getInterestDebit(SQLiteDatabase db){
		Cursor cursor = db.rawQuery("SELECT interest_debit FROM settings ", null);
		System.out.println("Passou no débito");
		double temp=0;
		if(cursor != null && cursor.moveToFirst()){
			temp = cursor.getDouble(cursor.getColumnIndex(KEY_SETTINGS_INTEREST_DEBIT));
			cursor.close();
		}
		if(temp==0){
			if(values.size() <= 0){
				for(int i=0; i<Settings.getLimit();i++){
					int x = i;
					values.put(KEY_SETTINGS_INTEREST_CREDIT + "_" + (x+1), Settings.interest_creditDefault[i]);
				}
				values.put(KEY_SETTINGS_INTEREST_DEBIT, 3.14);
			}
			db.update(TABLE_SETTINGS, values, "WHERE id == 0", null);
			getInterestDebit(db);
		}
		db.close();
		return temp;
	}
	public void setInterestDebit(SQLiteDatabase db, double value){
		db.execSQL("UPDATE settings SET interest_debit = " + value);
		db.close();
	}
	
	public double[] getInterestCredit(SQLiteDatabase db){
		Cursor cursor = db.rawQuery("SELECT interest_credit_1, interest_credit_2, interest_credit_3," +
				                          " interest_credit_4, interest_credit_5, interest_credit_6," +
				                          " interest_credit_7, interest_credit_8, interest_credit_9," +
				                          " interest_credit_10, interest_credit_11, interest_credit_12 FROM settings", null);
		double[] temp = new double[12];
		if(cursor != null && cursor.moveToFirst()){
			System.out.println("Passou no crédito");
			for(int x=0; x<Settings.getLimit(); x++){
				int y = x;
				temp[x] = cursor.getDouble(cursor.getColumnIndex(KEY_SETTINGS_INTEREST_CREDIT+"_"+(y+1)));
			}
		}cursor.close();
		db.close();
		return temp;
	}
	public void setInterestCredit(SQLiteDatabase db, double value, int parcel){
		db.execSQL("UPDATE settings SET interest_credit_" + (parcel+1) + " = " + value);
		db.close();
	}
	
	public void addDebitSale(SQLiteDatabase db, Sale s){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		db.execSQL("INSERT INTO debit_sale VALUES (" + s.id + ", DATETIME('now')"
					+ ", " + s.value + ")");
		
        Toast.makeText(MainActivity.baseContext, "Venda registrada com sucesso!", Toast.LENGTH_SHORT).show();
		db.close();
	}
	
	public void removeDebitSale(SQLiteDatabase db, Sale s){
		db.execSQL("REMOVE FROM debit_sale WHERE id = " + s.getId() + ";");
		db.close();
	}
	
	public List<Sale> getDebitSales(SQLiteDatabase db){
		ArrayList<Sale> temp = new ArrayList<Sale>();
		Cursor cursor = db.rawQuery("SELECT * FROM debit_sale", null);
		if(cursor != null && cursor.moveToFirst()){
			do{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				int id = cursor.getInt(cursor.getColumnIndex("id"));
				Calendar sale_date = new GregorianCalendar();
				try {
					sale_date.setTime(sdf.parse(cursor.getString(cursor.getColumnIndex("sale_date"))));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				double value = cursor.getDouble(cursor.getColumnIndex("value"));
				Sale s = new Sale(id, sale_date, value);
				temp.add(s);
			}while(cursor.moveToNext());
		} cursor.close();
		return temp;
	}
	
	public void addCreditSale(SQLiteDatabase db, CreditSale s){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		db.execSQL("INSERT INTO credit_sale VALUES (" + s.id + ", DATETIME('now')"
					+ ", " + s.value + ", " + s.getParcels() + ")");
		
        Toast.makeText(MainActivity.baseContext, "Venda registrada com sucesso!", Toast.LENGTH_SHORT).show();
		db.close();
	}
	
	public List<CreditSale> getCreditSales(SQLiteDatabase db){
		ArrayList<CreditSale> temp = new ArrayList<CreditSale>();
		Cursor cursor = db.rawQuery("SELECT * FROM credit_sale", null);
		if(cursor != null && cursor.moveToFirst()){
			do{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				int id = cursor.getInt(cursor.getColumnIndex("id"));
				Calendar sale_date = new GregorianCalendar();
				try {
					sale_date.setTime(sdf.parse(cursor.getString(cursor.getColumnIndex("sale_date"))));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				double value = cursor.getDouble(cursor.getColumnIndex("value"));
				int parcel = cursor.getInt(cursor.getColumnIndex("parcels"));
				CreditSale s = new CreditSale(id, value, parcel, sale_date);
				temp.add(s);
			}while(cursor.moveToNext());
		} cursor.close();
		return temp;
	}
}
