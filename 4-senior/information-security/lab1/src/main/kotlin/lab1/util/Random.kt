package lab1.util

class Random {
    companion object {
        @JvmStatic
        fun getRandomString(length: Int): String {
            val allowedChars = ('A'..'Z')
            return (1..length)
                .map { allowedChars.random() }
                .joinToString("")
        }
    }
}