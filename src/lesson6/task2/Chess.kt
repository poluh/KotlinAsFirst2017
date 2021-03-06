@file:Suppress("UNUSED_PARAMETER")

package lesson6.task2

import lesson6.task3.Graph
import java.lang.Math.*

/**
 * Клетка шахматной доски. Шахматная доска квадратная и имеет 8 х 8 клеток.
 * Поэтому, обе координаты клетки (горизонталь row, вертикаль column) могут находиться в пределах от 1 до 8.
 * Горизонтали нумеруются снизу вверх, вертикали слева направо.
 */
data class Square(val column: Int, val row: Int) {
    /**
     * Пример
     *
     * Возвращает true, если клетка находится в пределах доски
     */
    fun inside(): Boolean = column in 1..8 && row in 1..8

    /**
     * Простая
     *
     * Возвращает строковую нотацию для клетки.
     * В нотации, колонки обозначаются латинскими буквами от a до h, а ряды -- цифрами от 1 до 8.
     * Для клетки не в пределах доски вернуть пустую строку
     */
    fun notation(): String {

        if (!inside()) return ""
        return "${(column + 'a'.toInt() - 1).toChar()}$row"
    }
}

/**
 * Простая
 *
 * Создаёт клетку по строковой нотации.
 * В нотации, колонки обозначаются латинскими буквами от a до h, а ряды -- цифрами от 1 до 8.
 * Если нотация некорректна, бросить IllegalArgumentException
 */
fun square(notation: String): Square {

    if ((notation.length != 2) || (notation[0] !in 'a'..'h') || (notation[1] !in '1'..'8')) {
        throw IllegalArgumentException("Invalid string")
    }

    return Square(notation[0].toInt() + 1 - 'a'.toInt(), notation[1].toInt() - '0'.toInt())

}

/**
 * Простая
 *
 * Определить число ходов, за которое шахматная ладья пройдёт из клетки start в клетку end.
 * Шахматная ладья может за один ход переместиться на любую другую клетку
 * по вертикали или горизонтали.
 * Ниже точками выделены возможные ходы ладьи, а крестиками -- невозможные:
 *
 * xx.xxххх
 * xх.хxххх
 * ..Л.....
 * xх.хxххх
 * xx.xxххх
 * xx.xxххх
 * xx.xxххх
 * xx.xxххх
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Пример: rookMoveNumber(Square(3, 1), Square(6, 3)) = 2
 * Ладья может пройти через клетку (3, 3) или через клетку (6, 1) к клетке (6, 3).
 */
fun rookMoveNumber(start: Square, end: Square): Int {

    if (!start.inside() || !end.inside()) throw IllegalArgumentException("Invalid square")

    return when {

        start == end -> 0
        start.row == end.row || start.column == end.column -> 1
        else -> 2

    }

}

/**
 * Средняя
 *
 * Вернуть список из клеток, по которым шахматная ладья может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов ладьи см. предыдущую задачу.
 * Список всегда включает в себя клетку start. Клетка end включается, если она не совпадает со start.
 * Между ними должны находиться промежуточные клетки, по порядку от start до end.
 * Примеры: rookTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 *          (здесь возможен ещё один вариант)
 *          rookTrajectory(Square(3, 1), Square(6, 3)) = listOf(Square(3, 1), Square(3, 3), Square(6, 3))
 *          (здесь возможен единственный вариант)
 *          rookTrajectory(Square(3, 5), Square(8, 5)) = listOf(Square(3, 5), Square(8, 5))
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
fun rookTrajectory(start: Square, end: Square): List<Square> {

    if (!start.inside() || !end.inside()) throw IllegalArgumentException("Invalid square")

    val rookMoves = rookMoveNumber(start, end)

    return when (rookMoves) {

        0 -> listOf(start)
        1 -> listOf(start, end)
        else -> listOf(start, Square(start.column, end.row), end)

    }

}

/**
 * Простая
 *
 * Определить число ходов, за которое шахматный слон пройдёт из клетки start в клетку end.
 * Шахматный слон может за один ход переместиться на любую другую клетку по диагонали.
 * Ниже точками выделены возможные ходы слона, а крестиками -- невозможные:
 *
 * .xxx.ххх
 * x.x.xххх
 * xxСxxxxx
 * x.x.xххх
 * .xxx.ххх
 * xxxxx.хх
 * xxxxxх.х
 * xxxxxхх.
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если клетка end недостижима для слона, вернуть -1.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Примеры: bishopMoveNumber(Square(3, 1), Square(6, 3)) = -1; bishopMoveNumber(Square(3, 1), Square(3, 7)) = 2.
 * Слон может пройти через клетку (6, 4) к клетке (3, 7).
 */
