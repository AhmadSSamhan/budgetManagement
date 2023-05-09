package com.android.task.budgetmanagement.ui.month_year

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.task.budgetmanagement.data.database.entity.AccountEntity
import com.android.task.budgetmanagement.databinding.FragmentMonthYearBinding
import com.android.task.budgetmanagement.ui.add_month.AddMonthDialogFragment
import com.android.task.budgetmanagement.ui.update_month.UpdateMonthDialogFragment
import com.android.task.budgetmanagement.utils.Status
import com.android.task.budgetmanagement.utils.hide
import com.android.task.budgetmanagement.utils.hideSoftKeyboard
import com.android.task.budgetmanagement.utils.show
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MonthsFragment : Fragment() {

    private var _binding: FragmentMonthYearBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MonthsViewModel by viewModels()
    private lateinit var mothYearAdapter: MothYearAdapter

    @Inject
    lateinit var accountEntity: AccountEntity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMonthYearBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserver()
        initListener()
    }

    private fun initListener() {
        binding.btnAddMonthYear.setOnClickListener {
            AddMonthDialogFragment.newInstance(parentFragmentManager, accountEntity.id.toInt()) {
                initObserver()
            }
        }
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
            requireActivity().hideSoftKeyboard()
        }
    }

    private fun initView() {
        accountEntity = arguments?.let { MonthsFragmentArgs.fromBundle(it).accountEntity }!!
        requireActivity().hideSoftKeyboard()
        setupRecyclerView()
    }

    private fun initObserver() {
        fetchList(accountEntity.id.toInt())
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            viewModel.monthsList.collect {
                when (it.status) {
                    Status.SUCCESS -> {
                        showView()
                        setupRecyclerView()
                        mothYearAdapter.differ.submitList(it.data)
                        viewModel.updateAccount(accountEntity, it.data)
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
        binding.rvMonthYearList.show()
        binding.tvEmptyText.hide()
        binding.ivArrowDots.hide()
    }

    private fun loadView() {
        binding.rvMonthYearList.hide()
        binding.tvEmptyText.show()
        binding.ivArrowDots.show()
    }

    private fun setupRecyclerView() {
        binding.rvMonthYearList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            mothYearAdapter = MothYearAdapter({
                val directions =
                    MonthsFragmentDirections.actionNavigationMonthsFragmentToNavigationTransaction(
                        it
                    )
                findNavController().navigate(directions)
            },
                {
                    UpdateMonthDialogFragment.newInstance(
                        parentFragmentManager,
                        it,
                        { entity -> fetchList(entity.accountId.toInt()) },
                        { entity -> fetchList(entity.accountId.toInt()) })
                })
            adapter = mothYearAdapter
        }
    }

    private fun fetchList(accountId: Int) {
        viewModel.fetchMonthsByAccountId(accountId)
    }
}