package lab7

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import lab7.entity.Equation
import lab7.entity.Point
import lab7.solve.PointFinder

class Main : CliktCommand() {
    private val pPointCoords by option("-p", "--pPoint", help = "P point coordinates (x,y)").required()
    private val qPointCoords by option("-q", "--qPoint", help = "Q point coordinates (x,y)").required()
    private val rPointCoords by option("-r", "--rPoint", help = "R point coordinates (x,y)").required()

    override fun run() {
        val equation = Equation(0, -1, 1, 751)
        val finder = PointFinder(equation)
        echo("Finding point 2P + 3Q - R for equation y^2 = x^3 + (${equation.a})*x^2 + (${equation.b})*x + (${equation.c}) (mod ${equation.modulo})")

        val pPointCoordsParsed = pPointCoords.replace("\\s".toRegex(), "").split(",", limit = 2)
        val qPointCoordsParsed = qPointCoords.replace("\\s".toRegex(), "").split(",", limit = 2)
        val rPointCoordsParsed = rPointCoords.replace("\\s".toRegex(), "").split(",", limit = 2)

        val pPoint = Point(pPointCoordsParsed[0].toInt(), pPointCoordsParsed[1].toInt())
        val qPoint = Point(qPointCoordsParsed[0].toInt(), qPointCoordsParsed[1].toInt())
        val rPoint = Point(rPointCoordsParsed[0].toInt(), rPointCoordsParsed[1].toInt())

        val pTimes2 = finder.calcSum(pPoint, pPoint)
        val qTimes3 = finder.calcSum(finder.calcSum(qPoint, qPoint), qPoint)
        val rMinus = Point(rPoint.x, -rPoint.y)                                 // по определению конечной группы

        val result = finder.calcSum(finder.calcSum(pTimes2, qTimes3), rMinus)
        echo("Result - point S (x: ${result.x}, y: ${result.y})")
    }
}

fun main(args: Array<String>) = Main().main(args)