package com.android.task.budgetmanagement.ui.transaction

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.task.budgetmanagement.R
import com.android.task.budgetmanagement.data.database.entity.TransactionEntity
import com.android.task.budgetmanagement.databinding.RowTransactionBinding
import com.android.task.budgetmanagement.utils.Constants

class TransactionAdapter(
    private val invokeClick: (TransactionEntity) -> Unit,
    private val invokeEditClick: (TransactionEntity) -> Unit
) : RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {
    private lateinit var binding: RowTransactionBinding
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = RowTransactionBinding.inflate(inflater, parent, false)
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
        fun bind(item: TransactionEntity) {
            binding.apply {
                tvDateTitle.text = item.date
                tvTransName.text = item.name

                // Set text color based on transaction type
                val bgColorRes = if (item.isIncome) {
                    R.color.green
                } else {
                    R.color.red
                }
                tvCurrency.setBackgroundColor(context.getColor(bgColorRes))
                val correctCurrencyUnit =
                    if (item.currency.equals(Constants.CURRENCY_DOLLAR, true)) {
                        context.getString(R.string.dollar_unit)
                    } else {
                        context.getString(R.string.dinar_unit)
                    }

                val priceType = if (item.isIncome) {
                    "$correctCurrencyUnit ${item.total}"
                } else {
                    "$correctCurrencyUnit ${item.total}-"
                }
                tvCurrency.text = priceType

                ivEdit.setOnClickListener {
                    invokeEditClick.invoke(item)
                }
                root.setOnClickListener {
                    invokeClick.invoke(item)
                }
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<TransactionEntity>() {
        override fun areItemsTheSame(
            oldItem: TransactionEntity,
            newItem: TransactionEntity
        ): Boolean {
            return oldItem.monthId == newItem.monthId
        }

        override fun areContentsTheSame(
            oldItem: TransactionEntity,
            newItem: TransactionEntity
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
}