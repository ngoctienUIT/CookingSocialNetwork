package com.example.cookingsocialnetwork.post2.PostPage2TabFragment

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.databinding.AddIngredientBinding
import com.example.cookingsocialnetwork.databinding.UnitPickerBinding
import com.example.cookingsocialnetwork.post2.model.*
import com.google.android.material.button.MaterialButton


class PostPage2Fragment4 : Fragment() {

    private lateinit var addStep: MaterialButton
    private lateinit var stepRec: RecyclerView
    private lateinit var stepAdapter: StepAdapter

    private var stepList: MutableList<Step> = ArrayList() //lấy cái này

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.post_page_2_fragment4, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addStep = requireView().findViewById(R.id.post_page2_fragment4_add_step)
        addStep.setOnClickListener{
            addStep()
        }

        stepAdapter = StepAdapter(stepList)

        stepRec = requireView().findViewById(R.id.post_page2_fragment4_step)
        stepRec.adapter = stepAdapter
        stepRec.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        val callBack: ItemTouchHelper.Callback = StepRowMoveCallBack(stepAdapter)
        val touchHelper = ItemTouchHelper(callBack)
        touchHelper.attachToRecyclerView(stepRec)

    }
    private fun addStep(){
        stepList.add(Step("asd","asd" ))
        stepAdapter.notifyDataSetChanged()
//        val dialog = context?.let { Dialog(it) }
//        val dialogBinding: AddIngredientBinding = AddIngredientBinding.inflate(layoutInflater)
//        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        dialog.setContentView(dialogBinding.root)
//
//        dialogBinding.addIngredientCloseBtn.setOnClickListener {
//            dialog.dismiss()
//        }
//
//        dialogBinding.addIngredientDoneBtn.setOnClickListener {
//            ingredientList.add(Ingredient(dialogBinding.addIngredientAmount.text.toString(),dialogBinding.addIngredientUnit.text.toString(),dialogBinding.addIngredientsIngredient.text.toString()))
//            ingredientAdapter.notifyDataSetChanged()
//            dialog.dismiss()
//        }
//        dialogBinding.addIngredientUnit.setOnClickListener {
//            val secondDialog = context?.let { Dialog(it) }
//            val secondDialogBinding: UnitPickerBinding = UnitPickerBinding.inflate(layoutInflater)
//            secondDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
//            secondDialog.setContentView(secondDialogBinding.root)
//
//            secondDialogBinding.unitPickerDoneBtn.setOnClickListener {
//                dialogBinding.addIngredientUnit.setText(unit[secondDialogBinding.unitPickerUnit.value])
//                secondDialog.dismiss()
//            }
//
//            secondDialogBinding.unitPickerUnit.minValue = 0
//            secondDialogBinding.unitPickerUnit.maxValue = unit.size-1
//            secondDialogBinding.unitPickerUnit.displayedValues = unit
//
//            secondDialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
//            secondDialog.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
//            secondDialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
//            secondDialog.window?.setGravity(Gravity.BOTTOM)
//            secondDialog.show()
//        }
//
//        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
//        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
//        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
//        dialog.window?.setGravity(Gravity.BOTTOM)
//        dialog.show()
    }

}