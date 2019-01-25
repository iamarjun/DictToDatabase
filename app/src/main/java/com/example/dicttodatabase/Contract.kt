package com.example.dicttodatabase

import com.example.dicttodatabase.database.Dictionary

interface Contract {

    //The main in name just represents that its the view interface for MainActivity
    interface MainView: BaseView<MainPresenter> {
        fun onSuccessAddingWordToDB()
        fun onErrorAddingWordToDB(error: String)
        fun onErrorFetchingHTML(message: String)
    }

    //The main in name just represents that its the presenter interface for MainActivity
    interface MainPresenter: BasePresenter {
        fun downloadWords()
        fun addWordToDB(dictionary: Dictionary)
    }

}