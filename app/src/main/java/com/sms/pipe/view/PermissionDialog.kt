package com.sms.pipe.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatDialogFragment
import com.sms.pipe.R
import com.sms.pipe.databinding.DialogPermissionsBinding
import com.sms.pipe.utils.ARG_PERMISSION_CONTENT
import com.sms.pipe.utils.ARG_PERMISSION_TYPE

class PermissionDialog private constructor() : AppCompatDialogFragment() {

    private lateinit var binding: DialogPermissionsBinding

    var onAcceptClicked: (() -> Unit)? = null
    var onDenyClicked: (() -> Unit)? = null

    companion object {
        fun build(
            @StringRes permission_type: Int,
            @StringRes permission_content: Int
        ): PermissionDialog {
            return PermissionDialog().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PERMISSION_TYPE, permission_type)
                    putInt(ARG_PERMISSION_CONTENT, permission_content)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogPermissionsBinding.inflate(inflater, container, false)
        initViews()
        return binding.root
    }

    fun initViews() {
        dialog?.setCanceledOnTouchOutside(false);
        binding.accept.setOnClickListener {
            onAcceptClicked?.invoke()
            dismissNow()
        }
        binding.deny.setOnClickListener {
            onDenyClicked?.invoke()
            dismissNow()
        }
        requireArguments().apply {
            binding.content.text = getString(
                R.string.permission_dialog,
                getString(this.getInt(ARG_PERMISSION_TYPE)),
                getString(this.getInt(ARG_PERMISSION_CONTENT))
            )
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        onAcceptClicked = null
        onDenyClicked = null
    }

}