package com.vikayarska.kotlinapplicationcompose.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.vikayarska.kotlinapplicationcompose.R
import com.vikayarska.kotlinapplicationcompose.databinding.FragmentUsersListBinding
import com.vikayarska.kotlinapplicationcompose.presentation.model.ViewState
import com.vikayarska.kotlinapplicationcompose.presentation.viewmodel.UsersListViewModel
import dagger.hilt.android.AndroidEntryPoint

//TODO: is needed to crate base fragment with loading and show error?
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
        setUpView()
        return view
    }

    private fun setUpView() {
        setUpViewState()
        binding.btDeleteUsersList.setOnClickListener { viewModel.deleteUsers() }
        binding.rvUsersList.apply {
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    (layoutManager as LinearLayoutManager).orientation
                )
            )
            adapter = viewModel.setUpPagingAdapter {
                findNavController().navigate(
                    R.id.userProfileFragment,
                    bundleOf("user" to it)
                )
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

    //TODO: make reusable for other fragments
    private fun showLoading(visibility: Int) {
        binding.progressCircularUserList.visibility = visibility
    }
}