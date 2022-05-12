package com.darkabhi.appinesstask.hierarchy.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.darkabhi.appinesstask.R
import com.darkabhi.appinesstask.databinding.MockableItemBinding
import com.darkabhi.appinesstask.models.MockableResponse
import timber.log.Timber
import java.util.Locale

class MockableAdapter(
    private val onPhone: (MockableResponse.DataObject.Hierarchy.HierarchyObjects) -> Unit,
    private val onSms: (MockableResponse.DataObject.Hierarchy.HierarchyObjects) -> Unit
) : ListAdapter<MockableResponse.DataObject.Hierarchy.HierarchyObjects, MockableAdapter.MockableViewHolder>(
    MockableComparator()
), Filterable {

    private var dataList = mutableListOf<MockableResponse.DataObject.Hierarchy.HierarchyObjects>()

    fun setData(list: MutableList<MockableResponse.DataObject.Hierarchy.HierarchyObjects>) {
        this.dataList = list
        submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MockableViewHolder {
        val binding =
            MockableItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MockableViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MockableViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bind(item) }
    }

    class MockableComparator :
        DiffUtil.ItemCallback<MockableResponse.DataObject.Hierarchy.HierarchyObjects>() {
        override fun areItemsTheSame(
            oldItem: MockableResponse.DataObject.Hierarchy.HierarchyObjects,
            newItem: MockableResponse.DataObject.Hierarchy.HierarchyObjects
        ): Boolean {
            return oldItem.phoneNumber == newItem.phoneNumber
        }

        override fun areContentsTheSame(
            oldItem: MockableResponse.DataObject.Hierarchy.HierarchyObjects,
            newItem: MockableResponse.DataObject.Hierarchy.HierarchyObjects
        ): Boolean {
            return oldItem == newItem
        }
    }

    inner class MockableViewHolder(
        private val binding: MockableItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.phoneButton.setOnClickListener {
                onPhone(currentList[adapterPosition])
            }
            binding.smsButton.setOnClickListener {
                onSms(currentList[adapterPosition])
            }
        }

        fun bind(item: MockableResponse.DataObject.Hierarchy.HierarchyObjects) {
            binding.nameTv.text = binding.root.context.getString(
                R.string.mockable_name,
                item.firstName,
                item.lastName
            )
            binding.postitionTv.text = item.categoryName
        }
    }

    override fun getFilter(): Filter {
        return nameFilter
    }

    private val nameFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList =
                mutableListOf<MockableResponse.DataObject.Hierarchy.HierarchyObjects>()
            Timber.i(filteredList.toString())
            if (constraint == null || constraint.isEmpty()) {
                Timber.i("EMPTY")
                Timber.i(dataList.toString())
                filteredList.addAll(dataList)
            } else {
                for (item in dataList) {
                    if (item.firstName.lowercase(Locale.getDefault())
                            .startsWith(constraint.toString().lowercase(Locale.getDefault()))
                    ) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence?, filterResults: FilterResults?) {
            submitList(filterResults?.values as MutableList<MockableResponse.DataObject.Hierarchy.HierarchyObjects>)
        }

    }

}