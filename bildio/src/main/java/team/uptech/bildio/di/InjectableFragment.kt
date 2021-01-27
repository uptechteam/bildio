package team.uptech.bildio.di

import androidx.lifecycle.ViewModel

interface InjectableFragment<VM : ViewModel> : Injectable {
  fun injectFragment(vm: VM)
}