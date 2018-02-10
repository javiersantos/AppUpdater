package com.github.javiersantos.appupdater;

import com.github.javiersantos.appupdater.objects.Update;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.net.MalformedURLException;
import java.net.URL;

class RssHandler extends DefaultHandler {
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
            if (localName.equals("latestVersion")) {
                update.setLatestVersion(builder.toString().trim());
            } else if (localName.equals("latestVersionCode")) {
                update.setLatestVersionCode(Integer.valueOf(builder.toString().trim()));
            } else if (localName.equals("releaseNotes")) {
                update.setReleaseNotes(builder.toString().trim());
            } else if (localName.equals("url")) {
                try {
                    update.setUrlToDownload(new URL(builder.toString().trim()));
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
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
