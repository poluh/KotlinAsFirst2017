@file:Suppress("UNUSED_PARAMETER")

package lesson6.task1

import lesson1.task1.sqr
import java.lang.Math.*

//Дорабатываю прошлые уроки


/**
 * Точка на плоскости
 */
data class Point(val x: Double, val y: Double) {
    /**
     * Пример
     *
     * Рассчитать (по известной формуле) расстояние между двумя точками
     */
    fun distance(other: Point): Double = sqrt(sqr(x - other.x) + sqr(y - other.y))
}

/**
 * Треугольник, заданный тремя точками (a, b, c, см. constructor ниже).
 * Эти три точки хранятся в множестве points, их порядок не имеет значения.
 */
class Triangle private constructor(private val points: Set<Point>) {

    private val pointList = points.toList()

    val a: Point get() = pointList[0]

    val b: Point get() = pointList[1]

    val c: Point get() = pointList[2]

    constructor(a: Point, b: Point, c: Point) : this(linkedSetOf(a, b, c))

    /**
     * Пример: полупериметр
     */
    fun halfPerimeter() = (a.distance(b) + b.distance(c) + c.distance(a)) / 2.0

    /**
     * Пример: площадь
     */
    fun area(): Double {
        val p = halfPerimeter()
        return sqrt(p * (p - a.distance(b)) * (p - b.distance(c)) * (p - c.distance(a)))
    }

    /**
     * Пример: треугольник содержит точку
     */
    fun contains(p: Point): Boolean {
        val abp = Triangle(a, b, p)
        val bcp = Triangle(b, c, p)
        val cap = Triangle(c, a, p)
        return abp.area() + bcp.area() + cap.area() <= area()
    }

    override fun equals(other: Any?) = other is Triangle && points == other.points

    override fun hashCode() = points.hashCode()

    override fun toString() = "Triangle(a = $a, b = $b, c = $c)"
}

/**
 * Окружность с заданным центром и радиусом
 */
data class Circle(val center: Point, val radius: Double) {
    /**
     * Простая
     *
     * Рассчитать расстояние между двумя окружностями.
     * Расстояние между непересекающимися окружностями рассчитывается как
     * расстояние между их центрами минус сумма их радиусов.
     * Расстояние между пересекающимися окружностями считать равным 0.0.
     */
    fun distance(other: Circle): Double =
            if (center.distance(other.center) > radius + other.radius) {
                (center.distance(other.center) - radius - other.radius)
            } else 0.0

    /**
     * Тривиальная
     *
     * Вернуть true, если и только если окружность содержит данную точку НА себе или ВНУТРИ себя
     */
    fun contains(p: Point): Boolean = (sqr(center.x - p.x) + sqr(center.y - p.y)) <= sqr(radius)
}

/**
 * Отрезок между двумя точками
 */
data class Segment(val begin: Point, val end: Point) {
    override fun equals(other: Any?) =
            (other is Segment) &&
                    (begin == other.begin && end == other.end ||
                            end == other.begin && begin == other.end)

    override fun hashCode() =
            begin.hashCode() + end.hashCode()
}

/**
 * Средняя
 *
 * Дано множество точек. Вернуть отрезок, соединяющий две наиболее удалённые из них.
 * Если в множестве менее двух точек, бросить IllegalArgumentException
 */
fun diameter(vararg points: Point): Segment {

    var max = -1.0
    var maxPoint = Pair(Point(0.0, 0.0), Point(0.0, 0.0))

    if (points.size < 2) {
        throw IllegalArgumentException()
    }

    for (first in points) {
        for (second in points.filter { it != first }) {
            if (first.distance(second) > max) {

                max = first.distance(second)
                maxPoint = Pair(first, second)

            }
        }
    }
    return Segment(maxPoint.first, maxPoint.second)

}


/**
 * Простая
 *
 * Построить окружность по её диаметру, заданному двумя точками
 * Центр её должен находиться посередине между точками, а радиус составлять половину расстояния между ними
 */
fun circleByDiameter(diameter: Segment): Circle =
        Circle(Point((diameter.begin.x + diameter.end.x) / 2,
                (diameter.begin.y + diameter.end.y) / 2),
                diameter.begin.distance(Point((diameter.begin.x + diameter.end.x) / 2,
                        (diameter.begin.y + diameter.end.y) / 2)))

/**
 * Прямая, заданная точкой point и углом наклона angle (в радианах) по отношению к оси X.
 * Уравнение прямой: (y - point.y) * cos(angle) = (x - point.x) * sin(angle)
 * или: y * cos(angle) = x * sin(angle) + b, где b = point.y * cos(angle) - point.x * sin(angle).
 * Угол наклона обязан находиться в диапазоне от 0 (включительно) до PI (исключительно).
 */
class Line private constructor(val b: Double, val angle: Double) {
    init {
        assert(angle >= 0 && angle < PI) { "Incorrect line angle: $angle" }
    }

    constructor(point: Point, angle: Double) : this(point.y * cos(angle) - point.x * sin(angle), angle)

