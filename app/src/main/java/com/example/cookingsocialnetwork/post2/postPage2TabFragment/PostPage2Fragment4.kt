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
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.AnyRes
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.databinding.AddStepBinding
import com.example.cookingsocialnetwork.post2.model.*
import com.google.android.material.button.MaterialButton


class PostPage2Fragment4 : Fragment() {

    private lateinit var addStep: MaterialButton
    private lateinit var stepRec: RecyclerView
    private lateinit var stepAdapter: StepAdapter
    private  var uriStep : Uri = Uri.EMPTY

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

    }
    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    private fun addStep(){
        val dialog = context?.let { Dialog(it) }
        val dialogBinding: AddStepBinding = AddStepBinding.inflate(layoutInflater)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(dialogBinding.root)

        dialogBinding.addStepLb.text = "Bước " + (stepList.size + 1)

        dialogBinding.addStepCloseBtn.setOnClickListener {
            dialog.dismiss()
        }

        dialogBinding.pickImageStep.setOnClickListener{
            imageChooser(dialogBinding)
            //dialogBinding.addStepImage.load(uriStep)
        }

        dialogBinding.addStepDoneBtn.setOnClickListener {
          //  val imageUri :Uri = Uri.fromFile("  @drawable/food_picker")//"nói chung là URI của cái image m làm nhá"
           // val uri : Uri = Uri.parse("content://com.android.providers.media.documents/document/image%3A16558")

            val uri : Uri = getUriToDrawable(this,R.drawable.food_picker)
             Log.d("hoicham", uri.toString())
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
                uriStep = result.data?.data!!
        }
    }

    private fun imageChooser(dialogBinding: AddStepBinding) {

        // create an instance of the
        // intent of the type image
        val i = Intent()
        i.type = "image/*"
        i.action = Intent.ACTION_GET_CONTENT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            i.putExtra(Intent.EXTRA_AUTO_LAUNCH_SINGLE_CHOICE,true)
        }

        // pass the constant to compare it
        // with the returned requestCode

            imagesChooserLauncher.launch(Intent.createChooser(i, "Select Picture"))
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


