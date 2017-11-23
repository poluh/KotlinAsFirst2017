@file:Suppress("UNUSED_PARAMETER")

package lesson8.task1

import java.io.File


/**
 * Пример
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * Вывести его в выходной файл с именем outputName, выровняв по левому краю,
 * чтобы длина каждой строки не превосходила lineLength.
 * Слова в слишком длинных строках следует переносить на следующую строку.
 * Слишком короткие строки следует дополнять словами из следующей строки.
 * Пустые строки во входном файле обозначают конец абзаца,
 * их следует сохранить и в выходном файле
 */
fun alignFile(inputName: String, lineLength: Int, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    var currentLineLength = 0
    for (line in File(inputName).readLines()) {
        if (line.isEmpty()) {
            outputStream.newLine()
            if (currentLineLength > 0) {
                outputStream.newLine()
                currentLineLength = 0
            }
            continue
        }
        for (word in line.split(" ")) {
            if (currentLineLength > 0) {
                if (word.length + currentLineLength >= lineLength) {
                    outputStream.newLine()
                    currentLineLength = 0
                } else {
                    outputStream.write(" ")
                    currentLineLength++
                }
            }
            outputStream.write(word)
            currentLineLength += word.length
        }
    }
    outputStream.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * На вход подаётся список строк substrings.
 * Вернуть ассоциативный массив с числом вхождений каждой из строк в текст.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 */

fun String.findOccurrences(substrings: String): Int {

    var answer = 0
    var pos = 0

    if (indexOf(substrings) != -1) {
        while (true) {
            val foundPos = indexOf(substrings, pos)
            if (foundPos == -1) break

            answer++
            pos = foundPos + 1
        }
    }

    return answer

}

fun countSubstrings(inputName: String, substrings: List<String>): Map<String, Int> {

    val answer = mutableMapOf<String, Int>()
    var allText = ""

    for (line in File(inputName).readLines()) {
        allText += line.toLowerCase() + " "
    }

    for (substr in substrings) {
        answer[substr] = allText.findOccurrences(substr.toLowerCase())
    }

    return answer
}


/**
 * Средняя
 *
 * В русском языке, как правило, после букв Ж, Ч, Ш, Щ пишется И, А, У, а не Ы, Я, Ю.
 * Во входном файле с именем inputName содержится некоторый текст на русском языке.
 * Проверить текст во входном файле на соблюдение данного правила и вывести в выходной
 * файл outputName текст с исправленными ошибками.
 *
 * Регистр заменённых букв следует сохранять.
 *
 * Исключения (жюри, брошюра, парашют) в рамках данного задания обрабатывать не нужно
 *
 */
fun sibilants(inputName: String, outputName: String) {

    File(outputName).bufferedWriter().use {
        val containerLitter = mapOf("Ы" to "И", "ы" to "и", "Я" to "А", "я" to "а", "Ю" to "У", "ю" to "у")
        for ((index, line) in File(inputName).readLines().withIndex()) {

            if (line.length <= 1) {
                it.write(line)
            } else {
                it.append(line[0])

                for (symbol in 1 until line.length)
                    if ((line[symbol] in "ЫЯЮыяю") && (line[symbol - 1] in "ЖЧШЩжчшщ")) {
                        it.append(containerLitter[line[symbol].toString()])
                    } else {
                        it.append(line[symbol])
                    }
                if (index != File(inputName).readLines().size - 1)
                    it.newLine()
            }
        }
    }
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по центру
 * относительно самой длинной строки.
 *
 * Выравнивание следует производить путём добавления пробелов в начало строки.
 *
 *
 * Следующие правила должны быть выполнены:
 * 1) Пробелы в начале и в конце всех строк не следует сохранять.
 * 2) В случае невозможности выравнивания строго по центру, строка должна быть сдвинута в ЛЕВУЮ сторону
 * 3) Пустые строки не являются особым случаем, их тоже следует выравнивать
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых)
 *
 */

fun Int.createGaps(): String {

    var answer = ""
    for (i in 0..this) {
        answer += " "
    }

    return answer
}

fun longestString(inputName: String): Int {

    var max = 0
    for (line in File(inputName).readLines()) {
        max = Math.max(Regex("\\s+").replace(line, " ").length, max)
    }

    return max
}

