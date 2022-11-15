package com.navigation.latihan.githubuserapp.ui.Favorit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.navigation.latihan.githubuserapp.data.model.User
import com.navigation.latihan.githubuserapp.databinding.ActivityFavoritBinding
import com.navigation.latihan.githubuserapp.ui.detail.DetailUser
import com.navigation.latihan.githubuserapp.ui.local.Favorite
import com.navigation.latihan.githubuserapp.ui.search.SearchAdapter

class FavoritActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFavoritBinding
    private lateinit var adapterUser : SearchAdapter
    private lateinit var viewModel: FavoritModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapterUser = SearchAdapter()
        adapterUser.notifyDataSetChanged()

        viewModel = ViewModelProvider(this).get(FavoritModel::class.java)

        adapterUser.setOnItemClickCallback(object : SearchAdapter.OnItemCickCallback {
            override fun onItemClicked(user: User) {
                Intent(this@FavoritActivity, DetailUser::class.java).also {
                    it.putExtra(DetailUser.EXTRA_USERNAME, user.login)
                    it.putExtra(DetailUser.EXTRA_ID, user.id)
                    it.putExtra(DetailUser.EXTRA_URL, user.avatar_url)
                    startActivity(it)
                }
            }
        })

        binding.apply {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager = LinearLayoutManager(this@FavoritActivity)
            rvUser.adapter = adapterUser
        }

        viewModel.getFavoriteUser()?.observe(this) {
            if (it != null) {
                val list = mapList(it)
                adapterUser.setList(list)
            }
        }
    }

    private fun mapList(users: List<Favorite>): ArrayList<User> {
        val listUsers = ArrayList<User>()
        for (user in users){
            val userMapped = User(
                user.id,
                user.login,
                user.avatar_url
            )
            listUsers.add(userMapped)
        }
        return listUsers
    }
}