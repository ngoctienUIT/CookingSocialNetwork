package com.example.cookingsocialnetwork.newviewpost.view

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.databinding.FragmentCommentBinding
import com.example.cookingsocialnetwork.databinding.LayoutDeleteBinding
import com.example.cookingsocialnetwork.newviewpost.ViewPostViewModel
import com.example.cookingsocialnetwork.newviewpost.ViewPostViewModelFactory
import com.example.cookingsocialnetwork.newviewpost.adapter.CommentAdapter
import com.example.cookingsocialnetwork.newviewpost.adapter.MyListListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CommentFragment : Fragment(), MyListListener {
    lateinit var viewModel: ViewPostViewModel
    lateinit var binding: FragmentCommentBinding
    var id: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_comment, container, false)
        val factory = ViewPostViewModelFactory()
        viewModel = ViewModelProvider(this, factory).get(ViewPostViewModel::class.java)
        viewModel.id = id
        viewModel.getData()
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        activity?.let {
            viewModel.post.observe(it) { post ->
                val commentAdapter = CommentAdapter(post.comments, id, this)
                val commentLayoutManager = LinearLayoutManager(it)
                binding.comment.layoutManager = commentLayoutManager
                binding.comment.adapter = commentAdapter
            }
        }

        binding.send.visibility = View.GONE
        binding.contentComment.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0.toString() == "") binding.send.visibility = View.GONE
                else binding.send.visibility = View.VISIBLE
            }

        })

        binding.send.setOnClickListener()
        {
            viewModel.updateComment(binding.contentComment.text.toString())
            binding.contentComment.text = Editable.Factory.getInstance().newEditable("")
        }

        return binding.root
    }

    override fun onItemLongPressed(index: Int) {
        if (FirebaseAuth.getInstance().currentUser?.email.toString()
                .compareTo(viewModel.post.value!!.comments[index]["userName"] as String) == 0
            || FirebaseAuth.getInstance().currentUser?.email.toString()
                .compareTo(viewModel.post.value!!.owner) == 0
        ) {
            activity?.let {
                val dialog = Dialog(it)
                val dialogBinding: LayoutDeleteBinding = LayoutDeleteBinding.inflate(layoutInflater)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setContentView(dialogBinding.root)
                dialogBinding.content.text = "Xóa bình luận"

                dialogBinding.delete.setOnClickListener()
                {
                    val listComment = viewModel.post.value?.comments
                    listComment?.removeAt(index)
                    FirebaseFirestore.getInstance()
                        .collection("post")
                        .document(id)
                        .update("comments", listComment)

                    dialog.dismiss()
                }

                dialog.window?.setLayout(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
                dialog.window?.setGravity(Gravity.BOTTOM)
                dialog.show()
            }
        }
    }
}