@file:Suppress("UNUSED_PARAMETER")

package lesson5.task1

import lesson1.task1.accountInThreeYears
import java.lang.IllegalArgumentException

fun maxNumIntFromList(list: List<Int>): Int {

    var answer = 0

    for (i in 0 until list.size) {

        if (list[i] > answer) {

            answer = list[i]

        }

    }

    return answer

}

fun maxIndexDoubleFromList(list: List<Double>): Int {

    var answer = 0

    for (i in 0 until list.size) {

        if (list[i] > answer) {

            answer = i - 1

        }

    }

    return if (answer >= 0) answer else 0

}

fun findIndex(index: Int, str: String): Int {

    var serialNum = 0

    for (i in 0 until str.length) {

        if (str[i] == ' ') {
            serialNum++
            if (serialNum == index) {
                return i + 1
            }
        }

    }

    return -1

}

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
fun main(args: Array<String>) {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        } else {
            println("Прошло секунд с начала суток: $seconds")
        }
    } else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
}

/**
 * Средняя
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку
 */
fun dateStrToDigit(str: String): String {

    val customStr = str.replace("\u0020{2,}", "\u0020").split(" ")
    val monthsArr =
            listOf<String>("января", "февраля", "марта", "апреля", "мая", "июня", "июля",
                    "августа", "сентября", "октября", "ноября", "декабря")
    val day: String
    val year: String
    var month = ""

    try {

        if (customStr.size == 3) {
            if ((customStr[0].toInt() in 1..31) && (customStr[2].toInt() in Int.MIN_VALUE..Int.MAX_VALUE)) {

                day = customStr[0]
                year = customStr[2]

                for (i in 0..11) {

                    if (customStr[1] == monthsArr[i]) {

                        month = (i + 1).toString()
                        break

                    } else {

                        if (i == 11) return ""

                    }

                }

            } else return ""
        } else return ""
    }
    catch (e: NumberFormatException) {
        return ""
    }
    return String.format("%02d.%02d.%d", day.toInt(), month.toInt(), year.toInt())

}

/**
 * Средняя
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 */
fun dateDigitToStr(digital: String): String {

    val customStr = digital.split(".")

    val monthsArr =
            listOf<String>("января", "февраля", "марта", "апреля", "мая", "июня", "июля",
                    "августа", "сентября", "октября", "ноября", "декабря")

    var day: String
    val month: String
    val year: String

    try {

        if (customStr.size == 3) {

            day = customStr[0]

            if (day.toInt() in 1..9) {

                day = day[1].toString()

            }
            if ((day.toInt() in 1..31) && (customStr[1].toInt() in 1..12)) {

                month = monthsArr[customStr[1].toInt() - 1]
                year = customStr[2]

            } else {

                return ""

            }

        } else {

            return ""

        }

    } catch (e: NumberFormatException) {
        return ""
    }

    return String.format("%s %s %s", day, month, year)

}

/**
 * Средняя
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -98 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку
 */
fun flattenPhoneNumber(phone: String): String {

    var phoneTrueStr = "+"
    val containerTrueSimbol = listOf<Char>('(', ')', '-', ' ')

    for (i in 0 until phone.length) {

        if (phone[0] != '+') {

            if ((phone[i] !in containerTrueSimbol) && (phone[i] !in '0'..'9')) {

                return ""

            }

        }

        if (phone[i] in '0'..'9') {

            phoneTrueStr += phone[i].toString()

        }

    }

    if (phoneTrueStr.length in 0..1) {
        return ""
    }

    return phoneTrueStr

}

/**
 * Средняя
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int {

    val containerTrueSymbol = listOf(" ", "%", "-")

    if (jumps != "") {

        for (i in 0 until jumps.length) {

            if ((jumps[i] !in '0'..'9') && (jumps[i].toString() !in containerTrueSymbol)) {

                return -1

            }

        }

    }

    val partStr = jumps.replace("\u0020{2,}", "\u0020").split(" ")
    var preliminaryResult = listOf<Int>()

    for (i in 0 until partStr.size) {

        if (partStr[i] !in containerTrueSymbol) {

            preliminaryResult += partStr[i].toInt()

        }

    }

    return if (preliminaryResult != listOf<Int>()) maxNumIntFromList(preliminaryResult) else -1

}

/**
 * Сложная
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки вернуть -1.
 */
