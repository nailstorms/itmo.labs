package lab1

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.choice
import com.github.ajalt.clikt.parameters.types.file
import lab1.grille.CardanGrille
import java.io.File

class Main : CliktCommand() {
    private val srcFile: File by option("-src", "--srcFile", help = "Source file with text to encrypt").file().required()
    private val dstFile: File by option("-dst", "--destFile", help = "Destination file with encrypted text").file().required()
    private val grilleTurnDirection: Boolean by option("-d", "--gtd", help = "The direction in which the grille will be turned when encrypting/decrypting").choice(
        "cw" to true,
        "ccw" to false,
        ignoreCase = true
    ).default(true)

    override fun run() {
        val grilleEncryptor = CardanGrille()
        val fileText = srcFile.inputStream().readBytes().toString(Charsets.UTF_8)
        var resText = grilleEncryptor.encrypt(fileText, grilleTurnDirection)
        dstFile.apply {
            createNewFile()
            writeText(resText)
        }
        val srcDecryptedFile = File(srcFile.parentFile.name.plus("/decrypted"))
        resText = grilleEncryptor.decrypt(resText, grilleTurnDirection)
        srcDecryptedFile.apply {
            createNewFile()
            writeText(resText)
        }
        echo("File successfully encrypted. See ${dstFile.path} for encryption results " +
                "and ${srcDecryptedFile.path} for a decryption attempt results.")
    }
}

fun main(args: Array<String>) = Main().main(args)
