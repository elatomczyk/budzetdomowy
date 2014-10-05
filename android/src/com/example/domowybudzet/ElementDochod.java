package com.example.domowybudzet;

public class ElementDochod {

	String id;
	String tytul;
	String opis;
	String data_zaplaty;
	String cena;

	public ElementDochod(String idd, String tytull, String opiss,
			String data_zaplatyy, String cenaa) {
		id = idd;
		tytul = tytull;
		opis = opiss;
		data_zaplaty = data_zaplatyy;
		cena = cenaa;
	}
}