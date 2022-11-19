package com.sms.pipe.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CircularRecyclerViewListener(private val noOfItems: Int) : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        val firstItemVisible: Int = layoutManager.findFirstVisibleItemPosition()
        if (firstItemVisible != 1 && (firstItemVisible % noOfItems == 1)) {
            layoutManager.scrollToPosition(firstItemVisible % noOfItems)
        } else if (firstItemVisible != 1 && firstItemVisible > noOfItems && (firstItemVisible % noOfItems > 1)) {
            layoutManager.scrollToPosition(1)
        } else if (firstItemVisible == noOfItems) {
            layoutManager.scrollToPositionWithOffset(
                0,
                recyclerView.computeHorizontalScrollOffset()
            )
        }
    }
}