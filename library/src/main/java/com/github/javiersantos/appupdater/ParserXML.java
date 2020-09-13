package com.github.javiersantos.appupdater;

import android.support.annotation.Nullable;
import android.util.Log;
import android.util.Pair;

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

class ParserXML {
    private URL xmlUrl;

    public ParserXML(String url) {
        try {
            this.xmlUrl = new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Nullable
    public Pair<Update,Exception> parse() {
        SAXParserFactory factory = SAXParserFactory.newInstance();

        InputStream inputStream = null;

        try {
            URLConnection connection = xmlUrl.openConnection();
            inputStream = connection.getInputStream();
            SAXParser parser = factory.newSAXParser();
            HandlerXML handler = new HandlerXML();
            parser.parse(inputStream, handler);
            return new Pair(handler.getUpdate(),null);
        } catch (ParserConfigurationException | SAXException e) {
            Log.e("AppUpdater", "The XML updater file is mal-formatted. AppUpdate can't check for updates.", e);
            return new Pair(null,e);
        } catch (FileNotFoundException | UnknownHostException | ConnectException e) {
            Log.e("AppUpdater", "The XML updater file is invalid or is down. AppUpdate can't check for updates.");
            return new Pair(null,e);
        } catch (IOException e) {
            Log.e("AppUpdater", "I/O error. AppUpdate can't check for updates.", e);
            return new Pair(null,e);
        } catch (Exception e) {
            Log.e("AppUpdater", "The server is down or there isn't an active Internet connection.", e);
            return new Pair(null,e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Log.e("AppUpdater", "Error closing input stream", e);
                }
            }
        }

    }

}
