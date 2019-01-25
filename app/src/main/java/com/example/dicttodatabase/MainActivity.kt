package com.example.dicttodatabase

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.dicttodatabase.database.DatabaseHandler
import com.example.dicttodatabase.utils.DBCallBack

class MainActivity : AppCompatActivity(), Contract.MainView {

    private lateinit var presenter: Contract.MainPresenter

    init {
        setPresenter(Presenter(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        presenter.downloadWords()
    }

    override fun onSuccessAddingWordToDB() {

        val currentDBPath = getDatabasePath("dictionary.db").absolutePath
        Toast.makeText(this, currentDBPath, Toast.LENGTH_LONG).show()

    }

    override fun onErrorAddingWordToDB(error: String) {
    }

    override fun onErrorFetchingHTML(message: String) {
    }

    override fun setPresenter(presenter: Contract.MainPresenter) {
        this.presenter = presenter
    }

}
