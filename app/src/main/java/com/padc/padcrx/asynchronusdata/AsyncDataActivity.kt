package com.padc.padcrx.asynchronusdata

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.padc.padcrx.R
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_fullscreen.*

@SuppressLint("StaticFieldLeak")
class AsyncDataActivity : AppCompatActivity() {
    private val mHideHandler = Handler()

    private val mHidePart2Runnable = Runnable {
        // Delayed removal of status and navigation bar

        // Note that some of these constants are new as of API 16 (Jelly Bean)
        // and API 19 (KitKat). It is safe to use them, as they are inlined
        // at compile-time and do nothing on earlier devices.
        container.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LOW_PROFILE or
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fullscreen)
        hide()

        // 1. synchronous data manipulation
        synchronousData()

        // 2. synchronous data with blocking method encapsulation
        //synchronousDataWithBlockingSum()

        // 3. synchronous data with long blocking method start blocking UI Thread
        //synchronousDataWithLongBlockingSum()

        // 4. asynchronous data for long blocking method's code
        //aynschronusDataWithLongBlockingSum()

        // 5. asynchronous data with Android's AsyncTask
        //asyncTaskWithBlockingSum()

        // 6. asynchronous data when blocking to get simple one
        //asyncTaskWithThreeBlockingSum()

        // 7. asynchronous data with concurrency
        //concurrencySumWithAsyncTask()

        // 8. Rx observables creation
        //helloRx()
        //from()
        //range()
        //interval()
        //create()

        // 9. Rx operators
        //filter()
        //map()
        //flatMap()

        // 10. Rx multi-threading
        //subScribeOn()
        //observeOn()
        //defaultScheduler()

        // 11. asynchronous sum with Rx
        asynchronousSumWithRx()

        // 12. asychronous stream with Rx
        asynchronousStreamCombination()

