package com.fajarikasaputra.agender.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.fajarikasaputra.agender.R
import com.fajarikasaputra.agender.api.ApiConfig
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.fajarikasaputra.agender.MortyAdapter
import com.fajarikasaputra.agender.ResponseMorty
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomepageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.homepage)

        val morty = findViewById<RecyclerView>(R.id.rv_morty)

        ApiConfig.getService().getPhotos().enqueue(object : Callback<ResponseMorty> {
            override fun onResponse(
                call: Call<ResponseMorty>,
                response: Response<ResponseMorty>
            ) {
                if (response.isSuccessful) {
                    val responseMorty = response.body()
                    val dataMorty = responseMorty?.results
                    val mortyAdapter = MortyAdapter(dataMorty)
                    morty.apply {
                        layoutManager = LinearLayoutManager(this@HomepageActivity)
                        setHasFixedSize(true)
                        mortyAdapter.notifyDataSetChanged()
                        adapter = mortyAdapter
                    }
                }
            }

            override fun onFailure(call: Call<ResponseMorty>, t: Throwable) {
                Toast.makeText(applicationContext, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }
}