package com.example.cookingsocialnetwork.main.fragment.search

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.databinding.FragmentSearchBinding
import com.example.cookingsocialnetwork.databinding.LayoutDialogSearchableBinding
import com.example.cookingsocialnetwork.model.adapter.ListAdapterSearchHistory
import com.example.cookingsocialnetwork.model.adapter.ListAdapterSearchUser
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SearchFragment : Fragment() {
    lateinit var viewModel: SearchViewModel
    lateinit var binding: FragmentSearchBinding
    var checkShowDialog: Boolean = true

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        val factory = SearchViewModelFactory()
        viewModel = ViewModelProvider(this, factory).get(SearchViewModel::class.java)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        binding.viewSearchPage.visibility = View.GONE
        binding.tabSearch.visibility = View.GONE
        val searchPageAdapter = SearchPageAdapter(this)
        searchPageAdapter.query = ""

        binding.viewSearchPage.adapter = searchPageAdapter
        binding.viewSearchPage.isSaveEnabled = false
        TabLayoutMediator(binding.tabSearch, binding.viewSearchPage)
        { tab, index ->
            tab.text = when (index) {
                0 -> {
                    getString(R.string.user)
                }
                1 -> {
                    getString(R.string.user)
                }
                else -> {
                    getString(R.string.post_title)
                }
            }
        }.attach()

        binding.search.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String): Boolean {
                    viewModel.query = newText
                    viewModel.listenToDataSearchHistory()
                    return false
                }

                override fun onQueryTextSubmit(query: String): Boolean {
                    searchPageAdapter.query = query
                    binding.viewSearchPage.adapter = searchPageAdapter
                    return false
                }
            }
        )

        binding.search.setOnQueryTextFocusChangeListener()
        { _, hasFocus ->
            if (hasFocus) {
                activity?.let { screen ->
                    if (checkShowDialog) showDialogSearch(screen)
                    else checkShowDialog = true
                    binding.search.clearFocus()
                }
            }
        }

        return binding.root
    }

    private fun showDialogSearch(context: FragmentActivity) {
        val dialogBinding: LayoutDialogSearchableBinding =
            LayoutDialogSearchableBinding.inflate(layoutInflater)
        val dialog = Dialog(context)
        dialog.setContentView(dialogBinding.root)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        dialog.window?.setGravity(Gravity.CENTER)

        dialogBinding.back.setOnClickListener {
//            binding.search.setQuery(dialogBinding.search.query, false)
            dialog.dismiss()
        }

        dialogBinding.search.setQuery(binding.search.query, false)
        dialogBinding.search.requestFocus()

        activity?.let {
            viewModel.searchHistory.observe(context) {
                val adapter =
                    viewModel.searchHistory.value?.let {
                        ListAdapterSearchHistory(
                            dialog,
                            it.asReversed()
                        )
                    }
                dialogBinding.listView.adapter = adapter
            }
        }

        dialogBinding.listView.isClickable = true
        dialogBinding.listView.setOnItemClickListener()
        { _, _, position, _ ->
            binding.viewSearchPage.visibility = View.VISIBLE
            binding.tabSearch.visibility = View.VISIBLE
            binding.search.setQuery(
                viewModel.searchHistory.value?.get(viewModel.searchHistory.value!!.size - position - 1),
                true
            )
            dialog.dismiss()
        }

        dialogBinding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.viewSearchPage.visibility = View.VISIBLE
                binding.tabSearch.visibility = View.VISIBLE
                binding.search.setQuery(query, true)
                query?.let { updateSearchHistory(it) }
                checkShowDialog = false
                dialog.dismiss()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.query = newText.toString()
                if (newText.toString() != "") {
                    viewModel.listenToDataUser()
                    activity?.let {
                        viewModel._users.observe(context) {
                            val adapter =
                                viewModel._users.value?.let {
                                    ListAdapterSearchUser(dialog, it)
                                }
                            dialogBinding.listView.adapter = adapter
                        }
                    }

                    dialogBinding.listView.isClickable = true
                    dialogBinding.listView.setOnItemClickListener()
                    { _, _, position, _ ->
                        Log.w("ok", "abc")
                        dialog.dismiss()
                    }
                }
                else {
                    viewModel.listenToDataSearchHistory()
                    activity?.let {
                        viewModel.searchHistory.observe(context) {
                            val adapter =
                                viewModel.searchHistory.value?.let {
                                    ListAdapterSearchHistory(
                                        dialog,
                                        it.asReversed()
                                    )
                                }
                            dialogBinding.listView.adapter = adapter
                        }
                    }

                    dialogBinding.listView.isClickable = true
                    dialogBinding.listView.setOnItemClickListener()
                    { _, _, position, _ ->
                        binding.viewSearchPage.visibility = View.VISIBLE
                        binding.tabSearch.visibility = View.VISIBLE
                        binding.search.setQuery(
                            viewModel.searchHistory.value?.get(viewModel.searchHistory.value!!.size - position - 1),
                            true
                        )
                        dialog.dismiss()
                    }
                }
                return false
            }
        })

        dialog.show()
    }

    fun updateSearchHistory(search: String)
    {
        val history = viewModel.history
        history.remove(search)
        history.add(search)
        FirebaseFirestore.getInstance()
            .collection("user")
            .document(FirebaseAuth.getInstance().currentUser?.email.toString())
            .update("searchHistory", history)
    }
}