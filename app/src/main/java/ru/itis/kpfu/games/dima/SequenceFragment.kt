package ru.itis.kpfu.games.dima

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.itis.kpfu.games.Constants
import ru.itis.kpfu.games.R
import ru.itis.kpfu.games.databinding.FragmentSequenceBinding

@Suppress("DEPRECATION")
class SequenceFragment : Fragment(R.layout.fragment_sequence) {
    private var adapter: CellAdapter? = null
    private var binding: FragmentSequenceBinding? = null
    private var preferences: SharedPreferences? = null
    private var field: List<Cell> = BaseGameField.createThreeByThree()
    private var from: Int = 1
    private var until: Int = 9
    private var time: Long = 5000
    private var matchCount = 1
    private var difficultLevel = 1
    private var rightOrder: MutableList<Int> = mutableListOf()
    private var firstPlayer: String? = null
    private var secondPlayer: String? = null
    private var firstPlayerScore = 0
    private var secondPlayerScore = 0
    private var currentPlayer: String? = null
    private var isFirstPlayerWin = false
    private var isSecondPlayerWin = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSequenceBinding.bind(view)
        preferences =
            requireActivity().getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE)
        initAdapter()
        initFragment()
        binding?.run {
            btStart.setOnClickListener {
                btPassMove.visibility = View.VISIBLE
                btStart.visibility = View.INVISIBLE
                rvGameField.visibility = View.VISIBLE
                tvStatus.visibility = View.VISIBLE
                setMatch()
            }
            btPassMove.setOnClickListener {
                if (currentPlayer == firstPlayer) {
                    if (isFirstPlayerWin) {
                        firstPlayerScore += 10 * difficultLevel
                        tvFirstPlayerScoreRes.text = firstPlayerScore.toString()
                    }
                    passMove()
                } else {
                    if (isSecondPlayerWin) {
                        secondPlayerScore += 10 * difficultLevel
                        tvSecondPlayerScoreRes.text = secondPlayerScore.toString()
                        if (isFirstPlayerWin) {
                            setNextRound()
                        } else {
                            val bundle = createBundle(secondPlayer, secondPlayerScore)
                            findNavController().navigate(
                                R.id.action_sequenceFragment_to_sequenceResultFragment,
                                bundle
                            )
                        }
                    } else {
                        if (isFirstPlayerWin) {
                            val bundle = createBundle(firstPlayer, firstPlayerScore)
                            findNavController().navigate(
                                R.id.action_sequenceFragment_to_sequenceResultFragment,
                                bundle
                            )
                        } else {
                            setNextRound()
                        }
                    }
                }
            }
            btEndGame.setOnClickListener {
                findNavController().navigate(R.id.action_sequenceFragment_to_startFragment)
            }
        }
    }

    private fun setNextRound() {
        if (isFirstPlayerWin && isSecondPlayerWin) {
            difficultLevel++
            recalculateConst()
        }
        matchCount++
        passMove()
        isFirstPlayerWin = false
        isSecondPlayerWin = false
    }

    private fun passMove() {
        currentPlayer = if (currentPlayer == firstPlayer) {
            secondPlayer
        } else {
            firstPlayer
        }
        binding?.run {
            tvCurrentPlayerRes.text = currentPlayer
            btPassMove.visibility = View.INVISIBLE
            btStart.visibility = View.VISIBLE
            rvGameField.visibility = View.INVISIBLE
            tvStatus.visibility = View.INVISIBLE
            btPassMove.text = resources.getString(R.string.surrender)
        }

    }

    private fun setMatch() {
        binding?.tvStatus?.text = resources.getString(R.string.get_ready)
        binding?.btPassMove?.isEnabled = false
        setFieldEnable(false)
        initField()
        showField()
        Handler(Looper.getMainLooper()).postDelayed({
            hideField()
            setFieldEnable(true)
            binding?.btPassMove?.isEnabled = true
            binding?.tvStatus?.text = resources.getString(R.string.start)
        }, time)


    }

    private fun recalculateConst() {
        time -= (50 + 10 * difficultLevel)
        from += difficultLevel
        until += 5 * difficultLevel
    }

    private fun initFragment() {
        firstPlayer = preferences?.getString(Constants.PLAYER_FIRST, "")
        secondPlayer = preferences?.getString(Constants.PLAYER_SECOND, "")
        currentPlayer = firstPlayer
        binding?.run {
            tvFirstPlayerRes.text = firstPlayer
            tvCurrentPlayerRes.text = currentPlayer
            tvSecondPlayerRes.text = secondPlayer
            tvFirstPlayerScoreRes.text = firstPlayerScore.toString()
            tvSecondPlayerScoreRes.text = secondPlayerScore.toString()
        }
    }

    private fun initField() {
        var i = 0
        rightOrder = (from..until).shuffled().take(field.size).map {
            field[i].number = it
            i++
            it
        }.sorted().toMutableList()
    }

    private fun showField() {
        var i = 0
        binding?.rvGameField?.forEach { view ->
            view.findViewById<TextView>(R.id.tv_Number).text = field[i].number.toString()
            view.findViewById<ConstraintLayout>(R.id.cl_cell)
                .setBackgroundColor(resources.getColor(R.color.sequence_cell))
            i++
        }
    }

    private fun hideField() {
        binding?.rvGameField?.forEach { view ->
            view.findViewById<TextView>(R.id.tv_Number).text = ""
        }
    }

    private fun setFieldEnable(isEnable: Boolean) {
        binding?.rvGameField?.forEach { view -> view.isEnabled = isEnable }
    }

    private fun initAdapter() {
        adapter = CellAdapter(
            field
        ) { cell: Cell ->
            val adapt =
                binding?.rvGameField?.findViewHolderForAdapterPosition(cell.row * cell.fieldSize + cell.col)?.itemView
            val tv = adapt?.findViewById<TextView>(R.id.tv_Number)
            val cl = adapt?.findViewById<ConstraintLayout>(R.id.cl_cell)
            binding?.tvStatus?.text = ""
            tv?.text = cell.number.toString()
            if (rightOrder[0] != cell.number) {
                cl?.setBackgroundColor(resources.getColor(R.color.sequence_mistake))
                binding?.btPassMove?.text = resources.getString(R.string.pass_move)
                setFieldEnable(false)
            } else {
                if (rightOrder.size - 1 == 0) {
                    setFieldEnable(false)
                    binding?.btPassMove?.text = resources.getString(R.string.pass_move)
                    if (currentPlayer == firstPlayer) {
                        isFirstPlayerWin = true
                    } else {
                        isSecondPlayerWin = true
                    }
                }
                rightOrder.removeAt(0)
                adapt?.isEnabled = false
                cl?.setBackgroundColor(resources.getColor(R.color.sequence_right))

            }
        }
        binding?.rvGameField?.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        from = 1
        until = 9
        difficultLevel = 1
        matchCount = 1
        firstPlayerScore = 0
        secondPlayerScore = 0
        time = 5000
    }

    companion object {
        const val ARG_LEVEL = "ARG_LEVEL"
        const val ARG_GAME_WINNER = "ARG_GAME_WINNER"
        const val ARG_TIME_RANGE = "ARG_TIME_RANGE"
        const val ARG_NUMBER_RANGE = "ARG_NUMBER_RANGE"
        const val ARG_SCORE = "ARG_SCORE"
    }

    private fun createBundle(gameWinner: String?, score: Int): Bundle {
        val bundle = Bundle()
        bundle.putString(ARG_GAME_WINNER, gameWinner)
        bundle.putString(ARG_SCORE, score.toString())
        bundle.putString(ARG_NUMBER_RANGE, "$from - $until")
        bundle.putString(ARG_TIME_RANGE, time.toString())
        bundle.putString(ARG_LEVEL, difficultLevel.toString())
        return bundle
    }

}
