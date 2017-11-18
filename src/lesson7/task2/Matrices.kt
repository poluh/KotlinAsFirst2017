@file:Suppress("UNUSED_PARAMETER")

package lesson7.task2

import com.sun.org.apache.xpath.internal.operations.Bool
import lesson3.task1.factorial
import lesson6.task2.Square
import lesson7.task1.Cell
import lesson7.task1.Matrix
import lesson7.task1.MatrixImpl
import lesson7.task1.createMatrix

// Все задачи в этом файле требуют наличия реализации интерфейса "Матрица" в Matrix.kt

/**
 * Пример
 *
 * Транспонировать заданную матрицу matrix.
 * При транспонировании строки матрицы становятся столбцами и наоборот:
 *
 * 1 2 3      1 4 6 3
 * 4 5 6  ==> 2 5 5 2
 * 6 5 4      3 6 4 1
 * 3 2 1
 */
fun <E> transpose(matrix: Matrix<E>): Matrix<E> {
    if (matrix.width < 1 || matrix.height < 1) return matrix
    val result = createMatrix(height = matrix.width, width = matrix.height, e = matrix[0, 0])
    for (i in 0 until matrix.width) {
        for (j in 0 until matrix.height) {
            result[i, j] = matrix[j, i]
        }
    }
    return result
}

/**
 * Пример
 *
 * Сложить две заданные матрицы друг с другом.
 * Складывать можно только матрицы совпадающего размера -- в противном случае бросить IllegalArgumentException.
 * При сложении попарно складываются соответствующие элементы матриц
 */
operator fun Matrix<Int>.plus(other: Matrix<Int>): Matrix<Int> {
    if (width != other.width || height != other.height) throw IllegalArgumentException()
    if (width < 1 || height < 1) return this
    val result = createMatrix(height, width, this[0, 0])
    for (i in 0 until height) {
        for (j in 0 until width) {
            result[i, j] = this[i, j] + other[i, j]
        }
    }
    return result
}

/**
 * Сложная
 *
 * Заполнить матрицу заданной высоты height и ширины width
 * натуральными числами от 1 до m*n по спирали,
 * начинающейся в левом верхнем углу и закрученной по часовой стрелке.
 *
 * Пример для height = 3, width = 4:
 *  1  2  3  4
 * 10 11 12  5
 *  9  8  7  6
 */
fun generateSpiral(height: Int, width: Int): Matrix<Int> {

    if (height < 1 || width < 1) throw IllegalArgumentException("Invalid matrix")

    val matrix = MatrixImpl(height, width, 1)

    var count = 1
    val notSum = height * width
    var x = 1
    while (count <= notSum) {

        for (i in (x - 1) until (width - x + 1)) {
            if (count > notSum) break
            matrix[x - 1, i] = count++
        }

        for (i in x until (height - x + 1)) {
            if (count > notSum) break
            matrix[i, width - x] = count++
        }

        for (i in (width - x - 1) downTo (x - 1)) {
            if (count > notSum) break
            matrix[height - x, i] = count++
        }

        for (i in (height - x - 1) downTo x) {
            if (count > notSum) break
            matrix[i, x - 1] = count++
        }

        x++
    }

    return matrix
}

/**
 * Сложная
 *
 * Заполнить матрицу заданной высоты height и ширины width следующим образом.
 * Элементам, находящимся на периферии (по периметру матрицы), присвоить значение 1;
 * периметру оставшейся подматрицы – значение 2 и так далее до заполнения всей матрицы.
 *
 * Пример для height = 5, width = 6:
 *  1  1  1  1  1  1
 *  1  2  2  2  2  1
 *  1  2  3  3  2  1
 *  1  2  2  2  2  1
 *  1  1  1  1  1  1
 */
fun generateRectangles(height: Int, width: Int): Matrix<Int> {

    var count = 1
    val matrix = createMatrix(height, width, 1)
    var argument = 1
    while (count <= height * width) {

        for (j in (argument - 1) until (width - argument + 1)) {
            if (count > height * width) break
            matrix[argument - 1, j] = argument
            count++
        }

        for (j in argument until (height - argument + 1)) {
            if (count > height * width) break
            matrix[j, width - argument] = argument
            count++
        }

        for (j in (width - argument - 1) downTo (argument - 1)) {
            if (count > height * width) break
            matrix[height - argument, j] = argument
            count++
        }

        for (j in (height - argument - 1) downTo argument) {
            if (count > height * width) break
            matrix[j, argument - 1] = argument
            count++
        }

        argument++
    }
    return matrix

}

