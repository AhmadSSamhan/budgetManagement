package com.android.task.budgetmanagement.ui.update_month

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.android.task.budgetmanagement.data.database.entity.MonthYearEntity
import com.android.task.budgetmanagement.databinding.DialogFragmentUpdateMonthBinding
import com.android.task.budgetmanagement.ui.base.BaseBottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateMonthDialogFragment : BaseBottomSheetDialog() {

    private var _binding: DialogFragmentUpdateMonthBinding? = null
    private val binding get() = _binding!!

    lateinit var monthYearEntity: MonthYearEntity
    private val viewModel: UpdateMonthViewModel by viewModels()

    private lateinit var selectedMonthYear: MonthYearEntity

    private var updateListener: ((MonthYearEntity) -> Unit?)? = null
    private var deleteListener: ((MonthYearEntity) -> Unit?)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogFragmentUpdateMonthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        initView()
    }

    private fun initView() {
        monthYearEntity = MonthYearEntity(
            accountId = selectedMonthYear.accountId,
            monthId = selectedMonthYear.monthId,
            year = selectedMonthYear.year,
            month = selectedMonthYear.month,
            day = selectedMonthYear.day,
            incomeDinar = selectedMonthYear.incomeDinar,
            incomeDollar = selectedMonthYear.incomeDollar,
            spendingDinar = selectedMonthYear.spendingDinar,
            spendingDollar = selectedMonthYear.spendingDollar
        )
        binding.datePicker.init(
            selectedMonthYear.year,
            selectedMonthYear.month.toInt(),
            selectedMonthYear.day
        ) { view, year, month, day ->
            monthYearEntity.day = day
            monthYearEntity.year = year
            monthYearEntity.month = month.plus(1).toString()
        }
    }

    private fun initListener() {
        binding.apply {
            btnDelete.setOnClickListener {
                viewModel.deleteAccount(monthYearEntity)
                deleteListener?.invoke(monthYearEntity)
                dismiss()
            }

            btnSave.setOnClickListener {
                viewModel.updateAccount(monthYearEntity)
                updateListener?.invoke(monthYearEntity)
                dismiss()
            }
            toolbar.setNavigationOnClickListener {
                dismiss()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "UpdateMonthDialogFragment"

        fun newInstance(
            supportFragmentManager: FragmentManager,
            item: MonthYearEntity,
            fetchUpdate: (MonthYearEntity) -> Unit,
            fetchDelete: (MonthYearEntity) -> Unit,
        ) = UpdateMonthDialogFragment().apply {
            selectedMonthYear = item
            updateListener = fetchUpdate
            deleteListener = fetchDelete
        }.show(supportFragmentManager, TAG)
    }
}