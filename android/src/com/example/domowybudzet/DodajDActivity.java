package com.example.domowybudzet;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import com.example.domowybudzet.REST;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class DodajDActivity extends ActionBarActivity {
	EditText tytul;
	EditText opis;
	EditText data_zaplaty;
	EditText cena;
	Button dodaj;
	REST ws;
	Context contextt;
	String url = "http://192.168.1.103:8080/api/dodajd/";
	String login;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dodajd);
		contextt = this;
		tytul = (EditText) findViewById(R.id.tytul);
		opis = (EditText) findViewById(R.id.opis);
		data_zaplaty = (EditText) findViewById(R.id.datazaplaty);
		cena = (EditText) findViewById(R.id.cena);
		Intent inte = getIntent();
		Bundle b = inte.getExtras();
		if (b != null) {
			login = (String) b.get("login");
		}

		dodaj = (Button) findViewById(R.id.dodaj);
		dodaj.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String tytull = tytul.getText().toString();
				String opiss = opis.getText().toString();
				String cenaa = cena.getText().toString();
				String url = "http://192.168.1.103:8080/api/dodajd/";
				ws = new REST(contextt, url, tytull, opiss, cenaa, login);
				ws.execute();
				ws.sleep();
				String tmp = ws.getString();
				Log.i("Django", tmp);

				if (tmp.equals("Dodano")) {
					Intent intent = new Intent(DodajDActivity.this,
							Dochody.class);
					intent.putExtra("login", login);
					startActivity(intent);

				} else {
					Toast.makeText(getApplicationContext(), "Problem",
							Toast.LENGTH_LONG).show();
				}
			}
		});
		Button wroc = (Button) findViewById(R.id.wrocd);
		wroc.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent in = new Intent(getApplicationContext(), Dochody.class);
				in.putExtra("login", login);
				startActivity(in);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.dodaj_d, menu);
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
