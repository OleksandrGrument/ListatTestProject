package com.grument.listat_test_project.util;

import android.util.Log;

import com.grument.listat_test_project.data_objects.IntervalInfo;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

public class IntervalXMLHandler extends DefaultHandler {

    private boolean currentElement = false;
    private String currentValue = "";


    private IntervalInfo intervalInfo;
    private ArrayList<IntervalInfo> intervalInfos;


    public ArrayList<IntervalInfo> getIntervalInfos() {
        return intervalInfos;
    }

    @Override
    public void startDocument() throws SAXException {
        Log.i("HANDLER" , "Start parse XML...");
        intervalInfos = new ArrayList<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {

        currentElement = true;

        if (qName.equals("interval")) {
            intervalInfo = new IntervalInfo();
        }

    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {

        currentElement = false;

        if (qName.equalsIgnoreCase("id"))
            intervalInfo.setId(Integer.valueOf(currentValue.trim()));
        else if (qName.equalsIgnoreCase("low"))
            intervalInfo.setLowInterval(Integer.valueOf(currentValue.trim()));
        else if (qName.equalsIgnoreCase("high"))
            intervalInfo.setHighInterval(Integer.valueOf(currentValue.trim()));


        else if (qName.equalsIgnoreCase("interval"))
            intervalInfos.add(intervalInfo);

        currentValue = "";
    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {

        if (currentElement) {
            currentValue = currentValue + new String(ch, start, length);
        }

    }

}