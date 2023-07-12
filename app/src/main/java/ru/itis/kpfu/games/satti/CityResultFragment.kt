import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
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

        binding?.run {
            val args = CityResultFragmentArgs.fromBundle(requireArguments())
            player1Score = args.player1Score
            player2Score = args.player2Score

            val winner = if (player1Score > player2Score) "Player 1 wins!" else "Player 2 wins!"
            val cityCount = "Cities named: ${player1Score + player2Score}"

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
        val action = CityResultFragmentDirections.actionCityResultFragmentToStartFragment()
        findNavController().navigate(action.actionId)

    }


    private fun restartGame() {
        player1Score = 0
        player2Score = 0
        citiesList.clear()

        citiesAdapter.setData(emptyList())

        currentPlayer = 1
        binding?.textViewScorePlayer1?.text = "Score: $player1Score"
        binding?.textViewScorePlayer2?.text = "Score: $player2Score"

    }
}
