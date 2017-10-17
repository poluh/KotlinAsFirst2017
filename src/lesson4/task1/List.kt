@file:Suppress("UNUSED_PARAMETER")

package lesson4.task1

import lesson1.task1.discriminant
import lesson1.task1.sqr
import lesson3.task1.isPrime

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
fun abs(v: List<Double>): Double = Math.sqrt(v.map { it * it }.sum())

/**
 * Простая
 *
 * Рассчитать среднее арифметическое элементов списка list. Вернуть 0.0, если список пуст
 */
fun mean(list: List<Double>): Double = if (list.isEmpty()) 0.0 else list.sum() / list.size.toDouble()

/**
 * Средняя
 *
 * Центрировать заданный список list, уменьшив каждый элемент на среднее арифметическое всех элементов.
 * Если список пуст, не делать ничего. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun center(list: MutableList<Double>): MutableList<Double> {

    val averageAllElement = mean(list)

    (0 until list.size).forEach { i -> list[i] -= averageAllElement }

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

    for (i in 0 until a.size) {

        answer += a[i] * b[i]

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

    for (i in 0 until list.size) {

        amountOfPrevMem += list[i]

        list[i] = amountOfPrevMem

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
    var i = 1

    if (isPrime(nForMember)) return answer + nForMember

    while (i <= Math.sqrt(nForMember.toDouble()).toInt()) {

        i++

        if (isPrime(nForMember)) {
            answer += nForMember
            break
        } else {

            if (nForMember % i == 0) {
                nForMember /= i
                answer += i
                i = 1
            }

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

    if (nForMember == 0) return listOf(0)

    while (nForMember > 0) {

        list += nForMember % base
        nForMember /= base

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
    val answerList = mutableListOf<String>()

    if (n > 0) {

        for (i in 0 until list.size) {

            if (list[i] > 9) {

                answerList.add(i, (87 + list[i]).toChar().toString())

            } else {

                answerList.add(i, list[i].toString())

            }

        }
    } else {

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


fun convertFromLetters(str: String, i: Int): Int {

    val constForSmallCharSymbol = 48
    val constForBigCharSymbol = 87

    return if (str[i] in '0'..'9') {
        str[i].toInt() - constForBigCharSymbol
    } else {
        str[i].toInt() - constForSmallCharSymbol
    }

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
        list += str[i].toInt() - 48
    }

    return decimal(list, base)

}


fun createIndicesRomanNum(str: String, len: Int): List<Int> { //все еще дорабатываю

    var list = listOf<Int>()
    val containerArabNum = listOf(1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000)
    var n = listOf<Int>()

    for (i in 0 until str.length) {

        n += (str[str.length - i].toInt() * Math.pow(10.0, i.toDouble())).toInt()

        for (j in 0 until containerArabNum.size) {

            if (n[i] == containerArabNum[j]) {

                list += j

            } else {

                for (k in (1 * Math.pow(10.0, i.toDouble()).toInt())..(4 * Math.pow(10.0, i.toDouble())).toInt()) {

                    if (n[i] - k == containerArabNum[j]) {

                        list += j

                    }

                }

            }

        }

    }


    return list
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

    val containerRimNum = listOf("I", "IV", "V", "IX", "X", "XL", "L", "XC", "C", "CD", "D", "CM", "M")
    val containerArabNum = listOf(1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000)
    var nForMem = n
    var i = 12
    var answer = ""

    while (nForMem > 0) {
            while (nForMem >= containerArabNum[i]) {
                nForMem -= containerArabNum[i]
                answer += containerRimNum[i]
            }
              i--
         }

    return answer

}


fun translationOfTripleOfNum(n: Int, len: Int, order: Int): String {

    val containerOne = listOf("", "один ", "два ", "три ", "четыре ", "пять ", "шесть ", "семь ", "восемь ",
            "девять ", "десять ", "одиннадцать ", "двенадцать ", "тринадцать ", "четырнадцать ", "пятнадцать ",
            "шестнадцать ", "семнадцать ", "восемнадцать ", "девятнадцать ", "одна ", "две ")
    val containerTen = listOf("", "", "двадцать ", "тридцать ", "сорок ", "пятьдесят ", "шестьдесят ",
            "семьдесят ", "восемьдесят ", "девяносто ")
    val containerHundred = listOf("", "сто ", "двести ", "триста ", "четыреста ", "пятьсот ", "шестьсот ",
            "семьсот ", "восемьсот ", "девятьсот ")
    var doping = 0

    when (len) {
        1 -> {

            if ((order == 2) && (n % 10 in 1..2)) doping = 19
            return containerOne[n + doping]

        }
        2 -> {

            when {
                n in 10..19 -> return containerOne[n]
                (order == 2) && (n % 10 in 1..2) -> doping = 19
            }

            return containerTen[n / 10] + containerOne[n % 10 + doping]

        }
        3 -> {

            return when {
                n % 100 == 0 -> containerHundred[n / 100]
                else -> containerHundred[n / 100] + translationOfTripleOfNum(n % 100, 2, order)   //РЕКУРШЕН
            }
        }
    }

    return ""

}

/**
 * Очень сложная
 *
 * Записать заданное натуральное число 1..999999 прописью по-русски.
 * Например, 375 = "триста семьдесят пять",
 * 23964 = "двадцать три тысячи девятьсот шестьдесят четыре"
 */
fun russian(n: Int): String {

    val containerThousand = listOf("тысяча ", "тысячи ", "тысяч ")
    val order: Int
    val lenN = n.toString().length
    var answer: String

    order = when (lenN) {
        in 1..3 -> 1
        in 4..6 -> 2
        else -> throw IllegalAccessError("This format is not supported (for now)")
    }

    if (order == 1) {
        return translationOfTripleOfNum(n, lenN, order).trim()
    } else {

        val nStr = n.toString()
        val partOneNum = nStr.substring(0, lenN - 3).toInt()
        val partTwoNum = nStr.substring(lenN - 3, lenN).toInt()

        answer = translationOfTripleOfNum(partOneNum, partOneNum.toString().length, order)
        answer += when (partOneNum.toString()[partOneNum.toString().length - 1]) {
            '1' -> containerThousand[0]
            in '2'..'4' -> containerThousand[1]
            else -> containerThousand[2]
        }
        answer += translationOfTripleOfNum(partTwoNum, partTwoNum.toString().length, order - 1)
    }

    return answer.trim()

}