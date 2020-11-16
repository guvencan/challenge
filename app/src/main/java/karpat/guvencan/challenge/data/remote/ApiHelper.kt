package karpat.guvencan.challenge.data.remote

import karpat.guvencan.challenge.common.Resource
import retrofit2.Response

/*
    copy, paste class
 */

object ApiHelper {
    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>): Resource<T> {
        return try {
            val response = call.invoke()
            if (response.isSuccessful) {
                Resource.success(response.body()!!)
            } else {
                Resource.error(response.errorBody().toString(), null)
            }

        } catch (e: Exception) {
            Resource.error(e.message.toString(), null)
        }
    }
}