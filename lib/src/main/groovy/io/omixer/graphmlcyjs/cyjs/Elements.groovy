package io.omixer.graphmlcyjs.cyjs

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Elements {

    @SerializedName("nodes")
    @Expose
    private List<Node> nodes
    @SerializedName("edges")
    @Expose
    private List<Edge> edges

}
