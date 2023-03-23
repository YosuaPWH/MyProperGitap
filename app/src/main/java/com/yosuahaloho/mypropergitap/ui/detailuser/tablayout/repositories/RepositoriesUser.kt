package com.yosuahaloho.mypropergitap.ui.detailuser.tablayout.repositories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yosuahaloho.mypropergitap.databinding.FragmentRepositoriesUserBinding

class RepositoriesUser : Fragment() {

    private var _binding: FragmentRepositoriesUserBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRepositoriesUserBinding.inflate(inflater, container, false)


        return binding.root
    }
}