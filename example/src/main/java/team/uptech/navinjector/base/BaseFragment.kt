package team.uptech.navinjector.base

import androidx.fragment.app.Fragment
import team.uptech.bildio.di.InjectableFragment

abstract class BaseFragment<T : BaseViewModel> : InjectableFragment<T>, Fragment() {
    protected lateinit var vm: T

    override fun injectFragment(vm: T) {
        this.vm = vm
    }
}