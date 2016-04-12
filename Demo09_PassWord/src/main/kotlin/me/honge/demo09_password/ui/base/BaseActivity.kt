package me.honge.demo09_password.ui.base

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup

import me.honge.demo09_password.R
import me.honge.demo09_password.ui.main.MainActivity

/**
 * Created by hong on 16/2/24.
 */
open class BaseActivity : AppCompatActivity() {
    var toolbar: Toolbar? = null
        private set

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        initToolBar()
    }

    override fun setContentView(view: View) {
        super.setContentView(view)
        initToolBar()
    }

    override fun setContentView(view: View, params: ViewGroup.LayoutParams) {
        super.setContentView(view, params)
        initToolBar()
    }

    private fun initToolBar() {
        toolbar = findViewById(R.id.realToolbar) as Toolbar?
        if (toolbar != null) {
            setSupportActionBar(toolbar)

            supportActionBar!!.setHomeButtonEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
            if (this is MainActivity) {
                getSupportActionBar()!!.setDisplayHomeAsUpEnabled(false)
            } else {
                supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> if (this is MainActivity) {

            } else {
                // finish();
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
