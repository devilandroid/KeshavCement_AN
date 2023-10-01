package com.loyaltyworks.keshavcement.utils.fetchData.repository

import android.util.Log
import com.example.authenticationmodule.AuthAPICall
import com.loyaltyworks.keshavcement.ApplicationClass
import com.loyaltyworks.keshavcement.BuildConfig
import com.loyaltyworks.keshavcement.utils.internet.Internet
import retrofit2.Response
import java.net.SocketException

open class BaseRepository {
    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>, error: String): T? {
        var output: T? = null
        try {

            val result = apiOutput(call, error)
            when (result) {
                is Output.Success ->
                    output = result.output
                is Output.Error -> Log.e("Error", "The $error and the ${result.exception}")
            }
            return output
        } catch (e:SocketException){
            return output
        }

    }

    private suspend fun <T : Any> apiOutput(
        call: suspend () -> Response<T>,
        error: String
    ): Output<T> {
        return if (Internet.isNetworkConnected()) {

            var response = call.invoke()

            if(response.code() == 401) {

                if(AuthAPICall.run(ApplicationClass.appContext, BuildConfig.token_url, BuildConfig.token_request)!=null){
                    response = call.invoke() // recall method
                }

            }

            return if (response.isSuccessful) {

                if (response.body() != null)
                    Output.Success(response.body()!!)
                else
                    Output.Error(Exception(response.errorBody().toString()))

            } else Output.Error(Exception("OOps .. Something went wrong due to  ${response.code()} : ${response.raw()}"))

        } else Output.Error(Exception("OOps .. Something went wrong"))
    }
}