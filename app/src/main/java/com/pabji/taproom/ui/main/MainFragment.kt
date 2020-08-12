package com.pabji.taproom.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.pabji.domain.model.ItemBeer
import com.pabji.taproom.R
import com.pabji.taproom.ui.common.GridSpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.scope.viewModel

class MainFragment : Fragment() {

    private lateinit var adapter: BeerListAdapter
    private val viewModel: MainViewModel by currentScope.viewModel(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        with(viewModel) {
            beerList.observe(viewLifecycleOwner, Observer(::updateList))
            navigation.observe(viewLifecycleOwner, Observer { event ->
                event.getContent()?.let {

                }
            })
        }
    }

    private fun updateList(list: List<ItemBeer>?) {
        Log.d("NUMLIST", "${list?.size ?: 0}")
        adapter.itemList = list ?: emptyList()
    }

    private fun setRecyclerView() {
        recycler_view.let {
            adapter = BeerListAdapter(viewModel::onItemClicked)
            val layoutManager = GridLayoutManager(context, GRID_SPAN_COUNT)
            val dividerItemDecoration = GridSpacingItemDecoration(GRID_SPAN_COUNT, resources.getDimensionPixelOffset(R.dimen.cardview_item_list_separation), true)

            it.adapter = adapter
            it.addItemDecoration(dividerItemDecoration)
            it.layoutManager = layoutManager
        }
    }

    companion object{
        const val GRID_SPAN_COUNT = 2
    }
}
