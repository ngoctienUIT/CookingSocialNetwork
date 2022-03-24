package com.example.cookingsocialnetwork.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.databinding.ActivityAccountPageBinding
import com.example.cookingsocialnetwork.viewmodel.AccountViewModel
import com.example.cookingsocialnetwork.viewmodelfactory.AccountViewModelFactory

class AccountPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_page)

        val activityMainBinding = DataBindingUtil.setContentView<ActivityAccountPageBinding>(this, R.layout.activity_account_page)
        activityMainBinding.viewModel = ViewModelProviders.of(this, AccountViewModelFactory())
            .get(AccountViewModel::class.java)
    }
}