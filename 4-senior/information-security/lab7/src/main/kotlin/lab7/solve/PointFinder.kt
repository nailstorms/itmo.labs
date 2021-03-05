package lab7.solve

import lab7.entity.Equation
import lab7.entity.Point

/**
 * Класс, реализующий функциональность по расчету суммы точек эллиптической кривой.
 * @property equation: lab7.entity.Equation - параметры уравнения эллиптической кривой над полем Галуа GF(p)
 */
class PointFinder(val equation: Equation) {

    /**
     * Метод поиска суммы двух точек на эллиптической кривой.
     *
     * Для расчета суммы были использованы теоретические формулы для p != 2:
     * x_3 = lam^2 - a - x_1 - x_2
     * y_3 = lam * (x_3 - x_1) - y_1
     *
     * @param point1: lab7.entity.Point - сущность первой точки
     * @param point2: lab7.entity.Point - сущность второй точки
     * @return Point - сущность результирующей точки, полученной в результате суммы переданных точек
     */
    fun calcSum(point1: Point, point2: Point): Point {
        val lambda = calcLambda(point1, point2)
        var temp : Int

        // если вычисленное значение получится отрицательным, необходимо привести его к положительному
        temp = lambda * lambda - equation.a - point1.x - point2.x
        val newX = if (temp < 0) (equation.modulo + temp % equation.modulo) else (temp % equation.modulo)

        temp = lambda * (point1.x - newX) - point1.y
        val newY = if (temp < 0) (equation.modulo + temp % equation.modulo) else (temp % equation.modulo)
        return Point(newX, newY)
    }

    /**
     * Метод поиска наклона прямой, проходящей через две точки.
     *
     * Для расчета суммы были использованы теоретические формулы для p != 2:
     * P1 = P2: lam = (3*x_1^2 + 2*a*x_1 + b)/(2*y_1)
     * P1 != P2: lam = (y_2 - y_1)/(x_2 - x_1)
     *
     * @param point1: lab7.entity.Point - сущность первой точки
     * @param point2: lab7.entity.Point - сущность второй точки
     * @return Int - коэффициент наклона
     */
    private fun calcLambda(point1: Point, point2: Point): Int {
        val nominator = when (point1) {
            point2 -> 3 * point1.x * point1.x + 2 * equation.a * point1.x + equation.b
            else -> point2.y - point1.y
        }
        var denominator = when (point1) {
            point2 -> 2 * point1.y
            else -> point2.x - point1.x
        }

        // Модульная арифметика производится на целых числах, поэтому нам необходимо заменить делитель на сопоставимый ему множитель
        // По определению i * i^-1 = 1. То же самое можно сказать и в контексте модульной арифметики - i * i^-1 = 1 mod p
        // i = {делитель}, p = 751. Необходимо решить уравнение i*x = 1 mod 751, взяв первое подходящее целочисленное решение
        for (i in 0 until equation.modulo)
            if ((denominator * i) % equation.modulo == 1) {
                denominator = i
                break
            }

        // если вычисленное значение получится отрицательным, необходимо привести его к положительному
        return if (nominator * denominator < 0) equation.modulo + (nominator * denominator) % equation.modulo
        else (nominator * denominator) % equation.modulo
    }

}