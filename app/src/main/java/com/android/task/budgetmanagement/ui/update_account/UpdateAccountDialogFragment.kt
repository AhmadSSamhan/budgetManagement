package com.android.task.budgetmanagement.ui.update_account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.android.task.budgetmanagement.R
import com.android.task.budgetmanagement.data.database.entity.AccountEntity
import com.android.task.budgetmanagement.databinding.DialogFragmentUpdateAccountBinding
import com.android.task.budgetmanagement.ui.base.BaseBottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UpdateAccountDialogFragment : BaseBottomSheetDialog() {

    private var _binding: DialogFragmentUpdateAccountBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var accountEntity: AccountEntity

    private val viewModel: UpdateAccountViewModel by viewModels()

    private var accountId: Long = 0
    private var defaultTitle = ""

    private var updateListener: ((AccountEntity) -> Unit?)? = null
    private var deleteListener: ((AccountEntity) -> Unit?)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogFragmentUpdateAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    private fun initListener() {
        binding.apply {
            defaultTitle = viewModel.getAccountId(accountId.toInt()).accountName
            edtTitle.setText(defaultTitle)

            btnDelete.setOnClickListener {
                accountEntity = AccountEntity(accountId, defaultTitle)
                viewModel.deleteAccount(accountEntity)
                //deleteListener?.invoke(accountEntity)
                dismiss()
            }

            btnSave.setOnClickListener {
                val title = edtTitle.text.toString()
                if (title.isBlank()) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.mandatory_field_account_name),
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                accountEntity = AccountEntity(accountId, title)
                viewModel.updateAccount(accountEntity)
                // updateListener?.invoke(accountEntity)
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
        const val TAG = "UpdateAccountFragment"

        fun newInstance(
            supportFragmentManager: FragmentManager,
            id: Long
        ) = UpdateAccountDialogFragment().apply {
            accountId = id
        }.show(supportFragmentManager, TAG)
    }
}