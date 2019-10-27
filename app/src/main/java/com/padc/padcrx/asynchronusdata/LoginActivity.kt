package com.padc.padcrx.asynchronusdata

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.padc.padcrx.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import kotlinx.android.synthetic.main.activity_login.*
import java.util.concurrent.TimeUnit

class LoginActivity : AppCompatActivity() {

    private lateinit var disposable: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val userName = Observable.create<String> { emitter ->

            etUserName.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {

                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
                    emitter.onNext(p0.toString())
                }
            })

        }

        val password = Observable.create<String> { emitter ->

            password_edit_text.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {

                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
                    emitter.onNext(p0.toString())
                }
            })

        }

        val validateUserName = userName.map { it.trim().length > 6 }
            .doOnNext { txlUserName.error = null }
            .debounce(600, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { valid ->
                if (!valid) txlUserName.error = "UserName must be more than 6 characters."
            }

        val validatePassword = password.map { it.trim().length > 6 }
            .doOnNext { password_text_input.error = null }
            .debounce(600, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { valid ->
                if (!valid) password_text_input.error = "Password must be more than 6 characters."
            }

        disposable = Observable.combineLatest<Boolean, Boolean, Boolean>(
            validateUserName,
            validatePassword,
            BiFunction { nameValid, passwordValid ->
                nameValid && passwordValid
            })
            .subscribe({
                next_button.isEnabled = it
            }, {
                Log.e("error", it.localizedMessage)
            })

    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }

}
