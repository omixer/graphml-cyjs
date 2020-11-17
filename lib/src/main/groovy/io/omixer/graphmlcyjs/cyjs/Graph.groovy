package io.omixer.graphmlcyjs.cyjs

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Graph {
    @SerializedName("format_version")
    @Expose
    private String formatVersion
    @SerializedName("generated_by")
    @Expose
    private String generatedBy
    @SerializedName("target_cytoscapejs_version")
    @Expose
    private String targetCytoscapejsVersion
    @SerializedName("elements")
    @Expose
    private Elements elements
}
