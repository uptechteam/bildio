package team.uptech.navinjector.other

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import team.uptech.navinjector.base.BaseFragment
import team.uptech.navinjector.databinding.FragmentSubgraphBinding

class SubgraphFragment : BaseFragment<SubgraphViewModel>() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSubgraphBinding.inflate(inflater)
        return binding.root
    }
}