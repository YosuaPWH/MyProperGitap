package com.yosuahaloho.mypropergitap.ui.detailuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
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

        val username = intent.extras?.let { DetailUserActivityArgs.fromBundle(it).username }

        binding.tvUsername.text = username
        Log.d("dawdaw", username.toString())
        detailUserViewModel.getDetailUser(username.toString()).observe(this) {
            when (it) {
                is Result.Success -> {
                    Log.d("DetailActivity", it.data.toString())
                }
                is Result.Loading -> {

                }
                is Result.Error -> {
                    Log.e("DetailActivity&getDetailUser", it.error)
                }
            }
        }
    }
}