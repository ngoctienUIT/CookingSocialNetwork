package com.example.cookingsocialnetwork.account

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.cookingsocialnetwork.R
import kotlinx.android.synthetic.main.activity_account_page.*

class AccountPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_page)

        val mViewModel = ViewModelProviders.of(this).get(AccountViewModel::class.java)
        mViewModel.getUser.observe(this, Observer {
            text1.text=it.name
        })
    }
}