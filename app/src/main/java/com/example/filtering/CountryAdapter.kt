package com.example.filtering

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.filtering.databinding.CountryItemBinding
import java.util.*
import kotlin.collections.ArrayList

//extends recyclerview.adapter, implements filterable
class CountryAdapter(
    private val context: Context,
    private var countryList: ArrayList<String>
) : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>(), Filterable{

    private var countryFilterList = ArrayList<String>()

    inner class CountryViewHolder(var view: CountryItemBinding): RecyclerView.ViewHolder(view.root)

    init {
        countryFilterList = countryList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val binding  = CountryItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return CountryViewHolder(binding)
    }

    override fun onBindViewHolder(holder:CountryViewHolder, position: Int) {
        holder.view.tvCountry.text = countryFilterList[position]
    }

    override fun getItemCount(): Int = countryFilterList.size

    override fun getFilter(): Filter {
        return object: Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint.toString()
                if(charString.isEmpty()){
                    countryFilterList = countryList
                }else{
                    val filteredList= ArrayList<String>()
                    for (row in countryList){
                        if(row.lowercase(Locale.ROOT).contains(charString.lowercase(Locale.ROOT)))
                            filteredList.add(row)
                    }
                    countryFilterList = filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = countryFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                countryFilterList = results?.values as ArrayList<String>
                notifyDataSetChanged()
            }
        }

    }

}

//inner class (val/var xx) vs inner class (xx)
//val/var -> can be used, without val/var -> cant be used