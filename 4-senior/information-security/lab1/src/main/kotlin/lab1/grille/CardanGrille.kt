package lab1.grille

import lab1.util.Random
import lab1.util.StringUtil

/**
 *  Класс, реализующий шифрование и дешифрование с помощью решетки Кардано 4х4.
 *  Алгоритм имплементации взят с https://ru.wikipedia.org/wiki/Решётка_Кардано (см. "Шифрование с добавлением мусора")
 */
class CardanGrille {
    private val MATRIX_SIZE: Int = 4                                    // размер двумерного массива, представляющего решетку Кардано
    private val MATRIX_ELEM_COUNT: Int = MATRIX_SIZE * MATRIX_SIZE      // количество элементов массива
    private var grilleMaskMatrix: Array<IntArray>? = null               // решетка Кардано

    init {
        generateGrilleMask()
    }

    /**
     *  Метод генерации решетки Кардано - секретного ключа, использующегося при шифрации данных.
     *
     *  Решетку Кардано 4x4 можно представить следующим образом:
     *
     *  0 1 1 0
     *  0 0 0 0
     *  1 0 0 0
     *  0 0 0 1
     *
     *  где единицы отображают символы исходного текста (отверстия в решетке), а нули - набор случайных символов.
     */
    private fun generateGrilleMask() {
        // Вырезанные клетки должны располагаться таким образом, чтобы никакие две из них не оказывались
        // в одном и том же месте при поворотах решётки. Для этого разобьем нашу решетку на 4 части:
        // северо-западная (NW), северо-восточная (NE), юго-западная (SW) и юго-восточная (SE)
        // Заполним решетку NW числами от 1 до 4 случайным образом: эти числа будут показывать, в каком
        // месте нужно сделать отверстие в каждой четверти.
        val tempArr = Array(MATRIX_SIZE) { i -> i + 1 }.apply { shuffle() }
        val nwQuadrant =
            Array(MATRIX_SIZE / 2) { i -> Array(MATRIX_SIZE / 2) { j -> tempArr[i * (MATRIX_SIZE / 2) + j] } }
        val neQuadrant =
            Array(MATRIX_SIZE / 2) { i -> Array(MATRIX_SIZE / 2) { j -> nwQuadrant[MATRIX_SIZE / 2 - j - 1][i] % 4 + 1 } }
        val seQuadrant =
            Array(MATRIX_SIZE / 2) { i -> Array(MATRIX_SIZE / 2) { j -> neQuadrant[MATRIX_SIZE / 2 - j - 1][i] % 4 + 1 } }
        val swQuadrant =
            Array(MATRIX_SIZE / 2) { i -> Array(MATRIX_SIZE / 2) { j -> seQuadrant[MATRIX_SIZE / 2 - j - 1][i] % 4 + 1 } }

        // Заполняем конечную маску.
        // Если число в четверти равно 1, то в этом месте необходимо проделать отверстие.
        grilleMaskMatrix = Array(MATRIX_SIZE) { i ->
            IntArray(MATRIX_SIZE) { j ->
                when {
                    i in (0 until MATRIX_SIZE / 2) && j in (0 until MATRIX_SIZE / 2) -> if (nwQuadrant[i][j] == 1) 1 else 0
                    i in (0 until MATRIX_SIZE / 2) && j in (MATRIX_SIZE / 2 until MATRIX_SIZE) -> if (neQuadrant[i][j - MATRIX_SIZE / 2] == 1) 1 else 0
                    i in (MATRIX_SIZE / 2 until MATRIX_SIZE) && j in (0 until MATRIX_SIZE / 2) -> if (swQuadrant[i - MATRIX_SIZE / 2][j] == 1) 1 else 0
                    i in (MATRIX_SIZE / 2 until MATRIX_SIZE) && j in (MATRIX_SIZE / 2 until MATRIX_SIZE) -> if (seQuadrant[i - MATRIX_SIZE / 2][j - MATRIX_SIZE / 2] == 1) 1 else 0
                    else -> 0
                }
            }
        }
    }

    /**
     *  Метод для поворота матрицы по часовой стрелке.
     *
     *  @param matrix: Array<IntArray>? - матрица для поворота
     *  @return Array<IntArray> - получившаяся после поворота матрица
     */
    private fun rotateMatrixClockwise(matrix: Array<IntArray>?) = Array(MATRIX_SIZE) { i -> IntArray(MATRIX_SIZE) { j -> matrix!![MATRIX_SIZE - j - 1][i] } }

