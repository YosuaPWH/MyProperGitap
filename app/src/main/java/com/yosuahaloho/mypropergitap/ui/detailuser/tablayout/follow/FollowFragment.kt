package com.yosuahaloho.mypropergitap.ui.detailuser.tablayout.follow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yosuahaloho.mypropergitap.utils.Result
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.yosuahaloho.mypropergitap.R
import com.yosuahaloho.mypropergitap.databinding.FragmentFollowBinding
import com.yosuahaloho.mypropergitap.repos.model.User
import com.yosuahaloho.mypropergitap.utils.ListUserAdapter
import com.yosuahaloho.mypropergitap.utils.Util
import com.yosuahaloho.mypropergitap.utils.ViewModelFactory
import timber.log.Timber

class FollowFragment : Fragment() {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private lateinit var followUserAdapter: ListUserAdapter

    private val followViewModel by viewModels<FollowViewModel> {
        ViewModelFactory.getInstance(
            requireContext()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        Util.startShimmer(binding.rvUserFollow, binding.loadingShimmer)

        binding.rvUserFollow.layoutManager = LinearLayoutManager(requireContext())
        followUserAdapter = ListUserAdapter(isHomeFragments = false, isFavoriteFragments = false)
        binding.rvUserFollow.adapter = followUserAdapter

        val username = arguments?.getString(DETAIL_USERNAME)
        val sectionNumberPager = arguments?.getInt(SECTION_NUMBER_PAGER)

        Timber.d("dawdaw $username")
        Timber.d("dagfaw $sectionNumberPager")
        if (username != null) {
            if (sectionNumberPager == 0) {
                followViewModel.getFollowers(username).observeDataFollow("getFollowers")
                Timber.d("$sectionNumberPager")
            } else {
                followViewModel.getFollowing(username).observeDataFollow("getFollowing")
                Timber.d("$sectionNumberPager")
            }
            Timber.d("$sectionNumberPager")
        }
        return binding.root
    }

    private fun LiveData<Result<List<User>>>.observeDataFollow(functionName: String) {
        this.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    Util.stopShimmer(binding.rvUserFollow, binding.loadingShimmer)
                    Timber.d("$functionName -> ${it.data}")
                    Timber.d("$functionName size data ->: ${it.data.size}")

                    if (it.data.isNotEmpty()) {
                        followUserAdapter.submitList(it.data)
                        binding.rvUserFollow.adapter = followUserAdapter
                    } else {
                        Util.displayNoUser(
                            viewToGone = binding.rvUserFollow,
                            noUserView = binding.layoutNotFound,
                            stringResourceFollowFragment = if (functionName == "getFollowers") {
                                R.string.no_follower
                            } else R.string.no_following,
                            textNoUserTextViewHomeFragment = binding.txtNotFound,
                        )
                    }
                }
                is Result.Loading -> {
                    Util.startShimmer(binding.rvUserFollow, binding.loadingShimmer)
                    Timber.d("Loading Follow")
                }
                is Result.Error -> {
                    Timber.e(it.error)
                }
            }
        }
    }

    companion object {
        const val SECTION_NUMBER_PAGER = "SECTION_NUMBER_PAGER"
        const val DETAIL_USERNAME = "DETAIL_USERNAME"
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}