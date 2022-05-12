package com.example.cookingsocialnetwork.setting.changeProfile

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.databinding.ActivitySettingChangeProfileBinding
import com.squareup.picasso.Picasso
import com.yalantis.ucrop.UCrop
import java.io.File
import java.util.*

class SettingChangeProfile : AppCompatActivity() {
    private lateinit var viewModel: SettingChangeProfileViewModel
    private lateinit var dataBinding: ActivitySettingChangeProfileBinding
    private var uri: Uri? =null

    private var getContent = registerForActivityResult(ActivityResultContracts.GetContent())
    {
        if (it != null) {
            val inputUri = it
            val outputUri = File(filesDir, "croppedImage.jpg").toUri()
            val listUri = listOf<Uri>(inputUri, outputUri)
            cropImage.launch(listUri)
        }
    }

    private val uCropContract = object: ActivityResultContract<List<Uri>, Uri>() {
        override fun createIntent(context: Context, input: List<Uri>?): Intent {
            val inputUri = input?.get(0)
            val outputUri = input?.get(1)

            val uCrop = UCrop.of(inputUri!!, outputUri!!)
                .withAspectRatio(5f, 5f)
                .withMaxResultSize(800, 800)

            return uCrop.getIntent(context)
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
            return intent?.let {
                UCrop.getOutput(
                    it
                )
            }
        }
    }

    private val cropImage = registerForActivityResult(uCropContract)
    {
        if (it!=null) {
            dataBinding.ivAvatar.setImageURI(it)
            uri = it
        }
    }

    private fun selectGallery()
    {
        getContent.launch("image/*")
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_change_profile)
        supportActionBar?.hide()

        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_setting_change_profile)
        val factory = SettingChangeProfileViewModelFactory()

        viewModel = ViewModelProvider(this,factory).get(SettingChangeProfileViewModel::class.java)
        dataBinding.viewModel = viewModel
        dataBinding.lifecycleOwner = this

        viewModel.user.observe(this)
        {
            Picasso.get().load(it.avatar).into(dataBinding.ivAvatar)
            if (viewModel.user.value!!.gender.compareTo(resources.getStringArray(R.array.gender)[0]) == 0)
                dataBinding.gender.setSelection(0)
            else if (viewModel.user.value!!.gender.compareTo(resources.getStringArray(R.array.gender)[1]) == 0)
                dataBinding.gender.setSelection(1)
            else dataBinding.gender.setSelection(2)
        }

        dataBinding.backSettingProfile.setOnClickListener()
        {
            finish()
        }

        dataBinding.ivAvatar.setOnClickListener()
        {
            selectGallery()
        }

        dataBinding.birthday.setOnClickListener()
        {
            val c = Calendar.getInstance()
            var year = c.get(Calendar.YEAR)
            var month = c.get(Calendar.MONTH)
            var day = c.get(Calendar.DAY_OF_MONTH)
            if (viewModel.user.value!!.birthday.compareTo("None") != 0) {
                day = Integer.parseInt(viewModel.user.value!!.birthday.substring(0, 2))
                month = Integer.parseInt(viewModel.user.value!!.birthday.substring(5, 7)) - 1
                year = Integer.parseInt(
                    viewModel.user.value!!.birthday.substring(
                        10,
                        viewModel.user.value!!.birthday.length
                    )
                )
            }
            val datePickerDialog = DatePickerDialog(this,
                { _, newYear, monthOfYear, dayOfMonth ->
                    if (dayOfMonth < 10 && monthOfYear + 1 < 10)
                        dataBinding.birthday.text = "0$dayOfMonth - 0${monthOfYear + 1} - $newYear"
                    else if (dayOfMonth < 10) dataBinding.birthday.text = "0$dayOfMonth - ${monthOfYear + 1} - $newYear"
                    else if (monthOfYear + 1 < 10) dataBinding.birthday.text = "$dayOfMonth - 0${monthOfYear + 1} - $newYear"
                    else dataBinding.birthday.text = "$dayOfMonth - ${monthOfYear + 1} - $newYear"
                    viewModel.user.value!!.birthday = dataBinding.birthday.text as String
                }, year, month, day
            )
            datePickerDialog.datePicker.maxDate = Date().time
            datePickerDialog.show()
        }

        val adapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(this, R.array.gender, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        dataBinding.gender.adapter = adapter
        dataBinding.gender.onItemSelectedListener = object :AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                viewModel.user.value!!.gender = p0?.getItemAtPosition(p2).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        dataBinding.update.setOnClickListener()
        {
            viewModel.updateInfo(uri)
            finish()
        }
    }
}
