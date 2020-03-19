package com.github.javiersantos.appupdater;

import com.github.javiersantos.appupdater.objects.Update;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.net.MalformedURLException;
import java.net.URL;

import static android.util.Log.d;

class HandlerXML extends DefaultHandler {
    private Update update;
    private StringBuilder builder;

    public Update getUpdate() {
        return update;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);

        if (this.update != null) {
            builder.append(ch, start, length);
        }
    }

    @Override
    public void endElement(String uri, String localName, String name) throws SAXException {
        super.endElement(uri, localName, name);

        if (this.update != null) {
            switch (localName) {
                case "latestVersion":
                    update.setLatestVersion(builder.toString().trim());
                    break;
                case "latestVersionCode":
                    update.setLatestVersionCode(Integer.valueOf(builder.toString().trim()));
                    break;
                case "releaseNotes":
                    update.setReleaseNotes(builder.toString().trim());
                    break;
                case "url":
                    try {
                        update.setUrlToDownload(new URL(builder.toString().trim()));
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "lastMajorUpdateVersion":
                    d("-----", localName + "----" + builder.toString());
                    update.setLastMajorUpdateVersion(Integer.valueOf(builder.toString().trim()));
                    break;
            }

            builder.setLength(0);
        }
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();

        builder = new StringBuilder();
    }

    @Override
    public void startElement(String uri, String localName,
                             String name, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, name, attributes);

        if (localName.equals("update")) {
            update = new Update();
        }
    }

}
