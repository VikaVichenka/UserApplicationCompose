package com.vikayarska.kotlinapplicationcompose.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.vikayarska.kotlinapplicationcompose.R
import com.vikayarska.kotlinapplicationcompose.databinding.FragmentUsersListBinding
import com.vikayarska.kotlinapplicationcompose.presentation.activities.UserActivity.Companion.USER
import com.vikayarska.kotlinapplicationcompose.presentation.model.ViewState
import com.vikayarska.kotlinapplicationcompose.presentation.model.ViewStateUpdate
import com.vikayarska.kotlinapplicationcompose.presentation.recyclerviewhelpers.UsersAdapter
import com.vikayarska.kotlinapplicationcompose.presentation.viewmodel.UsersListViewModel
import com.vikayarska.kotlinapplicationcompose.presentation.viewmodel.collectWhenResumed
import dagger.hilt.android.AndroidEntryPoint

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
        val userAdapter = UsersAdapter(
            onClick = { user ->
                findNavController().navigate(
                    R.id.userProfileFragment,
                    bundleOf(USER to user)
                )
            }).apply {
            addLoadStateListener { loadState ->
                if (loadState.append.endOfPaginationReached) {
                    viewModel.addUsers()
                }
            }
            viewModel.usersFlow.collectWhenResumed(lifecycleScope) { pagingData ->
                this.submitData(pagingData)
            }
        }
        binding.rvUsersList.apply {
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    (layoutManager as LinearLayoutManager).orientation
                )
            )
            adapter = userAdapter
        }
    }

    private fun setUpViewState() {
        viewModel.viewState.observe(viewLifecycleOwner, { state ->
            when (state) {
                is ViewStateUpdate.Completed -> showLoading(Visibility.Hide)
                is ViewStateUpdate.Failure -> showError(state.errorMessage)
                is ViewStateUpdate.Loading -> showLoading(Visibility.Show)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}