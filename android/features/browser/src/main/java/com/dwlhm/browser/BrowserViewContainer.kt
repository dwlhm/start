package com.dwlhm.browser

import android.widget.FrameLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import org.mozilla.geckoview.GeckoView

@Composable
fun GeckoViewContainer(
    tab: BrowserTab?,
    modifier: Modifier = Modifier
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            val container = FrameLayout(context)
            val geckoView = GeckoView(context)
            container.addView(
                geckoView,
                FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT
                )
            )

            tab?.let { geckoView.setSession(it.session) }
            container
        },
        update = { view ->
            val geckoView = view.getChildAt(0) as GeckoView
            tab?.let { geckoView.setSession(it.session) }
        }
    )

    DisposableEffect(tab) {
        tab?.session?.setActive(true)
        onDispose {
            tab?.session?.setActive(false)
        }
    }
}