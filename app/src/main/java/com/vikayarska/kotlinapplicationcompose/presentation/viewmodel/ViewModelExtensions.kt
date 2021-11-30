package com.vikayarska.kotlinapplicationcompose.presentation.viewmodel

import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext


fun ViewModel.launch(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
) = this.viewModelScope.launch(context, start, block)

fun ViewModel.launchIO(
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
) = this.launch(Dispatchers.IO, start, block)

fun <T> Flow<T>.collectWhenResumed(
    viewScope: LifecycleCoroutineScope,
    action: suspend (value: T) -> Unit
) = viewScope.launchWhenResumed { collect { action(it) } }
