package com.example.fitsteps.screens.body.firebaseMeasuresData

data class Measure(
    val fecha : String = "",
    val foto: String = "",
    val hombros: Int = 0,
    val pecho: Int = 0,
    val antebrazoIzq: Int = 0,
    val antebrazoDer: Int = 0,
    val brazoIzq: Int = 0,
    val brazoDer: Int = 0,
    val cintura: Int = 0,
    val cadera: Int = 0,
    val piernaDer: Int = 0,
    val piernaIzq: Int = 0,
    val pantorrillaDer: Int = 0,
    val pantorillaIzq: Int = 0,
    val uid: String = "",
    var document: String = "",
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
            "pantorrillaIzq" to this.pantorillaIzq,
            "uid" to this.uid
        )
    }
}