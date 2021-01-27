package team.uptech.bildio.di

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.annotation.CallSuper
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph
import java.lang.ref.Reference

abstract class NavControllerInjector(
    activity: Reference<out FragmentActivity>
) : FragmentLifecycleInjector(), Injector<ControllerProvider>,
    NavController.OnDestinationChangedListener {

    companion object {
        val TAG = NavControllerInjector::class.java.simpleName
    }

    init {
        //Important! This should be registered before setting activity content, behavior undefined otherwise
        with(activity.get() ?: throw Exception("Activity has been GCed")) {
            supportFragmentManager.registerFragmentLifecycleCallbacks(
                this@NavControllerInjector,
                true
            )
        }
    }

    private val map = mutableMapOf<Int, NavBackStackEntry>()

    inner class DestinationLifecycleObserver(
        val destinationId: Int,
        val onCreate: () -> Unit,
        val onKill: () -> Unit
    ) : LifecycleObserver {

        @SuppressLint("LogNotTimber")
        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
        fun onCreate() {
            Log.e(TAG, "start $destinationId")
            onCreate.invoke()
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun onDestroy() {
            Log.e(TAG, "destroy $destinationId")
            onKill.invoke()
            map.remove(destinationId)
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        fun onResume() {
            Log.e(TAG, "resume $destinationId")
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        fun onPause() {
            Log.e(TAG, "pause $destinationId")
        }
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        destination.parent?.let { navGraph ->
            //TODO maybe think of smthing else?
            if (!map.containsKey(navGraph.id)) {
                with(controller.getBackStackEntry(navGraph.id)) {
                    lifecycle.addObserver(onSubGraph(navGraph) ?: return@let)
                    map[navGraph.id] = this
                }
            }
        }
    }

    @CallSuper
    override fun inject(injectable: ControllerProvider) {
        val controller = injectable.navController
        controller.addOnDestinationChangedListener(this)
    }

    abstract fun onSubGraph(navGraph: NavGraph): DestinationLifecycleObserver?
}