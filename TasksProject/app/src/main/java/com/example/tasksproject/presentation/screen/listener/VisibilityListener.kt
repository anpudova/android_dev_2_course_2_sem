package com.example.tasksproject.presentation.screen.listener

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class VisibilityListener(
    private val visibilityPercentage: Int
) : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val layoutManager: LinearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
        val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

        val firstVisibleItem = layoutManager.findViewByPosition(firstVisibleItemPosition)
        val lastVisibleItem = layoutManager.findViewByPosition(lastVisibleItemPosition)

        val screenHeight = recyclerView.height

        if (firstVisibleItem != null && lastVisibleItem != null) {
            val firstVisibleItemPercentage = getVisiblePercentage(firstVisibleItem, screenHeight)
            val lastVisibleItemPercentage = getVisiblePercentage(lastVisibleItem, screenHeight)

            if (dy < 0 && firstVisibleItemPercentage == visibilityPercentage) {
                Toast.makeText(recyclerView.context, "Элемент №${firstVisibleItemPosition + 1} виден на $visibilityPercentage%", Toast.LENGTH_SHORT).show()
            }

            if (dy > 0 && lastVisibleItemPercentage == visibilityPercentage) {
                Toast.makeText(recyclerView.context, "Элемент №${lastVisibleItemPosition + 1} виден на $visibilityPercentage%", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getVisiblePercentage(view: View, screenHeight: Int): Int {
        val viewHeight = view.height
        val visibleHeight = getVisibleHeight(view, screenHeight)
        return (visibleHeight * 100) / viewHeight
    }

    private fun getVisibleHeight(view: View, screenHeight: Int): Int {
        val viewTop = view.top
        val viewBottom = view.bottom
        return if (viewTop >= 0 && viewBottom <= screenHeight) {
            view.height
        } else if (viewTop < 0 && viewBottom > 0) {
            viewBottom
        } else if (viewTop < 0 && viewBottom <= screenHeight) {
            view.height + viewTop
        } else {
            screenHeight - viewTop
        }
    }

}