package io.omixer.graphmlcyjs

import io.omixer.graphmlcyjs.cyjs.Node
import io.omixer.graphmlcyjs.svg.Svg
import spock.lang.Specification

import java.awt.Dimension

class GraphMLSpec extends Specification {

    GraphML gml

    def setup() {
        gml = new GraphML()
        Map<String, Svg> svgMapping = (1..6).collectEntries {

            // compile local path
            String svgPath = "src/test/resources/images/${it}.svg".toString()
            // compile web path
            String webPath = "http://localhost:8080/public/8c574d84-0a99-4192-a483-126709392ddc/${it}.svg".toString()
            // get dimensions from local path
            Dimension dimension = Svg.dimensionFromFile(svgPath)
            // create an Svg object
            Svg svg = new Svg(path: webPath, dimension: dimension)
            // map it
            [(it.toString()): svg]
        }
        gml.svgMapping = svgMapping
        gml.parse(new File("src/test/resources/graph.graphml"))
    }

    def "graphML turns a graphML file into CyJs with links to SVG compound files"() {

        when: "a GraphML is built with graphml file and a directory of SVG files"
        gml.build()

        then:
        gml.graph != null
        gml.graph.elements.nodes.size() == 6
        Node node = gml.graph.elements.nodes.find{it.data["label"] == label}
        node.data["svg"] == svgPath
        node.data["width"] == width
        node.data["height"] == height


        where:
        label | svgPath | width | height
        "1" | "http://localhost:8080/public/8c574d84-0a99-4192-a483-126709392ddc/1.svg" | 250 | 250
        "2" | "http://localhost:8080/public/8c574d84-0a99-4192-a483-126709392ddc/2.svg" | 220 | 200
        "5" | "http://localhost:8080/public/8c574d84-0a99-4192-a483-126709392ddc/5.svg" | 250 | 250
        "6" | "http://localhost:8080/public/8c574d84-0a99-4192-a483-126709392ddc/6.svg" | 280 | 350
    }

    def "json serialization works"() {

        when: "a GraphML is built with graphml file and a directory of SVG files"
        gml.build()
        gml.graph.elements.nodes.collect {it.data["id"]}

        then:
        gml.toJson() != null
    }

    def "graphml node id is set on the json node id"() {

        when: "a GraphML is built with graphml file and a directory of SVG files"
        gml.build()
        Map<String, String> nodes = gml.graph.elements.nodes.collectEntries {[(it.data["label"]): it.data["id"]]}

        then:
        nodes[label] == id

        where:
        label | id
        "2" | "InChI=1S/C22H28N7O9P/c1-11(30)27-14(7-12-3-5-13(31)6-4-12)21(33)28-16-15(8-37-39(34,35)36)38-22(18(16)32)29-10-26-17-19(23-2)24-9-25-20(17)29/h3-6,9-10,14-16,18,22,31-32H,7-8H2,1-2H3,(H,27,30)(H,28,33)(H,23,24,25)(H2,34,35,36)"
        "1" | "InChI=1S/C22H29N7O5/c1-28(2)19-17-20(25-10-24-19)29(11-26-17)22-18(31)16(15(9-30)34-22)27-21(32)14(23)8-12-4-6-13(33-3)7-5-12/h4-7,10-11,14-16,18,22,30-31H,8-9,23H2,1-3H3,(H,27,32)"
        "5" | "InChI=1S/C22H30N7O8P/c1-28(2)19-17-20(25-10-24-19)29(11-26-17)22-18(30)16(15(37-22)9-36-38(32,33)34)27-21(31)14(23)8-12-4-6-13(35-3)7-5-12/h4-7,10-11,14-16,18,22,30H,8-9,23H2,1-3H3,(H,27,31)(H2,32,33,34)"
        "6" | "InChI=1S/C24H31N7O6/c1-13(33)28-16(9-14-5-7-15(36-4)8-6-14)23(35)29-18-17(10-32)37-24(20(18)34)31-12-27-19-21(30(2)3)25-11-26-22(19)31/h5-8,11-12,16-18,20,24,32,34H,9-10H2,1-4H3,(H,28,33)(H,29,35)"
    }
}
