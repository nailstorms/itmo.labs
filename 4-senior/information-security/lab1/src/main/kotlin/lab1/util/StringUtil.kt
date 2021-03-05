package lab1.util

class StringUtil {
    companion object {
        @JvmStatic
        fun setCharAt(string : String, index : Int, newChar : Char) : String {
            val chars = string.toMutableList()
            chars[index] = newChar
            return chars.joinToString(separator = "")
        }
    }
}