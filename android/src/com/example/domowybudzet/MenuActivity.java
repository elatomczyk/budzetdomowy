package com.example.domowybudzet;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MenuActivity extends ListActivity {
	String[] menus = { "Rachunki", "Dochody" };
	String login;

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent i = getIntent();
		Bundle b = i.getExtras();
		if (b != null) {
			login = (String) b.get("login");
		}
		String name = menus[position];
		try {
			Class<?> main = Class.forName("com.example.domowybudzet." + name);
			Intent intent = new Intent(MenuActivity.this, main);
			intent.putExtra("login", login);
			startActivity(intent);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setListAdapter(new ArrayAdapter<String>(MenuActivity.this,
				android.R.layout.simple_expandable_list_item_1, menus));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
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