fun bishopMoveNumber(start: Square, end: Square): Int {

    if (!start.inside() || !end.inside()) throw IllegalArgumentException("Invalid square")

    return when {

        start == end -> 0
        (start.column + start.row) % 2 != (end.column + end.row) % 2 -> -1
        Math.abs(start.column - end.column) == Math.abs(start.row - end.row) -> 1
        else -> 2

    }

}

/**
 * Сложная
 *
 * Вернуть список из клеток, по которым шахматный слон может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов слона см. предыдущую задачу.
 *
 * Если клетка end недостижима для слона, вернуть пустой список.
 *
 * Если клетка достижима:
 * - список всегда включает в себя клетку start
 * - клетка end включается, если она не совпадает со start.
 * - между ними должны находиться промежуточные клетки, по порядку от start до end.
 *
 * Примеры: bishopTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 *          bishopTrajectory(Square(3, 1), Square(3, 7)) = listOf(Square(3, 1), Square(6, 4), Square(3, 7))
 *          bishopTrajectory(Square(1, 3), Square(6, 8)) = listOf(Square(1, 3), Square(6, 8))
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
fun bishopTrajectory(start: Square, end: Square): List<Square> { //дорабатываю

    if (!start.inside() || !end.inside()) throw IllegalArgumentException("Invalid square")

    val bishopMove = bishopMoveNumber(start, end)
    var answer = listOf<Square>()

    when (bishopMove) {
        0 -> answer = listOf(end)
        1 -> answer = listOf(start, end)
        2 -> {

            for (i in 1..8) {
                for (j in 1..8) {
                    if ((abs(end.column - i) == abs(end.row - j)) &&
                            (abs(start.column - i) == abs(start.row - j))) {
                        answer = listOf(start, Square(i, j), end)
                        break
                    }
                }
                if (answer.isNotEmpty()) break
            }
        }

    }
    return answer
}

/**
 * Средняя
 *
 * Определить число ходов, за которое шахматный король пройдёт из клетки start в клетку end.
 * Шахматный король одним ходом может переместиться из клетки, в которой стоит,
 * на любую соседнюю по вертикали, горизонтали или диагонали.
 * Ниже точками выделены возможные ходы короля, а крестиками -- невозможные:
 *
 * xxxxx
 * x...x
 * x.K.x
 * x...x
 * xxxxx
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Пример: kingMoveNumber(Square(3, 1), Square(6, 3)) = 3.
 * Король может последовательно пройти через клетки (4, 2) и (5, 2) к клетке (6, 3).
 */
fun kingMoveNumber(start: Square, end: Square): Int {

    if (!start.inside() || !end.inside()) throw IllegalArgumentException()

    return max(abs(start.column - end.column), abs(start.row - end.row))

}

/**
 * Сложная
 *
 * Вернуть список из клеток, по которым шахматный король может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов короля см. предыдущую задачу.
 * Список всегда включает в себя клетку start. Клетка end включается, если она не совпадает со start.
 * Между ними должны находиться промежуточные клетки, по порядку от start до end.
 * Примеры: kingTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 *          (здесь возможны другие варианты)
 *          kingTrajectory(Square(3, 1), Square(6, 3)) = listOf(Square(3, 1), Square(4, 2), Square(5, 2), Square(6, 3))
 *          (здесь возможен единственный вариант)
 *          kingTrajectory(Square(3, 5), Square(6, 2)) = listOf(Square(3, 5), Square(4, 4), Square(5, 3), Square(6, 2))
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */

fun Square.isApproached(start: Square, termination: Square): Boolean {

    val boundaryColumn = if (start.column < termination.column) {
        Pair(start.column, termination.column)
    } else Pair(termination.column, start.column)
    val boundaryRow = if (start.row < termination.row) {
        Pair(start.row, termination.row)
    } else Pair(termination.row, start.row)

    return this.column in boundaryColumn.first..boundaryColumn.second &&
            this.row in boundaryRow.first..boundaryRow.second
}


