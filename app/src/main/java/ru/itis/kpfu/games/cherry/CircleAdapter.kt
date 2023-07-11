package ru.itis.kpfu.games.cherry

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.itis.kpfu.games.R
import ru.itis.kpfu.games.databinding.ItemCircleRowsBinding

class CircleAdapter(private var circleList: ArrayList<Circle>, private val onItemClick: (Circle) -> Unit) :RecyclerView.Adapter<CircleAdapter.CircleHolder>(){

    class CircleHolder(item: View, private val onItemClick: (Circle) -> Unit,) : RecyclerView.ViewHolder(item){
        val binding=ItemCircleRowsBinding.bind(item)
        fun bind(circle:Circle) = with(binding){
            imCircle.setImageResource(circle.imageId)
            root.setOnClickListener{
                onItemClick(circle)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CircleHolder {
        val view =LayoutInflater.from (parent.context).inflate(R.layout.item_circle_rows, parent, false)
        return CircleHolder(view, onItemClick)
    }

    override fun getItemCount(): Int {
        return circleList.size
    }

    override fun onBindViewHolder(holder: CircleHolder, position: Int) {
        holder.bind(circleList[position])
    }
}


