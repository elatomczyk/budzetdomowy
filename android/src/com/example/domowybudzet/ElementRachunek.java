package com.example.domowybudzet;

public class ElementRachunek {
	String id;
	String tytul;
	String opis;
	String data_zaplaty;
	String cena;
	String www;

	public ElementRachunek(String idd, String tytull, String opiss, String data_zaplatyy,
			String cenaa, String wwww) {
		id = idd;
		tytul = tytull;
		opis = opiss;
		data_zaplaty = data_zaplatyy;
		cena = cenaa;
		www = wwww;
	}
}
