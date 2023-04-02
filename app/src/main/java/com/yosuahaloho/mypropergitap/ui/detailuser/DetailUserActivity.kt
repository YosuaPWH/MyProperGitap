package com.yosuahaloho.mypropergitap.ui.detailuser

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.yosuahaloho.mypropergitap.R
import com.yosuahaloho.mypropergitap.databinding.ActivityDetailUserBinding
import com.yosuahaloho.mypropergitap.repos.local.entity.FavoriteUser
import com.yosuahaloho.mypropergitap.repos.model.DetailUser
import com.yosuahaloho.mypropergitap.ui.detailuser.follow.FollowFragment
import com.yosuahaloho.mypropergitap.utils.Result
import com.yosuahaloho.mypropergitap.utils.Util
import com.yosuahaloho.mypropergitap.utils.ViewModelFactory
import timber.log.Timber

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private var username: String? = null
    private var numFollowers: Int? = null
    private var numFollowing: Int? = null

    private val detailUserViewModel by viewModels<DetailUserViewModel> {
        ViewModelFactory.getInstance(
            application
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Util.startShimmer(binding.layoutDetail, binding.loadingShimmer)

        username = intent.extras?.let { DetailUserActivityArgs.fromBundle(it).username }

        getDetailUser(username.toString())
        Timber.d(username.toString())
        setTabViewPager()

    }

    private fun getDetailUser(username: String) {
        detailUserViewModel.getDetailUser(username).observe(this) {
            when (it) {
                is Result.Success -> {
                    Util.stopShimmer(binding.layoutDetail, binding.loadingShimmer)
                    setDataUser(it.data)
                    setFavorite(username, it.data.avatar_url)
                }
                is Result.Loading -> {
                    Util.startShimmer(binding.layoutDetail, binding.loadingShimmer)
                }
                is Result.Error -> {
                    Timber.e(it.error)
                }
            }
        }
    }

    private fun setDataUser(data: DetailUser) {
        binding.detailUsername.text = username
        Timber.d(data.toString())
        Glide
            .with(this)
            .load(data.avatar_url)
            .placeholder(android.R.drawable.btn_star)
            .into(binding.detailImage)

        binding.detailName.text = data.name
        binding.detailFollowersSize.text = data.followers.toString()
        numFollowers = data.followers
        binding.detailFollowingSize.text = data.following.toString()
        numFollowing = data.following
        binding.detailPublicRepos.text = data.public_repos.toString()
    }

    private fun setTabViewPager() {
        binding.detailViewPager.adapter = fragmentStateAdapter

        TabLayoutMediator(binding.tabsLayout, binding.detailViewPager) { tab, position ->
            tab.text = TITLE_PAGER[position]
        }.attach()
    }

    private val fragmentStateAdapter = object : FragmentStateAdapter(this) {
        override fun getItemCount(): Int {
            return 2
        }

        override fun createFragment(position: Int): Fragment {
            var fragment: Fragment? = null
            when (position) {
                0 -> {
                    fragment = FollowFragment()
                    fragment.arguments = Bundle().apply {
                        putString(FollowFragment.DETAIL_USERNAME, username)
                        putInt(FollowFragment.SECTION_NUMBER_PAGER, position)
                        numFollowers?.let { putInt(FollowFragment.NUM_FOLLOWERS, it) }
                    }
                }
                1 -> {
                    fragment = FollowFragment()
                    fragment.arguments = Bundle().apply {
                        putString(FollowFragment.DETAIL_USERNAME, username)
                        putInt(FollowFragment.SECTION_NUMBER_PAGER, position)
                        numFollowing?.let { putInt(FollowFragment.NUM_FOLLOWING, it) }
                    }
                }
            }
            return fragment as Fragment
        }
    }

    private fun setFavorite(username: String, avatar_url: String) {
        detailUserViewModel.isFavoriteUser(username)

        var btnFavorite = false
        detailUserViewModel.isFavorite.observe(this) {
            btnFavorite = it
            if (btnFavorite) {
                binding.fabFavorite.setImageResource(R.drawable.ic_filled_favorite)
            } else {
                binding.fabFavorite.setImageResource(R.drawable.ic_outlined_favorite)
            }
        }

        binding.fabFavorite.setOnClickListener {
            btnFavorite = !btnFavorite
            if (btnFavorite) {
                detailUserViewModel.addToFavorite(user = FavoriteUser(username, avatar_url))
                binding.fabFavorite.setImageResource(R.drawable.ic_filled_favorite)
            } else {
                detailUserViewModel.removeFromFavorite(user = FavoriteUser(username, avatar_url))
                binding.fabFavorite.setImageResource(R.drawable.ic_outlined_favorite)
            }
        }
    }

    companion object {
        private val TITLE_PAGER = arrayOf(
            "Followers",
            "Following"
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        username = null
    }
}