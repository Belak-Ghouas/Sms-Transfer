package com.sms.pipe.view.base

import android.app.Dialog
import android.os.Bundle
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sms.pipe.R
import com.sms.pipe.view.getScreenHeight
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module


abstract class BaseBottomSheet : BottomSheetDialogFragment() {

    companion object {
        //is the bottom sheet can be dismissed with scroll down
        const val ARG_IS_CANCELLABLE = "is cancellable"
        const val ARG_IS_DRAGGABLE = "is draggable"
        const val ARG_HEIGHT_PERCENTAGE = "height percentage"
        const val ARG_HEIGHT_WRAP_CONTENT = " height wrap content"
    }

    open val moduleList: List<Module> = emptyList()

    private val isCancellable by lazy {
        arguments?.getBoolean(ARG_IS_CANCELLABLE, true) ?: true
    }

    private val isDraggable by lazy {
        arguments?.getBoolean(ARG_IS_DRAGGABLE, true) ?: true
    }

    private val heightPercentage by lazy {
        arguments?.getInt(ARG_HEIGHT_PERCENTAGE, 95) ?: 95
    }

    private val heightWrapContent by lazy {
        arguments?.getBoolean(ARG_HEIGHT_WRAP_CONTENT, true) ?: true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(moduleList)
    }

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
    }


    open fun initViews() {}
    open fun initObservers() {}

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.setOnShowListener {
            (it as BottomSheetDialog).apply {
                setCancelable(isCancellable)
                setUpConfiguration(this)
            }
        }
        return dialog
    }

    /**
     * setUp the bottom sheet to be on full Height
     */
    private fun setUpConfiguration(bottomSheetDialog: BottomSheetDialog) {
        val parentLayout =
            bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        parentLayout?.let { parent ->
            val behaviour = BottomSheetBehavior.from(parent)
            val layoutParams = parent.layoutParams
            if (!heightWrapContent) layoutParams.height = getHeight()
            parent.layoutParams = layoutParams
            behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            behaviour.skipCollapsed = true
            behaviour.isDraggable = isDraggable
            behaviour.isHideable = true
        }
    }


    private fun getHeight(): Int {
        return ((heightPercentage * getScreenHeight()) / 100)
    }
}