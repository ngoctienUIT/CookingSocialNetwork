package com.example.cookingsocialnetwork.setting.report.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.databinding.ActivityAddMaterialBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AddMaterialActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddMaterialBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMaterialBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)

        binding.backAddMaterial.setOnClickListener()
        {
            finish()
        }

        binding.send.setOnClickListener()
        {
            FirebaseFirestore.getInstance()
                .collection("report")
                .document("material")
                .get()
                .addOnSuccessListener {
                    val data = it.data
                    val errorList = data?.get("material") as MutableList<Map<String, Any>>
                    val error = hashMapOf(
                        "id" to (FirebaseAuth.getInstance().currentUser?.email.toString()),
                        "material" to binding.material.text.toString()
                    )
                    errorList.add(error)
                    FirebaseFirestore.getInstance()
                        .collection("report")
                        .document("material")
                        .update("material", errorList)
                    val view = View.inflate(this, R.layout.dialog_success, null)

                    val builder = AlertDialog.Builder(this)
                    builder.setView(view)

                    val dialog = builder.create()
                    dialog.show()
                    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                    dialog.setCancelable(false)

                    val button: Button = view.findViewById(R.id.btn_confirm)
                    val content: TextView = view.findViewById(R.id.content)
                    content.text = "Đã gửi yêu cầu thêm nguyên liệu đến nhà phát triển"
                    button.setOnClickListener()
                    {
                        dialog.dismiss()
                    }
                }
        }
    }
}