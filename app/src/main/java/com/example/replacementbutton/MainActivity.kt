package com.example.replacementbutton

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val itemAdapter = ItemAdapter(mutableListOf(
            ListItem("Hammer"),
            ListItem("Screwdriver"),
            ListItem("Wrench"),
            ListItem("Pliers"),
            ListItem("Saw"))
        ).apply {
            setDefaultButtonBackground(AppCompatResources.getDrawable(this@MainActivity, R.drawable.background_button)!!)
            setActiveButtonBackground(AppCompatResources.getDrawable(this@MainActivity, R.drawable.background_button_active)!!)
        }
        val recyclerView = findViewById<RecyclerView>(R.id.item_list).apply {
            layoutManager = object : GridLayoutManager(this@MainActivity, 2, GridLayoutManager.HORIZONTAL, false) {
                override fun canScrollVertically(): Boolean {
                    return false // 垂直スクロールを無効にする
                }

                override fun canScrollHorizontally(): Boolean {
                    return false // 水平スクロールを無効にする
                }
            }
            adapter = itemAdapter
        }


        val callback = ItemMoveCallback(itemAdapter)
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}