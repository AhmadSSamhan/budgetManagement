package com.android.task.budgetmanagement.ui.transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.android.task.budgetmanagement.R
import com.android.task.budgetmanagement.data.database.entity.MonthYearEntity
import com.android.task.budgetmanagement.data.database.entity.TransactionEntity
import com.android.task.budgetmanagement.databinding.DialogFragmentAddTransactionBinding
import com.android.task.budgetmanagement.ui.base.BaseBottomSheetDialog
import com.android.task.budgetmanagement.utils.Constants.CURRENCY_DINAR
import com.android.task.budgetmanagement.utils.Constants.CURRENCY_DOLLAR
import com.android.task.budgetmanagement.utils.hideSoftKeyboard
import com.android.task.budgetmanagement.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateTransactionDialogFragment : BaseBottomSheetDialog() {

    private var _binding: DialogFragmentAddTransactionBinding? = null
    private val binding get() = _binding!!


    private var mListener: ((TransactionEntity) -> Unit?)? = null
    private val viewModel: TransactionViewModel by viewModels()

    private lateinit var transactionEntity: TransactionEntity
    private lateinit var mainMonthEntity: MonthYearEntity
    private lateinit var transactionEntityFromDeleteOption: TransactionEntity
    private var isTransactionDataNeedToUpdate = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogFragmentAddTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        initView()
    }

    private fun initView() {
        if (isTransactionDataNeedToUpdate) {
            transactionEntity = TransactionEntity(
                monthId = transactionEntityFromDeleteOption.monthId,
                month = transactionEntityFromDeleteOption.month,
                year = transactionEntityFromDeleteOption.year,
                date = transactionEntityFromDeleteOption.date,
                name = transactionEntityFromDeleteOption.name,
                total = transactionEntityFromDeleteOption.total,
                isIncome = transactionEntityFromDeleteOption.isIncome,
                currency = transactionEntityFromDeleteOption.currency
            )
            if (transactionEntityFromDeleteOption.isIncome) {
                binding.rbIncome.isChecked = true
            } else {
                binding.rbOutcome.isChecked = true
            }
            if (transactionEntityFromDeleteOption.currency.equals(CURRENCY_DOLLAR, true)) {
                binding.rbDollar.isChecked = true
            } else {
                binding.rbDinar.isChecked = true
            }
            binding.edtTitle.setText(transactionEntityFromDeleteOption.name)
            binding.edtTotal.setText(transactionEntityFromDeleteOption.total.toString())
            return
        }
        val fullDate = "${mainMonthEntity.day}/${mainMonthEntity.month}/${mainMonthEntity.year}"
        transactionEntity = TransactionEntity(
            monthId = mainMonthEntity.monthId,
            month = mainMonthEntity.month,
            year = mainMonthEntity.year.toString(),
            date = fullDate,
            name = binding.edtTitle.text.toString(),
            total = 0.0,
            isIncome = true,
            currency = CURRENCY_DOLLAR
        )
    }

    private fun initListener() {
        binding.apply {
            btnSave.setOnClickListener {
                validateTransactionData()
            }
            rbDollar.setOnClickListener {
                requireActivity().hideSoftKeyboard()
                transactionEntity.currency = CURRENCY_DOLLAR
            }
            rbDinar.setOnClickListener {
                requireActivity().hideSoftKeyboard()
                transactionEntity.currency = CURRENCY_DINAR
            }
            rbIncome.setOnClickListener {
                requireActivity().hideSoftKeyboard()
                transactionEntity.isIncome = true
            }
            rbOutcome.setOnClickListener {
                requireActivity().hideSoftKeyboard()
                transactionEntity.isIncome = false
            }
            toolbar.setNavigationOnClickListener {
                dismiss()
            }
            binding.edtTitle.doAfterTextChanged {
                transactionEntity.name = it.toString()
            }
            binding.edtTotal.doAfterTextChanged {
                val inputText = it?.toString()
                if (inputText == "0") {
                    // Clear the EditText if the input is "0"
                    binding.edtTotal.text?.clear()
                }
            }

        }
    }

    private fun validateTransactionData() {
        if (binding.edtTitle.text?.isBlank() == true) {
            requireContext().toast(getString(R.string.missing_add_desc))
            return
        }
        if (binding.edtTotal.text?.isBlank() == true) {
            requireContext().toast(getString(R.string.missing_add_amount))
            return
        }
        if (!binding.rbDinar.isChecked && !binding.rbDollar.isChecked) {
            requireContext().toast(getString(R.string.missing_add_amount_currency))
            return
        }
        if (!binding.rbIncome.isChecked && !binding.rbOutcome.isChecked) {
            requireContext().toast(getString(R.string.missing_add_spend_type))
            return
        }
        transactionEntity.total = binding.edtTotal.text.toString().toDouble()
        if (isTransactionDataNeedToUpdate) {
            viewModel.updateTransaction(transactionEntity)
        } else {
            viewModel.insertTransaction(transactionEntity)
        }
        dismiss()
        mListener?.invoke(transactionEntity)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "AddTransactionDialogFragment"

        fun newInstance(
            supportFragmentManager: FragmentManager,
            monthEntity: MonthYearEntity,
            transactionEntity: TransactionEntity? = null,
            isNeedToUpdate: Boolean = false,
            invokeClick: (TransactionEntity) -> Unit,
        ) = UpdateTransactionDialogFragment().apply {
            mainMonthEntity = monthEntity
            isTransactionDataNeedToUpdate = isNeedToUpdate
            transactionEntity?.let { transactionEntityFromDeleteOption = it }
            mListener = invokeClick
        }.show(supportFragmentManager, TAG)
    }
}