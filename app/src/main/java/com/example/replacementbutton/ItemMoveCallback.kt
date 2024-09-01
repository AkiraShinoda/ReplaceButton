package com.example.replacementbutton

import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class ItemMoveCallback(private val adapter: RecyclerView.Adapter<*>) : ItemTouchHelper.Callback() {

    private var fromPosition: Int = RecyclerView.NO_POSITION
    private var toPosition: Int = RecyclerView.NO_POSITION
    private var lastTarget: RecyclerView.ViewHolder? = null

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        // ドラッグ方向の指定 (上下左右)
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        return makeMovementFlags(dragFlags, 0)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        Log.d("ReplaceButton", "onMove")
        val adapter = recyclerView.adapter as ItemAdapter
        if (fromPosition == RecyclerView.NO_POSITION) {
            fromPosition = viewHolder.bindingAdapterPosition
        }
        // 元の位置とターゲットの位置を入れ替える処理
        toPosition = target.bindingAdapterPosition

        // 前回のターゲットの色を元に戻す
        lastTarget?.let {
            val holder = it as ItemAdapter.ToolViewHolder
            //holder.testButton.background = adapter.defaultBackground
            holder.testButton.backgroundTintList = ColorStateList.valueOf(Color.BLACK)
        }

        // 新しいターゲットの色を変更
        val targetViewHolder = target as ItemAdapter.ToolViewHolder
        //targetViewHolder.testButton.background = adapter.activeBackground
        targetViewHolder.testButton.backgroundTintList = ColorStateList.valueOf(Color.BLUE)
        lastTarget = target
        return true
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        Log.d("ReplaceButton", "clearView")

        // ドラッグが終了したらアイテムを移動
        if (fromPosition != RecyclerView.NO_POSITION &&
            toPosition != RecyclerView.NO_POSITION &&
            fromPosition != toPosition) {
            Log.d("ReplaceButton", "if true")
            (adapter as ItemTouchHelperAdapter).onItemMove(fromPosition, toPosition)
            adapter.notifyItemMoved(fromPosition, toPosition)
        }
        // 前回のターゲットの色を元に戻す
        lastTarget?.let {
            val holder = it as ItemAdapter.ToolViewHolder
            holder.testButton.backgroundTintList = ColorStateList.valueOf(Color.BLACK)
        }

        viewHolder.let {
            val holder = it as ItemAdapter.ToolViewHolder
            holder.testButton.backgroundTintList = ColorStateList.valueOf(Color.BLACK)
        }

        // 位置をリセット
        fromPosition = RecyclerView.NO_POSITION
        toPosition = RecyclerView.NO_POSITION
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        // スワイプ時の処理（今回は必要なし）
    }

//    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
//        super.onSelectedChanged(viewHolder, actionState)
//        if (actionState == ItemTouchHelper.ACTION_STATE_DRAG && viewHolder != null) {
//            val button = viewHolder.itemView.findViewById<Button>(R.id.template_button)
//            button.setBackgroundColor(Color.YELLOW) // ボタンの色を黄色に変更
//        }
//    }

    override fun isLongPressDragEnabled(): Boolean {
        // 長押しでドラッグを開始するかどうかを指定
        return true
    }
}