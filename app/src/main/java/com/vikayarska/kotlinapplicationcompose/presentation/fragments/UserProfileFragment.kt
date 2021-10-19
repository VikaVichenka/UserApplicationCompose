package com.vikayarska.kotlinapplicationcompose.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.vikayarska.data.model.AppUser
import com.vikayarska.kotlinapplicationcompose.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user_profile, container, false)
        val user: AppUser = arguments?.getSerializable("user") as AppUser? ?: AppUser()
        view.findViewById<TextView>(R.id.tv_user_name).text = user.fullName()
        return view
    }
}