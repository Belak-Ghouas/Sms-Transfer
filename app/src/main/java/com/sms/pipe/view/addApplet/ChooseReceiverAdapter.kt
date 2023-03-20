package com.sms.pipe.view.addApplet

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sms.pipe.R
import com.sms.pipe.databinding.AppletTypeItemSmallBinding
import com.sms.pipe.view.model.AppletType

typealias Visitor = ((appletType: AppletType) -> Unit)


class ChooseReceiverAdapter(private val onClick: Visitor? = null) :
    RecyclerView.Adapter<ChooseReceiverAdapter.ChooseReceiverVH>() {
    private val data = mutableListOf<AppletType>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChooseReceiverVH {
        val binding =
            AppletTypeItemSmallBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChooseReceiverVH(binding, onClick)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<AppletType>) {
        this.data.apply {
            clear()
            addAll(data)
            notifyDataSetChanged()
        }
    }

    override fun onBindViewHolder(holder: ChooseReceiverVH, position: Int) {
        holder.bind(data[position % data.size])
    }

    override fun getItemCount(): Int {
        return data.size * 2
    }


    class ChooseReceiverVH(
        val binding: AppletTypeItemSmallBinding,
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
                }
                AppletType.MAIL -> {
                    binding.icon.setImageResource(R.drawable.ic_mail_64)
                    binding.title.text = binding.root.context.getString(R.string.transfer_to_mail)
                }
                AppletType.DEVICE -> {
                    binding.icon.setImageResource(R.drawable.ic_mobile_64)
                    binding.title.text = binding.root.context.getString(R.string.transfer_to_device)
                }
                AppletType.UNKNOWN -> {}
            }
        }

    }
}