import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ru.itis.kpfu.games.R
import ru.itis.kpfu.games.databinding.FragmentCityBinding
import ru.itis.kpfu.games.satti.CitiesAdapter

class CityFragment : Fragment(R.layout.fragment_city) {

    private var binding: FragmentCityBinding? = null
    private val citiesAdapter = CitiesAdapter()

    private var currentPlayer = 1
    private var player1Score = 0
    private var player2Score = 0

    private val citiesList = mutableListOf<String>()

    private fun checkCityName(previousCity: String, currentCity: String): Boolean {
        val lastChar = previousCity.last().lowercaseChar()
        val firstChar = currentCity.first().lowercaseChar()
        return lastChar != firstChar
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCityBinding.bind(view)

        binding?.run {
            recyclerViewCities.layoutManager = LinearLayoutManager(requireContext())
            recyclerViewCities.adapter = citiesAdapter

            buttonSubmit.setOnClickListener {
                validateCity()
            }

            buttonGiveUp.setOnClickListener {
                navigateToResultScreen()
            }

            buttonExit.setOnClickListener {
                navigateToMenuScreen()
            }
        }

        val citiesList = listOf("Moscow", "New York", "London", "Tokyo", "Paris")
        citiesAdapter.setData(citiesList)
    }

    private fun validateCity() {
        val city = binding?.editTextCity?.text.toString().trim()

        if (city.isEmpty()) {
            binding?.textInputLayoutCity?.error = "Введите название города"
            return
        }

        val lastCity = citiesList.lastOrNull()

        if (lastCity != null && !checkCityName(lastCity, city)) {
            binding?.textInputLayoutCity?.error = "Название города должно начинаться с другой буквы"
            return
        }

        if (city.endsWith("ь") || city.endsWith("ъ") || city.endsWith("ы")) {
            binding?.textInputLayoutCity?.error = "Название города не должно заканчиваться на 'ь', 'ъ' или 'ы'"
            return
        }

        binding?.textInputLayoutCity?.error = null
        citiesList.add(city)
        citiesAdapter.setData(citiesList)
        binding?.editTextCity?.text?.clear()

        currentPlayer = if (currentPlayer == 1) 2 else 1

        if (currentPlayer == 1) {
            player1Score++
            binding?.textViewScorePlayer1?.text = "Очки: $player1Score"
        } else {
            player2Score++
            binding?.textViewScorePlayer2?.text = "Очки: $player2Score"
        }
    }




    private fun navigateToMenuScreen() {
        val action = CityFragmentDirections.actionCityFragmentToStartFragment()
        findNavController().navigate(action)
    }

    private fun navigateToResultScreen() {
        val action = CityFragmentDirections.actionCityFragmentToCityResultFragment(player1Score, player2Score)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
