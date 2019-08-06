package com.ivelinstanchev.flickrimagesapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.Window
import android.widget.SearchView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = null
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val searchView: SearchView? = menu?.findItem(R.id.menuItemMainSearch)?.actionView as? SearchView
        searchView?.maxWidth = Int.MAX_VALUE // making search view full width
        return super.onCreateOptionsMenu(menu)
    }
}
