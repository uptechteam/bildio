package team.uptech.bildio.di

import android.app.Activity
import android.app.Application
import android.os.Bundle


abstract class ApplicationInjector<T : Injectable>(protected val application: Application) :
    Application.ActivityLifecycleCallbacks, Injector<T> {

    init {
        application.registerActivityLifecycleCallbacks(this)
    }

    private val injectors = mutableMapOf<Class<out Activity>, Injector<T>?>()
    private val states = mutableMapOf<Class<out Activity>, RetainedState?>()

    abstract fun createInjectorFor(activity: Activity): Injector<T>?

    open fun inject(activity: Activity, injector: Injector<T>) {
        injector.inject((activity as T))
    }

    override fun onActivityCreated(activity: Activity, savedState: Bundle?) {
        val retainedState = states[activity::class.java]
        createInjectorFor(activity)?.let { injector ->
            retainedState?.let {
                injector.restoreState(it)
            }
            injectors[activity::class.java] = injector
            inject(activity, injector)
        }
        states[activity::class.java] = null
    }

    override fun onActivityStarted(p0: Activity) {
    }

    override fun onActivityResumed(p0: Activity) {
    }

    override fun onActivityPaused(p0: Activity) {
    }

    override fun onActivityStopped(p0: Activity) {
    }

    override fun onActivityDestroyed(activity: Activity) {
        injectors[activity::class.java] = null
    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
        states[p0::class.java] = injectors[p0::class.java]?.saveState()
    }

    override fun saveState(): RetainedState {
        return AppInjectorState(states)
    }

    override fun restoreState(state: RetainedState) {
        if (state is AppInjectorState) {
            this.states.clear()
            this.states.putAll(state.activityStates)
        }
    }

    internal class AppInjectorState(
      val activityStates: Map<Class<out Activity>, RetainedState?>
    ) : RetainedState

    override fun inject(injectable: T) {

    }
}