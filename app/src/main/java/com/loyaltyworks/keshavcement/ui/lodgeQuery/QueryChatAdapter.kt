package com.loyaltyworks.keshavcement.ui.lodgeQuery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.loyaltyworks.keshavcement.databinding.RowLeftChatCellBinding
import com.loyaltyworks.keshavcement.databinding.RowRightChatCellBinding
import kotlinx.android.synthetic.main.row_left_chat_cell.view.*

class QueryChatAdapter/*(var queryListingResponse: List<ObjQueryResponseJson>?, var chatImageDisplay: ChatImageDisplay)*/ : RecyclerView.Adapter<QueryChatAdapter.ViewHolder>() {

    var leftItemView: RowLeftChatCellBinding? = null
    var rightItemView: RowRightChatCellBinding? = null

    val LEFT_CELL = 1
    val RIGHT_CELL = 2

    interface ChatImageDisplay {
        fun onClickChatImage(Url: String?)
    }

    class ViewHolder(binding: ViewBinding): RecyclerView.ViewHolder(binding.root) {
        val row_query_sender = binding.root.row_query_sender
        val row_query_missed_call = binding.root.row_query_missed_call
        val row_query_time = binding.root.row_query_time
        val row_query_text = binding.root.row_query_text
        val row_query_text_pdf = binding.root.pdf
        val chatImage = binding.root.chatImage



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return if (viewType == LEFT_CELL) {
            leftItemView = RowLeftChatCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ViewHolder(leftItemView!!)
        } else {
            rightItemView = RowRightChatCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(rightItemView!!)
        }

    }

    override fun getItemViewType(position: Int): Int {
        /*return if (queryListingResponse!![position].userType.equals("CUSTOMER",true)) RIGHT_CELL else LEFT_CELL*/
        return RIGHT_CELL
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        /*val lstQueryDetail = queryListingResponse!![position]

        Log.d("jdjfkj",""+lstQueryDetail)

        holder.row_query_sender.text = lstQueryDetail.repliedBy.toString()
        holder.row_query_time?.text = AppController.dateAPIFormat(lstQueryDetail.jCreatedDate.toString())

        if (!lstQueryDetail.queryResponseInfo.isNullOrEmpty() || !lstQueryDetail.imageUrl.isNullOrEmpty()) {

            if (!lstQueryDetail.imageUrl.isNullOrEmpty()) {

                if(lstQueryDetail.imageUrl.contains(".pdf") || lstQueryDetail.imageUrl.contains(".txt") || lstQueryDetail.imageUrl.contains(".txt") ) {

                    val extension = lstQueryDetail.imageUrl.toString().substringAfterLast(".").toLowerCase()

                    if (!FileSelector.isImage(extension)) {

                        holder.chatImage.visibility = View.GONE
                        holder.row_query_text_pdf.visibility = View.VISIBLE
                        holder.row_query_text_pdf.text = lstQueryDetail.imageUrl.toString().substringAfterLast("/")

                    }else{

                        holder.chatImage.visibility = View.VISIBLE
                        holder.row_query_text_pdf.visibility = View.GONE

                    }

                }else{

                    holder.chatImage.visibility = View.VISIBLE
                    holder.row_query_text_pdf.visibility = View.GONE

                }



                if (!lstQueryDetail.queryResponseInfo.isNullOrEmpty()) {

                    holder.row_query_text.visibility = View.VISIBLE
                    holder.row_query_text.text = lstQueryDetail.queryResponseInfo.toString()

                } else holder.row_query_text.visibility = View.GONE

                holder.row_query_missed_call.visibility = View.GONE
                Glide.with(holder.itemView)
                    .load(BuildConfig.PROMO_IMAGE_BASE + lstQueryDetail.imageUrl!!.replace("~", ""))
                    .placeholder(R.drawable.ic_default_img)
                    .error(R.drawable.ic_error)
                    .apply(RequestOptions().transform(RoundedCorners(50)))
                    .into(holder.chatImage)
            } else {
                holder.row_query_text.visibility = View.VISIBLE
                holder.chatImage.visibility = View.GONE
                holder.row_query_missed_call.visibility = View.GONE
                holder.row_query_text.text = lstQueryDetail.queryResponseInfo.toString()
            }
        } else {
            holder.chatImage.visibility = View.GONE
            holder.row_query_text.visibility = View.GONE
            holder.row_query_missed_call.visibility = View.VISIBLE
        }

        holder.chatImage.setOnClickListener(View.OnClickListener {
            chatImageDisplay.onClickChatImage(
                BuildConfig.PROMO_IMAGE_BASE + lstQueryDetail.imageUrl!!
                    .replace("~", "")
            )
        })*/



    }

    override fun getItemCount(): Int {

        /*return queryListingResponse!!.size*/
        return 10
    }

}
