package com.android.task.budgetmanagement.ui.add_month

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.android.task.budgetmanagement.data.database.entity.MonthYearEntity
import com.android.task.budgetmanagement.databinding.DialogFragmentDatePickerBinding
import com.android.task.budgetmanagement.ui.base.BaseBottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class AddMonthDialogFragment : BaseBottomSheetDialog() {

    private var _binding: DialogFragmentDatePickerBinding? = null
    private val binding get() = _binding!!

    lateinit var monthYearEntity: MonthYearEntity

    private val viewModel: AddMonthViewModel by viewModels()

    private val today = Calendar.getInstance()
    private val defaultMonth = today.get(Calendar.MONTH).plus(1)
    private var defaultYear = today.get(Calendar.YEAR)
    private var defaultDay = today.get(Calendar.DAY_OF_MONTH)

    private var accountId: Int = 0
    private var mListener: ((MonthYearEntity) -> Unit?)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogFragmentDatePickerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        initView()
    }

    private fun initView() {
        monthYearEntity = MonthYearEntity(accountId = accountId.toLong())
        monthYearEntity.year = defaultYear
        monthYearEntity.month = defaultMonth.toString()
        monthYearEntity.day = defaultDay
        binding.datePicker.init(
            today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)
        ) { view, year, month, day ->
            monthYearEntity.day = day
            monthYearEntity.year = year
            monthYearEntity.month = month.plus(1).toString()
        }
    }

    private fun initListener() {
        binding.apply {
            btnSaveDate.setOnClickListener {
                viewModel.insertMonthYear(monthYearEntity)
                mListener?.invoke(monthYearEntity)
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
        const val TAG = "AddMonthDialogFragment"

        fun newInstance(
            supportFragmentManager: FragmentManager,
            accId: Int,
            invokeClick: (MonthYearEntity) -> Unit
        ) = AddMonthDialogFragment().apply {
            accountId = accId
            mListener = invokeClick

        }.show(supportFragmentManager, TAG)
    }
}