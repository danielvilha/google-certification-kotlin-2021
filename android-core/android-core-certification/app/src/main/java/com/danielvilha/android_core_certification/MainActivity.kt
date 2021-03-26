package com.danielvilha.android_core_certification

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.danielvilha.android_core_certification.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    // https://developer.android.com/topic/libraries/view-binding
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun onStart() {
        super.onStart()
        binding.buttonMessageView.setOnClickListener {
            val manager: FragmentManager = supportFragmentManager
            val transaction: FragmentTransaction = manager.beginTransaction()
            transaction.replace(R.id.container, MessagesFragment.newInstance())
            transaction.commit()
        }

        binding.buttonNotificationView.setOnClickListener {
            val manager: FragmentManager = supportFragmentManager
            val transaction: FragmentTransaction = manager.beginTransaction()
            transaction.replace(R.id.container, NotificationFragment.newInstance())
            transaction.commit()
        }
    }
}