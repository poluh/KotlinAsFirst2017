@file:Suppress("UNUSED_PARAMETER")

package lesson3.task1

fun seq(n: Int): Int {

    var answer = 0

    for (i in 1..n)
        answer += i * i * (Math.pow(10.0, digitNumber(i*i).toDouble())).toInt()

    return answer

}

/**
 * Пример
 *
 * Вычисление факториала
 */
fun factorial(n: Int): Double {
    var result = 1.0
    for (i in 1..n) {
        result = result * i // Please do not fix in master
    }
    return result
}

/**
 * Пример
 *
 * Проверка числа на простоту -- результат true, если число простое
 */
fun isPrime(n: Int): Boolean {
    if (n < 2) return false
    for (m in 2..Math.sqrt(n.toDouble()).toInt()) {
        if (n % m == 0) return false
    }
    return true
}

/**
 * Пример
 *
 * Проверка числа на совершенность -- результат true, если число совершенное
 */
fun isPerfect(n: Int): Boolean {
    var sum = 1
    for (m in 2..n / 2) {
        if (n % m > 0) continue
        sum += m
        if (sum > n) break
    }
    return sum == n
}

/**
 * Пример
 *
 * Найти число вхождений цифры m в число n
 */
fun digitCountInNumber(n: Int, m: Int): Int =
        when {
            n == m -> 1
            n < 10 -> 0
            else -> digitCountInNumber(n / 10, m) + digitCountInNumber(n % 10, m)
        }

/**
 * Тривиальная
 *
 * Найти количество цифр в заданном числе n.
 * Например, число 1 содержит 1 цифру, 456 -- 3 цифры, 65536 -- 5 цифр.
 */
fun digitNumber(n: Int): Int {

    var buf: Int = Math.abs(n)
    var i: Int = 0

    if (buf == 0) {
        return 1
    }
    while (buf > 0) {

        i += 1
        buf /= 10


    }

    return i
}

/**
 * Простая
 *
 * Найти число Фибоначчи из ряда 1, 1, 2, 3, 5, 8, 13, 21, ... с номером n.
 * Ряд Фибоначчи определён следующим образом: fib(1) = 1, fib(2) = 1, fib(n+2) = fib(n) + fib(n+1)
 */
fun fib(n: Int): Int {


    var fibNumFirst = 1
    var fibNumSecond = 1
    var fibSum = 1

    var i = 2

    while (i < n) {

        fibSum = fibNumFirst + fibNumSecond
        fibNumFirst = fibNumSecond
        fibNumSecond = fibSum

        i += 1

    }

    return fibSum

}

/**
 * Простая
 *
 * Для заданных чисел m и n найти наименьшее общее кратное, то есть,
 * минимальное число k, которое делится и на m и на n без остатка
 */
fun lcm(m: Int, n: Int): Int {

    var numM = m
    var numN = n
    val prod = n * m

    if (numM == numN) {
        return numM
    } else {

        while (numM != numN) {
            if (numM > numN) {
                numM -= numN
            } else {
                numN -= numM
            }
        }

        return prod / numM
    }
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти минимальный делитель, превышающий 1
 */
fun minDivisor(n: Int): Int {

    var total = 0

    for (i in 2..n) {

        if ((n % i) == 0) {
            total = i
            break
        }
    }

    return total

}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти максимальный делитель, меньший n
 */
fun maxDivisor(n: Int): Int {

    var total = 0

    for (i in 1..(n - 1)) {

        if ((n % i) == 0) {
            total = i
        }
    }

    return total
}

/**
 * Простая
 *
 * Определить, являются ли два заданных числа m и n взаимно простыми.
 * Взаимно простые числа не имеют общих делителей, кроме 1.
 * Например, 25 и 49 взаимно простые, а 6 и 8 -- нет.
 */
fun isCoPrime(m: Int, n: Int): Boolean {

    var numN = n
    var numM = m

    while ((numM != 0) && (numN != 0)) {

        if (numM > numN) {

            numM %= numN

        }
        else {

            numN %= numM

        }

    }

    return ((numM + numN) == 1)

}


/**
 * Простая
 *
 * Для заданных чисел m и n, m <= n, определить, имеется ли хотя бы один точный квадрат между m и n,
 * то есть, существует ли такое целое k, что m <= k*k <= n.
 * Например, для интервала 21..28 21 <= 5*5 <= 28, а для интервала 51..61 квадрата не существует.
 */
fun squareBetweenExists(m: Int, n: Int): Boolean {


    for (i in m..n) {

        if ((Math.sqrt(i.toDouble()) % 1) == 0.0) {
            return true
        }

    }

    return false

}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * sin(x) = x - x^3 / 3! + x^5 / 5! - x^7 / 7! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun sin(x: Double, eps: Double): Double {        //Не понял, что сделать с cos и sin, выдает NaN при тестировании, идей нет

    var i = 1
    var sinX = 0.0
    var rememberNum = 1.0

    while (Math.abs(rememberNum) > eps) {



        rememberNum = Math.pow(x, i * 2.0 + 1) / factorial(i * 2 + 1)

        if ((i % 2) == 1) {
            sinX -= rememberNum
        } else {
            sinX += rememberNum
        }

        i++

    }
    return sinX

}


/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * cos(x) = 1 - x^2 / 2! + x^4 / 4! - x^6 / 6! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun cos(x: Double, eps: Double): Double {


    var i = 0
    var cosX = 1.0
    var rememberNum = x


    while (Math.abs(rememberNum) > eps) {

        i++

        rememberNum = Math.pow(x, i * 2.0) / factorial(i * 2)

        if ((i % 2) == 1) {
            cosX = cosX - rememberNum
        } else {
            cosX = cosX + rememberNum
        }

    }
    return cosX

}

/**
 * Средняя
 *
 * Поменять порядок цифр заданного числа n на обратный: 13478 -> 87431.
 * Не использовать строки при решении задачи.
 */
fun revert(n: Int): Int {

    var total = 0
    var nForChange = n

    while (nForChange > 0) {

        total = total * 10 + nForChange % 10

        nForChange /= 10

    }

    return total

}

/**
 * Средняя
 *
 * Проверить, является ли заданное число n палиндромом:
 * первая цифра равна последней, вторая -- предпоследней и так далее.
 * 15751 -- палиндром, 3653 -- нет.
 */
fun isPalindrome(n: Int): Boolean = (revert(n) == n)


/**
 * Средняя
 *
 * Для заданного числа n определить, содержит ли оно различающиеся цифры.
 * Например, 54 и 323 состоят из разных цифр, а 111 и 0 из одинаковых.
 */
fun hasDifferentDigits(n: Int): Boolean = !(digitCountInNumber(n, (n % 10)) == digitNumber(n))


/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из квадратов целых чисел:
 * 149162536496481100121144...
 * Например, 2-я цифра равна 4, 7-я 5, 12-я 6.
 */
fun squareSequenceDigit(n: Int): Int = TODO()

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из чисел Фибоначчи (см. функцию fib выше):
 * 1123581321345589144...
 * Например, 2-я цифра равна 1, 9-я 2, 14-я 5.
 */
fun fibSequenceDigit(n: Int): Int = TODO()
