package com.github.javiersantos.appupdater

import android.util.Log
import com.github.javiersantos.appupdater.objects.Update
import org.xml.sax.SAXException
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream
import java.net.ConnectException
import java.net.MalformedURLException
import java.net.URL
import java.net.UnknownHostException
import javax.xml.parsers.ParserConfigurationException
import javax.xml.parsers.SAXParserFactory

internal class ParserXML(url: String?) {
    private var xmlUrl: URL? = null
    fun parse(): Update? {
        val factory = SAXParserFactory.newInstance()
        var inputStream: InputStream? = null
        return try {
            val connection = xmlUrl!!.openConnection()
            inputStream = connection.getInputStream()
            val parser = factory.newSAXParser()
            val handler = HandlerXML()
            parser.parse(inputStream, handler)
            handler.update
        } catch (e: ParserConfigurationException) {
            Log.e("AppUpdater", "The XML updater file is mal-formatted. AppUpdate can't check for updates.", e)
            null
        } catch (e: SAXException) {
            Log.e("AppUpdater", "The XML updater file is mal-formatted. AppUpdate can't check for updates.", e)
            null
        } catch (e: FileNotFoundException) {
            Log.e("AppUpdater", "The XML updater file is invalid or is down. AppUpdate can't check for updates.")
            null
        } catch (e: UnknownHostException) {
            Log.e("AppUpdater", "The XML updater file is invalid or is down. AppUpdate can't check for updates.")
            null
        } catch (e: ConnectException) {
            Log.e("AppUpdater", "The XML updater file is invalid or is down. AppUpdate can't check for updates.")
            null
        } catch (e: IOException) {
            Log.e("AppUpdater", "I/O error. AppUpdate can't check for updates.", e)
            null
        } catch (e: Exception) {
            Log.e("AppUpdater", "The server is down or there isn't an active Internet connection.", e)
            null
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close()
                } catch (e: IOException) {
                    Log.e("AppUpdater", "Error closing input stream", e)
                }
            }
        }
    }

    init {
        try {
            xmlUrl = URL(url)
        } catch (e: MalformedURLException) {
            throw RuntimeException(e)
        }
    }
}