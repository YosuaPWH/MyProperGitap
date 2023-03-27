package com.yosuahaloho.mypropergitap.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.yosuahaloho.mypropergitap.databinding.FragmentProfileBinding
import com.yosuahaloho.mypropergitap.ui.detailuser.tablayout.follow.FollowFragment
import com.yosuahaloho.mypropergitap.ui.detailuser.tablayout.repositories.RepositoriesUser
import com.yosuahaloho.mypropergitap.utils.ViewModelFactory
import com.yosuahaloho.mypropergitap.utils.Result

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private var username: String? = null

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
        getDetailUser("YosuaPWH")
        return binding.root
    }


    private fun getDetailUser(username: String) {
        profileViewModel.getDetailUser(username).observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    binding.detailUsername.text = username

                    Glide
                        .with(this)
                        .load(it.data.avatar_url)
                        .into(binding.detailImage)

                    binding.detailName.text = it.data.name
                    binding.detailFollowersSize.text = it.data.followers.toString()
                    binding.detailFollowingSize.text = it.data.following.toString()
                    binding.detailPublicRepos.text = it.data.public_repos.toString()
                }
                is Result.Loading -> {

                }
                is Result.Error -> {

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

        binding.viewpagerProfile.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return 3
            }

            override fun createFragment(position: Int): Fragment {
                var fragment: Fragment? = null
                when (position) {
                    0 -> {
                        fragment = FollowFragment()
                        fragment.arguments = Bundle().apply {
                            putString(FollowFragment.DETAIL_USERNAME, "YosuaPWH")
                            putInt(FollowFragment.SECTION_NUMBER_PAGER, position)
                        }
                    }
                    1 -> {
                        fragment = FollowFragment()
                        fragment.arguments = Bundle().apply {
                            putString(FollowFragment.DETAIL_USERNAME, "YosuaPWH")
                            putInt(FollowFragment.SECTION_NUMBER_PAGER, position)
                        }
                    }
                    2 -> fragment = RepositoriesUser()
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
            "Following",
            "Repositories"
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}