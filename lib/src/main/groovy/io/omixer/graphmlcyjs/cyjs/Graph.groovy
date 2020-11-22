package io.omixer.graphmlcyjs.cyjs

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Graph {
    @SerializedName("format_version")
    @Expose
    String formatVersion
    @SerializedName("generated_by")
    @Expose
    String generatedBy
    @SerializedName("target_cytoscapejs_version")
    @Expose
    String targetCytoscapejsVersion
    @SerializedName("elements")
    @Expose
    Elements elements
}
