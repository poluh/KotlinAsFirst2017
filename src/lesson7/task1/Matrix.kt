@file:Suppress("UNUSED_PARAMETER", "unused")
package lesson7.task1


/**
 * Ячейка матрицы: row = ряд, column = колонка
 */
data class Cell(val row: Int, val column: Int)

/**
 * Интерфейс, описывающий возможности матрицы. E = тип элемента матрицы
 */
interface Matrix<E> {
    /** Высота */
    val height: Int

    /** Ширина */
    val width: Int

    /**
     * Доступ к ячейке.
     * Методы могут бросить исключение, если ячейка не существует или пуста
     */
    operator fun get(row: Int, column: Int): E
    operator fun get(cell: Cell): E

    /**
     * Запись в ячейку.
     * Методы могут бросить исключение, если ячейка не существует
     */
    operator fun set(row: Int, column: Int, value: E)
    operator fun set(cell: Cell, value: E)
    fun equals(other: Matrix<E>): Boolean
}

/**
 * Простая
 *
 * Метод для создания матрицы, должен вернуть РЕАЛИЗАЦИЮ Matrix<E>.
 * height = высота, width = ширина, e = чем заполнить элементы.
 * Бросить исключение IllegalArgumentException, если height или width <= 0.
 */
fun <E> createMatrix(height: Int, width: Int, e: E): Matrix<E> {

    if (height <= 0 || width <= 0) throw IllegalArgumentException("Invalid options for matrix")

    return MatrixImpl(height, width, e)


}


/**
 * Средняя сложность
 *
 * Реализация интерфейса "матрица"
 */
class MatrixImpl<E>(override val height: Int, override val width: Int, e: E) : Matrix<E> {

    private val list = mutableListOf<E>()

    init {

        (0 until height * width).forEach { list.add(e) }

    }

    override fun get(row: Int, column: Int): E = list[width * row + column]

    override fun get(cell: Cell): E  = list[cell.row * width + cell.column]

    override fun set(row: Int, column: Int, value: E) {

        list[width * row + column] = value

    }

    override fun set(cell: Cell, value: E) {

        list[cell.row * width + cell.column] = value

    }

    override fun equals(other: Matrix<E>): Boolean {

        if ((other is MatrixImpl<*>) && (height == other.height) && (width == other.width)) {
            for (i in 0 until height) {
                for (j in 0 until width) {
                    if (other[i, j] != this [i, j]) {
                        return false
                    }
                }
            }
            return true
        }

        return true

    }

    override fun toString(): String {

        val answer = StringBuilder()

        for (i in 0 until height) {
            for (j in 0 until width) {

                answer.append(this [i, j], " ")
            }
            answer.append("\n")
        }
        return "$answer"
    }

    override fun hashCode(): Int {
        var result = height
        result = 31 * result + width
        result = 31 * result + list.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MatrixImpl<*>

        if (height != other.height) return false
        if (width != other.width) return false
        if (list != other.list) return false

        return true
    }


}

