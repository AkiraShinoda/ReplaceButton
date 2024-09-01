package com.example.replacementbutton

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import java.util.Collections

interface ItemTouchHelperAdapter {
    fun onItemMove(fromPosition: Int, toPosition: Int)
}

class ItemAdapter(private val itemList: MutableList<ListItem>)
    : RecyclerView.Adapter<ItemAdapter.ToolViewHolder>()
    , ItemTouchHelperAdapter {
    lateinit var defaultBackground: Drawable
    lateinit var activeBackground: Drawable

    fun setDefaultButtonBackground(background: Drawable) {
        defaultBackground = background
    }

    fun setActiveButtonBackground(background: Drawable) {
        activeBackground = background
    }

    class ToolViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val testButton: Button = itemView.findViewById(R.id.template_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToolViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ToolViewHolder(view)
    }

    override fun onBindViewHolder(holder: ToolViewHolder, position: Int) {
        val item = itemList[position]
        holder.testButton.apply {
            text = item.name

            setOnLongClickListener {
                backgroundTintList = ColorStateList.valueOf(Color.GRAY)
                true
            }
        }
    }

    override fun getItemCount() = itemList.size

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        // 項目の位置を入れ替える処理
        Log.d("ReplaceButton", "before itemList=[$itemList]")
        Collections.swap(itemList, fromPosition, toPosition)
        notifyDataSetChanged()
        Log.d("ReplaceButton", "after itemList=[$itemList]")
    }

}