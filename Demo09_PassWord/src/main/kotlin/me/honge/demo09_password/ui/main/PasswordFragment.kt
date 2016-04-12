package me.honge.demo09_password.ui.main


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.aspsine.swipetoloadlayout.OnLoadMoreListener
import com.aspsine.swipetoloadlayout.OnRefreshListener
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout

import me.honge.demo09_password.R
import me.honge.demo09_password.adapter.BaseRecyclerAdapter
import me.honge.demo09_password.adapter.RecyclerViewHolder
import me.honge.demo09_password.data.DataManager
import me.honge.demo09_password.data.config.Constans
import me.honge.demo09_password.data.model.Password
import me.honge.demo09_password.util.DividerItemDecoration
import rx.Subscriber

class PasswordFragment : Fragment(), OnRefreshListener, OnLoadMoreListener {
    private var rvPassword: RecyclerView? = null
    private var srlLayout: SwipeToLoadLayout? = null
    private var adapter: BaseRecyclerAdapter<Password>? = null
    private var pageIndex = 1
    private val pageCount = 5

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_password, container, false)
        init(view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        srlLayout!!.post { srlLayout!!.isRefreshing = true }
    }

    private fun init(view: View) {
        srlLayout = view.findViewById(R.id.swipeToLoadLayout) as SwipeToLoadLayout
        srlLayout!!.setOnRefreshListener(this)
        srlLayout!!.setOnLoadMoreListener(this)
        rvPassword = view.findViewById(R.id.swipe_target) as RecyclerView
        val manager = LinearLayoutManager(context)
        manager.orientation = LinearLayoutManager.VERTICAL
        rvPassword!!.layoutManager = manager
        rvPassword!!.setHasFixedSize(true)
        //        rvPassword.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.drawable_divider)));
        rvPassword!!.itemAnimator = DefaultItemAnimator()
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            rvPassword!!.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        }
        rvPassword!!.setOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (!ViewCompat.canScrollVertically(recyclerView, 1)) {
                        srlLayout!!.isLoadingMore = true
                    }
                }
            }
        })

        adapter = object : BaseRecyclerAdapter<Password>(context, null) {
            override fun getItemLayoutId(viewType: Int): Int {
                return R.layout.layout_password_item
            }

            override fun bindData(holder: RecyclerViewHolder, position: Int, item: Password) {
                holder.setText(R.id.tvAccountPw, item.password).setText(R.id.tvAccountName, item.mName).setText(R.id.tvRemark, item.reMark)

                val rmView = holder.getView(R.id.rlDelete)
                rmView.tag = item
                rmView.setOnClickListener(rmListener)
            }
        }
        rvPassword!!.adapter = adapter


    }

    private val rmListener = View.OnClickListener { v ->
        val password = v.tag as Password
        if (password != null) {
            val i = DataManager.getInstance().deletePassword(password.mId)
            if (Constans.DEBUG) {
                Log.e(TAG, " " + i)
            }
            Toast.makeText(context, "已经删除" + i + "条记录", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPause() {
        super.onPause()
        if (srlLayout!!.isRefreshing) {
            srlLayout!!.isRefreshing = false
        }
        if (srlLayout!!.isLoadingMore) {
            srlLayout!!.isLoadingMore = false
        }
    }

    override fun onRefresh() {
        pageIndex = 1
        getData(pageIndex, pageCount, true)
    }

    override fun onLoadMore() {
        pageIndex++
        getData(pageIndex, pageCount, false)
    }

    private fun getData(pageIndex: Int, pageCount: Int, isRefresh: Boolean) {
        DataManager.getInstance().getPasswordList(pageIndex, pageCount).subscribe(object : Subscriber<List<Password>>() {
            override fun onCompleted() {
                if (Constans.DEBUG) {
                    Log.e(TAG, "onCompleted")
                }
            }

            override fun onError(e: Throwable) {
                if (isRefresh) {
                    srlLayout!!.isRefreshing = false
                } else {
                    srlLayout!!.isLoadingMore = false
                }
            }

            override fun onNext(passwords: List<Password>) {
                if (isRefresh) {
                    adapter!!.setData(passwords, true)
                    srlLayout!!.isRefreshing = false
                } else {
                    adapter!!.setData(passwords, false)
                    srlLayout!!.isLoadingMore = false
                }
                if (passwords.size < pageCount) {
                    srlLayout!!.isLoadMoreEnabled = false
                } else {
                    srlLayout!!.isLoadMoreEnabled = true
                }
            }
        })
    }

    companion object {
        private val TAG = PasswordFragment::class.java.simpleName
    }
}
