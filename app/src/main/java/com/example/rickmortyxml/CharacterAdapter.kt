package com.example.rickmortyxml

import android.app.LauncherActivity.ListItem
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rickmortyxml.databinding.ListItemBinding
import coil.load

class CharacterAdapter(val listener:Listener):RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {

    var CharacterList=ArrayList<com.example.rickmortyxml.Character>()
    class ViewHolder(item:View):RecyclerView.ViewHolder(item) {
        val binding=ListItemBinding.bind(item)
        fun bind(character:com.example.rickmortyxml.Character,listener: Listener)= with(binding){
            name.text=character.getName()
            image.load(character.getImage())
            itemView.setOnClickListener {
                listener.onClickCharacter(character)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return CharacterList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(CharacterList[position],listener)
    }

    public fun updateCharacters(newList:ArrayList<com.example.rickmortyxml.Character>){
        CharacterList=newList
        notifyDataSetChanged()
    }

    interface Listener{
        fun onClickCharacter(character:com.example.rickmortyxml.Character)
    }


}