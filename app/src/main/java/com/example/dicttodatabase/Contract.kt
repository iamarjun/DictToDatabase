package com.example.dicttodatabase

import com.example.dicttodatabase.database.Dictionary

interface Contract {

    //The main in name just represents that its the view interface for MainActivity
    interface MainView: BaseView<MainPresenter> {
        fun onSuccessAddingWordToDB(alphabet: String)
        fun onErrorAddingWordToDB(error: String)
        fun progressBarVisibility(visibility: Boolean)
        fun getAllWords(allWords: List<Dictionary>)
        fun onSuccesWritingAllWordsToDB()
    }

    //The main in name just represents that its the presenter interface for MainActivity
    interface MainPresenter: BasePresenter {
        fun downloadWords()
        fun dropDB()
        fun getAllWords()
        fun getLastWords()
    }

}