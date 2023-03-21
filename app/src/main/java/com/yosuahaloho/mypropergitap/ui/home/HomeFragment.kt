package com.yosuahaloho.mypropergitap.ui.home

import android.graphics.Path.Direction
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.yosuahaloho.mypropergitap.utils.Result
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.facebook.shimmer.Shimmer
import com.yosuahaloho.mypropergitap.R
import com.yosuahaloho.mypropergitap.databinding.FragmentHomeBinding
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


//        binding.rvUser.layoutManager = LinearLayoutManager(context)
        binding.rvUser.setLayoutManager(LinearLayoutManager(context))
        binding.rvUser.shimmerEnable = true
        val shimmer = Shimmer.ColorHighlightBuilder()
            .setBaseColor(ContextCompat.getColor(requireContext(), R.color.purple_200))
            .setHighlightColor(ContextCompat.getColor(requireContext(), R.color.black))
            .setBaseAlpha(1f)
            .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
            .setHighlightAlpha(1f)
            .build()

        binding.rvUser.shimmer = shimmer
        binding.rvUser.addVeiledItems(10)
        binding.rvUser.veil()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.rvUser.getRecyclerView()



        homeViewModel.getDefaultUser().observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    binding.rvUser.unVeil()
                    binding.rvUser.shimmerEnable = false
                    Log.d("Home", it.data.toString())
                    binding.rvUser.getRecyclerView().adapter = ListUserAdapter(it.data)
                }
                is Result.Loading -> {
                    binding.rvUser.veil()
                    binding.rvUser.shimmerEnable = true
                    Log.d("Home", "Loading")
                }
                is Result.Error -> {
                    binding.rvUser.unVeil()
                    binding.rvUser.shimmerEnable = false
                    Log.d("Home", "Error")
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}