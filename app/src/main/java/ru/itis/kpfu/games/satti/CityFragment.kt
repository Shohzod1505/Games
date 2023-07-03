package ru.itis.kpfu.games.satti

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ru.itis.kpfu.games.R
import ru.itis.kpfu.games.databinding.FragmentCityBinding
import ru.itis.kpfu.games.databinding.FragmentCrossBinding

class CityFragment : Fragment(R.layout.fragment_city) {

    private var binding: FragmentCityBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCityBinding.bind(view)

        binding?.run {

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}
