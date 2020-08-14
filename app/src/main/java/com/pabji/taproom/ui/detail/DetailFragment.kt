package com.pabji.taproom.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import coil.api.load
import com.pabji.taproom.data.uimodel.UIBeerDetail
import com.pabji.taproom.databinding.FragmentDetailBinding
import com.pabji.taproom.ui.common.base.BaseFragmentViewBinding
import com.pabji.taproom.ui.common.setVisible
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.scope.viewModel
import org.koin.core.parameter.parametersOf

class DetailFragment : BaseFragmentViewBinding<FragmentDetailBinding>() {

    private val args: DetailFragmentArgs by navArgs()
    private val viewModel: DetailViewModel by currentScope.viewModel(this) {
        parametersOf(args.itemId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = onCreateViewBinding(FragmentDetailBinding.inflate(inflater, container, false))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbarWithBack(binding.toolbar)
        viewModel.model.observe(viewLifecycleOwner, Observer(::updateBeer))
        binding.btEmptyBeer.setOnClickListener {
            viewModel.onEmptyBarrelButtonClick()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                activity?.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateBeer(beer: UIBeerDetail?) {
        beer?.run {
            setTitle(name)
            binding.toolbarContainer.let {
                it.ivImage.load(imageUrl)
                it.tvNotAvailable.setVisible(isNotAvailable)
            }
            binding.descriptionContainer.let {
                it.tvDescription.text = description
                it.tvFoodPairing.text = foodPairing
                it.bbAbv.quantity = abv
                it.bbIbu.quantity = ibu
            }
            binding.btEmptyBeer.text = getString(buttonTextRes)
        }
    }
}