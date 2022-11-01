package com.sms.pipe.view.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.slack.api.model.admin.App
import com.sms.pipe.R
import com.sms.pipe.databinding.ItemAppletBinding
import com.sms.pipe.view.model.AppletFilterContent
import com.sms.pipe.view.model.AppletFilterSender
import com.sms.pipe.view.model.AppletUi

class AppletAdapter(private val onClick :((service: AppletUi)->Unit)?= null) : RecyclerView.Adapter<AppletAdapter.AppletHolder>() {

    private val data = mutableListOf<AppletUi>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppletHolder {
        val binding = ItemAppletBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return AppletHolder(binding,onClick)
    }

    override fun onBindViewHolder(holder: AppletHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size


    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<AppletUi>) {
        this.data.apply {
            clear()
            addAll(data)
            notifyDataSetChanged()
        }
    }
    class AppletHolder(val binding: ItemAppletBinding, private val listener:((service:AppletUi)->Unit)?) : RecyclerView.ViewHolder(binding.root) {

        fun bind(applet: AppletUi) {
            binding.card.setOnClickListener {
                listener?.invoke(applet)
            }
            binding.card.visibility = View.VISIBLE
            binding.cardTitle.text = applet.appletName
            binding.cardDescription.text = binding.root.context.getString(R.string.card_description, applet.channelName)
            applet.filters.forEach {
                when (it) {
                    is AppletFilterSender -> binding.icSender.visibility = View.VISIBLE
                    is AppletFilterContent -> binding.icSms.visibility = View.VISIBLE
                }
            }
            binding.rules.text =
                binding.root.context.getString(R.string.card_rules, applet.filters.map { it.toString() }.toString())
        }
    }
}