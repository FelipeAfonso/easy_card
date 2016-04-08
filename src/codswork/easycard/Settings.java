package codswork.easycard;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Locale;

import android.content.Context;

public class Settings {
	
		public static NumberFormat numberFormat = NumberFormat.getInstance(Locale.getDefault());
	
		private static double interest_debit = 0.0;
		private static double[] interest_credit = {(double)0.0,  (double)0.0, (double)0.0, 
				 								   (double)0.0,  (double)0.0, (double)0.0, 
				 								   (double)0.0,  (double)0.0, (double)0.0, 
				 								   (double)0.0,  (double)0.0, (double)0.0};
		
		private static double interest_debitDefault = (double) 2.4;
		public static double[] interest_creditDefault = {(double) 3.6,  (double)4.1,   (double)4.1, 
													      (double)10.36, (double)12.35, (double)14.34, 
													      (double)16.33, (double)18.32, (double)20.31, 
													      (double)22.30, (double)24.29, (double)26.28};
		private static double[] interest_creditNullEvaluation = {(double)0.0,  (double)0.0, (double)0.0, 
		      													 (double)0.0,   (double)0.0, (double)0.0, 
		      													 (double)0.0,   (double)0.0, (double)0.0, 
		      													 (double)0.0,   (double)0.0, (double)0.0};
		
		private static int parcels_limit;
		private static int parcels_limitDefault = 12;
		
		public static double getInterestDebit(){
			if(interest_debit == 0.0){
				DatabaseHelper db = DatabaseHelper.getInstance(MainActivity.baseContext);
				Settings.setInterest_debit(db.getInterestDebit(db.getWritableDatabase()));
				db.close();
				return interest_debit;
			}else return interest_debit;
		}
		
		public static double getInterestCredit(int parcels, Context context){
			if(Arrays.equals(interest_credit, interest_creditNullEvaluation)){
				DatabaseHelper db = DatabaseHelper.getInstance(context);
				double[] temp = db.getInterestCredit(db.getReadableDatabase());
				for(int x=0; x<Settings.getLimit(); x++){
					Settings.setInterest_credit(temp[x], x);
				}
				db.close();
				return interest_creditDefault[parcels-1];
			}else return interest_credit[parcels-1];
		}
		
		public static int getLimit(){
			if(parcels_limit == 0.0) return parcels_limitDefault;
			else return parcels_limit;
		}

		public static void setInterest_debit(double value) {
			DatabaseHelper db = DatabaseHelper.getInstance(MainActivity.baseContext); 
			db.setInterestDebit(db.getWritableDatabase(), value);
			Settings.interest_debit = value;
			db.close();
		}

		public static void setInterest_credit(double interest_credit, int parcel) {
			DatabaseHelper db = DatabaseHelper.getInstance(MainActivity.baseContext); 
			db.setInterestCredit(db.getWritableDatabase(), interest_credit, parcel);
			Settings.interest_credit[parcel] = interest_credit;
			db.close();
		}

		public static void setParcels_limit(int parcels_limit) {
			Settings.parcels_limit = parcels_limit;
		}
}
