package `in`.androidplay.trackme.ui.adapter

/**
 * DiffUtil is a utility class that can calculate the difference between
 * two lists and output a list of update operations that converts the first
 * list into the second one.It can be used to calculate updates for a RecyclerView Adapter.
 * This helps to load only the changed items and not the whole list every time
 */

import `in`.androidplay.trackme.room.Run
import androidx.recyclerview.widget.DiffUtil

/**
 * Created by Androidplay
 * Author: Ankush
 * On: 8/9/2020, 10:34 PM
 */
class RunDiffUtil : DiffUtil.ItemCallback<Run>() {
    override fun areItemsTheSame(oldItem: Run, newItem: Run): Boolean {
        return newItem.id == oldItem.id
    }

    override fun areContentsTheSame(oldItem: Run, newItem: Run): Boolean {
        return newItem.hashCode() == oldItem.hashCode()
    }
}