package com.yosuahaloho.mypropergitap.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.yosuahaloho.mypropergitap.databinding.FragmentFavoriteBinding
import com.yosuahaloho.mypropergitap.repos.local.entity.FavoriteUser
import com.yosuahaloho.mypropergitap.repos.model.User
import com.yosuahaloho.mypropergitap.utils.ListUserAdapter
import com.yosuahaloho.mypropergitap.utils.Result
import com.yosuahaloho.mypropergitap.utils.Util
import com.yosuahaloho.mypropergitap.utils.ViewModelFactory
import timber.log.Timber

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var favoriteUserAdapter: ListUserAdapter

    private val favoriteViewModel by viewModels<FavoriteViewModel> {
        ViewModelFactory.getInstance(
            requireContext()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        binding.rvUserFavorite.layoutManager = LinearLayoutManager(requireContext())
        favoriteUserAdapter = ListUserAdapter(isHomeFragments = false, isFavoriteFragments = true)
        binding.rvUserFavorite.adapter = favoriteUserAdapter

        Util.startShimmer(binding.rvUserFavorite, binding.loadingShimmer)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteViewModel.getFavoriteUser().observeDataFavorite("Get Favorite User")
    }

    private fun LiveData<Result<List<FavoriteUser>>>.observeDataFavorite(functionName: String) {
        this.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    Util.stopShimmer(binding.rvUserFavorite, binding.loadingShimmer)
                    Timber.d("$functionName -> ${it.data}")
                    Timber.d("$functionName size data ->: ${it.data.size}")
                    val items = arrayListOf<User>()
                    it.data.map { fav ->
                        val item = User(
                            username = fav.username,
                            avatar_url = fav.avatar_url,
                            id = 1
                        )
                        items.add(item)
                    }

                    favoriteUserAdapter.submitList(items)
                    binding.rvUserFavorite.adapter = favoriteUserAdapter
                }
                is Result.Loading -> {
                    Util.startShimmer(binding.rvUserFavorite, binding.loadingShimmer)
                }
                is Result.Error -> {
                    Timber.e("$functionName -> " + it.error)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        favoriteViewModel.getFavoriteUser().observeDataFavorite("Get Favorite User")
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}