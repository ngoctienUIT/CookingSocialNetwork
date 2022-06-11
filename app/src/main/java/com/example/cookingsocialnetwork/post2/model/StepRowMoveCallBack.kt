package com.example.cookingsocialnetwork.post2.model

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class StepRowMoveCallBack(private val touchHelperContract: RecyclerViewRowTouchHelperContract) :
    ItemTouchHelper.Callback() {
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlag = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        return makeMovementFlags(dragFlag, 0)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        touchHelperContract.onRowMove(viewHolder.adapterPosition, target.adapterPosition)
        return false
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            if (viewHolder is StepAdapter.ViewHolder) {
                touchHelperContract.onRowSelected(viewHolder)
            }
        }
        super.onSelectedChanged(viewHolder, actionState)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        if (viewHolder is StepAdapter.ViewHolder) {
            touchHelperContract.onRowClear(viewHolder)
        }
        super.clearView(recyclerView, viewHolder)
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}
    override fun isItemViewSwipeEnabled(): Boolean {
        return false
    }

    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    interface RecyclerViewRowTouchHelperContract {
        fun onRowMove(from: Int, to: Int)
        fun onRowSelected(stepViewHolder: StepAdapter.ViewHolder?)
        fun onRowClear(stepViewHolder: StepAdapter.ViewHolder?)
    }
}