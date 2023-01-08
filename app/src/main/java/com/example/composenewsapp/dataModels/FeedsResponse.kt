package com.example.composenewsapp.dataModels


@kotlinx.serialization.Serializable
data class FeedsResponse(

	val feed: Feed? = null,

	var items: List<ItemsItem>? = null,

	var status: String? = null,

	var message: String? = null
)

@kotlinx.serialization.Serializable
data class Feed(

	val image: String? = null,

	val author: String? = null,

	val link: String? = null,

	val description: String? = null,

	val title: String? = null,

	val url: String? = null
)

@kotlinx.serialization.Serializable
data class ItemsItem(

	val thumbnail: String? = null,

	val enclosure: Enclosure? = null,

	val author: String? = null,

	val link: String? = null,

	val guid: String? = null,

	var description: String? = null,

	val categories: List<String?>? = null,

	var title: String? = null,

	var pubDate: String? = null,

	val content: String? = null
)

@kotlinx.serialization.Serializable
data class Enclosure(

	var link: String? = null
)
