import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.*
import com.google.devtools.ksp.validate
import java.io.File
import java.io.OutputStream
import kotlin.math.round


class ErrorProcessor : SymbolProcessor {
    lateinit var codeGenerator: CodeGenerator
    lateinit var logger: KSPLogger
    lateinit var file: OutputStream
    var rounds = 0
    lateinit var exception: String

    override fun init(options: Map<String, String>, kotlinVersion: KotlinVersion, codeGenerator: CodeGenerator, logger: KSPLogger) {
        exception = if (options.containsKey("exception")) {
            options["exception"]!!
        } else {
            ""
        }
        if (exception == "init") {
            throw Exception("Test Exception in init")
        }
        this.logger = logger
        this.codeGenerator = codeGenerator
    }

    override fun process(resolver: Resolver): List<KSAnnotated> {
        if (exception == "process") {
            throw Exception("Test Exception in process")
        }
        rounds++
        if (rounds == 2) {
            if (exception == "" || exception == "error") {
                logger.error("Error processor: errored at ${rounds}")
            }
        } else {
            codeGenerator.createNewFile(Dependencies.ALL_FILES, "test", "error", "log")
        }
        return emptyList()
    }

    override fun finish() {
        if (exception == "finish") {
            throw Exception("Test Exception in finish")
        }
    }

    override fun onError() {
        if (exception == "error") {
            throw Exception("Test Exception in error")
        }
    }
}


