package org.jetbrains.jewel.bridge

import androidx.compose.ui.res.ResourceLoader
import com.intellij.ide.ui.IconMapLoader
import com.intellij.openapi.components.service
import com.intellij.openapi.diagnostic.thisLogger
import org.jetbrains.jewel.JewelResourceLoader

object IntelliJIconMapper : IconMapper {

    private val logger = thisLogger()

    private val mappingsByClassLoader
        get() = service<IconMapLoader>().loadIconMapping()

    override fun mapPath(originalPath: String, resourceLoader: ResourceLoader): String {
        logger.debug("Loading SVG from '$originalPath'")
        val searchClasses = (resourceLoader as? JewelResourceLoader)?.searchClasses
        if (searchClasses == null) {
            logger.warn(
                "Tried loading a resource but the provided ResourceLoader is now a JewelResourceLoader; " +
                    "this is probably a bug. Make sure you always use JewelResourceLoaders.",
            )
            return originalPath
        }

        val allMappings = mappingsByClassLoader
        if (allMappings.isEmpty()) {
            logger.info("No mapping info available yet, can't check for '$originalPath' mapping.")
            return originalPath
        }

        val applicableMappings = searchClasses.mapNotNull { allMappings[it.classLoader] }
        val mappedPath = applicableMappings.firstNotNullOfOrNull { it[originalPath.removePrefix("/")] }

        if (mappedPath == null) {
            logger.debug("Icon '$originalPath' has no mapping defined.")
            return originalPath
        }

        logger.debug("Icon '$originalPath' is mapped to '$mappedPath'.")
        return mappedPath
    }
}