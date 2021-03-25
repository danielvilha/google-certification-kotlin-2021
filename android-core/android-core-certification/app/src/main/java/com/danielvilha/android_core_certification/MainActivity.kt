package com.danielvilha.android_core_certification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        // https://www.baeldung.com/kotlin/concatenate-strings
        button_toast.setOnClickListener {
            Toast.makeText(this, "${getString(R.string.message)} ${getString(R.string.toast)}", Toast.LENGTH_LONG).show()
        }

        button_snackbar.setOnClickListener {
            Snackbar.make(constraint, "${getString(R.string.message)} ${getString(R.string.snackbar)}", Snackbar.LENGTH_LONG).show()
        }
    }
}