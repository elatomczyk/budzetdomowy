package com.example.domowybudzet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
public class SzczegolyRachunki extends ActionBarActivity {
	TextView tytul;
	TextView opis;
	TextView datazaplaty;
	TextView cena;
	TextView www;
	CheckBox powiadomienie;
	String id;
	String tytul2;
	String opis2;
	String datazaplaty2;
	String cena2;
	String www2;
	String powiadomieniee;
	Button usunr;
	Context context;
	String url = "http://192.168.1.103:8080/api/usunr/";
	String login;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_szczegoly_rachunki);	
		context = this;
		tytul = (TextView) findViewById(R.id.tytul);
		opis = (TextView) findViewById(R.id.opis);
		datazaplaty = (TextView) findViewById(R.id.datazaplaty);
		www = (TextView) findViewById(R.id.www);
		cena = (TextView) findViewById(R.id.cena);
		Intent inte = getIntent();
		Bundle b = inte.getExtras();
		if (b != null) {
			login = (String) b.get("login");
			id = (String) b.get("id");
			tytul2 = (String) b.get("tytul");
			opis2 = (String) b.get("opis");
			datazaplaty2 = (String) b.get("data_zaplaty");
			cena2 = (String) b.get("cena");
			www2 = (String) b.get("www");	
		}
		tytul.setText(tytul2);
		opis.setText(opis2);
		datazaplaty.setText(datazaplaty2);
		cena.setText(cena2);
		www.setText(www2);
		
		usunr= (Button) findViewById(R.id.btnCancel); 
		usunr.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {			
				REST ws1 = new REST(context, url, id);
				ws1.execute(); 
				ws1.sleep();			
				String komunikat = ws1.getString();
				Toast.makeText(getApplicationContext(), komunikat, Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(SzczegolyRachunki.this, Rachunki.class);
				intent.putExtra("login", login);
				startActivity(intent);
			}
		});
		
		Button wroc = (Button) findViewById(R.id.wroc);
		wroc.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent in = new Intent(getApplicationContext(), Rachunki.class);
				in.putExtra("login", login);
				startActivity(in);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.szczegoly_rachunki, menu);
		return true;
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
