package com.yosuahaloho.mypropergitap.ui.favorite

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.yosuahaloho.mypropergitap.R
import com.yosuahaloho.mypropergitap.databinding.FragmentFavoriteBinding
import com.yosuahaloho.mypropergitap.utils.ViewModelFactory

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private val factory by lazy { ViewModelFactory.getInstance(requireActivity()) }
    private val favoriteViewModel: FavoriteViewModel by viewModels { factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        favoriteViewModel.text.observe(viewLifecycleOwner) {
            binding.textA.text = it
        }

        return binding.root
    }


}