package com.heartsync.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Базовая ViewModel, реализующая паттерн MVI
 */
abstract class MviViewModel<S : State, E : Effect, A : Action>(
    initialState: S,
) : ViewModel() {

    /**
     * Поток состояния экрана
     */
    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<S> = _state.asStateFlow()

    /**
     * Поток одноразовых действий (эффектов) над UI,
     * таких как навигация, показ тостов, открытие диалогов и т.п.
     */
    private val _effect = MutableSharedFlow<E>()
    val effect: SharedFlow<E> = _effect.asSharedFlow()

    /**
     * Вносит изменения в объект состояния экрана
     * путем модификации части предыдущего состояния
     *
     * [function] - функция, создающая новое состояние из предыдущего состояния.
     */
    protected fun setState(function: S.() -> S) {
        _state.update { value ->
            value.function()
                .validate()
        }
    }

    /**
     * Инициирует вызов функции [validate] и обновляет стейт.
     * Необходимо вызывать когда изменились внешние условия, невходящие в стейт
     *
     */
    protected fun revalidate() {
        _state.update { value -> value.validate() }
    }

    /**
     * Актуализация зависимых полей стейта
     * от прочих полей стейта или прочих факторов.
     * Если таковых зависиомостей нет, то переопределять дефолтную реализацию не нужно.
     */
    protected open fun S.validate(): S = this

    /**
     * Отправка эффекта на выполнение в UI часть
     *
     * [effect] - объект c параметрами эффекта для отправки
     */
    protected fun postEffect(effect: E) {
        viewModelScope.launch {
            _effect.subscriptionCount.first { it > 0 }
            _effect.emit(effect)
        }
    }

    /**
     * Коллбэк UI событий действий пользователя
     *
     * [action] - объект с параметрами действия
     */
    abstract fun onAction(action: A)
}
