package com.traeen.fant

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.traeen.fant.network.HTTPAccess
import com.traeen.fant.network.VolleyHTTP

class
Main : AppCompatActivity(), HTTPAccess, NavigationView.OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var navController: NavController

    private lateinit var appViewModel: ApplicationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        navController = findNavController(R.id.nav_host_fragment)


        appViewModel = ViewModelProvider(this, ApplicationViewModelFactory(application)).get(
            ApplicationViewModel::class.java
        )

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

        setupUserStateLister()
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setNavigationItemSelectedListener(this)
        navView.setupWithNavController(navController)

    }

    fun logout(item: MenuItem) {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.close()
        appViewModel.logoutUser()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_sign_out -> {
                appViewModel.logoutUser()
            }
        }
        return true
    }

    private fun setupUserStateLister() {
        appViewModel.userState.observe(this, Observer {
            val navView: NavigationView = findViewById(R.id.nav_view)
            navView.menu.clear()
            if (it) {
                val user = appViewModel.currentUser
                navView.inflateMenu(R.menu.loggedin_drawer_menu)
            } else {
                navView.inflateMenu(R.menu.not_loggedin_drawer_menu)
            }
        })
    }

    private fun setupFloatingActionButton() {
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