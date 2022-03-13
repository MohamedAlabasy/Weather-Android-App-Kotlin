package com.elabasy.MyWeatherApp.Ui.Activities.Home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.elabasy.MyWeatherApp.Interface.OnItemRecyclerViewClickListener
import com.elabasy.MyWeatherApp.R
import com.elabasy.MyWeatherApp.Ui.Activities.SingleCity.SingleCityActivity
import com.elabasy.MyWeatherApp.Util.DataTesttt
import com.elabasy.MyWeatherApp.databinding.ActivityHomeBinding
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    // for Daily Recycler View
    private lateinit var dailyAdapter: DailyRecyclerViewAdapter
    private lateinit var dailyLayout: GridLayoutManager

    // for Country Recycler View
    private lateinit var cityAdapter: CityRecyclerViewAdapter
    private lateinit var cityLayout: GridLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cityName = intent.getStringExtra("CityName")
        Toast.makeText(this, "City Name :$cityName", Toast.LENGTH_SHORT).show()

        val dailyArrayList = ArrayList<DataTesttt>()
        dailyArrayList.add(DataTesttt("0"))
        dailyArrayList.add(DataTesttt("0"))
        dailyArrayList.add(DataTesttt("0"))
        dailyArrayList.add(DataTesttt("0"))
        dailyArrayList.add(DataTesttt("0"))



        dailyAdapter = DailyRecyclerViewAdapter(null, this)
        dailyAdapter.setList(dailyArrayList)
        binding.daysRecyclerView.adapter = dailyAdapter
        dailyLayout = GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false)
        binding.daysRecyclerView.layoutManager = dailyLayout


        // for Country Recycler View
        val cityArrayList = ArrayList<DataTesttt>()
        cityArrayList.add(DataTesttt("0"))
        cityArrayList.add(DataTesttt("0"))
        cityArrayList.add(DataTesttt("0"))
        cityArrayList.add(DataTesttt("0"))
        cityArrayList.add(DataTesttt("0"))



        cityAdapter = CityRecyclerViewAdapter(null, this)
        cityAdapter.setList(cityArrayList)
        binding.countryRecyclerView.adapter = cityAdapter
        cityLayout = GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false)
        binding.countryRecyclerView.layoutManager = cityLayout

        //for Item Click
        cityAdapter.setOnItemClick(object : OnItemRecyclerViewClickListener {
            override fun setOnItemClickListener(position: Int) {
                startActivity(Intent(this@HomeActivity, SingleCityActivity::class.java))
                finish()
            }
        })

        //for DeleteL items
        cityAdapter.setOnDeleteItem(object : OnItemRecyclerViewClickListener {
            override fun setOnItemClickListener(position: Int) {
                cityAdapter.remove(cityArrayList[position])
                if (cityArrayList.isEmpty() || cityArrayList.size == 0) {
                    binding.cityName.visibility = View.GONE
                    binding.viewCountry.visibility = View.GONE
                    binding.countryRecyclerView.visibility = View.GONE
                }
            }
        })


        //For Auto Complete Text
        val adapter = ArrayAdapter(
            this, android.R.layout.simple_list_item_1, resources.getStringArray(R.array.CITIES))
        binding.autoTextView.setAdapter(adapter)


        binding.autoTextView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val selectedCity = parent.getItemAtPosition(position).toString()
                val intent = Intent(this@HomeActivity, SingleCityActivity::class.java)
                intent.putExtra("CityName", selectedCity)
                startActivity(intent)
                finish()
            }
    }


}