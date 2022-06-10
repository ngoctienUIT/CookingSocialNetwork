package com.example.cookingsocialnetwork.post2.PostPage2TabFragment

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.databinding.TimePickerBinding
import com.google.android.material.button.MaterialButton
import com.google.type.LatLng


class PostPage2Fragment3 : Fragment() {
    private lateinit var addIngredient: MaterialButton
    private lateinit var ingredients: LinearLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.post_page_2_fragment3, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ingredients = requireView().findViewById(R.id.post_page2_fragment3_ingredients)

        addIngredient = requireView().findViewById(R.id.post_page2_fragment3_ingredients_btn)
        addIngredient.setOnClickListener{
//            val newLayout = LayoutInflater.from(context).inflate(R.layout.postpage_ingredient_child, null)
//            ingredients.addView(newLayout)
//            newLayout.findViewById<Button>(R.id.btn_Ingredients).setOnClickListener {
//                (newLayout.parent as LinearLayout).removeView(newLayout)
//            }
            addIngredient()
        }
    }
    private fun addIngredient(){
        val dialog = context?.let { Dialog(it) }
        val dialogBinding: TimePickerBinding = TimePickerBinding.inflate(layoutInflater)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(dialogBinding.root)



        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.BOTTOM)
        dialog.show()
    }


}