/**
 * Сложная
 *
 * Заполнить матрицу заданной высоты height и ширины width диагональной змейкой:
 * в левый верхний угол 1, во вторую от угла диагональ 2 и 3 сверху вниз, в третью 4-6 сверху вниз и так далее.
 *
 * Пример для height = 5, width = 4:
 *  1  2  4  7
 *  3  5  8 11
 *  6  9 12 15
 * 10 13 16 18
 * 14 17 19 20
 */
fun generateSnake(height: Int, width: Int): Matrix<Int> = TODO()

/**
 * Средняя
 *
 * Содержимое квадратной матрицы matrix (с произвольным содержимым) повернуть на 90 градусов по часовой стрелке.
 * Если height != width, бросить IllegalArgumentException.
 *
 * Пример:    Станет:
 * 1 2 3      7 4 1
 * 4 5 6      8 5 2
 * 7 8 9      9 6 3
 */
fun <E> rotate(matrix: Matrix<E>): Matrix<E> {

    if (matrix.width != matrix.height) throw IllegalArgumentException("Invalid matrix")

    val answerMatrix = createMatrix(matrix.height, matrix.width, matrix[0, 0])

    for (i in 0 until matrix.height) {
        for (j in 0 until matrix.height) {
            answerMatrix[i, j] = matrix[matrix.height - 1 - j, i]
        }
    }

    return answerMatrix


}

/**
 * Сложная
 *
 * Проверить, является ли квадратная целочисленная матрица matrix латинским квадратом.
 * Латинским квадратом называется матрица размером n x n,
 * каждая строка и каждый столбец которой содержат все числа от 1 до n.
 * Если height != width, вернуть false.
 *
 * Пример латинского квадрата 3х3:
 * 2 3 1
 * 1 2 3
 * 3 1 2
 */

fun isLatinSquare(matrix: Matrix<Int>): Boolean {

    if (matrix.height != matrix.width) throw IllegalArgumentException("Invalid matrix")
    if (matrix.height == 1 && matrix[0, 0] == 1) {
        return true
    } else if (matrix.height == 1) return false

    var compositionX = 0
    var compositionY = compositionX
    val buf = if (matrix.width > 2) factorial(matrix.width).toInt()
                    else factorial(matrix.width).toInt() + 1

    val rotateMatrix = rotate(matrix)

    for (i in 0 until matrix.width) {
        for (j in 0 until matrix.width) {
            compositionX += matrix[i, j]
            compositionY += rotateMatrix[i, j]
        }
        if (compositionX != compositionY || compositionX != buf) return false

        compositionX = 0
        compositionY = compositionX
    }

    return true
}


/**
 * Средняя
 *
 * В матрице matrix каждый элемент заменить суммой непосредственно примыкающих к нему
 * элементов по вертикали, горизонтали и диагоналям.
 *
 * Пример для матрицы 4 x 3: (11=2+4+5, 19=1+3+4+5+6, ...)
 * 1 2 3       11 19 13
 * 4 5 6  ===> 19 31 19
 * 6 5 4       19 31 19
 * 3 2 1       13 19 11
 *
 * Поскольку в матрице 1 х 1 примыкающие элементы отсутствуют,
 * для неё следует вернуть как результат нулевую матрицу:
 *
 * 42 ===> 0
 */
fun sumNeighbours(matrix: Matrix<Int>): Matrix<Int> {

    if (matrix.width == 1 && matrix.height == 1) return MatrixImpl(1, 1, 0)

    val matrixForChange = createMatrix(matrix.height + 2, matrix.width + 2, 0)

    for (i in 0 until matrix.height) {
        for (j in 0 until matrix.width) {
            matrixForChange[i + 1, j + 1] = matrix[i, j]
        }
    }

    for (i in 1..matrix.height) {
        for (j in 1..matrix.width) {

            val sumElement = matrixForChange[i - 1, j - 1] + matrixForChange[i - 1, j] +
                    matrixForChange[i - 1, j + 1] + matrixForChange[i, j - 1] +
                    matrixForChange[i, j + 1] + matrixForChange[i + 1, j - 1] +
                    matrixForChange[i + 1, j] + matrixForChange[i + 1, j + 1]
            matrix[i - 1, j - 1] = sumElement
        }
    }

    return matrix

}