    /**
     * Средняя
     *
     * Найти точку пересечения с другой линией.
     * Для этого необходимо составить и решить систему из двух уравнений (каждое для своей прямой)
     */
    fun crossPoint(other: Line): Point = when {
        this.angle == Math.PI / 2 -> Point(-this.b,
                (-this.b) * tan(other.angle) + other.b / cos(other.angle))

        other.angle == Math.PI / 2 -> Point(-other.b,
                (-other.b) * tan(this.angle) + this.b / cos(this.angle))

        else -> {
            val argument1 = -(this.b / cos(this.angle) - other.b / cos(other.angle)) /
                    (tan(this.angle) - tan(other.angle))
            val argument2 = (-(this.b / cos(this.angle) - other.b / cos(other.angle)) /
                    (tan(this.angle) - tan(other.angle))) *
                    tan(this.angle) + this.b / cos(this.angle)
            Point(argument1, argument2)
        }
    }

    override fun equals(other: Any?) = other is Line && angle == other.angle && b == other.b

    override fun hashCode(): Int {
        var result = b.hashCode()
        result = 31 * result + angle.hashCode()
        return result
    }

    override fun toString() = "Line(${cos(angle)} * y = ${sin(angle)} * x + $b)"
}

/**
 * Средняя
 *
 * Построить прямую по отрезку
 */
fun lineBySegment(s: Segment): Line {

    var angle = atan2((s.end.y - s.begin.y), (s.end.x - s.begin.x))

    when {
        angle < 0 -> angle += PI
        angle == PI -> angle -= PI
        else -> IllegalArgumentException("Invalid angle")
    }
    return Line(s.begin, angle)

}

/**
 * Средняя
 *
 * Построить прямую по двум точкам
 */
fun lineByPoints(a: Point, b: Point): Line = lineBySegment(Segment(a, b))

/**
 * Сложная
 *
 * Построить серединный перпендикуляр по отрезку или по двум точкам
 */
fun bisectorByPoints(a: Point, b: Point): Line {

    val grad =
            if (lineByPoints(a, b).angle + PI / 2 >= PI) {
                lineByPoints(a, b).angle - PI / 2
            } else {
                lineByPoints(a, b).angle + PI / 2
            }

    val middlePoint = Point((a.x + b.x) / 2, (a.y + b.y) / 2)

    return Line(middlePoint, grad)

}

/**
 * Средняя
 *
 * Задан список из n окружностей на плоскости. Найти пару наименее удалённых из них.
 * Если в списке менее двух окружностей, бросить IllegalArgumentException
 */
fun findNearestCirclePair(vararg circles: Circle): Pair<Circle, Circle> {

    if (circles.size < 2) {
        throw IllegalArgumentException("In the list of less than two circles\n")
    }


    var minDistance = circles[0].distance(circles[1])
    var answer = Pair(circles[0], circles[1])

    for (i in 0.until(circles.size)) {

        for (j in (i + 1).until(circles.size)) {

            if (circles[i].distance(circles[j]) < minDistance) {

                answer = Pair(circles[i], circles[j])
                minDistance = circles[i].distance(circles[j])
            }
        }
    }
    return answer

}

/**
 * Сложная
 *
 * Дано три различные точки. Построить окружность, проходящую через них
 * (все три точки должны лежать НА, а не ВНУТРИ, окружности).
 * Описание алгоритмов см. в Интернете
 * (построить окружность по трём точкам, или
 * построить окружность, описанную вокруг треугольника - эквивалентная задача).
 */
fun circleByThreePoints(a: Point, b: Point, c: Point): Circle {

    val bisectorAtoB = bisectorByPoints(a, b)
    val bisectorBtoC = bisectorByPoints(b, c)

    val point = bisectorAtoB.crossPoint(bisectorBtoC)

    return Circle(point, point.distance(a))

}

/**
 * Очень сложная
 *
 * Дано множество точек на плоскости. Найти круг минимального радиуса,
 * содержащий все эти точки. Если множество пустое, бросить IllegalArgumentException.
 * Если множество содержит одну точку, вернуть круг нулевого радиуса с центром в данной точке.
 *
 * Примечание: в зависимости от ситуации, такая окружность может либо проходить через какие-либо
 * три точки данного множества, либо иметь своим диаметром отрезок,
 * соединяющий две самые удалённые точки в данном множестве.
 */
fun minContainingCircle(vararg points: Point): Circle {

    if (points.isEmpty()) throw IllegalArgumentException("Invalid container points.")
    if (points.count() == 1) return Circle(points[0], 0.0)
    if (points.count() == 2) return circleByDiameter(Segment(points[0], points[1]))
    if (points.count() == 3) return circleByThreePoints(points[0], points[1], points[2])

    var bigCircle = Circle(Point(0.0, 0.0), 100000.0)

    for (first in points) {
        for (second in points.filter { it != first }) {
            for (third in points.filter { it != first && it != second }) {
                val createCircle = circleByThreePoints(first, second, third)

                if (points.all { createCircle.contains(it) }) {
                    if (createCircle.radius < bigCircle.radius)
                        bigCircle = createCircle
                }
            }
        }
    }


    val otherBigCircle = circleByDiameter(diameter(*points))
    return if (bigCircle.radius < otherBigCircle.radius ||
            !points.all { otherBigCircle.contains(it) }) {
        bigCircle
    } else otherBigCircle
}

