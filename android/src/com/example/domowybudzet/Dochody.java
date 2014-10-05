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

import com.example.domowybudzet.RESTLista2;
import com.example.domowybudzet.ElementDochod;

public class Dochody extends Activity {
	ListView dochodList;
	ListView dochodList2;
	public ArrayList<String> dochodArrayList = new ArrayList<String>();
	public ArrayList<String> dochodArrayList2 = new ArrayList<String>();
	ArrayAdapter<String> dochodAdapter;
	ArrayAdapter<String> dochodAdapter2;
	Context context;
	String durl = "http://192.168.1.103:8080/api/dochody/";
	ArrayList<ElementDochod> listadochod = new ArrayList<ElementDochod>();
	RESTLista2 ws;
	String login;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dochody);
		Intent inte = getIntent();
		Bundle b = inte.getExtras();
		if (b != null) {
			login = (String) b.get("login");
		}
		ws = new RESTLista2(context, durl, login);
		ws.execute();
		ws.sleep();
		listadochod.addAll(ws.getList());
		for (int i = 0; i < listadochod.size(); i++) {
			dochodArrayList.add(listadochod.get(i).tytul);
			dochodArrayList2.add(listadochod.get(i).cena);
		}
		dochodList = (ListView) findViewById(R.id.dochodList);
		dochodList2 = (ListView) findViewById(R.id.dochodList2);
		dochodAdapter = new ArrayAdapter<String>(this,
				R.layout.dochod_list_item, dochodArrayList);
		dochodList.setAdapter(dochodAdapter);
		dochodAdapter2 = new ArrayAdapter<String>(this,
				R.layout.dochod_list_item, dochodArrayList2);
		dochodList2.setAdapter(dochodAdapter2);
		context = this;
		dochodList
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int index, long arg3) {
						Intent intent = new Intent(Dochody.this,
								SzczegolyDochody.class);
						intent.putExtra("login", login);
						intent.putExtra("id", listadochod.get(index).id);
						intent.putExtra("tytul", listadochod.get(index).tytul);
						intent.putExtra("opis", listadochod.get(index).opis);
						intent.putExtra("data_zaplaty",
								listadochod.get(index).data_zaplaty);
						intent.putExtra("cena", listadochod.get(index).cena);
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
						DodajDActivity.class);
				in.putExtra("login", login);
				startActivity(in);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.dochod, menu);
		return true;
	}
}