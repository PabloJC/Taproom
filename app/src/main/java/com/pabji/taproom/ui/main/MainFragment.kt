package com.pabji.taproom.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.pabji.taproom.R
import com.pabji.taproom.data.uimodel.UIItemBeer
import com.pabji.taproom.databinding.FragmentMainBinding
import com.pabji.taproom.ui.common.EventObserver
import com.pabji.taproom.ui.common.GridSpacingItemDecoration
import com.pabji.taproom.ui.common.base.BaseFragmentViewBinding
import com.pabji.taproom.ui.common.gone
import com.pabji.taproom.ui.common.visible
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.scope.viewModel

class MainFragment : BaseFragmentViewBinding<FragmentMainBinding>() {

    private lateinit var adapter: BeerListAdapter
    private val viewModel: MainViewModel by currentScope.viewModel(this)
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = onCreateViewBinding(FragmentMainBinding.inflate(inflater, container, false))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()
        setRecyclerView()
        binding.progressBar.visible()
        with(viewModel) {
            beerList.observe(viewLifecycleOwner, Observer(::updateList))
            navigation.observe(viewLifecycleOwner, EventObserver { id ->
                val action = MainFragmentDirections.actionMainFragmentToDetailFragment(id)
                navController.navigate(action)
            })
        }
    }

    private fun updateList(list: List<UIItemBeer>?) {
        binding.progressBar.gone()
        adapter.itemList = list ?: emptyList()
    }

    private fun setRecyclerView() {
        binding.recyclerView.let {
            adapter = BeerListAdapter(viewModel::onItemClicked)
            val layoutManager = GridLayoutManager(context, GRID_SPAN_COUNT)
            val dividerItemDecoration = GridSpacingItemDecoration(
                GRID_SPAN_COUNT,
                resources.getDimensionPixelOffset(R.dimen.cardview_item_list_separation),
                true
            )

            it.adapter = adapter
            it.addItemDecoration(dividerItemDecoration)
            it.layoutManager = layoutManager
        }
    }

    companion object {
        const val GRID_SPAN_COUNT = 2
    }
}
