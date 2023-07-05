package ru.itis.kpfu.games.dima

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.itis.kpfu.games.R
import ru.itis.kpfu.games.databinding.FragmentSequenceResultBinding

class SequenceResultFragment : Fragment(R.layout.fragment_sequence_result) {

    private var binding: FragmentSequenceResultBinding? = null

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSequenceResultBinding.bind(view)

        binding?.run {
            tvGameWinnerRes.text = arguments?.getString(SequenceFragment.ARG_GAME_WINNER)
            tvScoreRes.text = arguments?.getString(SequenceFragment.ARG_SCORE)
            tvDifficultResNumberRange.text =
                tvDifficultResNumberRange.text.toString() + ": " + arguments?.getString(
                    SequenceFragment.ARG_NUMBER_RANGE
                )
            tvDifficultResTimeRange.text =
                tvDifficultResTimeRange.text.toString() + ": " + arguments?.getString(
                    SequenceFragment.ARG_TIME_RANGE
                )
            tvDifficultResLevel.text =
                tvDifficultResLevel.text.toString() + ": " + arguments?.getString(SequenceFragment.ARG_LEVEL)
            btPlayAgain.setOnClickListener {
                findNavController().navigate(R.id.action_sequenceResultFragment_to_sequenceFragment)
            }
            btBack.setOnClickListener {
                findNavController().navigate(R.id.action_sequenceResultFragment_to_startFragment)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}
