package com.yosuahaloho.mypropergitap.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.yosuahaloho.mypropergitap.utils.Result
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.yosuahaloho.mypropergitap.databinding.FragmentHomeBinding
import com.yosuahaloho.mypropergitap.repos.model.User
import com.yosuahaloho.mypropergitap.utils.ListUserAdapter
import com.yosuahaloho.mypropergitap.utils.ViewModelFactory

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val factory by lazy { ViewModelFactory.getInstance(requireActivity()) }
    private val homeViewModel: HomeViewModel by viewModels { factory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.rvUser.adapter = ListUserAdapter(emptyList())
        startShimmer()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getDefaultUser()
        setSearchView()
    }

    private fun setSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                homeViewModel.searchUser(query.toString()).observeData()
                binding.searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.i("Search", newText.toString())
                return true
            }
        })
    }

    private fun LiveData<Result<List<User>>>.observeData() {
        this.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    stopShimmer()
                    Log.d(this.toString(), it.data.toString())
                    binding.rvUser.layoutManager = LinearLayoutManager(requireContext())
                    val dataAdapter = ListUserAdapter(it.data)
                    binding.rvUser.adapter = dataAdapter
                }
                is Result.Loading -> {
                    startShimmer()
                }
                is Result.Error -> {
                    Log.e(this.toString(), it.error)
                }
            }
        }
    }

    private fun getDefaultUser() {
        homeViewModel.getDefaultUser().observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    stopShimmer()
                    Log.d("Home", it.data.toString())
                    binding.rvUser.layoutManager = LinearLayoutManager(requireContext())
                    val dataAdapter = ListUserAdapter(it.data)
                    binding.rvUser.adapter = dataAdapter

                }
                is Result.Loading -> {
                    startShimmer()
                }
                is Result.Error -> {
                    Log.e("HomeFragment&OnViewCreated&getDefaultUser", it.error)
                }
            }
        }
    }

    private fun stopShimmer() {
        binding.loadingShimmer.visibility = View.GONE
        binding.loadingShimmer.stopShimmer()
        binding.rvUser.visibility = View.VISIBLE
    }

    private fun startShimmer() {
        binding.rvUser.visibility = View.GONE
        binding.loadingShimmer.visibility = View.VISIBLE
        binding.loadingShimmer.startShimmer()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}