package com.example.qrsbazietor

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalTime

class InfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info) // Crea il layout activity_personal_tickets.xml
    }
}

fun calcolaTempo(direction: Int, stationName: Int): Int {
    val direzione: Map<String, Map<String, Int>> = mapOf(
        "Monte Po" to mapOf(
            "Monte Po" to 0,
            "Fontana" to 1,
            "Nesima" to 1,
            "San Nullo" to 3,
            "Cibali" to 2,
            "Milo" to 2,
            "Borgo" to 1,
            "Giuffrida" to 2,
            "Italia" to 2,
            "Galatea" to 1,
            "Giovanni XIII" to 2,
            "Stesicoro" to 2
        ),

        "Stesicoro" to mapOf(
            "Stesicoro" to 0,
            "Giovanni XIII" to 2,
            "Galatea" to 2,
            "Italia" to 1,
            "Giuffrida" to 2,
            "Borgo" to 2,
            "Milo" to 1,
            "Cibali" to 2,
            "San Nullo" to 2,
            "Nesima" to 3,
            "Fontana" to 1,
            "Monte Po" to 1
        )
    )

    val stationNamesMontePo = listOf(
        "Monte Po", "Fontana", "Nesima", "San Nullo", "Cibali", "Milo", "Borgo", "Giuffrida", "Italia", "Galatea", "Giovanni XIII", "Stesicoro"
    )
    val stationNamesStesicoro = listOf(
        "Stesicoro", "Giovanni XIII", "Galatea", "Italia", "Giuffrida", "Borgo", "Milo", "Cibali", "San Nullo", "Nesima", "Fontana", "Monte Po"
    )

    val startStationList = if (direction == 0) stationNamesMontePo else stationNamesStesicoro
    val startStationKey = if (direction == 0) "Monte Po" else "Stesicoro"
    val endStationIndex = if (direction == 0) 0 else 11

    if (stationName < 0 || stationName >= startStationList.size) {
        throw IllegalArgumentException("Invalid stationName: $stationName")
    }

    val startStation = startStationList[stationName]
    var currentStation = startStation
    var tempoTotale = 0
    var currentIndex = stationName

    while (currentIndex != endStationIndex) {
        val nextIndex = if (direction == 0) currentIndex - 1 else currentIndex + 1
        if (nextIndex < 0 || nextIndex >= startStationList.size) {
            break
        }
        val nextStation = startStationList[nextIndex]
        val tempo = direzione[startStationKey]?.get(nextStation) ?: 0
        tempoTotale += tempo
        currentStation = nextStation
        currentIndex = nextIndex
    }

    return tempoTotale
}


