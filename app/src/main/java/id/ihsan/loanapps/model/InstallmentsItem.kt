package id.ihsan.loanapps.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class InstallmentsItem(

	@field:SerializedName("amountDue")
	val amountDue: Int? = null,

	@field:SerializedName("dueDate")
	val dueDate: String? = null
) : Parcelable