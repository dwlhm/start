package com.dwlhm.browser

import android.content.Context
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.mozilla.geckoview.AllowOrDeny
import org.mozilla.geckoview.GeckoResult
import org.mozilla.geckoview.GeckoSession

class BrowserTab(context: Context) {
    val session = GeckoSession()
    private val runtime = GeckoRuntimeHolder.get(context)
    
    private val _currentUrl = MutableStateFlow<String?>(null)
    val currentUrl: StateFlow<String?> = _currentUrl.asStateFlow()
    
    private val _canGoBack = MutableStateFlow(false)
    val canGoBack: StateFlow<Boolean> = _canGoBack.asStateFlow()
    
    private val _canGoForward = MutableStateFlow(false)
    val canGoForward: StateFlow<Boolean> = _canGoForward.asStateFlow()
    
    private val _loadingProgress = MutableStateFlow(0)
    val loadingProgress: StateFlow<Int> = _loadingProgress.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        session.open(runtime)
        
        // Setup navigation delegate untuk tracking history
        session.navigationDelegate = object : GeckoSession.NavigationDelegate {
            override fun onCanGoBack(session: GeckoSession, canGoBack: Boolean) {
                _canGoBack.value = canGoBack
            }

            override fun onCanGoForward(session: GeckoSession, canGoForward: Boolean) {
                _canGoForward.value = canGoForward
            }
            
            override fun onLoadRequest(
                session: GeckoSession,
                request: GeckoSession.NavigationDelegate.LoadRequest
            ): GeckoResult<AllowOrDeny?>? {
                // Allow all navigation requests
                return null
            }
        }
        
        // Setup progress delegate untuk tracking URL changes
        session.progressDelegate = object : GeckoSession.ProgressDelegate {
            override fun onPageStart(session: GeckoSession, url: String) {
                super.onPageStart(session, url)
                Log.d("BrowserTab", "Page start: $url")
                _currentUrl.value = url
                _isLoading.value = true
                _loadingProgress.value = 0
            }

            override fun onProgressChange(session: GeckoSession, progress: Int) {
                Log.d("BrowserTab", "Progress: $progress")
                _loadingProgress.value = progress
            }

            override fun onPageStop(session: GeckoSession, success: Boolean) {
                Log.d("BrowserTab", "Page stop: $success")
                _isLoading.value = false
                _loadingProgress.value = 100
                // URL sudah diupdate di onPageStart, tidak perlu update lagi di sini
            }
        }
    }

    fun load(url: String) {
        Log.d("BrowserTab", "Loading URL: $url")
        session.loadUri(url)
    }
    
    fun goBack() {
        if (_canGoBack.value) {
            session.goBack()
        }
    }
    
    fun goForward() {
        if (_canGoForward.value) {
            session.goForward()
        }
    }

    fun close() {
        session.close()
    }
}
