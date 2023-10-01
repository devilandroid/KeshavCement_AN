package com.loyaltyworks.keshavcement.ui.support

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyaltyworks.keshavcement.baseClass.BaseViewModel
import com.loyaltyworks.keshavcement.model.HelpTopicRequest
import com.loyaltyworks.keshavcement.model.HelpTopicResponse
import com.loyaltyworks.keshavcement.model.SaveNewTicketQueryRequest
import com.loyaltyworks.keshavcement.model.SaveNewTicketQueryResponse
import kotlinx.coroutines.launch

class NewTicketViewModel: BaseViewModel() {

    /*Help topic listing view model */
    private val _topicListLiveData = MutableLiveData<HelpTopicResponse>()
    val topicListLiveData : LiveData<HelpTopicResponse> = _topicListLiveData

    fun getHelpTopicListLiveData(helpTopicRequest: HelpTopicRequest) {
        scope.launch {
            val helpTopic_data = apiRepository.getHelpTopic(helpTopicRequest)
            _topicListLiveData.postValue(helpTopic_data!!) }
    }


    /*Save new ticket request-response view model*/
    private val _saveNewTicketQueryLiveData = MutableLiveData<SaveNewTicketQueryResponse>()
    val saveNewTicketQueryLiveData : LiveData<SaveNewTicketQueryResponse> = _saveNewTicketQueryLiveData

    fun saveNewTicketQuery(saveNewTicketQueryRequest: SaveNewTicketQueryRequest) {

        scope.launch {
            _saveNewTicketQueryLiveData.postValue(
                apiRepository.getSaveNewTicketQuery(
                    saveNewTicketQueryRequest
                )
            ) }
    }
}