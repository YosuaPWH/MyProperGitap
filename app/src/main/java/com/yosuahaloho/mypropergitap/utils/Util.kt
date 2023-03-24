package com.yosuahaloho.mypropergitap.utils

import android.view.View
import com.facebook.shimmer.ShimmerFrameLayout

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

}
