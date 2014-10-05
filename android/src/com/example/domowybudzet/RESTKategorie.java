package com.example.domowybudzet;

import android.content.Context;
import android.os.AsyncTask;

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

public class RESTKategorie extends AsyncTask<Void, Void, Void> {
	String FORMATURL = "?format=json";

	String url;
	Context context;
	public ArrayList<String> djangoArrayList;

	public RESTKategorie(Context con, String url1) {

		url = url1 + FORMATURL;
		djangoArrayList = new ArrayList<String>();
		context = con;

	}

	public ArrayList<String> getList() {

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
				String id = jObj.getString("nazwa");
				djangoArrayList.add(id);

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
