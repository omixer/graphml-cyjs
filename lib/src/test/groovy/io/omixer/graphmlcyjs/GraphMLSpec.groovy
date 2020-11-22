package io.omixer.graphmlcyjs

import io.omixer.graphmlcyjs.cyjs.Node
import spock.lang.Specification

class GraphMLSpec extends Specification {

    def "graphML turns a graphML file into CyJs with links to SVG compound files"() {
        when: "a GraphML is built with graphml file and a directory of SVG files"
        GraphML gml = new GraphML()
        gml.svgDirectory = new File("src/test/resources/images")
        gml.parse(new File("src/test/resources/graph.graphml")).build()

        then:
        gml.graph != null
        gml.graph.elements.nodes.size() == 6
        Node node = gml.graph.elements.nodes.find{it.data["label"] == label}
        node.data["svg"] == svgPath.getAbsolutePath()

        where:
        label | svgPath
        "1" | new File("src/test/resources/images", "1.svg")
        "2" | new File("src/test/resources/images", "2.svg")
        "5" | new File("src/test/resources/images", "5.svg")
        "6" | new File("src/test/resources/images", "6.svg")
    }
}
