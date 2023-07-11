package ru.itis.kpfu.games.cherry

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import ru.itis.kpfu.games.Constants
import ru.itis.kpfu.games.Constants.PLAYER_FIRST
import ru.itis.kpfu.games.Constants.PLAYER_SECOND
import ru.itis.kpfu.games.R
import ru.itis.kpfu.games.databinding.FragmentRowsBinding


class RowsFragment : Fragment(R.layout.fragment_rows) {

    private val firstColor="#FFC107"
    private val secondColor="#6750A4"
    private val icons= listOf(R.drawable.cat_rows,R.drawable.devil_rows,R.drawable.compleated_cat_rows,R.drawable.compleated_devil_rows)
    private val circleList=arrayListOf(
        Circle(0, R.drawable.blank_rows),
        Circle(1, R.drawable.blank_rows),
        Circle(2, R.drawable.blank_rows),
        Circle(3, R.drawable.blank_rows),
        Circle(4, R.drawable.blank_rows),
        Circle(5, R.drawable.blank_rows),
        Circle(6, R.drawable.blank_rows),
        Circle(7, R.drawable.blank_rows),
        Circle(8, R.drawable.blank_rows),
        Circle(9, R.drawable.blank_rows),
        Circle(10, R.drawable.blank_rows),
        Circle(11, R.drawable.blank_rows),
        Circle(12, R.drawable.blank_rows),
        Circle(13, R.drawable.blank_rows),
        Circle(14, R.drawable.blank_rows),
        Circle(15, R.drawable.blank_rows),
        Circle(16, R.drawable.blank_rows),
        Circle(17, R.drawable.blank_rows),
        Circle(18, R.drawable.blank_rows),
        Circle(19, R.drawable.blank_rows),
        Circle(20, R.drawable.blank_rows),
        Circle(21, R.drawable.blank_rows),
        Circle(22, R.drawable.blank_rows),
        Circle(23, R.drawable.blank_rows),
        Circle(24, R.drawable.blank_rows),
        Circle(25, R.drawable.blank_rows),
        Circle(26, R.drawable.blank_rows),
        Circle(27, R.drawable.blank_rows),
        Circle(28, R.drawable.blank_rows),
        Circle(29, R.drawable.blank_rows),
        Circle(30, R.drawable.blank_rows),
        Circle(31, R.drawable.blank_rows),
        Circle(32, R.drawable.blank_rows),
        Circle(33, R.drawable.blank_rows),
        Circle(34, R.drawable.blank_rows),
        Circle(35, R.drawable.blank_rows),
        Circle(36, R.drawable.blank_rows),
        Circle(37, R.drawable.blank_rows),
        Circle(38, R.drawable.blank_rows),
        Circle(39, R.drawable.blank_rows),
        Circle(40, R.drawable.blank_rows),
        Circle(41, R.drawable.blank_rows),
    )

    private var binding: FragmentRowsBinding? = null
    private var preferences: SharedPreferences? = null
    private var adapter: CircleAdapter? = null
    private var mPlayer: MediaPlayer? = null
    private var m1Player: MediaPlayer? = null
    private var m2Player: MediaPlayer? = null