/**
 * Средняя
 *
 * Целочисленная матрица matrix состоит из "дырок" (на их месте стоит 0) и "кирпичей" (на их месте стоит 1).
 * Найти в этой матрице все ряды и колонки, целиком состоящие из "дырок".
 * Результат вернуть в виде Holes(rows = список дырчатых рядов, columns = список дырчатых колонок).
 * Ряды и колонки нумеруются с нуля. Любой из спискоов rows / columns может оказаться пустым.
 *
 * Пример для матрицы 5 х 4:
 * 1 0 1 0
 * 0 0 1 0
 * 1 0 0 0 ==> результат: Holes(rows = listOf(4), columns = listOf(1, 3)): 4-й ряд, 1-я и 3-я колонки
 * 0 0 1 0
 * 0 0 0 0
 */
fun findHoles(matrix: Matrix<Int>): Holes {

    val containerHolesRows = mutableListOf<Int>()
    val containerHolesColumns = mutableListOf<Int>()
    val answer = Holes(listOf(), listOf())

    for (i in 0 until matrix.height) {
        for (j in 0 until matrix.width) {
            containerHolesRows += matrix[i, j]
        }
        if (containerHolesRows.sorted()[matrix.width - 1] != 1) {
            answer.rows += i
        }
        containerHolesRows.clear()
    }

    for (i in 0 until matrix.width) {
        for (j in 0 until matrix.height) {
            containerHolesColumns += matrix[j, i]
        }
        if (containerHolesColumns.sorted()[matrix.height - 1] != 1) {
            answer.columns += i
        }
        containerHolesColumns.clear()
    }
    return answer
}

/**
 * Класс для описания местонахождения "дырок" в матрице
 */
data class Holes(var rows: List<Int>, var columns: List<Int>)

/**
 * Средняя
 *
 * В целочисленной матрице matrix каждый элемент заменить суммой элементов подматрицы,
 * расположенной в левом верхнем углу матрицы matrix и ограниченной справа-снизу данным элементом.
 *
 * Пример для матрицы 3 х 3:
 *
 * 1  2  3      1  3  6
 * 4  5  6  =>  5 12 21
 * 7  8  9     12 27 45
 *
 * К примеру, центральный элемент 12 = 1 + 2 + 4 + 5, элемент в левом нижнем углу 12 = 1 + 4 + 7 и так далее.
 */
fun sumSubMatrix(matrix: Matrix<Int>): Matrix<Int> {

    val matrixForChange = createMatrix(matrix.height + 1, matrix.width + 1, 0)

    for (i in 1..matrix.height) {
        for (j in 1..matrix.width) {
            matrixForChange[i, j] = matrix[i - 1, j - 1] + matrixForChange[i - 1, j] +
                    matrixForChange[i, j - 1] - matrixForChange[i - 1, j - 1]

        }
    }

    for (i in 0 until matrix.height) {
        for (j in 0 until matrix.width) {
            matrix[i, j] = matrixForChange[i + 1, j + 1]
        }
    }

    return matrix

}

/**
 * Сложная
 *
 * Даны мозаичные изображения замочной скважины и ключа. Пройдет ли ключ в скважину?
 * То есть даны две матрицы key и lock, key.height <= lock.height, key.width <= lock.width, состоящие из нулей и единиц.
 *
 * Проверить, можно ли наложить матрицу key на матрицу lock (без поворота, разрешается только сдвиг) так,
 * чтобы каждой единице в матрице lock (штырь) соответствовал ноль в матрице key (прорезь),
 * а каждому нулю в матрице lock (дырка) соответствовала, наоборот, единица в матрице key (штырь).
 * Ключ при сдвиге не может выходить за пределы замка.
 *
 * Пример: ключ подойдёт, если его сдвинуть на 1 по ширине
 * lock    key
 * 1 0 1   1 0
 * 0 1 0   0 1
 * 1 1 1
 *
 * Вернуть тройку (Triple) -- (да/нет, требуемый сдвиг по высоте, требуемый сдвиг по ширине).
 * Если наложение невозможно, то первый элемент тройки "нет" и сдвиги могут быть любыми.
 */

fun transformable(matrix: Matrix<Int>): Matrix<Int> {

    val invertedMatrix = createMatrix(matrix.height, matrix.width, 0)

    for (i in 0 until matrix.width) {
        for (j in 0 until matrix.height) {
            if (matrix[i, j] == 0) invertedMatrix[i, j] = 1
        }
    }
    return invertedMatrix

}

