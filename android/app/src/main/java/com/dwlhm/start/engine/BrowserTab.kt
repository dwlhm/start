package com.dwlhm.start.engine

import android.content.Context
import android.util.Log
import org.mozilla.geckoview.GeckoSession

class BrowserTab(context: Context) {
    val session = GeckoSession()
    private val runtime = GeckoRuntimeHolder.get(context)

    init {
        session.open(runtime)
        session.progressDelegate = object : GeckoSession.ProgressDelegate {
            override fun onPageStart(session: GeckoSession, url: String) {
                super.onPageStart(session, url)
                Log.d("BrowserTab", "Page start: $url")
            }

            override fun onProgressChange(session: GeckoSession, progress: Int) {
                Log.d("BrowserTab", "Progress: $progress")
            }

            override fun onPageStop(session: GeckoSession, success: Boolean) {
                Log.d("BrowserTab", "Page stop: $success")
            }
        }
    }

    fun load(url: String) {
        Log.d("BrowserTab", "Loading URL: $url")
        session.loadUri(url)
    }

    fun close() {
        session.close()
    }

}