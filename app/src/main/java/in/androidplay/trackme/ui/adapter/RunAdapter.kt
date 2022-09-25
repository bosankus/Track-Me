package `in`.androidplay.trackme.ui.adapter

import `in`.androidplay.trackme.R
import `in`.androidplay.trackme.data.room.Run
import `in`.androidplay.trackme.databinding.LayoutRunListItemBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter

/**
 * Created by Androidplay
 * Author: Ankush
 * On: 8/9/2020, 5:09 PM
 */
class RunAdapter : ListAdapter<Run, RunViewHolder>(RunDiffUtil()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RunViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = LayoutRunListItemBinding.inflate(layoutInflater, parent,false)
        return RunViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RunViewHolder, position: Int) {
        val runItem = getItem(position)
        holder.bind(runItem)
    }
}