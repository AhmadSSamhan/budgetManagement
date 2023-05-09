package com.android.task.budgetmanagement.ui.transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.task.budgetmanagement.data.database.entity.MonthYearEntity
import com.android.task.budgetmanagement.data.database.entity.TransactionEntity
import com.android.task.budgetmanagement.databinding.FragmentTransactionBinding
import com.android.task.budgetmanagement.utils.Status
import com.android.task.budgetmanagement.utils.hide
import com.android.task.budgetmanagement.utils.hideSoftKeyboard
import com.android.task.budgetmanagement.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionFragment : Fragment() {

    private lateinit var monthEntity: MonthYearEntity
    private var _binding: FragmentTransactionBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TransactionViewModel by viewModels()
    private lateinit var transactionAdapter: TransactionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserver()
        initListener()
    }

    private fun initListener() {
        binding.btnAddTransaction.setOnClickListener {
            displayUpdateTransactionDialog()
        }
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
            requireActivity().hideSoftKeyboard()
        }
    }

    private fun initView() {
        requireActivity().hideSoftKeyboard()
        setupRecyclerView()
        monthEntity = arguments?.let { TransactionFragmentArgs.fromBundle(it).monthEntity }!!

    }

    private fun initObserver() {
        fetchList(monthEntity.monthId.toString())
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            viewModel.transactionsList.collect {
                when (it.status) {
                    Status.SUCCESS -> {
                        showView()
                        setupRecyclerView()
                        transactionAdapter.differ.submitList(it.data)
                        updateMonthTable(it.data)
                    }
                    Status.LOADING -> {
                        loadView()
                    }
                    Status.ERROR -> {
                        loadView()
                    }
                }
            }
        }
    }

    private fun showView() {
        binding.rvTransaction.show()
        binding.tvEmptyText.hide()
        binding.ivArrowDots.hide()
    }

    private fun loadView() {
        binding.rvTransaction.hide()
        binding.tvEmptyText.show()
        binding.ivArrowDots.show()
    }

    private fun setupRecyclerView() {
        binding.rvTransaction.apply {
            layoutManager = LinearLayoutManager(requireContext())
            transactionAdapter = TransactionAdapter({
                displayUpdateTransactionDialog(it, true)
            },
                { deleteTransaction(it) })
            adapter = transactionAdapter
        }
    }

    private fun deleteTransaction(transactionEntity: TransactionEntity) {
        viewModel.deleteTransaction(transactionEntity)
        fetchList(monthEntity.monthId.toString())
    }

    private fun displayUpdateTransactionDialog(
        transactionEntity: TransactionEntity? = null,
        isNeedToUpdate: Boolean = false,
    ) {
        UpdateTransactionDialogFragment.newInstance(
            parentFragmentManager,
            monthEntity,
            transactionEntity,
            isNeedToUpdate
        ) {
            fetchList(it.monthId.toString())
        }
    }

    private fun fetchList(monthId: String) {
        viewModel.loadTransactionsByMonthYearId(monthId)
    }

    private fun updateMonthTable(data: List<TransactionEntity>?) {
        viewModel.handleUpdateMonthTableByTransactionList(data, monthEntity)
    }

}