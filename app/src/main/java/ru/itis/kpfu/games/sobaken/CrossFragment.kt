package ru.itis.kpfu.games.sobaken

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ru.itis.kpfu.games.R
import ru.itis.kpfu.games.databinding.FragmentCrossBinding

class CrossFragment : Fragment(R.layout.fragment_cross) {

    private var binding: FragmentCrossBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCrossBinding.bind(view)

        binding?.run {

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}
