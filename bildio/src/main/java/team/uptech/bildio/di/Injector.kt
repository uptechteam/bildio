package team.uptech.bildio.di

interface Injector<T : Injectable> {
    fun inject(injectable: T)

    fun saveState(): RetainedState

    fun restoreState(state: RetainedState)
}

interface RetainedState

object EmptyState : RetainedState
