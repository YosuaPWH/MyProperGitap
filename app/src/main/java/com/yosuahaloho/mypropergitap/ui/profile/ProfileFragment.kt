package com.yosuahaloho.mypropergitap.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.yosuahaloho.mypropergitap.R
import com.yosuahaloho.mypropergitap.databinding.FragmentProfileBinding
import com.yosuahaloho.mypropergitap.ui.detailuser.follow.FollowFragment
import com.yosuahaloho.mypropergitap.utils.Result
import com.yosuahaloho.mypropergitap.utils.Util.startShimmer
import com.yosuahaloho.mypropergitap.utils.Util.stopShimmer
import com.yosuahaloho.mypropergitap.utils.ViewModelFactory
import timber.log.Timber

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private var numFollowers: Int? = null
    private var numFollowing: Int? = null

    private val profileViewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(
            requireContext()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        getDetailUser()
        return binding.root
    }


    private fun getDetailUser() {
        profileViewModel.getDetailUser("YosuaPWH").observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    stopShimmer(binding.realLayoutProfile, binding.loadingShimmer)
                    binding.detailUsername.text = resources.getText(R.string.username)

                    Glide
                        .with(this)
                        .load(it.data.avatar_url)
                        .into(binding.detailImage)

                    binding.detailName.text = it.data.name
                    binding.detailFollowersSize.text = it.data.followers.toString()
                    numFollowers = it.data.followers
                    binding.detailFollowingSize.text = it.data.following.toString()
                    numFollowing = it.data.following
                    binding.detailPublicRepos.text = it.data.public_repos.toString()
                }
                is Result.Loading -> {
                    startShimmer(binding.realLayoutProfile, binding.loadingShimmer)
                }
                is Result.Error -> {
                    Timber.d("Gagal menghubungkan ke jaringan...")
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            profileViewModel.saveThemeSetting(isChecked)
        }

        profileViewModel.getThemeSettings().observe(viewLifecycleOwner) { isDarkModeActive ->
            binding.switchTheme.isChecked = isDarkModeActive
        }
        setTab()
    }

    private fun setTab() {
        binding.viewpagerProfile.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return 2
            }

            override fun createFragment(position: Int): Fragment {
                var fragment: Fragment? = null
                when (position) {
                    0 -> {
                        fragment = FollowFragment()
                        fragment.arguments = Bundle().apply {
                            putString(FollowFragment.DETAIL_USERNAME, "YosuaPWH")
                            putInt(FollowFragment.SECTION_NUMBER_PAGER, position)
                            numFollowers?.let { putInt(FollowFragment.NUM_FOLLOWERS, it) }
                        }
                    }
                    1 -> {
                        fragment = FollowFragment()
                        fragment.arguments = Bundle().apply {
                            putString(FollowFragment.DETAIL_USERNAME, "YosuaPWH")
                            putInt(FollowFragment.SECTION_NUMBER_PAGER, position)
                            numFollowing?.let { putInt(FollowFragment.NUM_FOLLOWING, it) }
                        }
                    }
                }
                return fragment as Fragment
            }
        }

        TabLayoutMediator(binding.tabLayoutProfile, binding.viewpagerProfile) { tab, position ->
            tab.text = TITLE_PAGER[position]
        }.attach()
    }

    companion object {
        private val TITLE_PAGER = arrayOf(
            "Followers",
            "Following"
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}