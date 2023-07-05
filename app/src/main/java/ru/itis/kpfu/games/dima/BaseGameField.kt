package ru.itis.kpfu.games.dima

class BaseGameField {

    companion object {
        fun createThreeByThree(): List<Cell> {
            return listOf(
                Cell(0, 0, 3),
                Cell(0, 1, 3),
                Cell(0, 2, 3),

                Cell(1, 0, 3),
                Cell(1, 1, 3),
                Cell(1, 2, 3),

                Cell(2, 0, 3),
                Cell(2, 1, 3),
                Cell(2, 2, 3)
            )
        }
    }
}