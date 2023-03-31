package com.yosuahaloho.mypropergitap.utils

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.facebook.shimmer.ShimmerFrameLayout
import com.yosuahaloho.mypropergitap.repos.model.User
import com.yosuahaloho.mypropergitap.ui.detailuser.DetailUserActivity
import com.yosuahaloho.mypropergitap.ui.favorite.FavoriteFragment
import com.yosuahaloho.mypropergitap.ui.favorite.FavoriteFragmentDirections
import com.yosuahaloho.mypropergitap.ui.home.HomeFragment
import com.yosuahaloho.mypropergitap.ui.home.HomeFragmentDirections

object Util {

    fun stopShimmer(realLayout: View, loadingLayout: ShimmerFrameLayout) {
        loadingLayout.visibility = View.GONE
        loadingLayout.stopShimmer()
        realLayout.visibility = View.VISIBLE
    }

    fun startShimmer(realLayout: View, loadingLayout: ShimmerFrameLayout) {
        realLayout.visibility = View.GONE
        loadingLayout.visibility = View.VISIBLE
        loadingLayout.startShimmer()
    }

    fun showError(realLayout: View, loadingLayout: ShimmerFrameLayout, layoutError: View) {
        realLayout.visibility = View.GONE
        loadingLayout.visibility = View.GONE
        loadingLayout.stopShimmer()
        layoutError.visibility = View.VISIBLE
    }

    fun removeError(layoutError: View) {
        layoutError.visibility = View.GONE
    }

    fun displayNoUser(
        viewToGone: View,
        noUserView: View,
        stringResourceFollowFragment: Int? = null,
        textNoUserTextViewHomeFragment: TextView? = null,
        username: String? = null
    ) {
        if (stringResourceFollowFragment != null) {
            textNoUserTextViewHomeFragment?.text =
                viewToGone.context.getString(stringResourceFollowFragment, username)
        }
        viewToGone.visibility = View.GONE
        noUserView.visibility = View.VISIBLE
    }

    fun unDisplayNoUser(noUserView: View) {
        noUserView.visibility = View.GONE
    }

    fun onUserClickedToDetailActivity(data: User, fragment: Fragment) {
        when (fragment) {
            is HomeFragment -> {
                val toDetailUserActivity =
                    HomeFragmentDirections.actionNavigationHomeToDetailUserActivity()
                toDetailUserActivity.username = data.username
                fragment.findNavController().navigate(toDetailUserActivity)
            }
            is FavoriteFragment -> {
                val toDetailUserActivity =
                    FavoriteFragmentDirections.actionNavigationFavoriteToDetailUserActivity()
                toDetailUserActivity.username = data.username
                fragment.findNavController().navigate(toDetailUserActivity)
            }
            else -> {
                val intent = Intent(fragment.requireContext(), DetailUserActivity::class.java)
                val bundle = Bundle().apply { putString("username", data.username) }
                intent.putExtras(bundle)
                fragment.startActivity(intent)
            }
        }
    }


}
