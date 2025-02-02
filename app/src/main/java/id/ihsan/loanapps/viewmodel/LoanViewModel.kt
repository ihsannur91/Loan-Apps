package id.ihsan.loanapps.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import id.ihsan.loanapps.service.RemoteDataSource
import id.ihsan.loanapps.service.Repository
import id.ihsan.loanapps.utils.NetworkResult
import id.ihsan.loanapps.model.ResponseLoan
import id.ihsan.loanapps.model.ResponseLoanItem
import kotlinx.coroutines.launch

class LoanViewModel(app:Application): AndroidViewModel(app) {

    private val remote = RemoteDataSource()
    private val repository = Repository(remote)

    private var _loanData: MutableLiveData<NetworkResult<List<ResponseLoanItem>>> = MutableLiveData()
    val loanData : LiveData<NetworkResult<List<ResponseLoanItem>>> = _loanData

    fun fetchLoanData(context: Context){
        viewModelScope.launch {
            safeFetchLoanData(context)
        }
    }

    private suspend fun safeFetchLoanData(context: Context){
        _loanData.postValue(NetworkResult.Loading(true))
            if (hasInternetConnection()){
                repository.remote.handleLoanResponse(context).collect{res ->
                    Log.d("fetchLoanData",res.toString())
                    _loanData.value = res
                }
            }else{
                _loanData.postValue(NetworkResult.Error("No Internet connection"))
            }
    }

    private fun hasInternetConnection(): Boolean {
        val connectionManager =
            getApplication<Application>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // checking active network
        val activeNetwork = connectionManager.activeNetwork ?: return false
        //checking capabilities network
        val capabilities = connectionManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

}