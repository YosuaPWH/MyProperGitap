package com.yosuahaloho.mypropergitap.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.yosuahaloho.mypropergitap.R
import com.yosuahaloho.mypropergitap.databinding.FragmentHomeBinding
import com.yosuahaloho.mypropergitap.repos.model.User
import com.yosuahaloho.mypropergitap.utils.ListUserAdapter
import com.yosuahaloho.mypropergitap.utils.Result
import com.yosuahaloho.mypropergitap.utils.Util
import com.yosuahaloho.mypropergitap.utils.ViewModelFactory
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var username: String
    private var page = 1
    private val searchData = arrayListOf<User>()
    private var loadDefaultUserUntilSearchHappen = true
    private var totalCountSearch = 0
    private var isQuerySubmitted = false
    private val listUserAdapter by lazy { ListUserAdapter() }

    private val homeViewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(
            requireContext()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.rvUser.layoutManager = LinearLayoutManager(requireContext())
        binding.rvUser.adapter = listUserAdapter
        setSearchView()
        Util.startShimmer(binding.rvUser, binding.loadingShimmer)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.getDefaultUser().observeData("getDefaultUser")
    }

    private fun setSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (!isQuerySubmitted) {
                    username = query
                    searchData.clear()
                    lifecycleScope.launch {
                        totalCountSearch = homeViewModel.getSearchUserCountAsync(username).await()
                        loadDefaultUserUntilSearchHappen = false
                        homeViewModel.searchUser(username, 1).observeData("searchUser")
                    }
                    Timber.d("DIKLIK")
                    isQuerySubmitted = true
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                Timber.i(newText)
                return true
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun LiveData<Result<List<User>>>.observeData(functionName: String) {
        this.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    Timber.d("$functionName size data -> ${it.data.size}")
                    Util.removeError(binding.layoutError)
                    Util.stopShimmer(binding.rvUser, binding.loadingShimmer)
                    if (it.data.isNotEmpty()) {
                        if (!loadDefaultUserUntilSearchHappen) {
                            searchData.addAll(it.data)
                            listUserAdapter.setData(searchData)
                        } else {
                            listUserAdapter.setData(it.data)
                        }
                        Timber.d("totalCountSearch -> $totalCountSearch")
                        Timber.d("searchData.size -> ${searchData.size}")
                        listUserAdapter.isLoadMoreUser = totalCountSearch > searchData.size
                        listUserAdapter.notifyDataSetChanged()
                        clickToDetailActivity()
                        onLoadMore()

                    } else {
                        Util.displayNoUser(
                            viewToGone = binding.rvUser,
                            noUserView = binding.layoutNotFound,
                            textNoUserTextViewHomeFragment = binding.txtNotFound,
                            stringResourceFollowFragment = R.string.not_found,
                            username = username,
                        )
                    }
                    isQuerySubmitted = false
                }
                is Result.Loading -> {
                    Util.removeError(binding.layoutError)
                    Util.unDisplayNoUser(binding.layoutNotFound)
                    Util.startShimmer(binding.rvUser, binding.loadingShimmer)
                }
                is Result.Error -> {
                    Timber.e("$functionName -> " + it.error)
                    Util.showError(binding.rvUser, binding.loadingShimmer, binding.layoutError)
                }
            }
        }
    }

    private fun onLoadMore() {
        listUserAdapter.setLoadMoreClickCallback(object : ListUserAdapter.OnLoadMoreClickCallBack {
            override fun onLoadMoreClicked() {
                homeViewModel.searchUser(username, ++page).observeData("Load More Data")
            }
        })
    }

    private fun clickToDetailActivity() {
        listUserAdapter.setOnUserClickCallback(object : ListUserAdapter.OnUserClickCallback {
            override fun onUserClicked(data: User) {
                Util.onUserClickedToDetailActivity(data, this@HomeFragment)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        totalCountSearch = 0
        searchData.clear()
    }
}