package com.example.domowybudzet;

import com.example.domowybudzet.REST;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import android.support.v7.app.ActionBarActivity;

public class LogowanieActivity extends ActionBarActivity {
	EditText nazwa;
	EditText haslo;
	Button login;
	REST ws;
	Context contextt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logowanie);
		contextt = this;
		nazwa = (EditText) findViewById(R.id.name);
		haslo = (EditText) findViewById(R.id.password);
		login = (Button) findViewById(R.id.login);
		login.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String nazwaString = nazwa.getText().toString();
				String hasloString = haslo.getText().toString();
				String url = "http://192.168.1.103:8080/api/login/";
				ws = new REST(contextt, url, nazwaString, hasloString);
				ws.execute();
				ws.sleep();
				String tmp = ws.getString();
				if (tmp.equals("Logged")) {
					Intent intent = new Intent(LogowanieActivity.this,
							MenuActivity.class);
					intent.putExtra("login", nazwaString);
					startActivity(intent);
				} else {
					Toast.makeText(getApplicationContext(), "Problem",
							Toast.LENGTH_LONG).show();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.logowanie, menu);
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
