package io.omixer.graphmlcyjs.svg

import groovy.xml.XmlSlurper
import groovy.xml.slurpersupport.GPathResult

import java.awt.Dimension

class Svg {

    String path
    Dimension dimension

    static Dimension dimensionFromFile(String path) {
        dimensionFromFile(new File(path))
    }
    static Dimension dimensionFromFile(File file) {
        file.withReader { reader ->
            //  skip xml stanza
            reader.readLine()
            // skip doctype
            reader.readLine()
            GPathResult svg = new XmlSlurper().parse(reader)
            new Dimension(svg.@width.toInteger(), svg.@height.toInteger())
        }
    }
}
