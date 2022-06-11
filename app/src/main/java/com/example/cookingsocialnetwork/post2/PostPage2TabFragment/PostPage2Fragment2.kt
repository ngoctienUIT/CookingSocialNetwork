package com.example.cookingsocialnetwork.post2.PostPage2TabFragment

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.databinding.PortionPickerBinding
import com.example.cookingsocialnetwork.databinding.TimePickerBinding

class PostPage2Fragment2 : Fragment(){

    private lateinit var portion: EditText  //lấy cái này
    private lateinit var prepTime: EditText //lấy cái này
    private lateinit var bakingTime :EditText   //lấy cái này
    private lateinit var restTime : EditText    //lấy cái này

    private lateinit var portionType: Array<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        return inflater.inflate(R.layout.post_page_2_fragment2, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        portionType = resources.getStringArray(R.array.portion_type)

        portion = requireView().findViewById(R.id.post_page2_fragment2_food_portion)
        portion.setOnClickListener{
            portionPicker()
        }

        prepTime = requireView().findViewById(R.id.post_page2_fragment2_food_prep)
        prepTime.setOnClickListener{
            timePicker(prepTime)
        }
        bakingTime = requireView().findViewById(R.id.post_page2_fragment2_food_baking)
        bakingTime.setOnClickListener{
            timePicker(bakingTime)
        }
        restTime = requireView().findViewById(R.id.post_page2_fragment2_food_rest)
        restTime.setOnClickListener{
            timePicker(restTime)
        }
    }
    private fun portionPicker() {
        val dialog = context?.let { Dialog(it) }
        val dialogBinding: PortionPickerBinding = PortionPickerBinding.inflate(layoutInflater)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(dialogBinding.root)


        dialogBinding.numberPortion.minValue = 1
        dialogBinding.numberPortion.maxValue = 100

        dialogBinding.portionType.minValue = 0
        dialogBinding.portionType.maxValue = portionType.size-1
        dialogBinding.portionType.displayedValues = portionType

        dialogBinding.portionPickerDoneBtn.setOnClickListener{
            portion.setText(dialogBinding.numberPortion.value.toString() + " " +portionType[dialogBinding.portionType.value]  )
            dialog.dismiss()
        }

        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.BOTTOM)
        dialog.show()
    }
    private fun timePicker(editText: EditText){
        val dialog = context?.let { Dialog(it) }
        val dialogBinding: TimePickerBinding = TimePickerBinding.inflate(layoutInflater)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(dialogBinding.root)

        dialogBinding.timePickerHour.minValue = 0
        dialogBinding.timePickerHour.maxValue = 72

        dialogBinding.timePickerMinute.minValue = 0
        dialogBinding.timePickerMinute.maxValue = 59

        dialogBinding.timePickerDoneBtn.setOnClickListener{
            editText.setText((dialogBinding.timePickerHour.value * 60 + dialogBinding.timePickerMinute.value).toString() + " phút")
            dialog.dismiss()
        }

        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.BOTTOM)
        dialog.show()
    }
}