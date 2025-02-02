package id.ihsan.loanapps.service

import android.content.Context
import android.util.Log
import id.ihsan.loanapps.service.configuration.RetrofitConfig
import id.ihsan.loanapps.service.network.LoanApi
import id.ihsan.loanapps.utils.Constants.BASE_URL
import id.ihsan.loanapps.utils.NetworkResult
import id.ihsan.loanapps.model.ResponseLoan
import id.ihsan.loanapps.model.ResponseLoanItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteDataSource {

    suspend fun handleLoanResponse(context: Context): Flow<NetworkResult<List<ResponseLoanItem>>> = flow {
        try {
            emit(NetworkResult.Loading(true))
            val loanApi = getLoanApi(context)
            val loanData = loanApi.getAllLoan()
            if (loanData.isSuccessful){
                val responseBody = loanData.body()
                Log.d("getLoanData", responseBody.toString())

                if (responseBody != null){
                    Log.d("apiServiceLoan", "Success statusCode:${loanData.code()}, data:${loanData}")
                    emit(NetworkResult.Success(responseBody))
                }else{
                    Log.d("apiServiceLoanError", " statusCode:${loanData.code()}, data:${loanData}")
                    emit(NetworkResult.Error("Belum ada tagihan :)"))
                }

            }else{
                Log.d("apiServiceLoanError","StatusCode : ${loanData.code()},")
                emit(NetworkResult.Error("Gagal ngambil data dari server:( coba lagi nanti yaa!"))
            }
        }catch (e:Exception){
            e.printStackTrace()
            Log.d("RemoteLoanError","${e.message}")
            emit(NetworkResult.Error("Something went wrong please check log"))
        }
    }

    private fun getLoanApi(context: Context) : LoanApi{

        val retrofitConfig = RetrofitConfig(context, BASE_URL )
        val retrofit = retrofitConfig.retrofit()
        return retrofit.create(LoanApi::class.java)

    }

}