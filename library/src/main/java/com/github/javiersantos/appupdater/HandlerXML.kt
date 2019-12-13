package com.github.javiersantos.appupdater

import com.github.javiersantos.appupdater.objects.Update
import org.xml.sax.Attributes
import org.xml.sax.SAXException
import org.xml.sax.helpers.DefaultHandler
import java.net.MalformedURLException
import java.net.URL

internal class HandlerXML : DefaultHandler() {
    var update: Update? = null
        private set
    private var builder: StringBuilder? = null

    @Throws(SAXException::class)
    override fun characters(ch: CharArray, start: Int, length: Int) {
        super.characters(ch, start, length)
        if (update != null) {
            builder!!.append(ch, start, length)
        }
    }

    @Throws(SAXException::class)
    override fun endElement(uri: String, localName: String, name: String) {
        super.endElement(uri, localName, name)
        if (update != null) {
            when (localName) {
                "latestVersion" -> {
                    update!!.latestVersion = builder.toString().trim { it <= ' ' }
                }
                "latestVersionCode" -> {
                    update!!.latestVersionCode = Integer.valueOf(builder.toString().trim { it <= ' ' })
                }
                "releaseNotes" -> {
                    update!!.releaseNotes = builder.toString().trim { it <= ' ' }
                }
                "url" -> {
                    try {
                        update!!.urlToDownload = URL(builder.toString().trim { it <= ' ' })
                    } catch (e: MalformedURLException) {
                        throw RuntimeException(e)
                    }
                }
            }
            builder!!.setLength(0)
        }
    }

    @Throws(SAXException::class)
    override fun startDocument() {
        super.startDocument()
        builder = StringBuilder()
    }

    @Throws(SAXException::class)
    override fun startElement(uri: String, localName: String,
                              name: String, attributes: Attributes) {
        super.startElement(uri, localName, name, attributes)
        if (localName == "update") {
            update = Update()
        }
    }
}