/**
 *
 */
package io.omixer.graphmlcyjs

import com.google.gson.Gson
import groovy.xml.XmlParser

import io.omixer.graphmlcyjs.cyjs.Edge
import io.omixer.graphmlcyjs.cyjs.Elements
import io.omixer.graphmlcyjs.cyjs.Graph


/**
 * @author omixer*
 * Created on Nov 16, 2020
 */
class GraphML {

    private Node root
    private Graph graph
    private Gson gsonSerializer

    GraphML() {
        gsonSerializer = new Gson()
    }

    GraphML parse(File graphML) {
        root = new XmlParser().parse(graphML)
        this
    }

    GraphML build() {

        graph = new Graph()
        graph.formatVersion = "1.0"
        graph.generatedBy = "omixer/graphml-cyjs"
        graph.targetCytoscapejsVersion = "~2.1"
        graph.elements = new Elements()

        def keys = root.key.collectEntries {
            [(it.@id): it.@"attr.name"]
        }

        // get nodes
        graph.elements.nodes = root.graph.node.collect {
            def node = new io.omixer.graphmlcyjs.cyjs.Node()
            node.data = it.data.collectEntries { data ->
                [(keys[data.@key]): data.text()]
            }
            node.data["id"] = it.@id
            node
        }

        // get edges
        graph.elements.edges = root.graph.edge.collect {

            def edge = new Edge()
            edge.data = it.data.collectEntries { data ->
                [(keys[data.@key]): data.text()]
            }

            edge.data["source"] = it.@source
            edge.data["target"] = it.@target
            edge
        }

        this
    }

    String toJson() {
        gsonSerializer.toJson(graph)
    }
}
