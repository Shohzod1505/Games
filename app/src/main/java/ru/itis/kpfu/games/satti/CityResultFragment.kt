package ru.itis.kpfu.games.satti

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import ru.itis.kpfu.games.R
import ru.itis.kpfu.games.databinding.FragmentCityResultBinding

class CityResultFragment : Fragment(R.layout.fragment_city_result) {

    private var binding: FragmentCityResultBinding? = null
    private var player1Score = 0
    private var player2Score = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCityResultBinding.bind(view)

        player1Score = arguments?.getInt("player1Score") ?: 0
        player2Score = arguments?.getInt("player2Score") ?: 0

        binding?.run {
            var winner = ""
            var cityCount = ""
            if (player1Score > player2Score) {
                winner = "Player 1 wins!"
                cityCount = "Cities named: ${player1Score}"
            } else {
                winner = "Player 2 wins!"
                cityCount = "Cities named: ${player2Score}"
            }

            textViewWinner.text = winner
            textViewCityCount.text = cityCount

            buttonBackToMenu.setOnClickListener {
                navigateToMenuScreen()
            }

            buttonRestart.setOnClickListener {
                restartGame()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun navigateToMenuScreen() {
        findNavController().navigate(R.id.action_cityResultFragment_to_startFragment)
    }


    private fun restartGame() {
        player1Score = 0
        player2Score = 0

        setFragmentResult("restart", bundleOf())

        findNavController().popBackStack()
    }
}