fun centerFile(inputName: String, outputName: String) {

    val max = longestString(inputName)

    File(outputName).bufferedWriter().use {
        for (line in File(inputName).readLines()) {
            it.append(((max - line.trim().length) / 2 - 1).createGaps())
            it.append(line.trim())
            it.newLine()
        }
    }

}

/**
 * Сложная
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по левому и правому краям относительно
 * самой длинной строки.
 * Выравнивание производить, вставляя дополнительные пробелы между словами: равномерно по всей строке
 *
 * Слова внутри строки отделяются друг от друга одним или более пробелом.
 *
 * Следующие правила должны быть выполнены:
 * 1) Каждая строка входного и выходного файла не должна начинаться или заканчиваться пробелом.
 * 2) Пустые строки или строки из пробелов трансформируются в пустые строки без пробелов.
 * 3) Строки из одного слова выводятся без пробелов.
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых).
 *
 * Равномерность определяется следующими формальными правилами:
 * 5) Число пробелов между каждыми двумя парами соседних слов не должно отличаться более, чем на 1.
 * 6) Число пробелов между более левой парой соседних слов должно быть больше или равно числу пробелов
 *    между более правой парой соседних слов.
 *
 * Следует учесть, что входной файл может содержать последовательности из нескольких пробелов  между слвоами. Такие
 * последовательности следует учитывать при выравнивании и при необходимости избавляться от лишних пробелов.
 * Из этого следуют следующие правила:
 * 7) В самой длинной строке каждая пара соседних слов должна быть отделена В ТОЧНОСТИ одним пробелом
 * 8) Если входной файл удовлетворяет требованиям 1-7, то он должен быть в точности идентичен выходному файлу
 */
