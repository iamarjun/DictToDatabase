package com.example.dicttodatabase

import android.util.Log
import com.example.dicttodatabase.database.Dictionary
import com.example.dicttodatabase.database.DictionaryDatabase
import com.example.dicttodatabase.network.ApiWrapper
import com.example.dicttodatabase.network.Client
import com.example.dicttodatabase.utils.CallBack
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.regex.Pattern
import javax.inject.Inject


class ApiCaller : ApiWrapper {

    @Inject
    internal lateinit var client: Client

    private var db: DictionaryDatabase = DictionaryDatabase.getInstance()!!


    init {
        App.appComponent.inject(this)
    }

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


    private val nukeDB: Disposable
        get() = Single.fromCallable<Unit> { db.clearAllTables() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { it.printStackTrace() }
            .subscribe(Consumer<Unit> { })

    override fun downloadFile(url: String, callBack: CallBack<String>) {

        nukeDB

        client.downloadFile(url)
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
                    callBack.onSuccess(t)
                    getAllWords
                }

                override fun onError(e: Throwable) {
                    callBack.onFailure(e.message.toString())
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


//
//
//


    }

}