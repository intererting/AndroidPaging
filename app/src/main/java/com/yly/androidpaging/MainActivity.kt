package com.yly.androidpaging

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val myAdapter by lazy {
        MyPagingAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pagingRecyclerView.adapter = myAdapter
        getItemList().observe(this, Observer {
            myAdapter.submitList(it)
        })
    }

    private fun getItemList(): LiveData<PagedList<String>> {
        return MyPagingFactory().toLiveData(20)
    }
}

class MyPagingFactory : DataSource.Factory<Int, String>() {
    override fun create(): DataSource<Int, String> {
        return MyPageingDataSource()
    }
}

class MyPageingDataSource : PageKeyedDataSource<Int, String>() {
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, String>
    ) {
        println("loadInitial  ${params.requestedLoadSize}")
        val firstInitData = arrayListOf<String>()
        for (i in 0 until params.requestedLoadSize) {
            firstInitData.add("firstInitData  $i")
        }
        callback.onResult(firstInitData, null, 1)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, String>) {
        println("loadAfter  happen  ${params.key}")
        val afterData = arrayListOf<String>()
        for (i in 0 until params.requestedLoadSize) {
            afterData.add("afterData  $i")
        }
        callback.onResult(afterData, params.key + 1)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, String>) {
        println("loadBefore  happen")
    }

}

fun <Key, Value> DataSource.Factory<Key, Value>.toLiveData(
    pageSize: Int
): LiveData<PagedList<Value>> {
    return LivePagedListBuilder(this, Config(pageSize)).build()
}

fun Config(
    pageSize: Int,
    //提前多少个Item加载,当提前数量不足一页时，提前加载无效
    prefetchDistance: Int = pageSize - 5,
    enablePlaceholders: Boolean = true,
    initialLoadSizeHint: Int = pageSize * 1,
    maxSize: Int = PagedList.Config.MAX_SIZE_UNBOUNDED
): PagedList.Config {
    return PagedList.Config.Builder()
        .setPageSize(pageSize)
        .setPrefetchDistance(prefetchDistance)
        .setEnablePlaceholders(enablePlaceholders)
        .setInitialLoadSizeHint(initialLoadSizeHint)
        .setMaxSize(maxSize)
        .build()
}