fun canOpenLock(key: Matrix<Int>, lock: Matrix<Int>): Triple<Boolean, Int, Int> {

    for (i in 0 until key.height) {
        for (j in 0 until key.width) {
            if (key[i, j] !in 0..1) return Triple(false, 0, 0)
        }
    }

    val invertedKey = transformable(key)

    val keyForChange = key

    for (i in 0..lock.height - keyForChange.height) {
        for (j in 0..lock.width - keyForChange.width) {
            for (k in 0 until keyForChange.height) {
                for (l in 0 until keyForChange.width) {
                    keyForChange[k, l] = lock[i + k, j + l]
                }
            }
            if (keyForChange == invertedKey) return Triple(true, i, j)
        }
    }

    return Triple(false, 0, 0)
}

/**
 * Простая
 *
 * Инвертировать заданную матрицу.
 * При инвертировании знак каждого элемента матрицы следует заменить на обратный
 */
operator fun Matrix<Int>.unaryMinus(): Matrix<Int> {

    for (i in 0 until this.height) {
        for (j in 0 until this.width) {
            this[i, j] = -this[i, j]
        }
    }

    return this

}

/**
 * Средняя
 *
 * Перемножить две заданные матрицы друг с другом.
 * Матрицы можно умножать, только если ширина первой матрицы совпадает с высотой второй матрицы.
 * В противном случае бросить IllegalArgumentException.
 * Подробно про порядок умножения см. статью Википедии "Умножение матриц".
 */
operator fun Matrix<Int>.times(other: Matrix<Int>): Matrix<Int> {

    if (this.width != other.height) throw IllegalArgumentException("Invalid input matrix")

    val matrix = MatrixImpl(this.width, other.height, 0)

    for (i in 0 until this.height) {
        for (j in 0 until other.width) {
            for (k in 0 until other.height) {
                matrix[i, j] = this[i, k] * other[k, j]
            }
        }
    }

    return matrix

}

/**
 * Сложная
 *
 * В матрице matrix размером 4х4 дана исходная позиция для игры в 15, например
 *  5  7  9  1
 *  2 12 14 15
 *  3  4  6  8
 * 10 11 13  0
 *
 * Здесь 0 обозначает пустую клетку, а 1-15 – фишки с соответствующими номерами.
 * Напомним, что "игра в 15" имеет квадратное поле 4х4, по которому двигается 15 фишек,
 * одна клетка всегда остаётся пустой. Цель игры -- упорядочить фишки на игровом поле.
 *
 * В списке moves задана последовательность ходов, например [8, 6, 13, 11, 10, 3].
 * Ход задаётся номером фишки, которая передвигается на пустое место (то есть, меняется местами с нулём).
 * Фишка должна примыкать к пустому месту по горизонтали или вертикали, иначе ход не будет возможным.
 * Все номера должны быть в пределах от 1 до 15.
 * Определить финальную позицию после выполнения всех ходов и вернуть её.
 * Если какой-либо ход является невозможным или список содержит неверные номера,
 * бросить IllegalStateException.
 *
 * В данном случае должно получиться
 * 5  7  9  1
 * 2 12 14 15
 * 0  4 13  6
 * 3 10 11  8
 */

fun Matrix<Int>.swapElement(mineIndex: Cell, minorIndex: Cell) {
    this[mineIndex] = this[minorIndex]
    this[minorIndex] = 0
}

fun <E> Matrix<E>.indexOf(element: E): Cell {
    for (i in 0 until this.height) {
        for (j in 0 until this.width) {
            if (this[i, j] == element) {
                return Cell(i, j)
            }
        }
    }
    return Cell(-1, -1)
}

fun Cell.isValidIndex(): Boolean = this.column in 0..3 && this.row in 0..3

fun fifteenGameMoves(matrix: Matrix<Int>, moves: List<Int>): Matrix<Int> {

    moves
            .filter { it !in 1..15 }
            .forEach { throw IllegalStateException("Invalid move. Check rule.") }

    val containerMoveZero =
            listOf(Pair(0, 1), Pair(1, 0), Pair(-1, 0), Pair(0, -1))

    for (move in moves) {
        for ((first, second) in containerMoveZero) {
            val positionZero = matrix.indexOf(0)
            if (positionZero.column == -1) throw IllegalStateException("Invalid zero.")

            val movePosition =
                    Cell(positionZero.row + first, positionZero.column + second)
            if (movePosition.isValidIndex() && matrix[movePosition] == move) {
                matrix.swapElement(positionZero, movePosition)
                break
            }
        }
    }

    return matrix

}

