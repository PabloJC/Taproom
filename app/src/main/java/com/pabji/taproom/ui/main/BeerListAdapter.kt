package com.pabji.taproom.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.pabji.taproom.R
import com.pabji.taproom.data.uimodel.UIItemBeer
import com.pabji.taproom.databinding.ItemBeerListBinding
import com.pabji.taproom.ui.common.notifyChanges
import kotlin.properties.Delegates

class BeerListAdapter(
    private val onItemClicked: (Long) -> Unit
) : ListAdapter<UIItemBeer, BeerListAdapter.ItemBeerViewHolder>(UIItemBeerDiffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemBeerViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_beer_list, parent, false)
        return ItemBeerViewHolder(view, onItemClicked)
    }

    override fun onBindViewHolder(holder: ItemBeerViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ItemBeerViewHolder(view: View, private val onItemClicked: (Long) -> Unit) :
        RecyclerView.ViewHolder(view) {

        private val binding = ItemBeerListBinding.bind(view)

        fun bind(item: UIItemBeer) {
            item.run {
                binding.tvName.text = name
                binding.tvTagline.text = tabline
                binding.ivImage.load(imageUrl)
                binding.clContainer.setBackgroundColor(backgroundColorRes)
                itemView.setOnClickListener { onItemClicked(id) }
            }
        }
    }
}

object UIItemBeerDiffCallBack : DiffUtil.ItemCallback<UIItemBeer>() {

    override fun areItemsTheSame(
        oldItem: UIItemBeer,
        newItem: UIItemBeer
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: UIItemBeer,
        newItem: UIItemBeer
    ): Boolean = oldItem == newItem
}
