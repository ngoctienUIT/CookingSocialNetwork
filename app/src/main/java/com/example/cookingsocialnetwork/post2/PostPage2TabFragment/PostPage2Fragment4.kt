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
import com.example.cookingsocialnetwork.databinding.AddStepBinding
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
        val dialog = context?.let { Dialog(it) }
        val dialogBinding: AddStepBinding = AddStepBinding.inflate(layoutInflater)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(dialogBinding.root)

        dialogBinding.addStepLb.text = "Bước " + (stepList.size + 1)

        dialogBinding.addStepCloseBtn.setOnClickListener {
            dialog.dismiss()
        }

        dialogBinding.addStepDoneBtn.setOnClickListener {
            val imageUri = "nói chung là URI của cái image m làm nhá"
            stepList.add(Step(imageUri,dialogBinding.addStepStepDes.text.toString()))
            stepAdapter.notifyDataSetChanged()
            dialog.dismiss()
        }

        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.BOTTOM)
        dialog.show()
    }

}