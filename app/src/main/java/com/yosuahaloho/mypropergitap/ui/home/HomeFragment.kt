package com.yosuahaloho.mypropergitap.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.yosuahaloho.mypropergitap.databinding.FragmentHomeBinding
import com.yosuahaloho.mypropergitap.repos.model.User
import com.yosuahaloho.mypropergitap.utils.ListUserAdapter
import com.yosuahaloho.mypropergitap.utils.Result
import com.yosuahaloho.mypropergitap.utils.Util
import com.yosuahaloho.mypropergitap.utils.ViewModelFactory
import timber.log.Timber

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var listUserAdapter: ListUserAdapter

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
        listUserAdapter = ListUserAdapter(isHomeFragments = true, isFavoriteFragments = false)
        binding.rvUser.adapter = listUserAdapter

        Util.startShimmer(binding.rvUser, binding.loadingShimmer)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.getDefaultUser().observeData("getDefaultUser")
        setSearchView()
    }

    private fun setSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(username: String): Boolean {
                homeViewModel.searchUser(username).observeData("searchUser")
                binding.searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                Timber.i(newText)
                return true
            }
        })
    }

    private fun LiveData<Result<List<User>>>.observeData(functionName: String) {
        this.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    Util.stopShimmer(binding.rvUser, binding.loadingShimmer)
                    Timber.d("$functionName -> ${it.data}")
                    Timber.d("$functionName size data -> ${it.data.size}")

                    listUserAdapter.submitList(it.data)
                    binding.rvUser.adapter = listUserAdapter
                }
                is Result.Loading -> {
                    Util.startShimmer(binding.rvUser, binding.loadingShimmer)
                }
                is Result.Error -> {
                    Timber.e("$functionName -> " + it.error)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}