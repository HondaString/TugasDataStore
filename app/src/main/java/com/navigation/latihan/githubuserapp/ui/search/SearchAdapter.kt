package com.navigation.latihan.githubuserapp.ui.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.navigation.latihan.githubuserapp.data.model.User
import com.navigation.latihan.githubuserapp.databinding.ItemUserBinding

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.UserViewHolder>() {

    private val listUser = ArrayList<User>()
    private var onItem : OnItemCickCallback? = null


    fun setOnItemClickCallback(onItem : OnItemCickCallback){
        this.onItem = onItem

    }

    fun setList(user: ArrayList<User>){
        listUser.clear()
        listUser.addAll(user)
        notifyDataSetChanged()
    }

    inner class UserViewHolder( val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun binding(user: User) {
            binding.root.setOnClickListener {
                onItem?.onItemClicked(user)
            }


            binding.apply {
                Glide.with(itemView)
                    .load(user.avatar_url)
                    .centerCrop()
                    .into(imgPhoto)
                tvId.text = "ID : ${user.id}"
                tvUsername.text = user.login.toString()

            }


        }
    }

    override fun onCreateViewHolder(view: ViewGroup, viewType: Int): UserViewHolder {
        val viewHolder = ItemUserBinding.inflate(LayoutInflater.from(view.context), view, false)
        return UserViewHolder(viewHolder)

    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.binding(listUser[position])
    }


    override fun getItemCount(): Int = listUser.size
    interface OnItemCickCallback {
        fun onItemClicked(user: User)

    }

}