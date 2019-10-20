package com.padc.padcrx.asynchronusdata

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.padc.padcrx.R
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import kotlinx.android.synthetic.main.activity_login.*
import java.io.File
import java.util.*

class LoginActivity : AppCompatActivity() {

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

        Observable.combineLatest<String, String, Boolean>(userName, password, BiFunction{ name, password ->
            name.length > 6 && password.length > 6
        }).subscribe {
            next_button.isEnabled = it
        }

    }

}