        // 13. error handling
        errorHandling()

    }

    private fun errorHandling() {

    }

    private fun asynchronousStreamCombination() {

    }

    private fun asynchronousSumWithRx() {

    }

    private fun defaultScheduler() {

    }

    private fun observeOn() {

    }

    private fun subScribeOn() {

    }

    private fun flatMap() {

    }

    private fun map() {

    }

    private fun filter() {

    }

    private fun create() {

    }

    private fun interval() {

    }

    private fun range() {

    }

    private fun from() {

    }

    private fun helloRx() {
        // Create
        val observable = Observable.just("Hello", "Reactive Extensions", "with", "RxJava")

        val observer = object : Observer<String> {

            override fun onComplete() {
                Log.d("Rx", "onComplete: ")
                //An Observable calls this method after it has called onNext for the final time,
                // if it has not encountered any errors.
            }

            override fun onSubscribe(d: Disposable) {
                Log.d("Rx", "onSubscribe: ")
                //An Observable calls this method before emission of first item
                //Disposable is used to unsubscribe from this observable(stream)
                //when we don't need data anymore.
            }

            override fun onNext(t: String) {
                Log.d("Rx", "onNext: $t")
                //An Observable calls this method whenever the Observable emits an item.
                // This method takes as a parameter the item emitted by the Observable.
            }

            override fun onError(e: Throwable) {
                Log.d("Rx", "onError: ")
                //An Observable calls this method to indicate that it has failed to generate
                // the expected data or has encountered some other error.
                // It will not make further calls to onNext or onCompleted.
                // The onError method takes as its parameter an indication of what caused the error.
            }
        }

        // Listen a observable stream
        observable.subscribe(observer)
    }

    private fun synchronousData() {

        val firstNumber = 1
        tvFirstNum.showInt(firstNumber)

        val secondNumber = 1
        tvSecondNum.showInt(secondNumber)

        // start counting time ------------
        chronometer.start()

        val result = firstNumber + secondNumber
        tvResult.showInt(result)

        // end counting ---------------------
        chronometer.stop()

    }

    private fun synchronousDataWithBlockingSum() {

        val firstNumber = 1
        tvFirstNum.showInt(firstNumber)

        val secondNumber = 1
        tvSecondNum.showInt(secondNumber)

        // start counting time ------------
        chronometer.start()

        // operation in a method/fun that return a value
        val result = blockingSum(firstNumber, secondNumber)
        tvResult.showInt(result)

        // end counting ---------------------
        chronometer.stop()

    }

    private fun synchronousDataWithLongBlockingSum() {

        val firstNumber = 1
        tvFirstNum.showInt(firstNumber)

        val secondNumber = 1
        tvSecondNum.showInt(secondNumber)

        // start counting time ------------
        chronometer.start()
        val startTime = System.currentTimeMillis()

        // operation in a method/fun that return a value
        val result = longBlockingSum(firstNumber, secondNumber)
        tvResult.showInt(result)

        // end counting ---------------------
        chronometer.stop()

        val endTime = System.currentTimeMillis() - startTime
        chronometer.text = "00:0${endTime / 1000}"
    }

    private fun aynschronusDataWithLongBlockingSum() {

        val firstNumber = 1
        tvFirstNum.showInt(firstNumber)

        val secondNumber = 1
        tvSecondNum.showInt(secondNumber)

        // start counting time ------------
        chronometer.start()


        // operation in a non-blocking method/fun that need a callback/closure as fun param
        asyncSum(firstNumber, secondNumber) {
            tvResult.showInt(it)

            // end counting ---------------------
            chronometer.stop()
        }

    }

    private fun asyncTaskWithBlockingSum() {

        val firstNumber = 1
        tvFirstNum.showInt(firstNumber)

        val secondNumber = 1
        tvSecondNum.showInt(secondNumber)

        // start counting time ------------
        chronometer.start()


        // operation in a non-blocking method/fun that need a callback/closure as fun param
        sumWithAsyncTask(firstNumber, secondNumber) {

            tvResult.showInt(it)

            // end counting ---------------------
            chronometer.stop()
        }

    }

    private fun asyncTaskWithThreeBlockingSum() {

        // start counting time ------------
        chronometer.start()


        // operation in a non-blocking method/fun that need a callback/closure as fun param
        sumWithAsyncTaskMultiple {

            tvResult.showInt(it)

            // end counting ---------------------
            chronometer.stop()
        }

    }

    private fun longBlockingFirstNum(): Int {
        Thread.sleep(1200)
        runOnUiThread {
            tvFirstNum.showInt(1)
        }
        return 1
    }

    private fun longBlockingSecondNum(): Int {

        Thread.sleep(2200)

        runOnUiThread {
            tvSecondNum.showInt(1)
        }
        return 1
    }

    private fun sumWithAsyncTask(first: Int, second: Int, onComplete: (Int) -> Unit) {
        val asyncTask = object : AsyncTask<Void, Void, Int>() {

            override fun doInBackground(vararg p0: Void?): Int {
                Log.d("ThreadSwitching", "doInBackground: ${Thread.currentThread().name}")
                return longBlockingSum(first, second)
            }

            override fun onPostExecute(result: Int) {
                super.onPostExecute(result)
                Log.d("ThreadSwitching", "onPostExecute: ${Thread.currentThread().name}")
                onComplete(result)
            }

        }
        asyncTask.execute()

    }

    private fun sumWithAsyncTaskMultiple(onComplete: (Int) -> Unit) {
        val asyncTask = object : AsyncTask<Void, Void, Int>() {

            override fun doInBackground(vararg p0: Void?): Int {
                Log.d("ThreadSwitching", "doInBackground: ${Thread.currentThread().name}")

                // Synchronous execution in a single background thread

                val first = longBlockingFirstNum()
                val second = longBlockingSecondNum()

                return longBlockingSum(first, second)
            }

            override fun onPostExecute(result: Int) {
                super.onPostExecute(result)
                Log.d("ThreadSwitching", "onPostExecute: ${Thread.currentThread().name}")
                onComplete(result)
            }

        }
        asyncTask.execute()

    }

    private fun concurrencySumWithAsyncTask() {

        // Get first Num and second Num on each background thread with concurrency

        // After sum two numbers on another background thread

        // AsyncTask cannot be used!!! , and if so, you will encounter concurrency bugs like race condition
    }

    private fun blockingSum(first: Int, second: Int) = first + second

    private fun longBlockingSum(first: Int, second: Int): Int {
        // blocking current thread for about 3 seconds
        Thread.sleep(3200)

        return first + second
    }

    private fun asyncSum(first: Int, second: Int, onComplete: (Int) -> Unit) {
        // background threading model to handle long blocking code
        Thread {
            val result = longBlockingSum(first, second)
            onComplete(result)
        }.start()
    }

    private fun hide() {
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY.toLong())
    }

    private fun show() {
        // Show the system bar
        container.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
    }

    companion object {
        /**
         * Whether or not the system UI should be auto-hidden after
         * [AUTO_HIDE_DELAY_MILLIS] milliseconds.
         */
        private val AUTO_HIDE = true

        /**
         * If [AUTO_HIDE] is set, the number of milliseconds to wait after
         * user interaction before hiding the system UI.
         */
        private val AUTO_HIDE_DELAY_MILLIS = 3000

        /**
         * Some older devices needs a small delay between UI widget updates
         * and a change of the status and navigation bar.
         */
        private val UI_ANIMATION_DELAY = 300
    }

    private fun TextView.showInt(number: Int) {
        visibility = GONE
        text = number.toString()

        visibility = VISIBLE


    }

}
