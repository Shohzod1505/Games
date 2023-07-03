package ru.itis.kpfu.games.dima

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ru.itis.kpfu.games.R
import ru.itis.kpfu.games.databinding.FragmentSequenceResultBinding

class SequenceResultFragment : Fragment(R.layout.fragment_sequence_result) {

    private var binding: FragmentSequenceResultBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSequenceResultBinding.bind(view)

        binding?.run {

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}
