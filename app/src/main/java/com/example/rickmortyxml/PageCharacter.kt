package com.example.rickmortyxml

import android.R
import android.content.res.Resources
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import coil.load
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.rickmortyxml.databinding.CharacterPageBinding
import org.json.JSONObject


class PageCharacter() : AppCompatActivity() {

    lateinit var binding: CharacterPageBinding
    var id=1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=CharacterPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mIntent=intent
        id = mIntent.getIntExtra("id",1)
        val url="https://rickandmortyapi.com/api/character/$id"
        val queue= Volley.newRequestQueue(this)
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            {
                    response->
                val obj = JSONObject(response)
                binding.nameTitle.text=obj.getString("name")
                binding.status.text="Status: "+obj.getString("status")
                binding.race.text="Race: "+obj.getString("species")
                binding.gender.text="Gender: "+obj.getString("gender")
                binding.origin.text="Origin: "+obj.getJSONObject("origin").getString("name")
                binding.date.text="Created: "+obj.getString("created").toString().slice(0..9)
                binding.image.load(obj.getString("image"))
                if(obj.getString("status")=="Alive"){
                    binding.stround.getBackground()?.setTint(Color.parseColor("#1fd537"))

                }else if(obj.getString("status")=="Dead") {binding.stround.getBackground()?.setTint(Color.parseColor("#F3000C"))}else binding.stround.getBackground()?.setTint(Color.parseColor("#D8D8D8"))
            },
            {
                Log.d("MyLog","Volley error: $it")
            }
        )
        queue.add(stringRequest)
    }

}