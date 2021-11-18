package com.bd.jayson.paging

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.bd.jayson.BR

class BaseViewHolder(val binding: ViewDataBinding)
    :RecyclerView.ViewHolder(binding.root) {

    fun bind(obj: Any, callback: Any?, position: Int, selected: Any? = null) {
        binding.setVariable(1, callback)
        binding.setVariable(12, position)
        binding.setVariable(2, obj)
        if(selected != null) {
            binding.setVariable(15, selected)
        }

        binding.executePendingBindings()
    }
}