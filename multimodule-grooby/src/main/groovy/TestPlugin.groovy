import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by Dzianis_Sevastseyenk on 07/12/2017.
 */
class TestPlugin implements Plugin<Project> {

    String userName;

    @Override
    void apply(Project project) {
        project.task("hello") {
            doLast {
                println "Hello $userName"
            }
        }
    }
}
//resources/META-INF/gradle-plugins/org.samples.greeting.properties