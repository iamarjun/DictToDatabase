package com.example.dicttodatabase

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.example.dicttodatabase.database.Dictionary
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity(), Contract.MainView {

    private lateinit var presenter: Contract.MainPresenter
    private lateinit var timer: Timer
    private var execTime = 0

    init {
        setPresenter(Presenter(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter.dropDB()
        exec_time.text = "exec_time = 0 sec"
        setupRecycler()
    }

    override fun onResume() {
        super.onResume()

        get_all_words.setOnClickListener {
            presenter.downloadWords()
            execTimer()
        }
        show_all_words.setOnClickListener { presenter.getAllWords() }
    }

    override fun progressBarVisibility(visibility: Boolean) {
        if (visibility)
            progress.visibility = View.VISIBLE
        else
            progress.visibility = View.GONE
    }

    override fun onSuccessAddingWordToDB(alphabet: String) {
        Toast.makeText(this, "Done Writing Words Starting With $alphabet", Toast.LENGTH_SHORT).show()
    }


    override fun onErrorAddingWordToDB(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }

    override fun getAllWords(allWords: List<Dictionary>) {
        recycler_view.adapter = DictionaryAdapter(allWords)
    }

    override fun onSuccesWritingAllWordsToDB() {
        Toast.makeText(this, "WRITING COMPLETED !!!", Toast.LENGTH_LONG).show()
        timer.cancel()
    }

    override fun setPresenter(presenter: Contract.MainPresenter) {
        this.presenter = presenter
    }

    private fun setupRecycler() {
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recycler_view.layoutManager = linearLayoutManager
        recycler_view.addItemDecoration(DividerItemDecoration(this, linearLayoutManager.orientation))
    }

    private fun execTimer() {
        timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    exec_time.text = "exec_time = $execTime sec"
                    execTime++
                }
            }
        }, 1000, 1000)

    }
}
