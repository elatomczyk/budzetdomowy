package com.example.domowybudzet;

import android.content.Context;
import android.os.AsyncTask;
import com.example.domowybudzet.ElementDochod;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class RESTLista2 extends AsyncTask<Void, Void, Void> {
	String FORMATURL = "/?format=json";

	String url;
	Context context;
	public ArrayList<ElementDochod> djangoArrayList;

	public RESTLista2(Context con, String url1, String nazwa) {

		url = url1 + nazwa + FORMATURL;
		djangoArrayList = new ArrayList<ElementDochod>();
		context = con;

	}

	public ArrayList<ElementDochod> getList() {

		return djangoArrayList;
	}

	public void sleep() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected Void doInBackground(Void... params) {
		HttpClient client = new DefaultHttpClient();
		HttpGet getRequest = new HttpGet(url);

		try {

			HttpResponse response = client.execute(getRequest);
			StatusLine statusline = response.getStatusLine();
			int statusCode = statusline.getStatusCode();

			if (statusCode != 200) {
				return null;
			}

			InputStream jsonStream = response.getEntity().getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					jsonStream));
			StringBuilder builder = new StringBuilder();
			String line;

			while ((line = reader.readLine()) != null) {

				builder.append(line);
			}

			String jsonData = builder.toString();

			JSONArray arr = new JSONArray(jsonData);

			for (int i = 0; i < arr.length(); i++) {
				JSONObject jObj = arr.getJSONObject(i);
				String id = jObj.getString("id");
				String tytul = jObj.getString("tytul");
				String opis = jObj.getString("opis");
				String data_zaplaty = jObj.getString("data_zaplaty");
				String cena = jObj.getString("cena");

				ElementDochod element = new ElementDochod(id, tytul, opis,
						data_zaplaty, cena);

				djangoArrayList.add(element);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return null;
	}
}
