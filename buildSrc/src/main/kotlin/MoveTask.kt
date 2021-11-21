import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption

open class MoveTask : DefaultTask() {

    @TaskAction
    fun boostrap() {
        if (project == project.rootProject) {
            val oldPluginJsonDir = File("${project.projectDir}/plugins.json")
            val oldReleaseDir = File("${project.projectDir}/release")
            val newPluginJsonDir = File(System.getProperty("user.home") + "/IdeaProjects/nma-release/plugins.json")
            val newReleaseDir = File(System.getProperty("user.home") + "/IdeaProjects/nma-release/release")

            if (oldReleaseDir.exists()) {
                Files.move(oldReleaseDir.toPath(), newReleaseDir.toPath(), StandardCopyOption.REPLACE_EXISTING)
            } else {
                System.err.println("old /release does not exist.")
            }
            if (oldPluginJsonDir.exists()) {
                Files.move(oldPluginJsonDir.toPath(), newPluginJsonDir.toPath(), StandardCopyOption.REPLACE_EXISTING)
            } else {
                System.err.println("old plugin.json does not exist.")
            }


        }
    }
}
