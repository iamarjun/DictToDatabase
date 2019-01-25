package com.example.dicttodatabase

import com.example.dicttodatabase.database.DatabaseHandler
import com.example.dicttodatabase.database.Dictionary
import com.example.dicttodatabase.database.DictionaryDatabase
import com.example.dicttodatabase.utils.CallBack
import com.example.dicttodatabase.utils.DBCallBack
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.regex.Pattern
import javax.inject.Inject


class Presenter(private val view: Contract.MainView) : Contract.MainPresenter {
    override fun addWordToDB(dictionary: Dictionary) {
    }

    @Inject
    internal lateinit var repository: Repository

    @Inject
    internal lateinit var apiCaller: ApiCaller

    private var db: DictionaryDatabase = DictionaryDatabase.getInstance()!!
    private var databaseHandler: DatabaseHandler


    init {
        App.appComponent.inject(this)
        databaseHandler = DatabaseHandler(App.appContext)
    }

    override fun downloadWords() {

//        var alphabet = 'a'
//        while (alphabet <= 'z') {
//
//        }

        databaseHandler.removeData()

        apiCaller.downloadFile("wb1913_a.html", object : CallBack<String> {
            override fun onSuccess(t: String) {
                //val m = t


                //htmlParser(t)
                //alphabet++
                view.onSuccessAddingWordToDB()
            }

            override fun onFailure(message: String) {
                view.onErrorFetchingHTML(message)
            }

        })


//        var alphabet = 'a'
//        while (alphabet <= 'z') {
//
//            disposable = Single.fromCallable<Unit> {
//                this.downloadWordsFromUrl("http://www.mso.anu.edu.au/~ralph/OPTED/v003/wb1913_${alphabet}.html")
//            }
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnError { it.printStackTrace() }
//                .subscribe(Consumer<Unit> { this.onResult() })
//            alphabet++
//        }
//
//        compositeDisposable.add(disposable)
//        compositeDisposable.dispose()

    }



    fun addWord(DBCallBack: DBCallBack, dictionary: Dictionary) {
        Completable.fromAction { db.dictionaryDAO().insert(dictionary) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : CompletableObserver {
                override fun onComplete() {
                    DBCallBack.onWordAdded()
                    db.endTransaction()
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    DBCallBack.onWordNotAvailable(e.message.toString())
                }

            })
    }


    override fun onAttach() {
    }

    override fun onDetach() {
    }
}