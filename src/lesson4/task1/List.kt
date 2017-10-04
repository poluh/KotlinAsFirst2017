@file:Suppress("UNUSED_PARAMETER")

package lesson4.task1

import lesson1.task1.discriminant
import lesson1.task1.sqr

/**
 * Пример
 *
 * Найти все корни уравнения x^2 = y
 */
fun sqRoots(y: Double) =
        when {
            y < 0 -> listOf()
            y == 0.0 -> listOf(0.0)
            else -> {
                val root = Math.sqrt(y)
                // Результат!
                listOf(-root, root)
            }
        }

/**
 * Пример
 *
 * Найти все корни биквадратного уравнения ax^4 + bx^2 + c = 0.
 * Вернуть список корней (пустой, если корней нет)
 */
fun biRoots(a: Double, b: Double, c: Double): List<Double> {
    if (a == 0.0) {
        return if (b == 0.0) listOf()
        else sqRoots(-c / b)
    }
    val d = discriminant(a, b, c)
    if (d < 0.0) return listOf()
    if (d == 0.0) return sqRoots(-b / (2 * a))
    val y1 = (-b + Math.sqrt(d)) / (2 * a)
    val y2 = (-b - Math.sqrt(d)) / (2 * a)
    return sqRoots(y1) + sqRoots(y2)
}

/**
 * Пример
 *
 * Выделить в список отрицательные элементы из заданного списка
 */
fun negativeList(list: List<Int>): List<Int> {
    val result = mutableListOf<Int>()
    for (element in list) {
        if (element < 0) {
            result.add(element)
        }
    }
    return result
}

/**
 * Пример
 *
 * Изменить знак для всех положительных элементов списка
 */
fun invertPositives(list: MutableList<Int>) {
    for (i in 0 until list.size) {
        val element = list[i]
        if (element > 0) {
            list[i] = -element
        }
    }
}

/**
 * Пример
 *
 * Из имеющегося списка целых чисел, сформировать список их квадратов
 */
fun squares(list: List<Int>) = list.map { it * it }

/**
 * Пример
 *
 * По заданной строке str определить, является ли она палиндромом.
 * В палиндроме первый символ должен быть равен последнему, второй предпоследнему и т.д.
 * Одни и те же буквы в разном регистре следует считать равными с точки зрения данной задачи.
 * Пробелы не следует принимать во внимание при сравнении символов, например, строка
 * "А роза упала на лапу Азора" является палиндромом.
 */
fun isPalindrome(str: String): Boolean {
    val lowerCase = str.toLowerCase().filter { it != ' ' }
    for (i in 0..lowerCase.length / 2) {
        if (lowerCase[i] != lowerCase[lowerCase.length - i - 1]) return false
    }
    return true
}

/**
 * Пример
 *
 * По имеющемуся списку целых чисел, например [3, 6, 5, 4, 9], построить строку с примером их суммирования:
 * 3 + 6 + 5 + 4 + 9 = 27 в данном случае.
 */
fun buildSumExample(list: List<Int>) = list.joinToString(separator = " + ", postfix = " = ${list.sum()}")

/**
 * Простая
 *
 * Найти модуль заданного вектора, представленного в виде списка v,
 * по формуле abs = sqrt(a1^2 + a2^2 + ... + aN^2).
 * Модуль пустого вектора считать равным 0.0.
 */
fun abs(v: List<Double>): Double {

    var vectorAllPar = 0.0

    if (v != listOf<Double>()) {

        for (i in 0 until v.size) {

            vectorAllPar += sqr(v[i])

        }
    }

    return Math.sqrt(vectorAllPar)


}

/**
 * Простая
 *
 * Рассчитать среднее арифметическое элементов списка list. Вернуть 0.0, если список пуст
 */
fun mean(list: List<Double>): Double {

    var allParameterList = 0.0

    if (list != listOf<Double>()) {

        for (i in 0 until list.size) {

            allParameterList += list[i]

        }

        allParameterList /= list.size

    }

    return allParameterList

}

/**
 * Средняя
 *
 * Центрировать заданный список list, уменьшив каждый элемент на среднее арифметическое всех элементов.
 * Если список пуст, не делать ничего. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun center(list: MutableList<Double>): MutableList<Double> {

    var averageAllElement = 0.0

    if (list != listOf<Double>()) {

        for (i in 0 until list.size) {

            averageAllElement += list[i]

        }

        averageAllElement /= list.size

        for (i in 0 until list.size) {

            list[i] -= averageAllElement

        }

    }

    return list

}

/**
 * Средняя
 *
 * Найти скалярное произведение двух векторов равной размерности,
 * представленные в виде списков a и b. Скалярное произведение считать по формуле:
 * C = a1b1 + a2b2 + ... + aNbN. Произведение пустых векторов считать равным 0.0.
 */
fun times(a: List<Double>, b: List<Double>): Double {

    var answer = 0.0

    if ((a != listOf<Double>()) && (b != listOf<Double>())) {

        for (i in 0 until a.size) {

            answer += a[i] * b[i]

        }


    }


    return answer

}

/**
 * Средняя
 *
 * Рассчитать значение многочлена при заданном x:
 * p(x) = p0 + p1*x + p2*x^2 + p3*x^3 + ... + pN*x^N.
 * Коэффициенты многочлена заданы списком p: (p0, p1, p2, p3, ..., pN).
 * Значение пустого многочлена равно 0.0 при любом x.
 */
