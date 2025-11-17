package com.dwlhm.browser

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.application
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn

class BrowserViewModel(
    application: Application
): AndroidViewModel(application) {
    private val _tabs = mutableListOf<BrowserTab>()
    private var _activeTabIndex = 0
    private val _activeTab = MutableStateFlow<BrowserTab?>(null)

    val activeTab: StateFlow<BrowserTab?> = _activeTab.asStateFlow()
    val tabs get() = _tabs.toList()
    val activeIndex get() = _activeTabIndex
    
    // Expose current URL dari active tab (akan update ketika activeTab berubah)
    val currentUrl: StateFlow<String?> = _activeTab
        .flatMapLatest { tab ->
            tab?.currentUrl ?: MutableStateFlow(null).asStateFlow()
        }
        .stateIn(
            scope = viewModelScope,
            started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )
    
    // Expose navigation state dari active tab
    val canGoBack: StateFlow<Boolean> = _activeTab
        .flatMapLatest { tab ->
            tab?.canGoBack ?: MutableStateFlow(false).asStateFlow()
        }
        .stateIn(
            scope = viewModelScope,
            started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )
    
    val canGoForward: StateFlow<Boolean> = _activeTab
        .flatMapLatest { tab ->
            tab?.canGoForward ?: MutableStateFlow(false).asStateFlow()
        }
        .stateIn(
            scope = viewModelScope,
            started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )
    
    fun goBack() {
        _activeTab.value?.goBack()
    }
    
    fun goForward() {
        _activeTab.value?.goForward()
    }

    fun newTab(url: String): Int {
        val tab = BrowserTab(application.applicationContext)
        tab.load(url)
        _tabs += tab
        _activeTabIndex = _tabs.lastIndex
        _activeTab.value = tab
        return _activeTabIndex
    }

    fun switchTab(index: Int) {
        if (index in _tabs.indices) {
            _activeTabIndex = index
            _activeTab.value = _tabs[index]
        }
    }

    fun changeTabUrl(newUrl: String, index: Int) {
        if (index in _tabs.indices) {
            _tabs[index].load(newUrl)
            // Trigger update untuk recompose jika ini adalah active tab
            if (index == _activeTabIndex) {
                _activeTab.value = _tabs[index]
            }
        }
    }

    fun closeTab(index: Int) {
        if (index in _tabs.indices) {
            _tabs[index].close()
            _tabs.removeAt(index)
            _activeTabIndex = _tabs.lastIndex.coerceAtLeast(0)
            _activeTab.value = _tabs.getOrNull(_activeTabIndex)
        }
    }

    override fun onCleared() {
        _tabs.forEach { it.close() }
        super.onCleared()
    }
}