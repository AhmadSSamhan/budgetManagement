package com.android.task.budgetmanagement.ui.base

import android.app.Dialog
import android.os.Bundle
import android.widget.FrameLayout
import com.android.task.budgetmanagement.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

open class BaseBottomSheetDialog : BottomSheetDialogFragment() {

    private var isHideable: Boolean = true //enable/disable close by drag down
    private var sheetBehavior: BottomSheetBehavior<FrameLayout>? = null

    override fun getTheme() = R.style.BottomSheetDialog

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        isCancelable = false
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener {
            val bottomSheet =
                dialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            sheetBehavior = BottomSheetBehavior.from(bottomSheet!!)
            sheetBehavior?.isHideable = isHideable
            sheetBehavior?.state = BottomSheetBehavior.STATE_EXPANDED
        }
        return dialog
    }
}