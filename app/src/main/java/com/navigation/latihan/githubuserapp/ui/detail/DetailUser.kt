package com.navigation.latihan.githubuserapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.navigation.latihan.githubuserapp.databinding.ActivityDetailUserBinding
import com.navigation.latihan.githubuserapp.ui.detail.model.DetailModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUser : AppCompatActivity() {

    private lateinit var binding : ActivityDetailUserBinding
    private lateinit var viewModel : DetailModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.elevation = 0f
        supportActionBar?.title = "Detail User"

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatarUrl = intent.getStringExtra(EXTRA_URL)

        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        loading(true)
        viewModel=ViewModelProvider(this).get(DetailModel::class.java)

        if (username != null){
            viewModel.setDetail(username)
        }
        viewModel.getDetail().observe(this) {
            if (it != null) {
                binding.apply {
                    itemUsername.text = it.login
                    itemName.text = it.name
                    itemCompany.text = it.company
                    itemLocation.text = it.location
                    repository.text = "${it.public_repos}"
                    followers.text = "${it.followers}"
                    following.text = "${it.following}"
                    Glide.with(this@DetailUser)
                        .load(it.avatar_url)
                        .centerCrop()
                        .into(imgPhoto)
                        loading(false)
                }
            }
        }

        var _Checked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main){
                if (count != null){
                    if (count>0){
                        binding.toggleFavorite.isChecked = true
                        _Checked = true
                    }else{
                        binding.toggleFavorite.isChecked = false
                        _Checked = false
                    }
                }
            }
        }


        binding.toggleFavorite.setOnClickListener {
            _Checked = !_Checked
            if (_Checked) {
                    viewModel.addToFavorite(username!!, id, avatarUrl)
                } else {
                    viewModel.removeFromFavorite(id)
                }
                binding.toggleFavorite.isChecked = _Checked
            }

        val pager = PagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewPager.adapter = pager
            tabs.setupWithViewPager(viewPager)
        }

    }

    private fun loading(statement : Boolean){
        if(statement){
            binding.progress.visibility = View.VISIBLE
        }else{

            binding.progress.visibility = View.GONE
        }
    }
    companion object{
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_URL = "extra_url"
    }
}