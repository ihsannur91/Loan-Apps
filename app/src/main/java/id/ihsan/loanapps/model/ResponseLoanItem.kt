package id.ihsan.loanapps.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.RawValue

@Parcelize
data class ResponseLoanItem(

    @field:SerializedName("interestRate")
	val interestRate: @RawValue Any? = null,

    @field:SerializedName("amount")
	val amount: Int? = null,

    @field:SerializedName("purpose")
	val purpose: String? = null,

    @field:SerializedName("documents")
	val documents: List<DocumentsItem?>? = null,

    @field:SerializedName("borrower")
	val borrower: Borrower? = null,

    @field:SerializedName("term")
	val term: Int? = null,

    @field:SerializedName("id")
	val id: String? = null,

    @field:SerializedName("collateral")
	val collateral: Collateral? = null,

    @field:SerializedName("repaymentSchedule")
	val repaymentSchedule: RepaymentSchedule? = null,

    @field:SerializedName("riskRating")
	val riskRating: String? = null
) : Parcelable