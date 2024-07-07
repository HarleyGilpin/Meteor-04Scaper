package meteor.plugin.customsprites

import meteor.config.Config
import meteor.config.ConfigItem
import meteor.plugin.Plugin

/**
 * @author Harley <github.com/HarleyGilpin>
 * Created: 7/4/2024 6:47 AM
 */

class CustomSpritesConfig(plugin: Plugin) : Config(plugin) {
    val customSprites = ConfigItem(this, "Load Custom Sprites", "CustomSprites".key(), false)
}