fun alignFileByWidth(inputName: String, outputName: String) {
TODO()
/*    val maxLength = longestString(inputName)

    File(outputName).bufferedWriter().use {
        for (line in File(inputName).readLines()) {
            val correctLine = Regex("\\s+").replace(line, " ")
            var newLine = ""
            var i = 0
            if (line.isEmpty()) {
                while (newLine.trim().length <= maxLength) {
                    for (word in correctLine.split(" ")) {
                        newLine += word + i.createGaps()
                    }
                    if (newLine.trim().length >= maxLength) break else newLine = ""
                    i++
                }
            }
            it.write(newLine.trim() + "\n")
        }
    }*/
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * Вернуть ассоциативный массив, содержащий 20 наиболее часто встречающихся слов с их количеством.
 * Если в тексте менее 20 различных слов, вернуть все слова.
 *
 * Словом считается непрерывная последовательность из букв (кириллических,
 * либо латинских, без знаков препинания и цифр).
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 * Ключи в ассоциативном массиве должны быть в нижнем регистре.
 *
 */

fun Map<String, Int>.max(words: List<String>): Pair<String, Int> {
    var max = 0
    var keyWord = ""
    for (word in words) {
        if (this[word]!! > max) {
            max = this[word]!!
            keyWord = word
        }
    }
    return Pair(keyWord, max)
}

fun MutableMap<String, Int>.sorted(words: List<String>): Map<String, Int> {

    val map = mutableMapOf<String, Int>()

    for (i in 0..19) {
        val max = this.max(words)
        map[max.first] = max.second
        this[max.first] = -1
    }
    return map
}

fun top20Words(inputName: String): Map<String, Int> {

    val containerWords = mutableListOf<String>()
    val map = mutableMapOf<String, Int>()

    for (line in File(inputName).readLines()) {
        for (wordDamp in line.split(Regex("\\s+"))) {
            val word = wordDamp.toLowerCase()
            if (word.matches(Regex("[а-я]+"))) {

                if (map[word] == null) {
                    containerWords += word
                    map[word] = 1
                } else {
                    val plus = map[word]!! + 1
                    map[word] = plus
                }
            }
        }
    }

    return map.sorted(containerWords)

}

/**
 * Средняя
 *
 * Реализовать транслитерацию текста из входного файла в выходной файл посредством динамически задаваемых правил.

 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * В ассоциативном массиве dictionary содержится словарь, в котором некоторым символам
 * ставится в соответствие строчка из символов, например
 * mapOf('з' to "zz", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "yy", '!' to "!!!")
 *
 * Необходимо вывести в итоговый файл с именем outputName
 * содержимое текста с заменой всех символов из словаря на соответствующие им строки.
 *
 * При этом регистр символов в словаре должен игнорироваться,
 * но при выводе символ в верхнем регистре отображается в строку, начинающуюся с символа в верхнем регистре.
 *
 * Пример.
 * Входной текст: Здравствуй, мир!
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun transliterate(inputName: String, dictionary: Map<Char, String>, outputName: String) {

    var lines = ""
    val fileInput = File(inputName).readLines()


    for ((indexLine, line) in fileInput.withIndex()) {
        for (index in 0 until line.length) {
            lines += when {
                dictionary[line[index].toLowerCase()] != null -> dictionary[line[index].toLowerCase()]
                dictionary[line[index].toUpperCase()] != null -> dictionary[line[index].toUpperCase()]
                else -> line[index]
            }
        }
        if (indexLine != fileInput.size - 1) {
            lines += "\n"
        }
    }

    File(outputName).bufferedWriter().use {
        for (index in 0 until lines.length) {
            if (index == 0) {
                it.append(lines[index].toUpperCase())
            } else {
                it.append(lines[index])
            }
        }
    }

}

/**
 * Средняя
 *
 * Во входном файле с именем inputName имеется словарь с одним словом в каждой строчке.
 * Выбрать из данного словаря наиболее длинное слово,
 * в котором все буквы разные, например: Неряшливость, Четырёхдюймовка.
 * Вывести его в выходной файл с именем outputName.
 * Если во входном файле имеется несколько слов с одинаковой длиной, в которых все буквы разные,
 * в выходной файл следует вывести их все через запятую.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 * Пример входного файла:
 * Карминовый
 * Боязливый
 * Некрасивый
 * Остроумный
 * БелогЛазый
 * ФиолетОвый

 * Соответствующий выходной файл:
 * Карминовый, Некрасивый
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */

fun String.ifDifferentLetters(): Boolean {
    val line = this.toLowerCase()
    val containerLetter = mutableListOf<Char>()
    for (letter in line) {
        if (containerLetter.indexOf(letter) == -1) {
            containerLetter += letter
        }
    }
    return (containerLetter.size == line.length)
}

fun chooseLongestChaoticWord(inputName: String, outputName: String) {

    val containerWord = mutableListOf<String>()
    var theMaxLength = File(inputName).readLines()[0].length
    for (word in File(inputName).readLines()) {
        if (word.ifDifferentLetters()) {
            if (word.length >= theMaxLength) {
                theMaxLength = word.length
                containerWord += word
            }
        }
    }

    File(outputName).bufferedWriter().use {
        if (containerWord.size > 1) {
            for ((index, word) in containerWord.withIndex()) {
                it.append(word)
                if (index != containerWord.size - 1) {
                    it.append(", ")
                }
            }
        } else {
            it.append(containerWord[0])
        }
    }
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе элементы текстовой разметки следующих типов:
 * - *текст в курсивном начертании* -- курсив
 * - **текст в полужирном начертании** -- полужирный
 * - ~~зачёркнутый текст~~ -- зачёркивание
 *
 * Следует вывести в выходной файл этот же текст в формате HTML:
 * - <i>текст в курсивном начертании</i>
 * - <b>текст в полужирном начертании</b>
 * - <s>зачёркнутый текст</s>
 *
 * Кроме того, все абзацы исходного текста, отделённые друг от друга пустыми строками, следует обернуть в теги <p>...</p>,
 * а весь текст целиком в теги <html><body>...</body></html>.
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 * Отдельно следует заметить, что открывающая последовательность из трёх звёздочек (***) должна трактоваться как "<b><i>"
 * и никак иначе.
 *
 * Пример входного файла:
Lorem ipsum *dolor sit amet*, consectetur **adipiscing** elit.
Vestibulum lobortis, ~~Est vehicula rutrum *suscipit*~~, ipsum ~~lib~~ero *placerat **tortor***,

Suspendisse ~~et elit in enim tempus iaculis~~.
 *
 * Соответствующий выходной файл:
<html>
<body>
<p>
Lorem ipsum <i>dolor sit amet</i>, consectetur <b>adipiscing</b> elit.
Vestibulum lobortis. <s>Est vehicula rutrum <i>suscipit</i></s>, ipsum <s>lib</s>ero <i>placerat <b>tortor</b></i>.
</p>
<p>
Suspendisse <s>et elit in enim tempus iaculis</s>.
</p>
</body>
</html>
 *
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlSimple(inputName: String, outputName: String) {

    val containerTags =
            mapOf("*open" to "<i>", "*close" to "</i>", "**open" to "<b>", "**close" to "</b>",
                    "~~open" to "<s>", "~~close" to "</s>")

}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе набор вложенных друг в друга списков.
 * Списки бывают двух типов: нумерованные и ненумерованные.
 *
 * Каждый элемент ненумерованного списка начинается с новой строки и символа '*', каждый элемент нумерованного списка --
 * с новой строки, числа и точки. Каждый элемент вложенного списка начинается с отступа из пробелов, на 4 пробела большего,
 * чем список-родитель. Максимально глубина вложенности списков может достигать 6. "Верхние" списки файла начинются
 * прямо с начала строки.
 *
 * Следует вывести этот же текст в выходной файл в формате HTML:
 * Нумерованный список:
 * <ol>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ol>
 *
 * Ненумерованный список:
 * <ul>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ul>
 *
 * Кроме того, весь текст целиком следует обернуть в теги <html><body>...</body></html>
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 *
 * Пример входного файла:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
 * Утка по-пекински
 * Утка
 * Соус
 * Салат Оливье
1. Мясо
 * Или колбаса
2. Майонез
3. Картофель
4. Что-то там ещё
 * Помидоры
 * Фрукты
1. Бананы
23. Яблоки
1. Красные
2. Зелёные
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 *
 *
 * Соответствующий выходной файл:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
<html>
<body>
<ul>
<li>
Утка по-пекински
<ul>
<li>Утка</li>
<li>Соус</li>
</ul>
</li>
<li>
Салат Оливье
<ol>
<li>Мясо
<ul>
<li>
Или колбаса
</li>
</ul>
</li>
<li>Майонез</li>
<li>Картофель</li>
<li>Что-то там ещё</li>
</ol>
</li>
<li>Помидоры</li>
<li>
Яблоки
<ol>
<li>Красные</li>
<li>Зелёные</li>
</ol>
</li>
</ul>
</body>
</html>
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlLists(inputName: String, outputName: String) {
    TODO()
}

/**
 * Очень сложная
 *
 * Реализовать преобразования из двух предыдущих задач одновременно над одним и тем же файлом.
 * Следует помнить, что:
 * - Списки, отделённые друг от друга пустой строкой, являются разными и должны оказаться в разных параграфах выходного файла.
 *
 */
fun markdownToHtml(inputName: String, outputName: String) {
    TODO()
}

/**
 * Средняя
 *
 * Вывести в выходной файл процесс умножения столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 111):
    19935
 *    111
--------
   19935
+ 19935
+19935
--------
2212785
 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 * Нули в множителе обрабатывать так же, как и остальные цифры:
   235
 *  10
-----
0
+235
-----
2350
 *
 */

fun Int.createDelimiter(): String{
    var answer = ""
    for (i in 1..this) {
        answer += "-"
    }

    return answer
}

fun printMultiplicationProcess(lhv: Int, rhv: Int, outputName: String) {

    val delimiter = (lhv.toString().length + rhv.toString().length).createDelimiter()
    var secondFactor = rhv

    File(outputName).bufferedWriter().use {
        it.append((rhv.toString().length - 1).createGaps() + "$lhv\n")
        it.append("*" + (delimiter.length - rhv.toString().length - 2).createGaps() + "$rhv\n")
        it.append(delimiter + "\n")

        for (i in 1..(secondFactor.toString().length)) {

            val space = if (i != 1) {
                it.append("\n+")
                (secondFactor.toString().length - 2).createGaps()
            } else {
                (secondFactor.toString().length - 1).createGaps()
            }

            it.append(space + "${lhv * (secondFactor % 10)}")
            secondFactor /= 10
        }

        it.append("\n" + delimiter + "\n")
        it.append((delimiter.length - (lhv * rhv).toString().length - 1).createGaps() + "${lhv * rhv}")


    }

}


/**
 * Очень сложная
 *
 * Вывести в выходной файл процесс деления столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 22):
19935 | 22
-198     906
----
13
-0
--
135
-132
----
3

 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 *
 */
fun printDivisionProcess(lhv: Int, rhv: Int, outputName: String) {
    TODO()
}

