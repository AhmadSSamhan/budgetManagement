package com.android.task.budgetmanagement.ui.create_account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.android.task.budgetmanagement.R
import com.android.task.budgetmanagement.data.database.entity.AccountEntity
import com.android.task.budgetmanagement.databinding.DialogFragmentAddAccountBinding
import com.android.task.budgetmanagement.ui.base.BaseBottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddAccountDialogFragment : BaseBottomSheetDialog() {

    private var _binding: DialogFragmentAddAccountBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var accountEntity: AccountEntity

    private var mListener: ((AccountEntity) -> Unit?)? = null

    private val viewModel: AddAccountViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogFragmentAddAccountBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    private fun initListener() {
        binding.apply {
            btnSave.setOnClickListener {
                val title = edtTitle.text.toString()
                if (title.isNotEmpty()) {
                    accountEntity = AccountEntity(0, title)
                    viewModel.insertAccount(accountEntity)
                    dismiss()
                    mListener?.invoke(accountEntity)
                } else {
                    Snackbar.make(it, getString(R.string.empty_account_field), Snackbar.LENGTH_LONG)
                        .show()
                }
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
        const val TAG = "AddAccountDialogFragment"

        fun newInstance(
            supportFragmentManager: FragmentManager,
            canCancel: Boolean = true,
            invokeClick: (AccountEntity) -> Unit
        ) = AddAccountDialogFragment().apply {
            isCancelable = canCancel
            mListener = invokeClick
        }.show(supportFragmentManager, TAG)
    }
}