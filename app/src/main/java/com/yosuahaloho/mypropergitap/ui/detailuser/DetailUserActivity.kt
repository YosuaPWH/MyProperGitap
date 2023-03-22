package com.yosuahaloho.mypropergitap.ui.detailuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.yosuahaloho.mypropergitap.databinding.ActivityDetailUserBinding
import com.yosuahaloho.mypropergitap.utils.ViewModelFactory
import com.yosuahaloho.mypropergitap.utils.Result

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding

    private val detailUserFactory by lazy { ViewModelFactory.getInstance(this) }
    private val detailUserViewModel: DetailUserViewModel by viewModels { detailUserFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        startShimmer()

        val username = intent.extras?.let { DetailUserActivityArgs.fromBundle(it).username }

        Log.d("dawdaw", username.toString())
        detailUserViewModel.getDetailUser(username.toString()).observe(this) {
            when (it) {
                is Result.Success -> {
                    stopShimmer()
                    binding.detailUsername.text = username
                    Log.d("DetailActivity", it.data.toString())
                    Glide
                        .with(this)
                        .load(it.data.avatar_url)
                        .placeholder(android.R.drawable.btn_star)
                        .into(binding.detailImage)

                    binding.detailName.text = it.data.name
                    binding.detailFollowersSize.text = it.data.followers.toString()
                    binding.detailFollowingSize.text = it.data.following.toString()
                    binding.detailPublicRepos.text = it.data.public_repos.toString()
                }
                is Result.Loading -> {
                    startShimmer()
                }
                is Result.Error -> {
                    Log.e("DetailActivity&getDetailUser", it.error)
                }
            }
        }
    }

    private fun startShimmer() {
        binding.layoutDetail.visibility = View.GONE
        binding.loadingShimmer.visibility = View.VISIBLE
        binding.loadingShimmer.startShimmer()
    }

    private fun stopShimmer() {
        binding.loadingShimmer.visibility = View.GONE
        binding.loadingShimmer.stopShimmer()
        binding.layoutDetail.visibility = View.VISIBLE
    }
}