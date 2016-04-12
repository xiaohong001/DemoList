package me.honge.demo09_password.ui.main

import android.content.Intent
import android.content.res.Configuration
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.NavigationView
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout

import me.honge.demo09_password.R
import me.honge.demo09_password.ui.base.BaseActivity

class MainActivity : BaseActivity() {

    private var mDrawerToggle: ActionBarDrawerToggle? = null
    private var nvMenu: NavigationView? = null
    private var fab: FloatingActionButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mDrawerLayout = findViewById(R.id.drawer) as DrawerLayout?
        mDrawerToggle = ActionBarDrawerToggle(this,
                mDrawerLayout, toolbar, 0, 0)
        mDrawerToggle!!.syncState()
        mDrawerLayout!!.setDrawerListener(mDrawerToggle)

        nvMenu = findViewById(R.id.nvMenu) as NavigationView?
        nvMenu!!.setNavigationItemSelectedListener { item ->
            item.isChecked = true
            mDrawerLayout.closeDrawers()
            true
        }

        //        flContent = (FrameLayout) findViewById(R.id.flContent);
        val passwordFragment = PasswordFragment()
        supportFragmentManager.beginTransaction().replace(R.id.flContent, passwordFragment).commit()

        fab = findViewById(R.id.fab) as FloatingActionButton?
        fab!!.setOnClickListener { startActivity(Intent(this@MainActivity, AddActivity::class.java)) }
        Log.e(TAG,"Kotlin");
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return mDrawerToggle!!.onOptionsItemSelected(item) || super.onOptionsItemSelected(item)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        mDrawerToggle!!.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        mDrawerToggle!!.onConfigurationChanged(newConfig)
    }

    companion object {

        private val TAG = MainActivity::class.java.simpleName
    }

}
