package ru.itis.kpfu.games.dima

data class Cell(
    val row: Int,
    val col: Int,
    val fieldSize: Int
) {
    var number: Int = 0
}