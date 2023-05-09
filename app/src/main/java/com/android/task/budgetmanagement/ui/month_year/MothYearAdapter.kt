package com.android.task.budgetmanagement.ui.month_year

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.task.budgetmanagement.R
import com.android.task.budgetmanagement.data.database.entity.MonthYearEntity
import com.android.task.budgetmanagement.databinding.RowMonthYearBinding

class MothYearAdapter(
    private val invokeClick: (MonthYearEntity) -> Unit,
    private val invokeEditClick: (MonthYearEntity) -> Unit
) : RecyclerView.Adapter<MothYearAdapter.ViewHolder>() {
    private lateinit var binding: RowMonthYearBinding
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = RowMonthYearBinding.inflate(inflater, parent, false)
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
        fun bind(item: MonthYearEntity) {
            binding.apply {
                val txtIncomeDollar =
                    "${context.getString(R.string.dollar_currency)} ${item.incomeDollar}"
                val txtIncomeDinar =
                    "${context.getString(R.string.dinar_currency)} ${item.incomeDinar}"

                val txtSpendingDollar =
                    "${context.getString(R.string.dollar_currency)} ${item.spendingDollar}"
                val txtSpendingDinar =
                    "${context.getString(R.string.dinar_currency)} ${item.spendingDinar}"
                val date = "${item.month}-${item.year}"
                tvTitle.text = date
                tvDollarIncome.text = txtIncomeDollar
                tvDinarIncome.text = txtIncomeDinar
                tvDollarSpending.text = txtSpendingDollar
                tvDinarSpending.text = txtSpendingDinar
                ivEdit.setOnClickListener {
                    invokeEditClick.invoke(item)
                }
                root.setOnClickListener {
                    invokeClick.invoke(item)
                }
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<MonthYearEntity>() {
        override fun areItemsTheSame(oldItem: MonthYearEntity, newItem: MonthYearEntity): Boolean {
            return oldItem.monthId == newItem.monthId
        }

        override fun areContentsTheSame(
            oldItem: MonthYearEntity,
            newItem: MonthYearEntity
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

}