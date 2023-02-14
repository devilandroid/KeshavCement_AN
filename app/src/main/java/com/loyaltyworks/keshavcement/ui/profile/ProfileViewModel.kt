package com.loyaltyworks.keshavcement.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyaltyworks.keshavcement.baseClass.BaseViewModel
import com.loyaltyworks.keshavcement.model.*
import kotlinx.coroutines.launch

class ProfileViewModel: BaseViewModel() {

    /*** Profile Image Update View Model ***/
    private val _profileImageUpdateLiveData = MutableLiveData<ProfileImageUpdateResponse>()
    val profileImageUpdateLiveData: LiveData<ProfileImageUpdateResponse> = _profileImageUpdateLiveData

    fun setProfileImageData(profileImageUpdateRequest: ProfileImageUpdateRequest) {
        ///launch the coroutine scope
        scope.launch {

            //post the value inside live data
            _profileImageUpdateLiveData.postValue(apiRepository.getProfileImageUpdateAccount(profileImageUpdateRequest))
        }
    }

}