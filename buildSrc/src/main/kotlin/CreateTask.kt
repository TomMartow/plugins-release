import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File

open class CreateTask : DefaultTask() {

    @TaskAction
    fun boostrap() {

        val bootstrapPluginJson = File("${project.projectDir}/plugins.json")
        if (!bootstrapPluginJson.exists()) {
            bootstrapPluginJson.createNewFile();
        } else {
            System.err.println("$bootstrapPluginJson already exists.")
        }
    }
}
