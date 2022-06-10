package com.example.cookingsocialnetwork.post2.PostPage2TabFragment

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.databinding.LayoutBottomsheetBinding
import com.example.cookingsocialnetwork.mainActivity.MainActivity
import com.example.cookingsocialnetwork.model.LanguageManager

class PostPage2Fragment2 : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        return inflater.inflate(R.layout.post_page_2_fragment2, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val assd = getView()?.findViewById<TextView>(R.id.post_page2_fragment2_food_portion)
        assd?.setOnClickListener(){
            showDialog()
        }

    }
    private fun showDialog()
    {
        val dialog = context?.let { Dialog(it) }
        val dialogBinding: LayoutBottomsheetBinding = com.example.cookingsocialnetwork.databinding.LayoutBottomsheetBinding.inflate(layoutInflater)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(dialogBinding.root)





        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.BOTTOM)
        dialog.show()
    }


}