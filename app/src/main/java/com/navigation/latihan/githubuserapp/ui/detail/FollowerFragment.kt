package com.navigation.latihan.githubuserapp.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.navigation.latihan.githubuserapp.R
import com.navigation.latihan.githubuserapp.databinding.FragmentFollowersBinding
import com.navigation.latihan.githubuserapp.ui.detail.model.FollowModel
import com.navigation.latihan.githubuserapp.ui.search.SearchAdapter

class FollowerFragment : Fragment(R.layout.fragment_followers) {

    private var _binding : FragmentFollowersBinding?=null
    private val binding get() = _binding
    private lateinit var viewModel : FollowModel
    private lateinit var followerAdapter : SearchAdapter
    private lateinit var username : String

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentFollowersBinding.bind(view)

        username = arguments?.getString(DetailUser.EXTRA_USERNAME).toString()

        followerAdapter = SearchAdapter()
        binding?.apply {
            rvFollow.layoutManager = LinearLayoutManager(requireContext())
            rvFollow.setHasFixedSize(true)
            rvFollow.adapter = followerAdapter

        }
        loading(true)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).
        get(FollowModel::class.java)

        viewModel.setFollower(username)
        viewModel.getFollower().observe(viewLifecycleOwner) {
            if (it != null) {
                followerAdapter.setList(it)
                loading(false)
            }
        }
    }
    private fun loading(statement : Boolean){
        if(statement){
            binding?.progress?.visibility = View.VISIBLE
        }else{

            binding?.progress?.visibility = View.GONE
        }
    }
}