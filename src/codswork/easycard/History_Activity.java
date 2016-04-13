package codswork.easycard;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class History_Activity extends ActionBarActivity {
	
	public static DecimalFormat df = new DecimalFormat("#.00");
	
	private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_activity);
        
        layout = (LinearLayout) findViewById(R.id.lL_LayoutScroll);
        
        Update();
    }
    
    private void Update(){
    	
    	for(int x=0; x<Entry.entries.size(); x++){
    		TextView tempTV = new TextView(this);
			LinearLayout tempLL = new LinearLayout(this);
			tempLL.setOrientation(LinearLayout.VERTICAL);
			tempLL.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			
			tempTV.setText(Entry.getSalesByDates().get(x).getType() + " - " 
			+ Entry.getSalesByDates().get(x).getReceiveDate() 
			+ " - R$ " + History_Activity.df.format(Entry.getSalesByDates().get(x).getValue()));
			tempLL.addView(tempTV);
			tempLL.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					
				}
			});
			
			layout.addView(tempLL);
    	}
    	/*
    	for(int x=0; x<testAcc.Sales.size(); x++){
    		
    		Sale temp = testAcc.Sales.get(x);
    		String type;
    		
    		if(temp.getClass() == Sale.class) type = "Débito";
    		else type = "Crédito";
    		
    		
    		if(temp.getClass() == Sale.class){
    			
    			TextView tempTV = new TextView(this);
    			LinearLayout tempLL = new LinearLayout(this);
    			tempLL.setOrientation(LinearLayout.VERTICAL);
    			tempLL.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    			
    			tempTV.setText(type + " - " + temp.getReceiveDate() + " - R$ " + temp.getValueToEnter());
    			tempLL.addView(tempTV);
				layout.addView(tempLL);

    		}else{
    			CreditSale tempCredit = (CreditSale) temp;
    			for(int y=0; y< tempCredit.values.size();y++){
    				
        			TextView tempTV = new TextView(this);

    				LinearLayout tempLL = new LinearLayout(this);
        			tempLL.setOrientation(LinearLayout.VERTICAL);
        			tempLL.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        			
    				tempTV.setText(type + " - " + tempCredit.getReceiveDate(y) + " - R$ " + History_Activity.df.format(tempCredit.values.get(y)));
    				tempLL.addView(tempTV);
    				layout.addView(tempLL);
    			}
    		}
    		
    	}*/
    	
    	
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
