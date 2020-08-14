package com.pabji.taproom.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import coil.api.load
import com.pabji.domain.model.Beer
import com.pabji.taproom.R
import com.pabji.taproom.ui.common.setVisible
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_main.toolbar
import kotlinx.android.synthetic.main.view_detail_description_container.*
import kotlinx.android.synthetic.main.view_detail_toolbar_container.*
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.scope.viewModel
import org.koin.core.parameter.parametersOf


class DetailFragment : Fragment() {

    private val args: DetailFragmentArgs by navArgs()
    private val viewModel: DetailViewModel by currentScope.viewModel(this) {
        parametersOf(args.itemId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.slide_left)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbar()
        viewModel.model.observe(viewLifecycleOwner, Observer(::updateBeer))
        bt_empty_beer.setOnClickListener {
            viewModel.onEmptyBarrelButtonClick()
        }
    }

    private fun setToolbar() {
        (activity as? AppCompatActivity)?.let {
            it.setSupportActionBar(toolbar)
            it.supportActionBar?.setDisplayHomeAsUpEnabled(true)
            it.supportActionBar?.setHomeButtonEnabled(true)
            setTitle("")
        }
    }

    private fun setTitle(title: String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = title
    }

    private fun updateBeer(beer: Beer?) {
        beer?.run {
            setTitle(name)
            iv_image.load(imageUrl) {
                crossfade(true)
                error(R.mipmap.ic_launcher)
            }
            tv_description.text = description
            tv_food_pairing.text = foodPairing.joinToString("") { "$it\n" }
            bb_abv.quantity = abv.toString()
            bb_ibu.quantity = ibu.toString()
            tv_not_available.setVisible(isBarrelEmpty)
            bt_empty_beer.text = if (isBarrelEmpty) {
                getString(R.string.no_empty_barrel)
            } else {
                getString(R.string.empty_barrel)
            }
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
}