package com.example.cookingsocialnetwork.post2.PostPage2TabFragment

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import android.widget.LinearLayout
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.databinding.AddIngredientBinding
import com.example.cookingsocialnetwork.databinding.UnitPickerBinding
import com.google.android.material.button.MaterialButton


class PostPage2Fragment3 : Fragment() {
    private lateinit var addIngredient: MaterialButton
    private lateinit var ingredients: LinearLayout
    private lateinit var unit : Array<String>


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
            addIngredient()
        }
        unit = resources.getStringArray(R.array.unit)
    }
    private fun addIngredient(){
        val dialog = context?.let { Dialog(it) }
        val dialogBinding: AddIngredientBinding = AddIngredientBinding.inflate(layoutInflater)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(dialogBinding.root)

        dialogBinding.addIngredientCloseBtn.setOnClickListener {
            dialog.dismiss()
        }

        dialogBinding.addIngredientDoneBtn.setOnClickListener {
            val newLayout = LayoutInflater.from(context).inflate(R.layout.post_page_2_ingredient_child, null)
            ingredients.addView(newLayout)
            newLayout.findViewById<Button>(R.id.post_page_2_ingredient_child_delete_btn).setOnClickListener {
                (newLayout.parent as LinearLayout).removeView(newLayout)
            }
            dialog.dismiss()
        }
        dialogBinding.addIngredientUnit.setOnClickListener {
            val secondDialog = context?.let { Dialog(it) }
            val secondDialogBinding: UnitPickerBinding = UnitPickerBinding.inflate(layoutInflater)
            secondDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            secondDialog.setContentView(secondDialogBinding.root)

            secondDialogBinding.unitPickerDoneBtn.setOnClickListener {
                dialogBinding.addIngredientUnit.setText(unit[secondDialogBinding.unitPickerUnit.value])
                secondDialog.dismiss()
            }

            secondDialogBinding.unitPickerUnit.minValue = 0
            secondDialogBinding.unitPickerUnit.maxValue = unit.size-1
            secondDialogBinding.unitPickerUnit.displayedValues = unit

            secondDialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            secondDialog.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
            secondDialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
            secondDialog.window?.setGravity(Gravity.BOTTOM)
            secondDialog.show()
        }

        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.BOTTOM)
        dialog.show()
    }


}