fun bestHighJump(jumps: String): Int {


    val containerTrueSymbol = listOf(" ", "%", "-", "+")

    if (jumps != "") {

        for (i in 0 until jumps.length) {

            if ((jumps[i] !in '0'..'9') && (jumps[i].toString() !in containerTrueSymbol)) {

                return -1

            }

        }

    }

    val partStr = jumps.replace("\u0020{2,}", "\u0020").split(' ')
    var preliminaryResult = listOf<Int>()

    for (i in 0 until partStr.size) {

        if ((partStr[i] !in containerTrueSymbol) && (partStr[i + 1] == "+")) {

            preliminaryResult += partStr[i].toInt()

        }

    }

    return if (preliminaryResult != listOf<Int>()) maxNumIntFromList(preliminaryResult) else -1

}

/**
 * Сложная
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int {

    val containerTrueOperations = listOf("+", "-", " ")
    var answer = 0
    var i = 0

    if (!expression.isEmpty()) {

        for (i in 0 until expression.length) {

            if ((expression[i].toString() !in containerTrueOperations) && (expression[i] !in '0'..'9')) {

                throw IllegalArgumentException("Error > Invalid string format")

            }

        }

    } else throw IllegalArgumentException("Error > Empty string")


    val containerPart = expression.replace("\u0020{2,}", "\u0020").split(" ")

    if (expression.indexOf(' ') == -1) {
        return expression.toInt()
    }

    if (containerPart.size % 2 == 0) {
        throw IllegalArgumentException("Error > Invalid format imput")
    }

    while (i < containerPart.size - 2) {

        if (i == 0) {
            answer = containerPart[0].toInt()
        }


        val operator = containerPart[i + 1]
        val tern = containerPart[i + 2].toInt()

        when (operator) {
            "+" -> answer += tern
            "-" -> answer -= tern
            else -> throw IllegalArgumentException("Error > Unknow operator")
        }


        i += 2

    }

    return answer
}

/**
 * Сложная
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int {

    val conteinerPartStr = str.toLowerCase().replace("\u0020{2,}", "\u0020").split(" ")

    for (i in 0 until conteinerPartStr.size - 1) {

        if (conteinerPartStr[i] == conteinerPartStr[i + 1]) {
            return findIndex(i, str)
        }

    }

    return -1
}

/**
 * Сложная
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62.5; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть положительными
 */
fun mostExpensive(description: String): String {

    var allPrices = listOf<Double>()
    var allNameGoods = listOf<String>()

    if (description == "") {
        return description
    }

    val dampsPartStr = description.replace("\u0020{2,}", "\u0020").split("; ")

    if (dampsPartStr.isEmpty()) {
        return ""
    }

    for (i in 0 until dampsPartStr.size) {

        val partOfdampStr = dampsPartStr[i].split(" ")

        if (partOfdampStr.size == 2) {

            allPrices += partOfdampStr[1].toDouble()
            allNameGoods += partOfdampStr[0]

        } else {
            return ""
        }

    }

    if (allPrices.isEmpty()) {
        return ""
    } else {
        return allNameGoods[maxIndexDoubleFromList(allPrices)]
    }


}



/**
 * Сложная
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
fun fromRoman(roman: String): Int {

    val containerRimNum = listOf("M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I")
    val containerArabNum = listOf(1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1)

    var answer = 0

    if (roman != "") {

        for (i in 0 until roman.length) {

            if (roman[i].toString() !in containerRimNum) {
                return -1
            }

        }

    }
    else {
        return -1
    }

    val containerPart = roman.split("")

    for (i in 0 until containerPart.size) {
        for (j in 0 until containerRimNum.size) {
            if ((i != containerPart.size - 1) && (containerPart[i] + containerPart[i + 1] == containerRimNum[j])) {
                answer += containerArabNum[j]
            }

        }

    }

    /*
    -------
     */

    return answer
    
}

/**
 * Очень сложная
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> = TODO()
