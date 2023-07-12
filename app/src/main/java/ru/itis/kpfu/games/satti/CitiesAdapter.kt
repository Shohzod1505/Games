package ru.itis.kpfu.games.satti
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.itis.kpfu.games.R

class CitiesAdapter : RecyclerView.Adapter<CitiesAdapter.CityViewHolder>() {

    private val citiesList = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_city, parent, false)
        return CityViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val city = citiesList[position]
        holder.bind(city)
    }

    override fun getItemCount(): Int {
        return citiesList.size
    }

    fun setData(cities: List<String>) {
        citiesList.clear()
        citiesList.addAll(cities)
        notifyDataSetChanged()
    }

    inner class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewCity: TextView = itemView.findViewById(R.id.textViewCity)

        fun bind(city: String) {
            textViewCity.text = city
        }
    }
}
