import android.content.Context
import androidx.lifecycle.ViewModel
import com.dwlhm.start.engine.BrowserTab

class BrowserViewModel(
    private val context: Context
): ViewModel() {
    private val _tabs = mutableListOf<BrowserTab>()
    private var _activeTabIndex = 0

    val activeTab get() = _tabs.getOrNull(_activeTabIndex)
    val tabs get() = _tabs.toList()
    val activeIndex get() = _activeTabIndex

    init {
        newTab("https://google.com")
    }

    fun newTab(url: String): Int {
        val tab = BrowserTab(context)
        tab.load(url)
        _tabs += tab
        _activeTabIndex = _tabs.lastIndex
        return _activeTabIndex
    }

    fun switchTab(index: Int) {
        if (index in _tabs.indices) _activeTabIndex = index
    }

    fun closeTab(index: Int) {
        if (index in _tabs.indices) {
            _tabs[index].close()
            _tabs.removeAt(index)
            _activeTabIndex = _tabs.lastIndex.coerceAtLeast(0)
        }
    }

    override fun onCleared() {
        _tabs.forEach { it.close() }
        super.onCleared()
    }
}