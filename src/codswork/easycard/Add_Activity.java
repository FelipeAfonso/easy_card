package codswork.easycard;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class Add_Activity extends ActionBarActivity{
	
	private Account testAcc = Account.Accounts.get(0);
	private EditText eT_Valor;
	private EditText eT_Descricao;
	private Switch switch_FdP;
	private TextView tV_Parcelas;
	private NumberPicker numberPicker;
	private Button btn_Register;
	
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.add_activity);
	        switch_FdP = (Switch) findViewById(R.id.switch_FdP);
	        eT_Valor = (EditText) findViewById(R.id.eT_Valor);
	        eT_Descricao = (EditText) findViewById(R.id.eT_Descricao);
	        tV_Parcelas = (TextView) findViewById(R.id.tV_Parcelas);
	        numberPicker = (NumberPicker) findViewById(R.id.numberPicker);
	        numberPicker.setMinValue(1);
	        numberPicker.setMaxValue(12);
	        btn_Register = (Button) findViewById(R.id.btn_Register);
	        
	        switch_FdP.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if (isChecked){
						tV_Parcelas.setVisibility(0);
						numberPicker.setVisibility(0);
					}
					else if (!isChecked){
						tV_Parcelas.setVisibility(8);
						numberPicker.setVisibility(8);
					}
				}
			});
	        
	        btn_Register.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					if(eT_Valor.getText().toString().isEmpty() || Double.parseDouble(eT_Valor.getText().toString()) <= 0){
						Toast toast = Toast.makeText(Add_Activity.this, "Valor inválido", Toast.LENGTH_SHORT);
					    int xOffset = 0;
					    int yOffset = 0;
					    Rect gvr = new Rect();
					    int parentHeight = eT_Valor.getHeight();
					    if (eT_Valor.getGlobalVisibleRect(gvr)) {       
					        View root = eT_Valor.getRootView();
					        int halfWidth = root.getRight() / 2;
					        int halfHeight = root.getBottom() / 2;
					        int parentCenterX = ((gvr.right - gvr.left) / 2) + gvr.left;
					        int parentCenterY = ((gvr.bottom - gvr.top) / 2) + gvr.top;
					        if (parentCenterY <= halfHeight){
					            yOffset = -(halfHeight - parentCenterY) - parentHeight;
					        }
					        else{
					            yOffset = (parentCenterY - halfHeight) - parentHeight;
					        }

					        if (parentCenterX < halfWidth){         
					            xOffset = -(halfWidth - parentCenterX);     
					        }   

					        if (parentCenterX >= halfWidth){
					            xOffset = parentCenterX - halfWidth;
					        }  
					    }
						toast.setGravity(Gravity.CENTER, xOffset, yOffset);
						toast.show();
						eT_Valor.setText("");
					} else{
						if(!switch_FdP.isChecked()){
							Sale temp = new Sale(new Card("Teste"), testAcc, 
									Double.parseDouble(eT_Valor.getText().toString()));
							testAcc.Sales.add(temp);
							Entry.entries.add(new Entry("Débito", temp.receiveDate, temp.getValueToEnter(), temp));
						}
						else{
							CreditSale temp = new CreditSale(new Card("Teste"), testAcc, 
									Double.parseDouble(eT_Valor.getText().toString()), 
									numberPicker.getValue());
							testAcc.Sales.add(temp);
							for(int x=0; x<temp.getParcels(); x++){
								Entry.entries.add(new Entry("Crédito", temp.days.get(x), temp.values.get(x), temp));
							}
						}
						AlertDialog.Builder dialog = new AlertDialog.Builder(Add_Activity.this);
						dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								Add_Activity.this.eT_Valor.setText("");
								Add_Activity.this.eT_Descricao.setText("");
								Add_Activity.this.numberPicker.setValue(1);
								Add_Activity.this.switch_FdP.setChecked(false);
							}
						});
						dialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								Add_Activity.this.finish();
							}
						});
						dialog.setMessage("Cadastrar outra venda?");
						AlertDialog showDialog = dialog.create();
						showDialog.show();
					}
				}
	        });
	 }

	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        int id = item.getItemId();
	        if (id == R.id.action_settings) {
	            return true;
	        }
	        return super.onOptionsItemSelected(item);
	    }

}