/**
 * Очень сложная
 *
 * В матрице matrix размером 4х4 дана исходная позиция для игры в 15, например
 *  5  7  9  2
 *  1 12 14 15
 *  3  4  6  8
 * 10 11 13  0
 *
 * Здесь 0 обозначает пустую клетку, а 1-15 – фишки с соответствующими номерами.
 * Напомним, что "игра в 15" имеет квадратное поле 4х4, по которому двигается 15 фишек,
 * одна клетка всегда остаётся пустой.
 *
 * Цель игры -- упорядочить фишки на игровом поле, приведя позицию к одному из следующих двух состояний:
 *
 *  1  2  3  4          1  2  3  4
 *  5  6  7  8   ИЛИ    5  6  7  8
 *  9 10 11 12          9 10 11 12
 * 13 14 15  0         13 15 14  0
 *
 * Можно математически доказать, что РОВНО ОДНО из этих двух состояний достижимо из любой исходной позиции.
 *
 * Вернуть решение -- список ходов, приводящих исходную позицию к одной из двух упорядоченных.
 * Каждый ход -- это перемена мест фишки с заданным номером с пустой клеткой (0),
 * при этом заданная фишка должна по горизонтали или по вертикали примыкать к пустой клетке (но НЕ по диагонали).
 * К примеру, ход 13 в исходной позиции меняет местами 13 и 0, а ход 11 в той же позиции невозможен.
 *
 * Одно из решений исходной позиции:
 *
 * [8, 6, 14, 12, 4, 11, 13, 14, 12, 4,
 * 7, 5, 1, 3, 11, 7, 3, 11, 7, 12, 6,
 * 15, 4, 9, 2, 4, 9, 3, 5, 2, 3, 9,
 * 15, 8, 14, 13, 12, 7, 11, 5, 7, 6,
 * 9, 15, 8, 14, 13, 9, 15, 7, 6, 12,
 * 9, 13, 14, 15, 12, 11, 10, 9, 13, 14,
 * 15, 12, 11, 10, 9, 13, 14, 15]
 *
 * Перед решением этой задачи НЕОБХОДИМО решить предыдущую
 */

fun Matrix<Int>.swapElement2(mineIndex: Cell, minorIndex: Cell): Matrix<Int> {
    this[mineIndex] = this[minorIndex]
    this[minorIndex] = 0
    return this
}

fun Matrix<Int>.isValid(): Boolean {
    val start1 = createMatrix(4, 4, listOf(listOf(1, 2, 3, 4), listOf(5, 6, 7, 8),
            listOf(9, 10, 11, 12), listOf(13, 14, 15, 0)))
    val start2 = createMatrix(4, 4, listOf(listOf(1, 2, 3, 4), listOf(5, 6, 7, 8),
            listOf(9, 10, 11, 12), listOf(13, 15, 14, 0)))

    return this == start1 || this == start2
}

fun fifteenGameSolution(matrix: Matrix<Int>): List<Int> {

    val collectionDesk = mutableListOf(matrix)
    val containerMoveZero =
            listOf(Pair(0, 1), Pair(1, 0), Pair(-1, 0), Pair(0, -1))
    var i = 0
    var changeMatrix = matrix

    while (i < 100000) {
        for ((first, second) in containerMoveZero) {
            var locationZero = changeMatrix.indexOf(0)
            var newMove = Cell(locationZero.row + first, locationZero.column + second)
            if (newMove.isValidIndex()) changeMatrix = changeMatrix.swapElement2(locationZero, newMove)

            for ((first2, second2) in containerMoveZero) {
                newMove = Cell(locationZero.row + first2, locationZero.column + second2)
                if (newMove.isValidIndex()) {
                    locationZero = changeMatrix.indexOf(0)
                    val newMatrix = changeMatrix.swapElement2(locationZero, newMove)
                    if (newMatrix !in collectionDesk) {
                        collectionDesk += newMatrix
                    } else {
                        if (newMatrix.isValid()) return listOf(1, 2, 3)
                    }
                }
            }
            if (changeMatrix.isValid()) return listOf(4, 2, 3)
        }
        i++

    }

    return listOf(0, 0, 0 )

}
