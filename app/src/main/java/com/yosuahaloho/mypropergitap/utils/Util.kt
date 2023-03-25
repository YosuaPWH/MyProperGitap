package com.yosuahaloho.mypropergitap.utils

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.yosuahaloho.mypropergitap.R
import com.yosuahaloho.mypropergitap.ui.home.HomeFragment

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

    fun displayNoUser(
        viewToGone: View,
        noUserView: View,
        stringResourceFollowFragment: Int? = null,
        textNoUserTextViewHomeFragment: TextView? = null,
        username: String? = null
    ) {
        if (stringResourceFollowFragment != null) {
            textNoUserTextViewHomeFragment?.text = viewToGone.context.getString(stringResourceFollowFragment, username)
        }
        viewToGone.visibility = View.GONE
        noUserView.visibility = View.VISIBLE
    }

    fun unDisplayNoUser(noUserView: View) {
        noUserView.visibility = View.GONE
    }

}
