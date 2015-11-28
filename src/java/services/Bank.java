package services;

import entity.Rate;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.ArrayList;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import services.Util;


public class Bank extends DefaultHandler {

	ArrayList<Rate> rates = new ArrayList<>();
	long date;

	@Override
	public void startDocument() throws SAXException {
		date = Util.getDate();
	}
	@Override
	public void endDocument() throws SAXException {
	}
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if(localName != "currency")
			return;

		try {
			Double.parseDouble(attributes.getValue(2));
		} catch(NumberFormatException e) { return; }

		rates.add(new Rate(attributes.getValue(0), date, Double.parseDouble(attributes.getValue(2))));
	}

	public ArrayList<Rate> getRates() {
		try {
			XMLReader xr = XMLReaderFactory.createXMLReader();
			Bank bank = new Bank();
			xr.setContentHandler(bank);
			URL url = new URL("http://www.nationalbanken.dk/_vti_bin/DN/DataService.svc/CurrencyRatesXML?lang=en");
			xr.parse(new InputSource(url.openStream()));
			return bank.rates;

		} catch (SAXException | IOException e) {
			e.printStackTrace();
			return null; 
		}
	}

}