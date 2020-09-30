package com.traeen.fant

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.Menu
import android.view.View
import com.google.android.material.floatingactionbutton.FloatingActionButton

import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.traeen.fant.network.HTTPAccess
import com.traeen.fant.network.VolleyHTTP
import com.traeen.fant.ui.item_display.ItemDisplayViewModel

class
Main : AppCompatActivity(), HTTPAccess {

    private lateinit var itemDisplayViewModel: ItemDisplayViewModel

    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        itemDisplayViewModel = ViewModelProvider(this).get(ItemDisplayViewModel::class.java)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        navController = findNavController(R.id.nav_host_fragment)

        setupFloatingActionButton()
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_search, R.id.nav_add_item, R.id.nav_view_item
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {

        val navView: NavigationView = findViewById(R.id.nav_view)
        AuthenticationRepository.getInstance(getHTTPInstace()).loggedInUser
        return super.onCreateView(name, context, attrs)
    }

    private fun setupFloatingActionButton(){
        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.show()
        fab.setOnClickListener { view ->
            val addItemId = R.id.nav_add_item
            if (navController.currentDestination?.id?.equals(addItemId) == false) {
                navController.navigate(R.id.nav_add_item)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun getHTTPInstace(): VolleyHTTP {
        return VolleyHTTP.getInstance(this)
    }

}