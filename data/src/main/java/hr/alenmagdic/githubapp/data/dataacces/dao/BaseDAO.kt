package hr.alenmagdic.githubapp.data.dataacces.dao

import hr.alenmagdic.githubapp.data.dataacces.DataAccessException

open class BaseDAO {

    protected fun <T> getDataFromService(serviceCall : retrofit2.Call<T>) : T {
        val queryResponse = try {
            serviceCall.execute().body()
        } catch (ex : Exception) {
            ex.printStackTrace()
            null
        }

        return queryResponse ?: throw DataAccessException("Failed to fetch repositories from remote source.")
    }
}