package com.example.laser;



import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.text.InputFilter;
import android.view.Menu;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class HostActivity extends Activity {
	EditText HostName;
	EditText PasswordName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_host);
		setupWidget();
		//addItemsOnSpinner();
		
	}
    public void setupWidget() {
    	HostName = (EditText)findViewById(R.id.name1);
    	HostName.setFilters(new InputFilter[] {new InputFilter.LengthFilter(12)});	//sets max characters length
    	PasswordName = (EditText)findViewById(R.id.editText1);
    	PasswordName.setFilters(new InputFilter[] {new InputFilter.LengthFilter(12)});	//sets max characters length
    	
    }
    public void addItemsOnSpinner() {
    /*
    Spinner spinner = (Spinner) findViewById(R.id.players);
    ArrayList<String> spinnerArray = new ArrayList<String>();    
    spinnerAr
    ArrayAdapter  mAdapter = new ArrayAdapter (this, spinnerArray);
    mSpinner1.setAdapter(mAdapter);
    */
      }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.host, menu);
		return true;
	}

}
