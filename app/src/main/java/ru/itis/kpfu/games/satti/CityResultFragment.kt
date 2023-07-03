package ru.itis.kpfu.games.satti

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ru.itis.kpfu.games.R
import ru.itis.kpfu.games.databinding.FragmentCityResultBinding

class CityResultFragment : Fragment(R.layout.fragment_city_result) {

    private var binding: FragmentCityResultBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCityResultBinding.bind(view)

        binding?.run {

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}
