package lab7.entity

/**
 * Класс-сущность параметров уравнения эллиптической кривой
 * @property a: Int - коэффициент при x^2
 * @property b: Int - коэффициент при x
 * @property c: Int - свободный член
 * @property modulo: Int - характеристика поля Галуа
 */
data class Equation(val a : Int, val b : Int, val c : Int, val modulo : Int)
