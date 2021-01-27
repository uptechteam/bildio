package team.uptech.navinjector

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import team.uptech.bildio.di.ControllerProvider
import team.uptech.navinjector.databinding.ActivityMainBinding

class HostActivity : AppCompatActivity(), ControllerProvider {

    val contentView by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    override val navController: NavController
        get() {
            contentView
            return (supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment)
                .navController
        }

}