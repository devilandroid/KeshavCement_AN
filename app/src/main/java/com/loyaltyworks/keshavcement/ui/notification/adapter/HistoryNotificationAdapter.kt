package com.loyaltyworks.keshavcement.ui.notification.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.loyaltyworks.keshavcement.BuildConfig
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.model.HistoryNotificationResponse
import com.loyaltyworks.keshavcement.model.LstPushHistoryJson
import kotlinx.android.synthetic.main.row_history_notification.view.*

class HistoryNotificationAdapter(var hisotryListingResponse: HistoryNotificationResponse?,
                                 var itemClicked: ItemClicked):RecyclerView.Adapter<HistoryNotificationAdapter.ViewHolder>() {

    interface ItemClicked {
        fun itemclicks(notificationHistory: LstPushHistoryJson?)
        fun imageclicks(imageurls: String)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val NReadOrNot = itemView.NReadOrNot
        val NImage = itemView.NImage
        //        val NtitleLL = itemView.NtitleLL
        val Ntitle = itemView.Ntitle
        val Ndate = itemView.Ndate
        val Ndesc = itemView.Ndesc
        val NdescExpandable = itemView.NdescExpandable
        val view = itemView.view
        val NReadMore = itemView.NReadMore
        val cardView = itemView.cardView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.row_history_notification,
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notificationHistory = hisotryListingResponse!!.lstPushHistoryJson!![position]

        if (notificationHistory.imagesURL != null && !notificationHistory.imagesURL.isNullOrEmpty())
            Glide.with(holder.itemView)
                .load(BuildConfig.PROMO_IMAGE_BASE + notificationHistory.imagesURL)
                .placeholder(R.drawable.ic_default_img) //                .error(R.drawable.ic_default_img)
                .into(holder.NImage)
        else holder.NImage.visibility = View.GONE

        if (notificationHistory.isRead === 1) holder.NReadOrNot.visibility = View.VISIBLE else holder.NReadOrNot.visibility = View.INVISIBLE

        holder.Ntitle.text = notificationHistory.title

        holder.Ndesc.text = notificationHistory.pushMessage
        holder.NdescExpandable.text = notificationHistory.pushMessage


        try {

            Log.d("fjhdskjf",notificationHistory.jCreatedDate.toString())

            holder.Ndate.text = notificationHistory.jCreatedDate.toString().split(" ")[0]

            if (notificationHistory.pushMessage!!.length > 96) {
                holder.NReadMore.text = holder.view.context.getString(R.string.ReadMore)
                holder.NReadMore.visibility = View.VISIBLE
                holder.NdescExpandable.visibility = View.GONE
                holder.Ndesc.visibility = View.VISIBLE
            } else {
                holder.NReadMore.visibility = View.INVISIBLE
                holder.NdescExpandable.visibility = View.GONE
                holder.Ndesc.visibility = View.VISIBLE
            }

        }catch (e: Exception){}

        holder.itemView.setOnClickListener(View.OnClickListener {
            itemClicked.itemclicks(notificationHistory)
            holder.NReadOrNot.visibility = View.VISIBLE
            if (holder.NReadMore.text.toString().equals(holder.view.context.getString(R.string.ReadMore), ignoreCase = true)) {
                holder.NdescExpandable.visibility = View.VISIBLE
                holder.Ndesc.visibility = View.GONE
                holder.NReadMore.text = holder.view.context.getString(R.string.ReadLess)
            } else {
                holder.NdescExpandable.visibility = View.GONE
                holder.Ndesc.visibility = View.VISIBLE
                holder.NReadMore.text = holder.view.context.getString(R.string.ReadMore)
            }
        })

        holder.NImage.setOnClickListener {
            if (notificationHistory.imagesURL != null && !notificationHistory.imagesURL.isNullOrEmpty()){
                var imageurls = BuildConfig.PROMO_IMAGE_BASE + notificationHistory.imagesURL
                itemClicked.imageclicks(imageurls)
            }
        }

    }

    override fun getItemCount(): Int {
        return hisotryListingResponse!!.lstPushHistoryJson?.size!!
    }

    fun removeItem(position: Int) {

        hisotryListingResponse!!.lstPushHistoryJson?.removeAt(position)
        notifyItemRemoved(position)
    }

    fun getData(): List<LstPushHistoryJson?>? {
        return hisotryListingResponse!!.lstPushHistoryJson
    }

}