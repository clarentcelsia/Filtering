package com.example.filtering

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filtering.databinding.ActivityMainBinding
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var countryAdapter: CountryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupRecyclerView(getCountryList())
        setupSearchView()

    }

    private fun getCountryList(): ArrayList<String>{
        val countryLists = ArrayList<String>()
        val countryCode = Locale.getISOCountries()

        for (mCountryCode in countryCode) {
            val locale = Locale("", mCountryCode)
            val countryName = locale.displayCountry
            countryLists.add("$countryName")
        }

       return countryLists

    }

    private fun setupRecyclerView(countryList: ArrayList<String>){
        countryAdapter = CountryAdapter(this, countryList)
        binding.rvCountry.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter = countryAdapter
        }
    }

    private fun setupSearchView(){
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        binding.svCountry.apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            maxWidth = Integer.MAX_VALUE
        }

        binding.svCountry.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                countryAdapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                countryAdapter.filter.filter(newText)
                return false
            }
        })
    }
}