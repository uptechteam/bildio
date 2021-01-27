package team.uptech.bildio.di

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import java.lang.reflect.ParameterizedType

abstract class FragmentLifecycleInjector : FragmentManager.FragmentLifecycleCallbacks() {

  @Suppress("UNCHECKED_CAST")
  override fun onFragmentPreCreated(
    fm: FragmentManager,
    f: Fragment,
    savedInstanceState: Bundle?
  ) {
    super.onFragmentPreCreated(fm, f, savedInstanceState)
    (f as? InjectableFragment<in ViewModel>)?.apply {
      val clazz = getViewModelClazz(this)
      val vm = ViewModelProvider(
        getViewModelStoreOwner(f),
        getVMFactory(f)
      )[clazz]
      doInject(this, vm)
    }
  }

  open fun doInject(injectingFragment: InjectableFragment<in ViewModel>, vm: ViewModel) {
    try {
      injectingFragment.injectFragment(vm)
    } catch (ex: ClassCastException) {
      ex.printStackTrace()
    }
  }

  open fun getViewModelStoreOwner(injectingFragment: Fragment): ViewModelStoreOwner {
    return injectingFragment
  }

  abstract fun getVMFactory(fragment: Fragment): ViewModelProvider.Factory

  @Suppress("UNCHECKED_CAST")
  open fun getViewModelClazz(injectingFragment: InjectableFragment<*>): Class<out ViewModel> {
    val fragmentClass = injectingFragment::class.java
    val superType = fragmentClass.genericSuperclass
    val args = (superType as ParameterizedType).actualTypeArguments
    return args[0] as Class<out ViewModel>
  }
}

