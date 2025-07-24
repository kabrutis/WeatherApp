// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.hilt) apply false
}

allprojects {
    configurations.all {
        resolutionStrategy.eachDependency {
            if (requested.group == "com.intellij" && requested.name == "annotations") {
                useTarget("org.jetbrains:annotations:23.0.0")
                because("Avoid conflict between com.intellij:annotations and org.jetbrains:annotations")
            }
        }
    }
}