package ru.itis.kpfu.games.dima

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.itis.kpfu.games.databinding.ItemSequenceCellBinding

class CellAdapter(
    private var cellList: List<Cell>,
    private val onItemClick: (Cell) -> Unit
) : RecyclerView.Adapter<CellItem>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CellItem {
        return CellItem(
            ItemSequenceCellBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onItemClick
        )
    }

    override fun getItemCount(): Int {
        return cellList.size
    }

    override fun onBindViewHolder(holder: CellItem, position: Int) {
        holder.onBind(cellList[position])
    }
}