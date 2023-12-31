package com.example.fitsteps.firebaseData.firebaseRunningData

import com.google.android.gms.maps.model.LatLng

data class Route(
    val date: String = "",
    val route: List<LatLng> = listOf(),
    val time: String = "",
    val distance: String = "",
    val steps: Int = 0,
    val hour: String = "",
    val timestamp: Long = 0,
) {
    fun toMap(): MutableMap<String, Any> {
        return mutableMapOf(
            "date" to this.date,
            "time" to this.time,
            "distance" to this.distance,
            "route" to this.route,
            "steps" to this.steps,
            "hour" to this.hour,
            "timestamp" to this.timestamp,
        )
    }
}