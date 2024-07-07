package meteor.plugin.customsprites

import meteor.Main
import meteor.Main.client
import meteor.Main.window
import meteor.events.client.ConfigChanged
import meteor.plugin.Plugin
import meteor.ui.config.AspectMode
import meteor.ui.config.CustomSprites

/**
 * @author Harley <github.com/HarleyGilpin>
 * Created: 7/4/2024 6:45 AM
 *
 * Description:
 * This file contains the implementation of BLANK.
 *
 */
class CustomSpritesPlugin : Plugin("Load Custom Sprites") {

    val config = configuration<CustomSpritesConfig>()

    override fun onConfigChanged(it: ConfigChanged) {
        if (it.affects(config)) {
            updateConfig()
        }
    }

    override fun onStart() {

    }

    override fun onStop() {

    }

    fun updateConfig() {
        when (config.customSprites.get<Boolean>()) {
            true -> client.customSprites = CustomSprites.TRUE
            false -> client.customSprites = CustomSprites.FALSE
        }
    }
}