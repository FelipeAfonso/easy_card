package codswork.easycard;

import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

public class Settings_Activity extends ActionBarActivity {
	
	private static boolean onCreateGambi = true;
	private TextView tV_Deb;
	//private TextView tV_Limit;
	private LinearLayout lL_Main;
	private LinearLayout lL_Deb;
	//private LinearLayout lL_Lim;

	 
		
	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.settings_activity);
	    
	    if(onCreateGambi){
	    	Intent intent = getIntent();
	    	finish();
	    	startActivity(intent);
	    	onCreateGambi = false;
	    }
	    
	    DatabaseHelper db = DatabaseHelper.getInstance(this);
	    db.close();
	    
	    tV_Deb = (TextView) findViewById(R.id.tV_Deb);
	    //tV_Limit = (TextView) findViewById(R.id.tV_Limit);
	    lL_Main = (LinearLayout) findViewById(R.id.lL_main);
	    lL_Deb = (LinearLayout) findViewById(R.id.lL_Deb);
	    //lL_Lim = (LinearLayout) findViewById(R.id.lL_LimiteParcelas);
	    lL_Deb.setOnClickListener(new LinearLayout.OnClickListener(){
	    	@Override
	    	public void onClick(View v) {
	    		final AlertDialog.Builder alert = new AlertDialog.Builder(Settings_Activity.this);
	    		
	    		alert.setMessage("Insira a o Juros (Débito)");
	    		final EditText input = new EditText(v.getContext());
	    		input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
	    		input.setRawInputType(Configuration.KEYBOARD_12KEY);
	    		alert.setView(input);  
	    		alert.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
	    			public void onClick(DialogInterface dialog, int whichButton) {
	    				try{
	    					Number n = Double.parseDouble((input.getText().toString().replaceAll(",", ".")));
	    					Settings.setInterest_debit(n.doubleValue());
	    					Intent intent = getIntent();
	    				    finish();
	    				    startActivity(intent);
	    				}catch(Exception e){
	    					AlertDialog.Builder alertTemp = new AlertDialog.Builder(Settings_Activity.this);
	    					alertTemp.setTitle("Erro");
	    					alertTemp.setMessage("Valor Inválido");
	    					alertTemp.setNeutralButton("Ok", null);
	    					alertTemp.show();
	    				}
	    			}
	    		});
	    		alert.setNegativeButton("Descartar", new DialogInterface.OnClickListener() {
	    			public void onClick(DialogInterface dialog, int whichButton) {
	    				//Put actions for CANCEL button here, or leave in blank
	    			}
	    		});
	    		alert.show();
	    	}
	    });
	    /*lL_Lim.setOnClickListener(new LinearLayout.OnClickListener(){
			@Override
			public void onClick(View v) {
				//numberPickerDialog().show();
			}
	    });*/
	    
	    updateListView();
	    
	 }
	 /*
	 private AlertDialog numberPickerDialog(){
		 LayoutInflater inflater = (LayoutInflater)
				    getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				    NumberPicker npView = (NumberPicker) inflater.inflate(R.layout.number_picker_dialog_layout, null);
				    npView.setMaxValue(120);
				    npView.setMinValue(1);
				    return new AlertDialog.Builder(Settings_Activity.this)
				        .setTitle("Escola a quantidade máxima de parcelas")
				        .setView(npView)
				        .setPositiveButton("Confirmar",
				            new DialogInterface.OnClickListener() {
				                public void onClick(DialogInterface dialog, int whichButton) {

				                }
				            })
				            .setNegativeButton("Descartar",
				                new DialogInterface.OnClickListener() {
				                    public void onClick(DialogInterface dialog, int whichButton) {
				                    }
				                })
				            .create();
	 }
	 */
	 
	 private void updateListView(){
		 
		 tV_Deb.setText(Settings.getInterestDebit() + "%");
		 
		 
		 //tV_Limit.setText(String.valueOf(Settings.getLimit()));
		 
		 
		 for(int x=0; x<Settings.getLimit(); x++){
			 TextView temp1 = new TextView(this);
			 final int y = x;
			 temp1.setText("Juros no Crédito " + (y+1) + "x (%)");
			 temp1.setTextSize(22);
			 TextView tempTV= (TextView)findViewById(R.id.textView1);
			 temp1.setTextColor(tempTV.getTextColors());
			 
			 TextView temp2 = new TextView(this);
			 temp2.setText(Settings.getInterestCredit((y+1), Settings_Activity.this) + "%");
			 temp2.setTextSize(18);
			 temp2.setTextColor(tV_Deb.getTextColors());
			 
			 LinearLayout temp3 = new LinearLayout(this);
			 temp3.setPadding(5, 5, 5, 5);
			 temp3.setOrientation(LinearLayout.VERTICAL);
			 temp3.addView(temp1);
			 temp3.addView(temp2);
			 temp3.setOnClickListener(new LinearLayout.OnClickListener(){
			    	@Override
			    	public void onClick(View v) {
			    		int z = y;
			    		AlertDialog.Builder alert = new AlertDialog.Builder(Settings_Activity.this);
			    		alert.setMessage("Insira a o Juros (" + (z+1) + "x)");
			    		final EditText input = new EditText(v.getContext());
			    		input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
			    		input.setRawInputType(Configuration.KEYBOARD_12KEY);
			    		alert.setView(input);
			    		alert.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
			    			public void onClick(DialogInterface dialog, int whichButton) {
			    				try{
			    					Number n = Double.parseDouble((input.getText().toString().replaceAll(",", ".")));
			    					Settings.setInterest_credit(n.doubleValue(), y);
			    					Intent intent = getIntent();
			    				    finish();
			    				    startActivity(intent);
			    				}catch(Exception e){
			    					AlertDialog.Builder alertTemp = new AlertDialog.Builder(Settings_Activity.this);
			    					alertTemp.setTitle("Erro");
			    					alertTemp.setMessage("Valor Inválido");
			    					alertTemp.setNeutralButton("Ok", null);
			    					alertTemp.show();
			    				}
			    			}
			    		});
			    		alert.setNegativeButton("Descartar", new DialogInterface.OnClickListener() {
			    			public void onClick(DialogInterface dialog, int whichButton) {
			    				//Put actions for CANCEL button here, or leave in blank
			    			}
			    		});
			    		alert.show();
			    	}
			    });
			 
			 lL_Main.addView(temp3);
		 }
	 }

}

