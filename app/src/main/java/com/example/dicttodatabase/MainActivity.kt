package com.example.dicttodatabase

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), Contract.MainView {

    private lateinit var presenter: Contract.MainPresenter

    init {
        setPresenter(Presenter(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter.dropDB()
    }

    override fun onResume() {
        super.onResume()
        presenter.downloadWords()
    }

    override fun progressBarVisibility(visibility: Boolean) {
        if (visibility)
            progress.visibility = View.VISIBLE
        else
            progress.visibility = View.GONE
    }

    override fun onSuccessAddingWordToDB() {
        val currentDBPath = getDatabasePath("dictionary.db").absolutePath
        Toast.makeText(this, currentDBPath, Toast.LENGTH_LONG).show()
    }


    override fun onErrorAddingWordToDB(error: String) {
    }

    override fun setPresenter(presenter: Contract.MainPresenter) {
        this.presenter = presenter
    }

}
