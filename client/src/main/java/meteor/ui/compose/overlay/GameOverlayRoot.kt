package meteor.ui.compose.overlay

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import meteor.Main
import meteor.ui.compose.GamePanel
import meteor.ui.compose.events.PreRender
import org.rationalityfrontline.kevent.KEVENT
import org.rationalityfrontline.kevent.KEvent

object GameOverlayRoot {
    /**
     * This overlay layer covers the entire game area
     * stretchedWidth/Height is updated every frame, so this composable will be too.
     */
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun render() {
        KEVENT.post(PreRender)
        Main.forceRecomposition.value
        val compositionStart = System.currentTimeMillis()
        //val scale = GamePanel.getHeightScale()
        var mod = Modifier.absoluteOffset(x = GamePanel.xPadding.value.dp)
            .size(DpSize(GamePanel.stretchedWidth.value.dp, GamePanel.stretchedHeight.value.dp))
            .clipToBounds()
        if (Main.client.loggedIn() && GamePanel.debugOverlays.value)
            mod = mod.background(Color.Cyan.copy(alpha = .2f))
        Box(mod) {
            ViewportOverlayRoot.render()
            //TODO: Remove this as it's just to verify compose / swing interop isn't broken
            Text(Main.text.value, color = Color.Cyan, fontSize = 8.sp, modifier = Modifier.fillMaxSize())
        }
        Main.composeTime.value = System.currentTimeMillis() - compositionStart
    }
}