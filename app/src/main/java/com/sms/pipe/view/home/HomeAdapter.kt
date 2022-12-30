package com.sms.pipe.view.home

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.sms.pipe.R
import com.sms.pipe.databinding.ItemAppletBinding
import com.sms.pipe.databinding.ViewPagerHeaderBinding
import com.sms.pipe.utils.classTag
import com.sms.pipe.view.model.AppletFilterContent
import com.sms.pipe.view.model.AppletFilterSender
import com.sms.pipe.view.model.AppletType
import com.sms.pipe.view.model.AppletUi

class HomeAdapter(
    private val onClick: ((service: AppletUi) -> Unit)? = null,
    private val headerData: List<AppletType>,
    private val onHeaderClick: Visitor? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_HEADER: Int = 0
        private const val TYPE_ITEM: Int = 1
    }

    private val data = mutableListOf<AppletUi>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_ITEM) {
            val binding = ItemAppletBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            AppletHolder(binding, onClick)
        } else {
            val binding = ViewPagerHeaderBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            HeaderViewHolder(binding, onHeaderClick)
        }


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_ITEM) {
            (holder as AppletHolder).bind(data[position - 1])
        } else {
            (holder as HeaderViewHolder).bind(headerData)
        }
    }


    override fun getItemViewType(position: Int): Int {
        return if (position == 0) TYPE_HEADER
        else TYPE_ITEM
    }

    override fun getItemCount(): Int = data.size + 1


    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<AppletUi>) {
        this.data.apply {
            clear()
            addAll(data)
            notifyDataSetChanged()
        }
    }

    class AppletHolder(
        val binding: ItemAppletBinding,
        private val listener: ((service: AppletUi) -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(applet: AppletUi) {
            binding.card.setOnClickListener {
                listener?.invoke(applet)
            }
            binding.card.visibility = View.VISIBLE
            binding.cardTitle.text = applet.appletName
            binding.cardDescription.text =
                binding.root.context.getString(R.string.card_description, applet.channelName)
            applet.filters.forEach {
                when (it) {
                    is AppletFilterSender -> binding.icSender.visibility = View.VISIBLE
                    is AppletFilterContent -> binding.icSms.visibility = View.VISIBLE
                }
            }
            binding.rules.text =
                binding.root.context.getString(
                    R.string.card_rules,
                    applet.filters.map { it.toString() }.toString()
                )

            when (applet.type) {
                AppletType.SLACK -> {
                    binding.container.background =
                        ContextCompat.getDrawable(binding.root.context, R.drawable.gradient_slack)
                }
                AppletType.MAIL -> {
                    binding.container.background =
                        ContextCompat.getDrawable(binding.root.context, R.drawable.gradient_mail)
                }
                AppletType.DEVICE -> {
                    binding.container.background =
                        ContextCompat.getDrawable(binding.root.context, R.drawable.gradient_device)
                }

                else -> {
                    Log.e(this.classTag, "")
                }
            }
        }
    }


    class HeaderViewHolder(
        private val binding: ViewPagerHeaderBinding,
        private val visitor: Visitor?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(headerData: List<AppletType>) {

            val childMembersAdapter = AppletTypeViewPagerAdapter(visitor)
            childMembersAdapter.setData(headerData)
            binding.viewPager.adapter = childMembersAdapter
            TabLayoutMediator(binding.indicator, binding.viewPager) { _, _ ->
            }.attach()
            binding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            binding.viewPager.autoScroll(5000)
        }
    }
}