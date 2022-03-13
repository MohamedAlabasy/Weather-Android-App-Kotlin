package com.elabasy.MyWeatherApp.Ui.Activities.SingleCity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.elabasy.MyWeatherApp.Ui.Activities.Home.DailyRecyclerViewAdapter
import com.elabasy.MyWeatherApp.Ui.Activities.Home.HomeActivity
import com.elabasy.MyWeatherApp.Util.DataTesttt
import com.elabasy.MyWeatherApp.databinding.ActivitySingleCountryBinding

class SingleCityActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySingleCountryBinding

    // for Daily Recycler View
    private lateinit var dailyAdapter: DailyRecyclerViewAdapter
    private lateinit var dailyLayout: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingleCountryBinding.inflate(layoutInflater)
        setContentView(binding.root)





        binding.back.setOnClickListener { onBackPressed() }
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

    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this@SingleCityActivity, HomeActivity::class.java))
        finish()
    }
}