package com.vikayarska.kotlinapplicationcompose.presentation.recyclerviewhelpers.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vikayarska.data.model.AppUser
import com.vikayarska.kotlinapplicationcompose.R

class UserViewHolder(itemView: View, val onClick: (AppUser) -> Unit) :
    RecyclerView.ViewHolder(itemView) {
    var user: AppUser? = null
        private set
    private val name = itemView.findViewById<TextView>(R.id.tv_name_user_item)
    private val image = itemView.findViewById<ImageView>(R.id.iv_user_item)
    private val intro = itemView.findViewById<TextView>(R.id.tv_info_user_item)

    init {
        itemView.setOnClickListener {
            user?.let {
                onClick(it)
            }
        }
    }

    fun bindTo(item: AppUser) {
        user = item
        name.text = item.fullName()
        intro.text = item.intro

        Glide.with(itemView.context)
            .load(item.imageUrl)
            .circleCrop()
            .placeholder(R.drawable.ic_outline_image_24)
            .into(image);
    }
}
