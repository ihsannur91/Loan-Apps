package id.ihsan.loanapps.service.network

import id.ihsan.loanapps.model.ResponseLoan
import id.ihsan.loanapps.model.ResponseLoanItem
import retrofit2.Response
import retrofit2.http.GET

interface LoanApi {

    @GET("andreascandle/p2p_json_test/main/api/json/loans.json")
    suspend fun getAllLoan():Response<List<ResponseLoanItem>>

}