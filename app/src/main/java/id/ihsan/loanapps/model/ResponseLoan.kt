package id.ihsan.loanapps.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class ResponseLoan(

	@field:SerializedName("ResponseLoan")
	val responseLoan: List<ResponseLoanItem?>? = null
) : Parcelable