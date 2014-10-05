package com.example.domowybudzet;

import java.util.ArrayList;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.domowybudzet.REST;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class DodajRActivity extends ActionBarActivity implements
		AdapterView.OnItemSelectedListener {
	EditText tytul;
	EditText opis;
	EditText data_zaplaty;
	EditText cena;
	CheckBox powiadomienie;
	Button dodaj;
	REST ws;
	Context contextt;
	String url = "http://192.168.1.103:8080/api/dodajr/";
	String login;

	String urlK = "http://192.168.1.103:8080/api/kategorie/";
	Spinner spinner;
	TextView myText;
	TextView textview;
	Context context;
	String wybrana_kategoria;
	ArrayList<String> kategorielista = new ArrayList<String>();
	Boolean powiadomieniee;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dodajr);
		powiadomieniee = false;
		contextt = this;
		tytul = (EditText) findViewById(R.id.tytul);
		opis = (EditText) findViewById(R.id.opis);
		data_zaplaty = (EditText) findViewById(R.id.datazaplaty);
		cena = (EditText) findViewById(R.id.cena);
		powiadomienie = (CheckBox) findViewById(R.id.powiadomienie);

		powiadomienie.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (powiadomienie.isChecked()) {
					powiadomieniee = false;
				} else {
					powiadomieniee = true;
				}
			}
		});
		Intent inte = getIntent();
		Bundle b = inte.getExtras();
		if (b != null) {
			login = (String) b.get("login");
		}
		spinner = (Spinner) findViewById(R.id.spinner);

		RESTKategorie kat = new RESTKategorie(context, urlK);
		kat.execute();
		kat.sleep();
		kategorielista.addAll(kat.getList());

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, kategorielista);
		spinner.setAdapter(dataAdapter);
		spinner.setOnItemSelectedListener(this);
		dodaj = (Button) findViewById(R.id.dodaj);
		dodaj.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String tytull = tytul.getText().toString();
				String opiss = opis.getText().toString();
				String cenaa = cena.getText().toString();
				String url = "http://192.168.1.103:8080/api/dodajr/";
				ws = new REST(contextt, url, tytull, opiss, cenaa,
						powiadomieniee, wybrana_kategoria, login);
				ws.execute();
				ws.sleep();
				String tmp = ws.getString();
				if (tmp.equals("Dodano")) {
					Intent intent = new Intent(DodajRActivity.this,
							Rachunki.class);
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
				Intent in = new Intent(getApplicationContext(), Rachunki.class);
				in.putExtra("login", login);
				startActivity(in);
			}
		});
	}

	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		myText = (TextView) view;
		wybrana_kategoria = String.valueOf(spinner.getSelectedItem());

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.dodaj_r, menu);
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
