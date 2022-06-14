package com.example.cookingsocialnetwork.post2.postPage2TabFragment

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.AnyRes
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.databinding.AddStepBinding
import com.example.cookingsocialnetwork.post2.model.*
import com.google.android.material.button.MaterialButton


class PostPage2Fragment4 : Fragment() {

    private lateinit var addStep: MaterialButton
    private lateinit var stepRec: RecyclerView
    private lateinit var stepAdapter: StepAdapter
    private lateinit var image: ImageView
    private lateinit var uri : Uri

    private var checkImage = false

    fun isInitialized() = ::stepRec.isInitialized

    var stepList: MutableList<Step> = ArrayList() //lấy cái này

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

        uri = getUriToDrawable(this,R.drawable.food_picker)



    }

    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    private fun addStep(){
        val dialog = context?.let { Dialog(it) }
        val dialogBinding: AddStepBinding = AddStepBinding.inflate(layoutInflater)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(dialogBinding.root)

        image = dialogBinding.addStepImage

        dialogBinding.addStepLb.text = "Bước " + (stepList.size + 1)

        dialogBinding.addStepCloseBtn.setOnClickListener {
            dialog.dismiss()
        }

        dialogBinding.pickImageStep.setOnClickListener{
            val i = Intent()
            i.type = "image/*"
            i.action = Intent.ACTION_GET_CONTENT
            i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true)

            imagesChooserLauncher.launch(Intent.createChooser(i, "Select Picture"))
            //dialogBinding.addStepImage.load(uriStep)
        }

        dialogBinding.addStepDoneBtn.setOnClickListener {
            stepList.add(Step( uri ,dialogBinding.addStepStepDes.text.toString()))
            stepAdapter.notifyDataSetChanged()
            dialog.dismiss()
        }

        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.BOTTOM)
        dialog.show()
    }
    private var imagesChooserLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            uri = result.data?.data!!
            image.load(uri)
        }
    }




    private fun getUriToDrawable(
        @NonNull context: PostPage2Fragment4,
        @AnyRes drawableId: Int
    ): Uri {
        return Uri.parse(
            ContentResolver.SCHEME_ANDROID_RESOURCE
                    + "://" + context.resources.getResourcePackageName(drawableId)
                    + '/' + context.resources.getResourceTypeName(drawableId)
                    + '/' + context.resources.getResourceEntryName(drawableId)
        )
    }

}


