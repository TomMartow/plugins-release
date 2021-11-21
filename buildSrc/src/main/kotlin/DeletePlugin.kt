import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.register

class DeletePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.tasks.register<DeleteTask>("delete") {}
    }
}
