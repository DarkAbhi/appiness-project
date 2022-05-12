package com.darkabhi.appinesstask.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MockableResponse(
    @Json(name = "dataObject")
    val dataObject: List<DataObject>,
    @Json(name = "status")
    val status: Boolean,
    @Json(name = "statusCode")
    val statusCode: Int
) {
    @JsonClass(generateAdapter = true)
    data class DataObject(
        @Json(name = "hierarchyList")
        val hierarchyList: List<Hierarchy>
    ) {
        @JsonClass(generateAdapter = true)
        data class Hierarchy(
            @Json(name = "hierarchy")
            val hierarchy: List<HierarchyObjects>
        ) {
            @JsonClass(generateAdapter = true)
            data class HierarchyObjects(
                @Json(name = "categoryName")
                val categoryName: String,
                @Json(name = "firstName")
                val firstName: String,
                @Json(name = "lastName")
                val lastName: String,
                @Json(name = "phoneNumber")
                val phoneNumber: String
            )
        }
    }
}