    /**
     *  Метод для поворота матрицы против часовой стрелки.
     *
     *  @param matrix: Array<IntArray>? - матрица для поворота
     *  @return Array<IntArray> - получившаяся после поворота матрица
     */
    private fun rotateMatrixCounterClockwise(matrix: Array<IntArray>?) = Array(MATRIX_SIZE) { i -> IntArray(MATRIX_SIZE) { j -> matrix!![j][MATRIX_SIZE - i - 1] } }

    /**
     *  Метод для шифрации текста решеткой Кардано.
     *
     *  @param text: String - текст для кодирования
     *  @param isClockwise: Boolean - флаг для определения направления поворота решетки (true - по часовой, false - против)
     *  @return String - закодированный текст
     */
    fun encrypt(text: String, isClockwise: Boolean): String {
        var tempMatrix = grilleMaskMatrix
        var turnCount = 0;
        var turn360Count = 0;
        var readCount = 0
        var tempText = text

        // Для полной шифрации сообщения решетка поворачивается на 90 градусов до тех пор, пока сообщение
        // не будет закодировано полностью. Если отверстия остались незаполненными, в них вписываются
        // случайные символы.
        if (tempText.length % MATRIX_ELEM_COUNT != 0)
            tempText = tempText.plus(Random.getRandomString(MATRIX_ELEM_COUNT - tempText.length % MATRIX_ELEM_COUNT))
        var resText = " ".repeat(tempText.length)
        readLoop@ while (true) {
            for (i in 0 until MATRIX_SIZE) {
                for (j in 0 until MATRIX_SIZE) {
                    if (tempMatrix!![i][j] == 1) {
                        // если это отверстие, то записываем символ сообщения в него
                        resText = StringUtil.setCharAt(
                            resText,
                            i * MATRIX_SIZE + j + MATRIX_ELEM_COUNT * turn360Count,
                            tempText[readCount]
                        )
                        // прочитали все сообщение - вышли из цикла
                        readCount++
                        if (readCount == tempText.length) break@readLoop
                    }
                }
            }
            turnCount++
            tempMatrix = if (isClockwise) this.rotateMatrixClockwise(tempMatrix) else this.rotateMatrixCounterClockwise(
                tempMatrix
            )
            // Когда решетка была повернута 4 раза (на 360 градусов), на бумаге должно закончиться место.
            // В программе таких ограничений нет, поэтому увеличиваем счетчик полных оборотов для того,
            // чтобы впоследствии не перезаписывать предыдущие символы.
            if (turnCount == 4) turn360Count++
            turnCount %= 4
        }

        return resText
    }

    /**
     *  Метод для дешифрации текста решеткой Кардано.
     *
     *  Стоит учесть, что если в исходное сообщение были добавлены "мусорные" символы, они будут
     *  присутствовать в декодированном сообщении.
     *
     *  @param text: String - текст для декодирования
     *  @param isClockwise: Boolean - флаг для определения направления поворота решетки (true - по часовой, false - против)
     *  @return String - декодированный текст
     */
    fun decrypt(text: String, isClockwise: Boolean): String {
        var tempMatrix = grilleMaskMatrix
        var readCount = 0;
        var turnCount = 0;
        var turn360Count = 0
        var resText = ""

        readLoop@ while (true) {
            for (i in 0 until MATRIX_SIZE) {
                for (j in 0 until MATRIX_SIZE) {
                    if (tempMatrix!![i][j] == 1) {
                        // если это отверстие, то считываем символ, находящийся в этом отверстии
                        resText = resText.plus(text[i * MATRIX_SIZE + j + MATRIX_ELEM_COUNT * turn360Count])

                        // прочитали все сообщение - вышли из цикла
                        readCount++
                        if (readCount == text.length) break@readLoop
                    }
                }
            }
            turnCount++
            tempMatrix = if (isClockwise) this.rotateMatrixClockwise(tempMatrix) else this.rotateMatrixCounterClockwise(
                tempMatrix
            )
            if (turnCount == 4) turn360Count++
            turnCount %= 4
        }

        return resText
    }
}