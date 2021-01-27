package team.uptech.navinjector.first

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import team.uptech.navinjector.base.BaseFragment
import team.uptech.navinjector.databinding.FragmentFirstBinding

class FirstFragment : BaseFragment<FirstViewModel>() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFirstBinding.inflate(inflater)

        binding.button.setOnClickListener {
            vm.onButtonClicked()
        }
        return binding.root
    }
}