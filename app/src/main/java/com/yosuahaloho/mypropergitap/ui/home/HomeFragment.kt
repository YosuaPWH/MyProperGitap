package com.yosuahaloho.mypropergitap.ui.home

import android.os.Bundle
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
import timber.log.Timber

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val factory by lazy { ViewModelFactory.getInstance(requireContext()) }
    private val homeViewModel: HomeViewModel by viewModels { factory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.rvUser.adapter = ListUserAdapter(emptyList(), true)
        startShimmer()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.getDefaultUser().observeData("getDefaultUser")
        setSearchView()
    }

    private fun setSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                homeViewModel.searchUser(query.toString()).observeData("searchUser")
                binding.searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Timber.i(newText.toString())
                return true
            }
        })
    }

    private fun LiveData<Result<List<User>>>.observeData(functionName: String) {
        this.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    stopShimmer()
                    Timber.d("$functionName -> ${it.data}")
                    Timber.d("$functionName size data -> ${it.data.size}")
                    binding.rvUser.layoutManager = LinearLayoutManager(requireContext())
                    val dataAdapter = ListUserAdapter(it.data, true)
                    binding.rvUser.adapter = dataAdapter
                }
                is Result.Loading -> {
                    startShimmer()
                }
                is Result.Error -> {
                    Timber.e("$functionName -> " + it.error)
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