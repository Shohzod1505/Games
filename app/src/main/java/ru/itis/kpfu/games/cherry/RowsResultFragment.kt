package ru.itis.kpfu.games.cherry

import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.itis.kpfu.games.Constants
import ru.itis.kpfu.games.R
import ru.itis.kpfu.games.databinding.FragmentRowsResultBinding

class RowsResultFragment : Fragment(R.layout.fragment_rows_result) {

    private var binding: FragmentRowsResultBinding? = null
    private var preferences: SharedPreferences? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRowsResultBinding.bind(view)

        binding?.run {

            val str=arguments?.getString("Winner")
            val str1=str+" "+getString(R.string.winner_rows)
            val str2=""+arguments?.getInt("Chips")+" "+getString(R.string.сhips_sum_rows)
            if (str != null) {
                if(str.length!=0) {
                    tvWinner.text = str1
                    tvSumChips.text=str2
                }else{
                    tvWinner.text = getString(R.string.draw_rows)
                    tvSumChips.text=""
                }
            }
            val i=arguments?.getString("Color")
            if(i!=null) {
                tvWinner.setTextColor(Color.parseColor(i))
            }
            tvWinner.animate().rotation(360F)

            btMenu.setOnClickListener {
                navigateMain()
            }

            btAgain.setOnClickListener {
                navigateRows()
            }

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun navigateRows() {
        findNavController().navigate(R.id.action_rowsResultFragment_to_rowsFragment)
    }

    private fun navigateMain() {
        preferences?.edit {
            putString(Constants.GAME, "")
            commit()
        }
        findNavController().navigate(R.id.action_rowsResultFragment_to_startFragment)
    }

    companion object {
        private const val ARG_WINNER = "Winner"
        private const val ARG_CHIPS = "Chips"
    }
}

