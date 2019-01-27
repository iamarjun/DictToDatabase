package com.example.dicttodatabase

import android.util.Log
import com.example.dicttodatabase.database.Dictionary
import com.example.dicttodatabase.database.DictionaryDatabase
import com.example.dicttodatabase.network.Client
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.regex.Pattern
import javax.inject.Inject


class Presenter(private val view: Contract.MainView) : Contract.MainPresenter {

    @Inject
    internal lateinit var client: Client

    private var db: DictionaryDatabase = DictionaryDatabase.getInstance()!!

    private val getAllWords: Disposable
        get() = Single.fromCallable<Unit> {
            for (i in 0 until 50) {
                Log.i("WORDS", db.dictionaryDAO().getAllMessages()[i].word)
                Log.i("SPEECHES", db.dictionaryDAO().getAllMessages()[i].speech)
                Log.i("MEANING", db.dictionaryDAO().getAllMessages()[i].meaning)
            }
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { it.printStackTrace() }
            .subscribe(Consumer<Unit> { })

    private val getLastWords: Disposable
        get() = Single.fromCallable<Unit> {
//            for (i in 0 until 50) {
//                Log.i("WORDS", db.dictionaryDAO().getLastEntries()[i].word)
//                Log.i("SPEECHES", db.dictionaryDAO().getLastEntries()[i].speech)
//                Log.i("MEANING", db.dictionaryDAO().getLastEntries()[i].meaning)
//            }

            Log.i("LAST RECORD", db.dictionaryDAO().getLastRecord().word)

        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { it.printStackTrace() }
            .subscribe(Consumer<Unit> { })


    private val nukeDB: Disposable
        get() = Single.fromCallable<Unit> { db.clearAllTables() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { it.printStackTrace() }
            .subscribe(Consumer<Unit> { })

    init {
        App.appComponent.inject(this)
    }


    override fun downloadWords() {

        var alphabet = 'a'
        view.progressBarVisibility(true)

        client.downloadFile("wb1913_$alphabet.html")
            .subscribeOn(Schedulers.io())
            .filter { html ->
                htmlParser(html)
                return@filter true
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<String>() {
                override fun onComplete() {
                }

                override fun onNext(t: String) {
                    alphabet++
                    view.progressBarVisibility(false)
                    view.onSuccessAddingWordToDB()
                }

                override fun onError(e: Throwable) {
                    view.progressBarVisibility(false)
                    view.onErrorAddingWordToDB(e.message.toString())
                }

            })
    }

    private fun htmlParser(html: String) {

        val matchedWords = ArrayList<String>()
        val matchedSpeeches = ArrayList<String>()
        val matchedMean = ArrayList<String>()

        val wrd = Pattern.compile("(<P><B>)(.*?)(<\\/B>)").matcher(html)
        val sp = Pattern.compile("(\\(<I>)(.*?)(<\\/I>\\))").matcher(html)
        val mn = Pattern.compile("(<\\/I>\\))(.*?)(<\\/P>)").matcher(html)


        while (wrd.find() && sp.find() && mn.find()) {
            matchedWords.add(wrd.group(2))
            matchedSpeeches.add(sp.group(2))
            matchedMean.add(mn.group(2))
        }

        val words = matchedWords.toTypedArray()
        val speeches = matchedSpeeches.toTypedArray()
        val meanings = matchedMean.toTypedArray()


        for (i in 0 until words.size) {
            db.dictionaryDAO().insert(Dictionary(0, words[i], speeches[i], meanings[i]))
        }

    }

    override fun dropDB() {
        nukeDB
    }

    override fun getAllWords() {
        getAllWords
    }

    override fun getLastWords() {
        getLastWords
    }

    override fun onAttach() {
    }

    override fun onDetach() {
    }
}