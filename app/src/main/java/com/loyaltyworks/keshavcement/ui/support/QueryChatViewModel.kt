package com.loyaltyworks.keshavcement.ui.support

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyaltyworks.keshavcement.baseClass.BaseViewModel
import com.loyaltyworks.keshavcement.model.PostChatStatusRequest
import com.loyaltyworks.keshavcement.model.PostChatStatusResponse
import com.loyaltyworks.keshavcement.model.QueryChatElementRequest
import com.loyaltyworks.keshavcement.model.QueryChatElementResponse
import com.vmb.fileSelect.FileSelector.scope
import kotlinx.coroutines.launch

class QueryChatViewModel: BaseViewModel() {

    /*Chat reply*/
    private val _queryChatLiveData = MutableLiveData<QueryChatElementResponse>()
    val queryChatLiveData : LiveData<QueryChatElementResponse> = _queryChatLiveData

    fun getQueryChat(chatQuery: QueryChatElementRequest){
        scope.launch { _queryChatLiveData.postValue(apiRepository.getChatQuery(chatQuery)) }
    }

    /*Post reply help topic*/

    private val _postChatStatusResponseLiveData = MutableLiveData<PostChatStatusResponse>()
    val postChatStatusResponseLiveData : LiveData<PostChatStatusResponse> = _postChatStatusResponseLiveData

    fun getPostReply(postChatStatusRequest: PostChatStatusRequest, context: Context) {

        scope.launch {
            _postChatStatusResponseLiveData.postValue(
                apiRepository.getPostReply(
                    postChatStatusRequest
                )
            )
        }
    }
}