import okhttp3.internal.waitMillis
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

open class DeleteTask : DefaultTask() {

    @TaskAction
    fun boostrap() {
        val oldPluginJsonDir = File("${project.projectDir}/plugins.json")
        val oldReleaseDir = File("${project.projectDir}/release")
        val newPluginJsonDir = File(System.getProperty("user.home") + "/IdeaProjects/nma-release/plugins.json")
        val newReleaseDir = File(System.getProperty("user.home") + "/IdeaProjects/nma-release/release")

        // Delete new release and plugin.json
        if (oldReleaseDir.exists()) {
            oldReleaseDir.deleteRecursively()
        } else {
            System.err.println("$oldReleaseDir does not exist.")
        }
        if (newReleaseDir.exists()) {
            newReleaseDir.deleteRecursively()
        } else {
            System.err.println("$newReleaseDir does not exist.")
        }
        if (oldPluginJsonDir.exists()) {
            Files.delete(oldPluginJsonDir.toPath())
        } else {
            System.err.println("$oldPluginJsonDir does not exist.")
        }
        if (newPluginJsonDir.exists()) {
            Files.delete(newPluginJsonDir.toPath())
        } else {
            System.err.println("$newPluginJsonDir does not exist.")
        }

    }
}
