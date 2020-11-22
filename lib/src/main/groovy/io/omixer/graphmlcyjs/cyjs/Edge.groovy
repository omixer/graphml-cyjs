package io.omixer.graphmlcyjs.cyjs

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Edge {

	@SerializedName("data")
	@Expose
	Map<String, String> data
}