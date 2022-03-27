package com.example.cookingsocialnetwork.account

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.databinding.ActivityAccountPageBinding

class AccountPage : AppCompatActivity() {
    private lateinit var viewModel: AccountViewModel
    private lateinit var databinding: ActivityAccountPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_page)

        databinding = DataBindingUtil.setContentView(this, R.layout.activity_account_page)
        val factory = AccountViewModelFactory()

        viewModel = ViewModelProvider(this,factory).get(AccountViewModel::class.java)
//        viewModel = ViewModelProviders.of(this,factory).get(AccountViewModel::class.java)
        databinding.viewModel = viewModel
        databinding.lifecycleOwner = this
    }
}