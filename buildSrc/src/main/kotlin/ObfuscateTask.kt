import com.savvasdalkitsis.jsonmerger.JsonMerger
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.extra
import org.gradle.kotlin.dsl.get
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.IOException
import java.nio.file.Paths
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

open class ObfuscateTask : DefaultTask() {

    @TaskAction
    fun boostrap() {
        if (project == project.rootProject) {
            val rootDir = "${project.projectDir}\\"
            val obfuscator = "obfuscator-1.9.3.jar"

            project.subprojects.forEach {
                val libs = "${it.project.buildDir}\\libs\\"
                val plugin = "${it.project.name}-${it.project.version}.jar"

                //exec("java -jar $rootDir$obfuscator --jarIn $libs$plugin --jarOut $libs$obfuscatedPlugin --config ${rootDir}obfConfig")
                exec("java -jar $rootDir$obfuscator --jarIn $libs$plugin --jarOut $libs$plugin")

            }
        }

    }

    fun exec(cmd: String, stdIn: String = "", captureOutput:Boolean = true, workingDir: File = File(".")): String? {
        try {
            val process = ProcessBuilder(*cmd.split("\\s".toRegex()).toTypedArray())
                .directory(workingDir)
                .redirectOutput(if (captureOutput) ProcessBuilder.Redirect.PIPE else ProcessBuilder.Redirect.INHERIT)
                .redirectError(if (captureOutput) ProcessBuilder.Redirect.PIPE else ProcessBuilder.Redirect.INHERIT)
                .start().apply {
                    if (stdIn != "") {
                        outputStream.bufferedWriter().apply {
                            write(stdIn)
                            flush()
                            close()
                        }
                    }
                    waitFor(60, TimeUnit.SECONDS)
                }
            if (captureOutput) {
                return process.inputStream.bufferedReader().readText()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

}
