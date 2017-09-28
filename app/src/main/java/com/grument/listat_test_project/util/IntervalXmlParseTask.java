package com.grument.listat_test_project.util;


import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import com.grument.listat_test_project.data_objects.IntervalInfo;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;



public class IntervalXmlParseTask extends AsyncTask<Void, Void, Void> {

    public IntervalXmlParseTask(Context context, ArrayList<IntervalInfo> intervalInfos, IntervalXmlParseTaskCallback intervalXmlParseTaskCallback) {
        this.context = context;
        this.intervalInfos = intervalInfos;
        this.intervalXmlParseTaskCallback = intervalXmlParseTaskCallback;
    }

    private Context context;

    private ArrayList<IntervalInfo> intervalInfos;

    private IntervalXmlParseTaskCallback intervalXmlParseTaskCallback;

    public interface IntervalXmlParseTaskCallback {

        void onXmlParseStart();

        void onXmlParseEnd();

    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        intervalXmlParseTaskCallback.onXmlParseStart();
    }

    @Override
    protected Void doInBackground(Void... params) {

        AssetManager assetManager = context.getAssets();

        try {

            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

            InputStream inputStream = assetManager.open("stream_intervals.xml");

            Document document = documentBuilder.parse(inputStream);

            Node root = document.getDocumentElement();

            NodeList rootNodes = root.getChildNodes();

            for (int i = 0; i < rootNodes.getLength(); i++) {
                Node intervals = rootNodes.item(i);

                if (intervals.getNodeType() != Node.TEXT_NODE) {

                    NodeList intervalsChildNodes = intervals.getChildNodes();

                    for (int j = 0; j < intervalsChildNodes.getLength(); j++) {

                        Node interval = intervalsChildNodes.item(j);

                        if (interval.getNodeType() != Node.TEXT_NODE) {

                            NodeList intervalChildNodes = interval.getChildNodes();

                            IntervalInfo intervalInfo = new IntervalInfo();

                            for (int d = 0; d < intervalChildNodes.getLength(); d++) {

                                if (intervalChildNodes.item(d).getNodeType() != Node.TEXT_NODE) {

                                    Node intervalNode = intervalChildNodes.item(d);



                                    String intervalNodeName = intervalNode.getNodeName();

                                    int intervalNodeValue = Integer.parseInt(intervalNode.getTextContent());

                                    switch (intervalNodeName) {
                                        case "id":
                                            intervalInfo.setId(intervalNodeValue);
                                            break;
                                        case "low":
                                            intervalInfo.setLowInterval(intervalNodeValue);
                                            break;
                                        case "high":
                                            intervalInfo.setHighInterval(intervalNodeValue);
                                            break;
                                    }
                                }
                            }
                            intervalInfos.add(intervalInfo);
                        }
                    }
                }
            }

            inputStream.close();

        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        intervalXmlParseTaskCallback.onXmlParseEnd();
    }
}