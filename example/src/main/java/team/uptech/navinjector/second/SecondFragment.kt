package team.uptech.navinjector.second

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import team.uptech.navinjector.base.BaseFragment
import team.uptech.navinjector.databinding.FragmentSecondBinding

class SecondFragment : BaseFragment<SecondViewModel>() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSecondBinding.inflate(inflater)

        binding.button.setOnClickListener {
            vm.onButtonClicked()
        }
        binding.button2.setOnClickListener {
            vm.onButton2Clicked()
        }
        return binding.root
    }
}