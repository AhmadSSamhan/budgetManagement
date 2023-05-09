package com.android.task.budgetmanagement.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.task.budgetmanagement.data.database.entity.AccountEntity
import com.android.task.budgetmanagement.databinding.FragmentHomeBinding
import com.android.task.budgetmanagement.ui.create_account.AddAccountDialogFragment
import com.android.task.budgetmanagement.ui.update_account.UpdateAccountDialogFragment
import com.android.task.budgetmanagement.utils.hide
import com.android.task.budgetmanagement.utils.hideSoftKeyboard
import com.android.task.budgetmanagement.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var accountAdapter: AccountAdapter

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
        initListener()
        //setupObserverByApiService()
    }

    private fun initObserver() {
        viewModel.allAccounts.observe(viewLifecycleOwner) {
            binding.apply {
                if (it.isNotEmpty()) {
                    rvAccountList.show()
                    tvEmptyText.hide()
                    ivArrowDots.hide()
                    setupRecyclerView(it)
                } else {
                    rvAccountList.hide()
                    tvEmptyText.show()
                    ivArrowDots.show()
                }
            }
        }
    }

    private fun initListener() {
        requireActivity().hideSoftKeyboard()
        binding.btnAddAccount.setOnClickListener {
            AddAccountDialogFragment.newInstance(parentFragmentManager, true) {
                initObserver()
            }
        }
    }

    private fun setupRecyclerView(accountEntities: List<AccountEntity>) {
        binding.rvAccountList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            accountAdapter = AccountAdapter({
                goToMonthsPage(it)
            }, {
                UpdateAccountDialogFragment.newInstance(parentFragmentManager, it.id)
            })
            adapter = accountAdapter
            accountAdapter.differ.submitList(accountEntities)
        }
    }

    private fun goToMonthsPage(accountEntity: AccountEntity) {
        val directions =
            HomeFragmentDirections.actionNavigationHomeToNavigationMonthsFragment(accountEntity)
        findNavController().navigate(directions)
    }

    /**
     * The below fun if you would like to handle read data from server by ApiServices.
     */
/*
 private fun setupObserverByApiService() {
        viewModel.users.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    it.data?.let { users -> renderList(users) }
                    recyclerView.visibility = View.VISIBLE
                    Toast.makeText(requireContext(), "Data Success", Toast.LENGTH_LONG).show()
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }
                Status.ERROR -> {
                    //Handle Error
                    progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
       private fun renderList(users: List<User>) {
        adapter.addData(users)
        adapter.notifyDataSetChanged()
    }*/

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}