fun getTrainStatus(direction: Int, stationName: Int): Pair<Int, LocalTime>? {
    val capolinea = if (direction == 0) "Monte Po" else "Stesicoro"
    val tempoAttuale = LocalTime.now()

    val orari: Map<String, List<LocalTime>> = mapOf(
        "Monte Po" to listOf(
            LocalTime.of(6, 40), LocalTime.of(6, 50), LocalTime.of(7, 0), LocalTime.of(7, 10),
            LocalTime.of(7, 20), LocalTime.of(7, 30), LocalTime.of(7, 40), LocalTime.of(7, 50),
            LocalTime.of(8, 0), LocalTime.of(8, 10), LocalTime.of(8, 20), LocalTime.of(8, 30),
            LocalTime.of(8, 40), LocalTime.of(8, 50), LocalTime.of(9, 0), LocalTime.of(9, 10),
            LocalTime.of(9, 20), LocalTime.of(9, 30), LocalTime.of(9, 40), LocalTime.of(9, 50),
            LocalTime.of(10, 0), LocalTime.of(10, 10), LocalTime.of(10, 20), LocalTime.of(10, 30),
            LocalTime.of(10, 40), LocalTime.of(10, 50), LocalTime.of(11, 0), LocalTime.of(11, 10),
            LocalTime.of(11, 20), LocalTime.of(11, 30), LocalTime.of(11, 40), LocalTime.of(11, 50),
            LocalTime.of(12, 0), LocalTime.of(12, 10), LocalTime.of(12, 20), LocalTime.of(12, 30),
            LocalTime.of(12, 40), LocalTime.of(12, 50), LocalTime.of(13, 0), LocalTime.of(13, 10),
            LocalTime.of(13, 20), LocalTime.of(13, 30), LocalTime.of(13, 40), LocalTime.of(13, 50),
            LocalTime.of(14, 0), LocalTime.of(14, 10), LocalTime.of(14, 20), LocalTime.of(14, 30),
            LocalTime.of(14, 40), LocalTime.of(14, 50), LocalTime.of(15, 0), LocalTime.of(15, 14),
            LocalTime.of(15, 28), LocalTime.of(15, 41), LocalTime.of(15, 54), LocalTime.of(16, 7),
            LocalTime.of(16, 20), LocalTime.of(16, 33), LocalTime.of(16, 46), LocalTime.of(16, 59),
            LocalTime.of(17, 12), LocalTime.of(17, 25), LocalTime.of(17, 38), LocalTime.of(17, 51),
            LocalTime.of(18, 4), LocalTime.of(18, 17), LocalTime.of(18, 30), LocalTime.of(18, 43),
            LocalTime.of(18, 56), LocalTime.of(19, 9), LocalTime.of(19, 22), LocalTime.of(19, 35),
            LocalTime.of(19, 48), LocalTime.of(20, 1), LocalTime.of(20, 14), LocalTime.of(20, 27),
            LocalTime.of(20, 40), LocalTime.of(20, 53), LocalTime.of(21, 6), LocalTime.of(21, 19),
            LocalTime.of(21, 32), LocalTime.of(21, 45), LocalTime.of(21, 58)
        ),
        "Stesicoro" to listOf(
            LocalTime.of(7, 5), LocalTime.of(7, 15), LocalTime.of(7, 25), LocalTime.of(7, 35),
            LocalTime.of(7, 45), LocalTime.of(7, 55), LocalTime.of(8, 5), LocalTime.of(8, 15),
            LocalTime.of(8, 25), LocalTime.of(8, 35), LocalTime.of(8, 45), LocalTime.of(8, 55),
            LocalTime.of(9, 5), LocalTime.of(9, 15), LocalTime.of(9, 25), LocalTime.of(9, 35),
            LocalTime.of(9, 45), LocalTime.of(9, 55), LocalTime.of(10, 5), LocalTime.of(10, 15),
            LocalTime.of(10, 25), LocalTime.of(10, 35), LocalTime.of(10, 45), LocalTime.of(10, 55),
            LocalTime.of(11, 5), LocalTime.of(11, 15), LocalTime.of(11, 25), LocalTime.of(11, 35),
            LocalTime.of(11, 45), LocalTime.of(11, 55), LocalTime.of(12, 5), LocalTime.of(12, 15),
            LocalTime.of(12, 25), LocalTime.of(12, 35), LocalTime.of(12, 45), LocalTime.of(12, 55),
            LocalTime.of(13, 5), LocalTime.of(13, 15), LocalTime.of(13, 25), LocalTime.of(13, 35),
            LocalTime.of(13, 45), LocalTime.of(13, 55), LocalTime.of(14, 5), LocalTime.of(14, 15),
            LocalTime.of(14, 25), LocalTime.of(14, 35), LocalTime.of(14, 45), LocalTime.of(14, 55),
            LocalTime.of(15, 5), LocalTime.of(15, 16), LocalTime.of(15, 28), LocalTime.of(15, 41),
            LocalTime.of(15, 54), LocalTime.of(16, 7), LocalTime.of(16, 20), LocalTime.of(16, 33),
            LocalTime.of(16, 46), LocalTime.of(16, 59), LocalTime.of(17, 12), LocalTime.of(17, 25),
            LocalTime.of(17, 38), LocalTime.of(17, 51), LocalTime.of(18, 4), LocalTime.of(18, 17),
            LocalTime.of(18, 30), LocalTime.of(18, 43), LocalTime.of(18, 56), LocalTime.of(19, 9),
            LocalTime.of(19, 22), LocalTime.of(19, 35), LocalTime.of(19, 48), LocalTime.of(20, 1),
            LocalTime.of(20, 14), LocalTime.of(20, 27), LocalTime.of(20, 40), LocalTime.of(20,53),
            LocalTime.of(21, 6), LocalTime.of(21, 19), LocalTime.of(21, 32), LocalTime.of(21, 45),
            LocalTime.of(21, 58), LocalTime.of(22,11), LocalTime.of(22,30)
        ))

    for (orario in orari[capolinea]!!) {
        if (orario.isAfter(tempoAttuale)) {
            val minutiPrevisti=calcolaTempo(direction, stationName)
            val orarioPrevisto=orario.plusMinutes(minutiPrevisti.toLong())
            return Pair(minutiPrevisti, orarioPrevisto)
        }
    }

    return null
}