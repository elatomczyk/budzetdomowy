package com.example.domowybudzet;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class REST extends AsyncTask<Void, Void, Void> {
	String FORMATURL = "/?format=json";
	ProgressDialog dialog;
	String url;
	String zawartosc;
	String numer;
	Context context;
	public ArrayList<String> tag;

	public REST(Context con, String dany_url_poczatek, String login,
			String haslo) {

		url = dany_url_poczatek + login + "/" + haslo + FORMATURL;
		tag = new ArrayList<String>();
		context = con;

	}

	public REST(Context con, String dany_url_poczatek, String id) {
		url = dany_url_poczatek + id + FORMATURL;
		tag = new ArrayList<String>();
		context = con;
	}

	public REST(Context con, String dany_url_poczatek, String tytull,
			String opiss, String cenaa, String login) {
		url = dany_url_poczatek + tytull + "/" + opiss + "/" + cenaa + "/"
				+ login + FORMATURL;
		tag = new ArrayList<String>();
		context = con;
	}

	public REST(Context con, String dany_url_poczatek, String tytull,
			String opiss, String cenaa, Boolean powiadomieniee,
			String kategoria, String login) {
		url = dany_url_poczatek + tytull + "/" + opiss + "/" + cenaa + "/"
				+ powiadomieniee + "/" + kategoria + "/" + login + FORMATURL;
		tag = new ArrayList<String>();
		context = con;
	}

	public String getString() {
		return zawartosc;
	}

	public void sleep() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	protected void onPreExecute() {

		dialog = new ProgressDialog(context);
		dialog.setTitle("Ładowanie danych");
		dialog.show();
		super.onPreExecute();
	}

	@Override
	public Void doInBackground(Void... params) { 

		HttpClient client = new DefaultHttpClient();
		HttpGet getRequest = new HttpGet(url);

		try {

			HttpResponse response = client.execute(getRequest);
			StatusLine statusline = response.getStatusLine();
			int statusCode = statusline.getStatusCode();

			if (statusCode != 200) {
				zawartosc = "Problem";
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
			JSONObject json = new JSONObject(jsonData);
			tag.add(json.getString("tag"));
			zawartosc = tag.get(0).toString();

		} catch (ClientProtocolException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

		catch (JSONException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		dialog.dismiss();
		super.onPostExecute(result);
	}

}
