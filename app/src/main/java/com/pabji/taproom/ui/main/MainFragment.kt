package com.pabji.taproom.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.pabji.taproom.R
import com.pabji.taproom.data.uimodel.UIItemBeer
import com.pabji.taproom.databinding.FragmentMainBinding
import com.pabji.taproom.ui.common.GridSpacingItemDecoration
import com.pabji.taproom.ui.common.base.BaseFragmentViewBinding
import com.pabji.taproom.ui.common.gone
import com.pabji.taproom.ui.common.visible
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
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
        setToolbar(binding.toolbar)
        binding.progressBar.visible()
        with(viewModel) {
            beerList.executeOnLifeCycle(::updateList)
            navigation.executeOnLifeCycle {
                val action = MainFragmentDirections.actionMainFragmentToDetailFragment(it)
                navController.navigate(action)
            }
        }
    }

    private fun updateList(list: List<UIItemBeer>) {
        binding.progressBar.gone()
        adapter.submitList(list)
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

    private fun <T> Flow<T>.executeOnLifeCycle(handleResult: (T) -> Unit){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                collect{ handleResult(it) }
            }
        }
    }

    companion object {
        const val GRID_SPAN_COUNT = 2
    }
}