fun polynom(p: List<Double>, x: Double): Double {

    var answer = 0.0

    if (p != listOf<Double>()) {


        answer += p[0]

        for (i in 1 until p.size) {

            answer += p[i] * Math.pow(x, i.toDouble())

        }


    }

    return answer


}

/**
 * Средняя
 *
 * В заданном списке list каждый элемент, кроме первого, заменить
 * суммой данного элемента и всех предыдущих.
 * Например: 1, 2, 3, 4 -> 1, 3, 6, 10.
 * Пустой список не следует изменять. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun accumulate(list: MutableList<Double>): MutableList<Double> {

    var amountOfPrevMem = 0.0

    if (list != listOf<Double>()) {


        for (i in 0 until list.size) {

            amountOfPrevMem += list[i]

            list[i] = amountOfPrevMem

        }

    }

    return list

}

/**
 * Средняя
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде списка множителей, например 75 -> (3, 5, 5).
 * Множители в списке должны располагаться по возрастанию.
 */
fun factorize(n: Int): List<Int> {
    var nForMember = n
    val answer = mutableListOf<Int>()
    var i = 2

    while (i <= nForMember) {

        if (nForMember % i == 0) {

            answer += i
            nForMember /= i

        } else {

            i++

        }

    }

    return answer.sorted()

}

/**
 * Сложная
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде строки, например 75 -> 3*5*5
 */
fun factorizeToString(n: Int): String = factorize(n).joinToString(separator = "*")

/**
 * Средняя
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием base > 1.
 * Результат перевода вернуть в виде списка цифр в base-ичной системе от старшей к младшей,
 * например: n = 100, base = 4 -> (1, 2, 1, 0) или n = 250, base = 14 -> (1, 3, 12)
 */
fun convert(n: Int, base: Int): List<Int> {

    var nForMember = n
    var list = listOf<Int>()

    if (n > 0) {

        while (nForMember > 0) {

            list += nForMember % base
            nForMember /= base

        }

    }
    else {

        list += 0

    }
    return list.reversed()

}

/**
 * Сложная
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием 1 < base < 37.
 * Результат перевода вернуть в виде строки, цифры более 9 представлять латинскими
 * строчными буквами: 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: n = 100, base = 4 -> 1210, n = 250, base = 14 -> 13c
 */
fun convertToString(n: Int, base: Int): String {

    val list = convert(n, base)
    var answerList = mutableListOf<String>()

    if (n > 0) {

        for (i in 0 until list.size) {

            if (list[i] > 9) {

                answerList.add(i, (87 + list[i]).toChar().toString())

            } else {

                answerList.add(i, list[i].toString())

            }

        }
    }
    else {

        answerList.add(0, "0")

    }


    return answerList.joinToString(separator = "")
}

/**
 * Средняя
 *
 * Перевести число, представленное списком цифр digits от старшей к младшей,
 * из системы счисления с основанием base в десятичную.
 * Например: digits = (1, 3, 12), base = 14 -> 250
 */
fun decimal(digits: List<Int>, base: Int): Int {


    var answer = 0.0
    val size = (digits.size - 1).toDouble()
    val baseDouble = base.toDouble()

    for (i in size.toInt() downTo 0) {

        answer += (digits[i] * Math.pow(baseDouble, (size - i)))

    }

    return answer.toInt()
}

/**
 * Сложная
 *
 * Перевести число, представленное цифровой строкой str,
 * из системы счисления с основанием base в десятичную.
 * Цифры более 9 представляются латинскими строчными буквами:
 * 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: str = "13c", base = 14 -> 250
 */
fun decimalFromString(str: String, base: Int): Int {

    var list = listOf<Int>()

    for (i in 0 until str.length) {

        if (str[i] in '0'..'9') {

            list += (str[i]).toInt() - 48

        }
        else {

            list += (str[i]).toInt() - 87

        }

    }

    return decimal(list, base)

}

/**
 * Сложная
 *
 * Перевести натуральное число n > 0 в римскую систему.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: 23 = XXIII, 44 = XLIV, 100 = C
 */
fun roman(n: Int): String {                             //Дорабатываю

    val containerRimNum = listOf("M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I")
    val containerArabNum = listOf("1000", "900", "500", "400", "100", "90", "50", "40", "10", "9", "5", "4", "1")
    var answer = ""

    val romanNumStr = n.toString()

    for (i in 0 until containerArabNum.size) {

        if (romanNumStr == containerArabNum[i]) {
            return containerRimNum[i]
        }
    }

    for (i in 0 until romanNumStr.length) {

        for (j in 0 until containerArabNum.size) {
            if ((romanNumStr[i].toDouble() * Math.pow(10.0, (romanNumStr.length - 1 - i).toDouble())).toString() == containerArabNum[j]) {

                answer += containerRimNum[j]
                break

            }
        }
    }

    return answer

}

/**
 * Очень сложная
 *
 * Записать заданное натуральное число 1..999999 прописью по-русски.
 * Например, 375 = "триста семьдесят пять",
 * 23964 = "двадцать три тысячи девятьсот шестьдесят четыре"
 */
fun russian(n: Int): String {

    val aloneNum = listOf("од", "дв", "три", "четыре", "пять", "шесть", "семь", "восемь", "деввять")
    val bigNum = listOf("тысяч", "миллион", "миллиард")
    val nStr = n.toString()

    for (i in 0 until nStr.length) {

        //

    }

    return ""

}