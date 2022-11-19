package com.sms.pipe.view.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.sms.pipe.R
import com.sms.pipe.databinding.AppletTypeItemBinding
import com.sms.pipe.view.model.AppletType


typealias Visitor = ((appletType: AppletType) -> Unit)


class AppletTypeViewPagerAdapter(private val onClick: Visitor? = null) :
    RecyclerView.Adapter<AppletTypeViewPagerAdapter.AppleTypeViewHolder>() {
    private val data = mutableListOf<AppletType>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppleTypeViewHolder {
        val binding =
            AppletTypeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AppleTypeViewHolder(binding, onClick)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<AppletType>) {
        this.data.apply {
            clear()
            addAll(data)
            notifyDataSetChanged()
        }
    }

    override fun onBindViewHolder(holder: AppleTypeViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }


    class AppleTypeViewHolder(
        val binding: AppletTypeItemBinding,
        private val listener: Visitor?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(appletType: AppletType) {
            binding.root.setOnClickListener {
                listener?.invoke(appletType)
            }
            when (appletType) {
                AppletType.SLACK -> {
                    binding.icon.setImageResource(R.drawable.ic_slack_64)
                    binding.title.text = binding.root.context.getString(R.string.transfer_to_slack)
                    binding.description.text = binding.root.context.getString(R.string.applet_type_slack_description)
                    binding.container.background = ContextCompat.getDrawable(binding.root.context,R.drawable.gradient_slack)
                }
                AppletType.MAIL -> {
                    binding.icon.setImageResource(R.drawable.ic_mail_64)
                    binding.title.text = binding.root.context.getString(R.string.transfer_to_mail)
                    binding.description.text = binding.root.context.getString(R.string.applet_type_email_description)
                    binding.container.background = ContextCompat.getDrawable(binding.root.context,R.drawable.gradient_mail)
                }
                AppletType.DEVICE -> {
                    binding.icon.setImageResource(R.drawable.ic_mobile_64)
                    binding.title.text = binding.root.context.getString(R.string.transfer_to_device)
                    binding.description.text = binding.root.context.getString(R.string.applet_type_device_description)
                    binding.container.background = ContextCompat.getDrawable(binding.root.context,R.drawable.gradient_device)
                }
            }
        }

    }
}