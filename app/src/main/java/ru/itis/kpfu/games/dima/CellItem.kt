package ru.itis.kpfu.games.dima

import androidx.recyclerview.widget.RecyclerView
import ru.itis.kpfu.games.databinding.ItemSequenceCellBinding

class CellItem(
    private val binding: ItemSequenceCellBinding,
    private val onItemClick: (Cell) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(cell: Cell) {
        binding.run {
            tvNumber.text = cell.number.toString()
            root.setOnClickListener {
                onItemClick(cell)
            }
        }
    }
}