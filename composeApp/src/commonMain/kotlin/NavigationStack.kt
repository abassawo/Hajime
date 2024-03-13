import androidx.compose.runtime.mutableStateListOf

class NavigationStack<T>(vararg initial: T) {
    val stack = mutableStateListOf(*initial)
    
    fun push(t: T) {
        stack.add(t)
    }
    
    fun back() {
        if(stack.size > 1) {
            // ALways keep at least one element on the view stack
            stack.removeLast()
        }
    }
    
    fun reset() {
        stack.removeRange(fromIndex = 1, toIndex = stack.size)
    }
    
    fun lastWithIndex() = stack.withIndex().last()
}