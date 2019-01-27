package com.example.dicttodatabase

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dicttodatabase.database.Dictionary
import kotlinx.android.synthetic.main.layout_dictionary.view.*

class DictionaryAdapter(private val dict: List<Dictionary>) :
    RecyclerView.Adapter<DictionaryAdapter.DictionaryViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): DictionaryViewHolder {

        val view = LayoutInflater.from(p0.context).inflate(R.layout.layout_dictionary, p0, false)
        return DictionaryViewHolder(view)
    }

    override fun getItemCount(): Int = dict.size

    override fun onBindViewHolder(p0: DictionaryViewHolder, p1: Int) {

        p0.mWord.text = dict[p1].word
        p0.mSpeech.text = dict[p1].speech
        p0.mMeaning.text = dict[p1].meaning
    }

    inner class DictionaryViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val mWord = view.word
        val mSpeech = view.speech
        val mMeaning = view.meaning

    }
}