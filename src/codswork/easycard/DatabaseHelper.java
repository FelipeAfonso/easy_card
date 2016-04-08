package codswork.easycard;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

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
		
	    String CREATE_ENTRY_TABLE = "CREATE TABLE entry(id INTEGER PRIMARY KEY, type CHAR(1), " +
	    		"entry_date DATE, value DOUBLE(15), sale_id INT(8)";
	    
	    db.execSQL(CREATE_ENTRY_TABLE);
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
		}
		db.close();
		return temp;
	}
	public void setInterestCredit(SQLiteDatabase db, double value, int parcel){
		db.execSQL("UPDATE settings SET interest_credit_" + (parcel+1) + " = " + value);
		db.close();
	}
}
