package id.ihsan.loanapps.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import id.ihsan.loanapps.model.InstallmentsItem

@Parcelize
data class RepaymentSchedule(

	@field:SerializedName("installments")
	val installments: List<InstallmentsItem?>? = null
) : Parcelable