package codswork.easycard;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

	public Account testAccount;
	public static Context baseContext;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        baseContext = getApplicationContext();
        
        ImageButton ibtn_Add = (ImageButton) findViewById(R.id.ibtn_Add);
        ibtn_Add.setOnClickListener(new ImageButton.OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, Add_Activity.class);
		    	MainActivity.this.startActivity(intent);
			}
        });
        
        ImageButton ibtn_History = (ImageButton) findViewById(R.id.ibtn_Historic);
        ibtn_History.setOnClickListener(new ImageButton.OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, History_Activity.class);
		    	MainActivity.this.startActivity(intent);
			}
        });
        
        ImageButton ibtn_Settings = (ImageButton) findViewById(R.id.ibtn_Settings);
        ibtn_Settings.setOnClickListener(new ImageButton.OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, Settings_Activity.class);
		    	MainActivity.this.startActivity(intent);
			}
        });
        
        
        testAccount = new Account("Teste", null);
        Account.Accounts.add(testAccount);
        
    }
    
    @Override
    public void onResume(){
    	super.onResume();
    	
    	LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
    	
    	TextView temp = new TextView(this);
    	
    	layout.removeAllViews();
    	
    	if(Entry.entries.size() <= 0){
    		temp.setText("N�o h� entradas");
    		layout.addView(temp);
    	}else{
    		if(Entry.entries.size() < 3){
    			for(int x=0; x<Entry.entries.size(); x++){
    				TextView tempTV = new TextView(this);
    				LinearLayout tempLL = new LinearLayout(this);
    				tempLL.setOrientation(LinearLayout.VERTICAL);
    				tempLL.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    				
    				tempTV.setText(Entry.getSalesByDates().get(x).getType() + " - " 
    						+ Entry.getSalesByDates().get(x).getReceiveDate() 
    						+ " - R$ " + History_Activity.df.format(Entry.getSalesByDates().get(x).getValue()));
    				tempLL.addView(tempTV);
    				layout.addView(tempLL);
    			}    	
    		}
    		else{
    			for(int x=0; x<3; x++){
    				TextView tempTV = new TextView(this);
    				LinearLayout tempLL = new LinearLayout(this);
    				tempLL.setOrientation(LinearLayout.VERTICAL);
    				tempLL.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    				
    				tempTV.setText(Entry.getSalesByDates().get(x).getType() + " - " 
    						+ Entry.getSalesByDates().get(x).getReceiveDate() 
    						+ " - R$ " + History_Activity.df.format(Entry.getSalesByDates().get(x).getValue()));
    				tempLL.addView(tempTV);
    				layout.addView(tempLL);
    			}
    		}
    	}
    }
    
}