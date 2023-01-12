package com.candra.latihanreactivex

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import androidx.core.content.ContextCompat
import com.candra.latihanreactivex.databinding.ActivityMainBinding
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.functions.Function3

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val emailStream = RxTextView.textChanges(binding.edEmail)
            .skipInitialValue()
            .map { email ->
                !Patterns.EMAIL_ADDRESS.matcher(email).matches()
            }
        emailStream.subscribe{
            showAllError(it,1)
        }

        val passwordStream = RxTextView.textChanges(binding.edPassword)
            .skipInitialValue()
            .map { password ->
                password.length < 6
            }
        passwordStream.subscribe {
           showAllError(it,2)
        }

        val passwordConfirmationStream = Observable.merge(
            RxTextView.textChanges(binding.edPassword)
                .map { password ->
                    password.toString() != binding.edConfirmPassword.text.toString()
                },
            RxTextView.textChanges(binding.edConfirmPassword)
                .map { confirmPassword ->
                    confirmPassword.toString() != binding.edPassword.text.toString()
                }
        )
        passwordConfirmationStream.subscribe {
            showAllError(it,3)
        }

        val invalidFieldsStream = Observable.combineLatest(
            emailStream,
            passwordStream,
            passwordConfirmationStream,
            Function3{emailInvalid: Boolean, passwordInvalid: Boolean, passwordConfirmationInvalid: Boolean->
                !emailInvalid && !passwordInvalid && !passwordConfirmationInvalid
            })

        invalidFieldsStream.subscribe { isValid ->
            if (isValid){
                binding.btnRegister.isEnabled = true
                binding.btnRegister.setBackgroundColor(ContextCompat.getColor(this,R.color.purple_500))
            }else{
                binding.btnRegister.isEnabled = false
                binding.btnRegister.setBackgroundColor(ContextCompat.getColor(this,android.R.color.darker_gray))
            }
        }

    }


    private fun showAllError(isNotValid: Boolean,position: Int) {
        when(position){
            1 -> binding.edEmail.error = if (isNotValid) getString(R.string.email_not_valid) else null
            2 ->  binding.edPassword.error = if (isNotValid) getString(R.string.password_not_valid) else null
            3 ->  binding.edConfirmPassword.error = if (isNotValid) getString(R.string.password_not_same) else null
        }
    }
    
}