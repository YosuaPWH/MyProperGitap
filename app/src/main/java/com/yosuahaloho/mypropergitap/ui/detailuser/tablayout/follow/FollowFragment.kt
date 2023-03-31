package com.yosuahaloho.mypropergitap.ui.detailuser.tablayout.follow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.yosuahaloho.mypropergitap.R
import com.yosuahaloho.mypropergitap.databinding.FragmentFollowBinding
import com.yosuahaloho.mypropergitap.repos.model.User
import com.yosuahaloho.mypropergitap.utils.ListUserAdapter
import com.yosuahaloho.mypropergitap.utils.Result
import com.yosuahaloho.mypropergitap.utils.Util
import com.yosuahaloho.mypropergitap.utils.ViewModelFactory
import timber.log.Timber

class FollowFragment : Fragment() {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private val followersData = arrayListOf<User>()
    private val followingData = arrayListOf<User>()
    private var pageFollowers = 1
    private var pageFollowing = 1
    private var username = "username"
    private val followUserAdapter by lazy { ListUserAdapter() }

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
        binding.rvUserFollow.adapter = followUserAdapter
        setTab()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        followUserAdapter.setLoadMoreClickCallback(object :
            ListUserAdapter.OnLoadMoreClickCallBack {
            override fun onLoadMoreClicked() {

            }
        })
    }

    private fun setTab() {
        username = requireArguments().getString(DETAIL_USERNAME).toString()
        val sectionNumberPager = requireArguments().getInt(SECTION_NUMBER_PAGER)

        if (sectionNumberPager == 0) {
            followViewModel.getFollowers(username, 1).observeDataFollow(isFollowers = true)
        } else {
            followViewModel.getFollowing(username, 1).observeDataFollow(isFollowers = false)
        }
    }

    private fun LiveData<Result<List<User>>>.observeDataFollow(isFollowers: Boolean) {
        this.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    Util.stopShimmer(binding.rvUserFollow, binding.loadingShimmer)
                    Util.removeError(binding.layoutError)
                    if (it.data.isNotEmpty()) {
                        if (isFollowers) {
                            followersData.addAll(it.data)
                            followUserAdapter.setData(followersData)
                        } else {
                            followingData.addAll(it.data)
                            followUserAdapter.setData(followingData)
                        }
                        clickToDetailActivityFromFollow()
                        isLoadMore(isFollowers)
                        onLoadMoreUser(isFollowers)
                    } else {
                        Util.displayNoUser(
                            viewToGone = binding.rvUserFollow,
                            noUserView = binding.layoutNotFound,
                            stringResourceFollowFragment = if (isFollowers) {
                                R.string.no_follower
                            } else R.string.no_following,
                            textNoUserTextViewHomeFragment = binding.txtNotFound,
                        )
                    }
                }
                is Result.Loading -> {
                    Util.removeError(binding.layoutError)
                    Util.startShimmer(binding.rvUserFollow, binding.loadingShimmer)
                    Util.unDisplayNoUser(binding.layoutNotFound)
                }
                is Result.Error -> {
                    Timber.e(it.error)
                    Util.showError(
                        binding.rvUserFollow,
                        binding.loadingShimmer,
                        binding.layoutError
                    )
                }
            }
        }
    }

    private fun isLoadMore(isFollowers: Boolean) {
        if (isFollowers) {
            val numFollowers = arguments?.getInt(NUM_FOLLOWERS)
            numFollowers?.let { countFollowers ->
                followUserAdapter.isLoadMoreUser = followersData.size < countFollowers
                Timber.d("countFollowers -> $countFollowers")
                Timber.d("followersData -> ${followersData.size}")
            }
        } else {
            val numFollowing = arguments?.getInt(NUM_FOLLOWING)
            numFollowing?.let { countFollowing ->
                followUserAdapter.isLoadMoreUser = followingData.size < countFollowing
                Timber.d("countFollowing -> $countFollowing")
                Timber.d("followingData -> ${followingData.size}")
            }
        }
    }

    private fun onLoadMoreUser(isFollowers: Boolean) {
        followUserAdapter.setLoadMoreClickCallback(object :
            ListUserAdapter.OnLoadMoreClickCallBack {
            override fun onLoadMoreClicked() {
                if (isFollowers) {
                    followViewModel.getFollowers(username, ++pageFollowers)
                        .observeDataFollow(isFollowers = true)
                } else {
                    followViewModel.getFollowers(username, ++pageFollowing)
                        .observeDataFollow(isFollowers = false)
                }
            }
        })
    }

    private fun clickToDetailActivityFromFollow() {
        followUserAdapter.setOnUserClickCallback(object : ListUserAdapter.OnUserClickCallback {
            override fun onUserClicked(data: User) {
                Util.onUserClickedToDetailActivity(data, this@FollowFragment)
            }
        })
    }

    companion object {
        const val SECTION_NUMBER_PAGER = "SECTION_NUMBER_PAGER"
        const val DETAIL_USERNAME = "DETAIL_USERNAME"
        const val NUM_FOLLOWERS = "NUM_FOLLOWERS"
        const val NUM_FOLLOWING = "NUM_FOLLOWING"
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}