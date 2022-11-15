package com.navigation.latihan.githubuserapp.ui.search

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.navigation.latihan.githubuserapp.DataStore.DarkNightModelFactory
import com.navigation.latihan.githubuserapp.DataStore.DarkNightView
import com.navigation.latihan.githubuserapp.DataStore.Darknight
import com.navigation.latihan.githubuserapp.R
import com.navigation.latihan.githubuserapp.data.model.User
import com.navigation.latihan.githubuserapp.databinding.ActivityMainBinding
import com.navigation.latihan.githubuserapp.ui.Favorit.FavoritActivity
import com.navigation.latihan.githubuserapp.ui.detail.DetailUser

class MainActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityMainBinding
    private lateinit var Model : SearchModel
    private lateinit var adapterUser : SearchAdapter
    private val Context.DataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapterUser = SearchAdapter()
        adapterUser.notifyDataSetChanged()


        adapterUser.setOnItemClickCallback(object : SearchAdapter.OnItemCickCallback {
            override fun onItemClicked(user: User) {
                Intent(this@MainActivity, DetailUser::class.java).also {
                    it.putExtra(DetailUser.EXTRA_USERNAME, user.login)
                    it.putExtra(DetailUser.EXTRA_ID, user.id)
                    it.putExtra(DetailUser.EXTRA_URL, user.avatar_url)
                    startActivity(it)
                }
            }
        })

        Model = ViewModelProvider(
            this, ViewModelProvider.NewInstanceFactory()
        ).get((SearchModel::class.java))

        binding.apply {
            rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUser.setHasFixedSize(true)
            rvUser.adapter = adapterUser


            search.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query != null && query.isNotEmpty()) {
                        search()
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean = false
            })
        }

        Model.getSearch().observe(this) {
            Log.d("MainActivity", it.toString())
            if (it != null) {
                adapterUser.setList(it)
                Loading(false)
                binding.rvUser.visibility = View.VISIBLE
            }
        }
    }

    private fun search(){
        binding.apply {
            val query = search.query.toString()
            Log.d("MainActivity", query)
            if (query.isEmpty()) return
            binding.rvUser.visibility = View.GONE
            Loading(true)
            Model.setSearch(query)
        }
    }
    private fun Loading(load: Boolean) {
        if (load) {
            binding.progress.visibility = View.VISIBLE
        } else {
            binding.progress.visibility = View.GONE
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val pref = Darknight.getInstance(DataStore)
        val darkViewModel = ViewModelProvider(this, DarkNightModelFactory(pref)).get(
            DarkNightView::class.java
        )
        when(item.itemId){
            R.id.favorit -> {
                Intent(this, FavoritActivity::class.java).also {
                    startActivity(it)
                }
        return super.onOptionsItemSelected(item)
    }
            R.id.darkmode ->
                darkViewModel.getThemeSettings().observe(this) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    darkViewModel.saveThemeSetting(true)
                }

            R.id.lightmode ->
                darkViewModel.getThemeSettings().observe(this) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    darkViewModel.saveThemeSetting(false)
                }
        }
        return true
    }
}