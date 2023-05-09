package com.android.task.budgetmanagement.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.task.budgetmanagement.R
import com.android.task.budgetmanagement.data.database.entity.AccountEntity
import com.android.task.budgetmanagement.databinding.RowItemAccountBinding


class AccountAdapter(
    private val invokeClick: (AccountEntity) -> Unit,
    private val invokeEditClick: (AccountEntity) -> Unit
) : RecyclerView.Adapter<AccountAdapter.ViewHolder>() {
    private lateinit var binding: RowItemAccountBinding
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = RowItemAccountBinding.inflate(inflater, parent, false)
        context = parent.context
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {
        //InitView
        fun bind(item: AccountEntity) {
            binding.apply {
                tvTitle.text = item.accountName
                val txtDollar = "${context.getString(R.string.dollar_currency)} ${item.dollarTotal}"
                val txtDinar = "${context.getString(R.string.dinar_currency)} ${item.dinarTotal}"
                tvDollar.text = txtDollar
                tvDinar.text = txtDinar
                ivEdit.setOnClickListener {
                    invokeEditClick.invoke(item)
                }
                root.setOnClickListener {
                    invokeClick.invoke(item)
                }
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<AccountEntity>() {
        override fun areItemsTheSame(oldItem: AccountEntity, newItem: AccountEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AccountEntity, newItem: AccountEntity): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

}