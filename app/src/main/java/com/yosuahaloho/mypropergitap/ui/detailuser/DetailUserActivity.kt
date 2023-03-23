package com.yosuahaloho.mypropergitap.ui.detailuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.yosuahaloho.mypropergitap.databinding.ActivityDetailUserBinding
import com.yosuahaloho.mypropergitap.ui.detailuser.tablayout.follow.FollowFragment
import com.yosuahaloho.mypropergitap.ui.detailuser.tablayout.repositories.RepositoriesUser
import com.yosuahaloho.mypropergitap.utils.ViewModelFactory
import com.yosuahaloho.mypropergitap.utils.Result
import timber.log.Timber

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private var username: String? = null

    private val detailUserFactory by lazy { ViewModelFactory.getInstance(this) }
    private val detailUserViewModel: DetailUserViewModel by viewModels { detailUserFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        startShimmer()

        username = intent.extras?.let { DetailUserActivityArgs.fromBundle(it).username }
        getDetailUser(username.toString())
        Timber.d(username.toString())

        setTabViewPager()
    }

    private fun getDetailUser(username: String) {
        detailUserViewModel.getDetailUser(username).observe(this) {
            when (it) {
                is Result.Success -> {
                    stopShimmer()
                    binding.detailUsername.text = username
                    Timber.d(it.data.toString())
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
                    Timber.e(it.error)
                }
            }
        }
    }

    private fun setTabViewPager() {
        binding.detailViewPager.adapter = fragmentStateAdapter

        TabLayoutMediator(binding.tabsLayout, binding.detailViewPager) { tab, position ->
            tab.text = TITLE_PAGER[position]
        }.attach()
    }

    private val fragmentStateAdapter = object : FragmentStateAdapter(this) {
        override fun getItemCount(): Int {
            return 3
        }

        override fun createFragment(position: Int): Fragment {
            var fragment: Fragment? = null
            when (position) {
                0 -> {
                    fragment = FollowFragment()
                    fragment.arguments = Bundle().apply {
                        putString(FollowFragment.DETAIL_USERNAME, username)
                        putInt(FollowFragment.SECTION_NUMBER_PAGER, position)
                    }
                }
                1 -> {
                    fragment = FollowFragment()
                    fragment.arguments = Bundle().apply {
                        putString(FollowFragment.DETAIL_USERNAME, username)
                        putInt(FollowFragment.SECTION_NUMBER_PAGER, position)
                    }
                }
                2 -> fragment = RepositoriesUser()
            }
            return fragment as Fragment
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

    companion object {
        private val TITLE_PAGER = arrayOf(
            "Followers",
            "Following",
            "Repositories"
        )
    }
}