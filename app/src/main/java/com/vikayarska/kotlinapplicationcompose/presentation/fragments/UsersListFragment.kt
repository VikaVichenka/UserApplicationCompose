package com.vikayarska.kotlinapplicationcompose.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.vikayarska.kotlinapplicationcompose.databinding.FragmentUsersListBinding
import com.vikayarska.kotlinapplicationcompose.presentation.viewmodel.MainActivityViewModel

class UsersListFragment : Fragment() {

    private val viewModel: MainActivityViewModel by viewModels()

    private var _binding: FragmentUsersListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUsersListBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.btAddUsersList.setOnClickListener { viewModel.addUsers() }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}