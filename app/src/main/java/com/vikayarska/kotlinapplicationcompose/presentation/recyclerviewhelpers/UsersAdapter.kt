package com.vikayarska.kotlinapplicationcompose.presentation.recyclerviewhelpers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.vikayarska.data.model.AppUser
import com.vikayarska.kotlinapplicationcompose.R
import com.vikayarska.kotlinapplicationcompose.presentation.recyclerviewhelpers.viewholder.UserViewHolder

class UsersAdapter(private val onClick: (AppUser) -> Unit) :
    PagingDataAdapter<AppUser, UserViewHolder>(diffCallback) {
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_user_item, parent, false),
            onClick
        )
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<AppUser>() {
            override fun areItemsTheSame(
                oldItem: AppUser,
                newItem: AppUser
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: AppUser,
                newItem: AppUser
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}