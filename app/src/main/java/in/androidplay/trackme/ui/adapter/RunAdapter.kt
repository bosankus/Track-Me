package `in`.androidplay.trackme.ui.adapter

import `in`.androidplay.trackme.R
import `in`.androidplay.trackme.data.room.Run
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
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_run_list_item, parent, false)
        return RunViewHolder(view)
    }

    override fun onBindViewHolder(holder: RunViewHolder, position: Int) {
        val runItem = getItem(position)
        holder.bind(runItem)
    }
}