package com.example.fitsteps.firebaseData.firebaseBodyMeasuresData

data class Measures(
    val fecha: String = "",
    var foto: String = "",
    var hombros: Int = 0,
    var pecho: Int = 0,
    var antebrazoIzq: Int = 0,
    var antebrazoDer: Int = 0,
    var brazoIzq: Int = 0,
    var brazoDer: Int = 0,
    var cintura: Int = 0,
    var cadera: Int = 0,
    var piernaDer: Int = 0,
    var piernaIzq: Int = 0,
    var pantorrillaDer: Int = 0,
    var pantorrillaIzq: Int = 0,
    val uid: String = "",
    var document: String = "",
    var timestamp: Long = 0L,
) {
    fun toMap(): MutableMap<String, Any> {
        return mutableMapOf(
            "fecha" to this.fecha,
            "foto" to this.foto,
            "hombros" to this.hombros,
            "pecho" to this.pecho,
            "antebrazoIzq" to this.antebrazoIzq,
            "antebrazoDer" to this.antebrazoDer,
            "brazoIzq" to this.brazoIzq,
            "brazoDer" to this.brazoDer,
            "cintura" to this.cintura,
            "cadera" to this.cadera,
            "piernaIzq" to this.piernaIzq,
            "piernaDer" to this.piernaDer,
            "pantorrillaDer" to this.pantorrillaDer,
            "pantorrillaIzq" to this.pantorrillaIzq,
            "uid" to this.uid,
            "document" to this.document,
            "timestamps" to this.timestamp,
        )
    }
}