fun Square.thoseSameMoves(termination: Square): List<Square> {

    val containerMoves = listOf(Pair(1, 1), Pair(-1, -1), Pair(1, -1), Pair(-1, 1),
            Pair(1, 0), Pair(0, 1), Pair(0, -1), Pair(-1, 0))

    val answer = mutableListOf(this)
    var startS = this
    if (!this.inside() || !termination.inside()) throw IllegalArgumentException("THIS SQUARE BAD")

    while (startS != termination) {
        for ((first, second) in containerMoves) {
            val newMove = Square(startS.column + first, startS.row + second)
            if (newMove.inside() && newMove.isApproached(startS, termination)) {
                startS = newMove
                answer += newMove
                break
            }
        }
    }

    return answer

}


fun kingTrajectory(start: Square, end: Square): List<Square> = start.thoseSameMoves(end)

/**
 * Сложная
 *
 * Определить число ходов, за которое шахматный конь пройдёт из клетки start в клетку end.
 * Шахматный конь одним ходом вначале передвигается ровно на 2 клетки по горизонтали или вертикали,
 * а затем ещё на 1 клетку под прямым углом, образуя букву "Г".
 * Ниже точками выделены возможные ходы коня, а крестиками -- невозможные:
 *
 * .xxx.xxx
 * xxKxxxxx
 * .xxx.xxx
 * x.x.xxxx
 * xxxxxxxx
 * xxxxxxxx
 * xxxxxxxx
 * xxxxxxxx
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Пример: knightMoveNumber(Square(3, 1), Square(6, 3)) = 3.
 * Конь может последовательно пройти через клетки (5, 2) и (4, 4) к клетке (6, 3).
 */

fun Graph.fillDesk(): Graph {
    (1..8).forEach { column ->
        (1..8)
                .forEach { row -> this.addVertex(Square(column, row).notation()) }
    }
    return this.createLine()
}

fun Graph.createLine(): Graph {
    val containerKnightMoves = listOf(Pair(2, 1), Pair(2, -1), Pair(-2, 1),
            Pair(-2, -1), Pair(1, 2), Pair(1, -2), Pair(-1, 2), Pair(-1, -2))

    (1..8).forEach { column ->
        (1..8).forEach { row ->
            for ((first, second) in containerKnightMoves) {
                if (Square(column + first, row + second).inside()) {
                    val firstSquare = Square(column, row).notation()
                    val secondSquare = Square(column + first, row + second).notation()
                    this.connect(firstSquare, secondSquare)
                }
            }
        }
    }

    return this
}

fun knightMoveNumber(start: Square, end: Square): Int =
        Graph().fillDesk().bfs(start.notation(), end.notation())

/**
 * Очень сложная
 *
 * Вернуть список из клеток, по которым шахматный конь может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов коня см. предыдущую задачу.
 * Список всегда включает в себя клетку start. Клетка end включается, если она не совпадает со start.
 * Между ними должны находиться промежуточные клетки, по порядку от start до end.
 * Примеры:
 *
 * knightTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 * здесь возможны другие варианты)
 * knightTrajectory(Square(3, 1), Square(6, 3)) = listOf(Square(3, 1), Square(5, 2), Square(4, 4), Square(6, 3))
 * (здесь возможен единственный вариант)
 * knightTrajectory(Square(3, 5), Square(5, 6)) = listOf(Square(3, 5), Square(5, 6))
 * (здесь опять возможны другие варианты)
 * knightTrajectory(Square(7, 7), Square(8, 8)) =
 *     listOf(Square(7, 7), Square(5, 8), Square(4, 6), Square(6, 7), Square(8, 8))
 *
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */

fun Square.findTrajectory(termination: Square): List<Square> {
    if (!this.inside() || !termination.inside()) {
        throw IllegalArgumentException("Invalid square")
    }

    val containerMoves = listOf(Pair(2, 1), Pair(2, -1), Pair(-2, 1),
            Pair(-2, -1), Pair(1, 2), Pair(1, -2), Pair(-1, 2), Pair(-1, -2))
    val answer = mutableListOf(this)
    var startSave = this
    var terminalDistance = knightMoveNumber(this, termination)

    while (terminalDistance != 0) {
        for ((first, second) in containerMoves) {
            val newSquare = Square(startSave.column + first,
                    startSave.row + second)
            if (newSquare.inside()) {

                val newDistance = knightMoveNumber(newSquare, termination)
                if (newDistance < terminalDistance) {

                    terminalDistance--
                    startSave = newSquare
                    answer.add(newSquare)
                }
            }
        }
    }
    return answer
}

fun knightTrajectory(start: Square, end: Square): List<Square> = start.findTrajectory(end)