    private var turn: Int = 0
    private var isFinished: Boolean =false
    private var winner: String=""
    private var firstPlayer: String? = null
    private var secondPlayer: String? = null
    private var currentPlayer: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPlayer= MediaPlayer.create(context, R.raw.wow_rows)
        m1Player= MediaPlayer.create(context, R.raw.nyaa_rows)
        m2Player= MediaPlayer.create(context, R.raw.zvuk_rows)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRowsBinding.bind(view)
        preferences = requireActivity().getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE)

        startGame()
        initTable()

        binding?.run {

            btMenu.setOnClickListener {
                navigateMain()
            }

            btEnd.setOnClickListener {
                navigateResults()
            }

        }

    }

    private fun startGame(){
        firstPlayer = preferences?.getString(PLAYER_FIRST, "")
        secondPlayer = preferences?.getString(PLAYER_SECOND, "")
        currentPlayer = firstPlayer
        binding?.run {
            setName(tvTurn, currentPlayer)
            btEnd.text = validateBtEndText()
        }
    }

    private fun addCircle(id: Int) {
        if(!isFinished) {
            val i = RowsGameLogic.addCircle(circleList,id)
            if(i!=null){
                if(turn%2==0){
                    m1Player?.start()
                } else{
                    m2Player?.start()
                }
                circleList[i].imageId = icons[turn%2]
                validateWin()
                if(!isTableFull()) {
                    turn += 1
                    passMove()
                }
            }
        }
    }

    private fun isTableFull() : Boolean{
        val flag=RowsGameLogic.isFull(circleList)
        if(RowsGameLogic.isFull(circleList)){
            isFinished=true
            binding?.run {
                tvTurn.text=getString(R.string.full_rows)
                winner=""
                btEnd.text = validateBtEndText()
            }
        }
        return flag
    }

    private fun passMove(){
        if(!isFinished) {
            currentPlayer = if (currentPlayer == firstPlayer) {
                secondPlayer
            } else {
                firstPlayer
            }
        }

        binding?.run {
            setName(tvTurn, currentPlayer)
            btEnd.text = validateBtEndText()
        }
    }

    private fun validateWin(){
        val l=RowsGameLogic.checkWin(circleList,turn%2)
        if(l[0]>=0){
            val str=currentPlayer
            if (str != null) {
                winner=str
            }
            isFinished=true
            highlightWinner(l)
            m1Player?.stop()
            m2Player?.stop()
            mPlayer?.start()
        }
    }

    private fun highlightWinner(list: List<Int>){
        for (element in list){
            circleList[element].imageId=icons[turn%2+2]
        }
    }

    private fun validateBtEndText():String{
        return if (!isFinished){
            getString(R.string.end_game_rows_defeat)
        } else{
            getString(R.string.end_game_rows)
        }
    }

    private fun setName(
        title: TextView,
        name: String?,
    ) {
        val turn1:Int = if(!isFinished){
            turn
        }else{
            turn-1
        }

        val i:String = if((turn1)%2==0){
            firstColor
        } else{
            secondColor
        }
        var str=""
        if(!isFinished) {
            str = name + getString(R.string.turn_name_rows)
        } else{
            str=name+" "+getString(R.string.winner_rows)
        }
        title.text = str
        title.setTextColor(Color.parseColor(i))
    }

    private fun initTable(){
        adapter = CircleAdapter(circleList) {
            addCircle(it.id)
            adapter?.notifyDataSetChanged()
        }
        binding?.apply {
            rvTable.layoutManager=GridLayoutManager(requireContext(), 7)
            rvTable.adapter=adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        turn=0
        isFinished=false
    }

    private fun navigateResults() {
        mPlayer?.stop()

        val bundle = Bundle()
        if(!isFinished){
            currentPlayer = if (currentPlayer == firstPlayer) {
                secondPlayer
            } else {
                firstPlayer
            }
            val str=currentPlayer
            if (str != null) {
                winner=str
            }
        }
        val i:String = if((turn-1)%2==0){
            firstColor
        } else{
            secondColor
        }
        bundle.putString(ARG_COLOR,i)
        bundle.putString(ARG_WINNER, winner)
        if(turn%2==0){
            bundle.putInt(ARG_CHIPS, turn/2)
        }else{
            bundle.putInt(ARG_CHIPS, turn/2+1)
        }
        findNavController().navigate(R.id.action_rowsFragment_to_rowsResultFragment,
            bundle
        )
    }

    private fun navigateMain() {
        mPlayer?.stop()

        preferences?.edit {
            putString(Constants.GAME, "")
            commit()
        }
        findNavController().navigate(R.id.action_rowsFragment_to_startFragment)
    }

    companion object {
        private const val ARG_WINNER = "Winner"
        private const val ARG_CHIPS = "Chips"
        private const val ARG_COLOR = "Color"
    }

}


