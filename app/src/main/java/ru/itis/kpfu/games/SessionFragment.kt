package ru.itis.kpfu.games

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import ru.itis.kpfu.games.Constants.GAME
import ru.itis.kpfu.games.Constants.PLAYER_FIRST
import ru.itis.kpfu.games.Constants.PLAYER_SECOND
import ru.itis.kpfu.games.databinding.FragmentSessionBinding
import ru.itis.kpfu.games.utils.checkField

class SessionFragment : Fragment(R.layout.fragment_session) {

    private var binding: FragmentSessionBinding? = null
    private var preferences: SharedPreferences? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSessionBinding.bind(view)
        preferences = requireActivity().getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE)

        binding?.run {

            val game = preferences?.getString(GAME, "")

            setGameStyle(tvTitle, game)

            btNext.setOnClickListener {
                val error = getString(R.string.error_empty)
                val playerFirstIsEmpty = etPlayerFirst.checkField(error)
                val playerSecondIsEmpty = etPlayerSecond.checkField(error)
                navigateNext(
                    etPlayerFirst,
                    etPlayerSecond,
                    game,
                    playerFirstIsEmpty,
                    playerSecondIsEmpty
                )
            }

            btBack.setOnClickListener {
                navigateBack()
            }

        }

    }

    private fun setGameStyle(
        title: TextView,
        game: String?,
    ) {
        when(game) {
            "CROSS" -> {
                title.text = getText(R.string.game_cross)
            }
            "CITY" -> {
                title.text = getText(R.string.game_city)
            }
            "SEQUENCE" -> {
                title.text = getText(R.string.game_sequence)
            }
            "ROWS" -> {
                title.text = getText(R.string.game_rows)
            }
        }
    }

    private fun navigateNext(
        playerFirst: TextInputLayout,
        playerSecond: TextInputLayout,
        game: String?,
        playerFirstIsEmpty: Boolean,
        playerSecondIsEmpty: Boolean,
    ) {
        if (playerFirstIsEmpty && playerSecondIsEmpty) {
            preferences?.edit {
                putString(PLAYER_FIRST, playerFirst.editText?.text.toString())
                putString(PLAYER_SECOND, playerSecond.editText?.text.toString())
                commit()
            }
            when(game) {
                "CROSS" -> {
                    findNavController().navigate(R.id.action_sessionFragment_to_crossFragment)
                }
                "CITY" -> {
                    findNavController().navigate(R.id.action_sessionFragment_to_cityFragment)
                }
                "SEQUENCE" -> {
                    findNavController().navigate(R.id.action_sessionFragment_to_sequenceFragment)
                }
                "ROWS" -> {
                    findNavController().navigate(R.id.action_sessionFragment_to_rowsFragment)
                }
            }
        }
    }

    private fun navigateBack() {
        preferences?.edit {
            putString(GAME, "")
            commit()
        }
        findNavController().navigate(R.id.action_sessionFragment_to_startFragment)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}
