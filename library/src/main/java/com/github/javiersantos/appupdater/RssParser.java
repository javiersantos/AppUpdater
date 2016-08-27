package com.github.javiersantos.appupdater;

import android.util.Log;

import com.github.javiersantos.appupdater.objects.Update;

import org.xml.sax.SAXException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

class RssParser {
    private URL rssUrl;

    public RssParser(String url) {
        try {
            this.rssUrl = new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public Update parse() {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        InputStream inputStream = this.getInputStream();

        if (inputStream != null) {
            try {
                SAXParser parser = factory.newSAXParser();
                RssHandler handler = new RssHandler();
                parser.parse(inputStream, handler);
                return handler.getUpdate();
            } catch (ParserConfigurationException | SAXException e) {
                Log.e("AppUpdater", "The XML updater file is mal-formatted. AppUpdate can't check for updates.");
                return null;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            return null;
        }
    }

    private InputStream getInputStream() {
        try {
            URLConnection connection = rssUrl.openConnection();
            if (connection == null) return null;
            return connection.getInputStream();
        } catch (FileNotFoundException | UnknownHostException | ConnectException e) {
            Log.e("AppUpdater", "The XML updater file is invalid or is down. AppUpdate can't check for updates.");
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
