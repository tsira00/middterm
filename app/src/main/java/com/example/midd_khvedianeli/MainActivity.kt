package com.example.midd_khvedianeli

import Artist
import ArtistAdapter
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ArtistAdapter

    private var currentPage = 1
    private var isLoading = false
    private val pageSize = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerViewArtists)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = ArtistAdapter(mutableListOf(), onDelete = { position ->
            adapter.deleteItem(position)
            Toast.makeText(this, "Deleted item at $position", Toast.LENGTH_SHORT).show()
        })

        recyclerView.adapter = adapter

        // Infinite Scrolling
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(rv: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(rv, dx, dy)
                val layoutManager = rv.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisible = layoutManager.findLastVisibleItemPosition()

                if (!isLoading && totalItemCount <= lastVisible + 3) {
                    loadMoreItems()
                }
            }
        })

        // Swipe to delete
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false // არ გადაადგილდება
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                adapter.deleteItem(position)
                Toast.makeText(this@MainActivity, "Deleted item at $position", Toast.LENGTH_SHORT).show()
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        loadMoreItems()
    }

    private fun loadMoreItems() {
        isLoading = true

        // იმიტირებული დატვირთვა (შეგიძლია Retrofit-ით ჩაანაცვლო)
        recyclerView.postDelayed({
            val newArtists = generateFakeArtists(page = currentPage, size = pageSize)
            adapter.addItems(newArtists)
            currentPage++
            isLoading = false
        }, 1500)
    }

    private fun generateFakeArtists(page: Int, size: Int): List<Artist<Any?>> {
        val list = mutableListOf<Artist<Any?>>()
        val startId = (page - 1) * size + 1
        for (i in startId until startId + size) {
            list.add(
                Artist(
                    id = i,
                    name = "Artist $i",
                    period = "1800 - 190$i",
                    description = "This is detailed info about artist $i. Famous for their unique style.",
                    imageUrl = "https://picsum.photos/seed/$i/200/200"
                )
            )
        }
        return list
    }
}
