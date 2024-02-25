package fr.jaetan.jmedia.models

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.compositionLocalOf

abstract class ReplaceableLocal<T : Any> {

    @Suppress("VariableNaming", "PrivatePropertyName")
    private val Local = compositionLocalOf<T?> { null }

    val current: T
        @Composable get() = Local.current ?: currentValue()

    /**
     * Compute current value
     */
    @Composable
    protected abstract fun currentValue(): T

    /**
     * Associates a [T] key to a value in a call to [CompositionLocalProvider].
     */
    infix fun provides(fixed: T): ProvidedValue<T> {
        @Suppress("UNCHECKED_CAST")
        return Local.provides(fixed) as ProvidedValue<T>
    }
}