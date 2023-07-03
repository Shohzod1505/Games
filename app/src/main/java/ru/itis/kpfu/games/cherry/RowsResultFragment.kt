package ru.itis.kpfu.games.cherry

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ru.itis.kpfu.games.R
import ru.itis.kpfu.games.databinding.FragmentRowsResultBinding

class RowsResultFragment : Fragment(R.layout.fragment_rows_result) {

    private var binding: FragmentRowsResultBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRowsResultBinding.bind(view)

        binding?.run {

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}
