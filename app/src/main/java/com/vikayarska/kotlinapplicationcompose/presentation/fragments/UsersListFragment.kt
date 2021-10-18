package com.vikayarska.kotlinapplicationcompose.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.vikayarska.kotlinapplicationcompose.R
import com.vikayarska.kotlinapplicationcompose.databinding.FragmentUsersListBinding
import com.vikayarska.kotlinapplicationcompose.presentation.model.ViewState
import com.vikayarska.kotlinapplicationcompose.presentation.recyclerviewhelpers.UsersAdapter
import com.vikayarska.kotlinapplicationcompose.presentation.viewmodel.UsersListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

//TODO: is needed to crate base fragment with loading and show error
@AndroidEntryPoint
class UsersListFragment : Fragment() {

    private val viewModel: UsersListViewModel by viewModels()

    private var _binding: FragmentUsersListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUsersListBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.btDeleteUsersList.setOnClickListener { viewModel.deleteUsers() }
        setUpViewState()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pagingAdapter = UsersAdapter {
            Toast.makeText(context, "user -> ${it.firstName}", Toast.LENGTH_SHORT).show()
        }
        pagingAdapter.addLoadStateListener { loadState ->
            if (loadState.append.endOfPaginationReached) {
                viewModel.addUsers()
            }
        }
        val recyclerView = binding.rvUsersList
        recyclerView.apply {
            addItemDecoration(
                DividerItemDecoration(
                    recyclerView.context,
                    (recyclerView.layoutManager as LinearLayoutManager).orientation
                )
            )
            adapter = pagingAdapter
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getUsers().collectLatest { pagingData ->
                pagingAdapter.submitData(pagingData)
            }
        }
    }

    private fun setUpViewState() {
        viewModel.viewState.observe(viewLifecycleOwner, { state ->
            when (state) {
                is ViewState.Loaded<*> -> {
                    showLoading(View.GONE)
                }
                is ViewState.Failure -> {
                    showError(state.errorMessage)
                }
                ViewState.Loading -> {
                    showLoading(View.VISIBLE)
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //TODO: moke reusable for other fragments
    private fun showLoading(visibility: Int) {
        binding.progressCircularUserList.visibility = visibility
    }
}

fun Fragment.showError(message: String) {
    val alertDialogBuilder = AlertDialog.Builder(this.requireContext())

    alertDialogBuilder.apply {
        setTitle(R.string.error)
        setMessage(message)
        setNegativeButton(R.string.ok) { dialog, _ ->
            dialog.dismiss()
        }
    }.create().show()

}