package ru.itis.kpfu.games

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.itis.kpfu.games.Constants.APP_PREFERENCES
import ru.itis.kpfu.games.Constants.GAME
import ru.itis.kpfu.games.databinding.FragmentStartBinding

class StartFragment : Fragment(R.layout.fragment_start) {

    private var binding: FragmentStartBinding? = null
    private var preferences: SharedPreferences? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentStartBinding.bind(view)
        preferences = requireActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

        binding?.run {

            btCross.setOnClickListener {
                navigate("CROSS")
            }

            btCity.setOnClickListener {
                navigate("CITY")
            }

            btSequence.setOnClickListener {
                navigate("SEQUENCE")
            }

            btRows.setOnClickListener {
                navigate("ROWS")
            }

        }

    }

    private fun navigate(game: String) {
        preferences?.edit {
            putString(GAME, game)
            commit()
        }
        findNavController().navigate(R.id.action_startFragment_to_sessionFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}
