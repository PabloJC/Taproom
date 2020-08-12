package com.pabji.taproom.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.pabji.domain.model.ItemBeer
import com.pabji.taproom.R
import com.pabji.taproom.ui.common.notifyChanges
import kotlinx.android.synthetic.main.item_beer_list.view.*
import kotlin.properties.Delegates

class BeerListAdapter(
    private val onItemClicked: (ItemBeer) -> Unit
) :
    RecyclerView.Adapter<BeerListAdapter.ItemBeerViewHolder>() {

    var itemList: List<ItemBeer> by Delegates.observable(emptyList()) { _, oldList, newList ->
        notifyChanges(oldList, newList) { o, n -> o.name == n.name }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemBeerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_beer_list, parent, false)
        return ItemBeerViewHolder(view, onItemClicked)
    }

    override fun getItemCount() = itemList.size

    override fun onBindViewHolder(holder: ItemBeerViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    class ItemBeerViewHolder(view: View, private val onItemClicked: (ItemBeer) -> Unit) : RecyclerView.ViewHolder(view) {

        fun bind(item: ItemBeer) {
            itemView.run {
                tv_name.text = item.name
                tv_tagline.text = item.tabline
                iv_image.load(item.imageUrl) {
                    crossfade(true)
                    error(R.mipmap.ic_launcher)
                }
                ll_content.setBackgroundColor(
                    ContextCompat.getColor(context, if (item.isAvailable) R.color.white else R.color.gray)
                )
                setOnClickListener { onItemClicked(item) }
            }
        }
    }
}
