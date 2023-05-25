package com.example.rickmortyxml

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.android.volley.Request
import com.example.rickmortyxml.databinding.ActivityMainBinding
import org.json.JSONObject

class MainActivity : AppCompatActivity(),CharacterAdapter.Listener {

    lateinit var binding: ActivityMainBinding
    private var adapter=CharacterAdapter(this)

    var charactersList: ArrayList<Character> = ArrayList<Character>()





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getAllCharacters()

        binding.searchText?.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(cs: CharSequence, arg1: Int, arg2: Int, arg3: Int) {
                var searchedList=ArrayList<com.example.rickmortyxml.Character>()
                var searchName=binding.searchText.text.toString()
                if(searchName!=""){
                    for (i in charactersList){
                        if(i.getName().toLowerCase().contains(searchName.toLowerCase())){
                            searchedList.add(i)
                        }
                    }
                    updateCharacters(searchedList)
                }

            }

            override fun beforeTextChanged(s: CharSequence, arg1: Int, arg2: Int, arg3: Int) {}
            override fun afterTextChanged(arg0: Editable) {

            }
        })





    }

    private fun getAllCharacters(){
        var pagescount=0
        val queue= Volley.newRequestQueue(this)
        //Запрос на получение кол-ва страниц
        val stringRequest = StringRequest(Request.Method.GET,
            "https://rickandmortyapi.com/api/character",
            {
                    response->
                val obj = JSONObject(response).getJSONObject("info")
                pagescount = obj.getInt("pages")
                Log.d("MyLog","Pages count:${pagescount}")
                for (i in 1..pagescount){
                    Log.d("MyLog","Цикл# :${i}")
                    getCharactersOnPage(i)

                }

                Log.d("MyLog","characters count:${charactersList.size.toString()}")
            },
            {
                Log.d("MyLog","Volley error: $it")
            }
        )
        queue.add(stringRequest)


    }
    private fun getCharactersOnPage(page:Int){
        val url="https://rickandmortyapi.com/api/character/?page=$page"
        val queue= Volley.newRequestQueue(this)
        val stringRequest = StringRequest(Request.Method.GET,
            url,
            {
                    response->
                val obj = JSONObject(response).getJSONArray("results")
                for (i in 0..obj.length()-1){
                    val personFromJson=obj.getJSONObject(i)
                    charactersList.add(
                        Character(personFromJson.getInt("id"), personFromJson.getString("name"), personFromJson.getString("image"))
                    )
                    Log.d("MyLog","Кол-во героев:${charactersList.size}")
                    updateCharacters(charactersList)
                }
            },
            {
                Log.d("MyLog","Volley error: $it")
            }
        )
        queue.add(stringRequest)
    }

    private fun updateCharacters(data:ArrayList<com.example.rickmortyxml.Character>){
        binding.apply {
            recycleView.layoutManager=GridLayoutManager(this@MainActivity,2)
            recycleView.adapter=adapter
            adapter.updateCharacters(data)
        }
    }

    override fun onClickCharacter(character: Character) {
        val myIntent = Intent(this@MainActivity, PageCharacter::class.java)
        myIntent.putExtra("id", character.getId().toInt())
        startActivity(myIntent)
    }


}