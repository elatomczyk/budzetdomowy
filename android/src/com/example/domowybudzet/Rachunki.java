package com.example.domowybudzet;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.domowybudzet.RESTLista;
import com.example.domowybudzet.ElementRachunek;

public class Rachunki extends Activity {
	ListView rachunekList;
	ListView rachunekList2;
	public ArrayList<String> rachunekArrayList = new ArrayList<String>();
	public ArrayList<String> rachunekArrayList2 = new ArrayList<String>();
	ArrayAdapter<String> rachunekAdapter;
	ArrayAdapter<String> rachunekAdapter2;
	Context context;
	String durl = "http://192.168.1.103:8080/api/rachunki/";
	ArrayList<ElementRachunek> listarachunek = new ArrayList<ElementRachunek>();
	RESTLista ws;
	String login;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rachunki);
		Intent inte = getIntent();
		Bundle b = inte.getExtras();
		if (b != null) {
			login = (String) b.get("login");
		}
		ws = new RESTLista(context, durl, login);
		ws.execute();
		ws.sleep();
		listarachunek.addAll(ws.getList());
		for (int i = 0; i < listarachunek.size(); i++) {
			rachunekArrayList.add(listarachunek.get(i).tytul);
			rachunekArrayList2.add(listarachunek.get(i).cena);
		}
		rachunekList = (ListView) findViewById(R.id.rachunekList);
		rachunekList2 = (ListView) findViewById(R.id.rachunekList2);
		rachunekAdapter = new ArrayAdapter<String>(this,
				R.layout.rachunek_list_item, rachunekArrayList);
		rachunekList.setAdapter(rachunekAdapter);
		rachunekAdapter2 = new ArrayAdapter<String>(this,
				R.layout.rachunek_list_item, rachunekArrayList2);
		rachunekList2.setAdapter(rachunekAdapter2);
		context = this;
		rachunekList
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int index, long arg3) {
						Intent intent = new Intent(Rachunki.this,
								SzczegolyRachunki.class);
						intent.putExtra("login", login);
						intent.putExtra("id", listarachunek.get(index).id);
						intent.putExtra("tytul", listarachunek.get(index).tytul);
						intent.putExtra("opis", listarachunek.get(index).opis);
						intent.putExtra("data_zaplaty",
								listarachunek.get(index).data_zaplaty);
						intent.putExtra("www", listarachunek.get(index).www);
						intent.putExtra("cena", listarachunek.get(index).cena);
						startActivity(intent);
					}
				});
		Button wroc = (Button) findViewById(R.id.wroc);
		Button dodaj = (Button) findViewById(R.id.dodaj);
		wroc.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent in = new Intent(getApplicationContext(),
						MenuActivity.class);
				in.putExtra("login", login);
				startActivity(in);
			}
		});
		dodaj.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent in = new Intent(getApplicationContext(),
						DodajRActivity.class);
				in.putExtra("login", login);
				startActivity(in);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.rachunek, menu);
		return true;
	}
}