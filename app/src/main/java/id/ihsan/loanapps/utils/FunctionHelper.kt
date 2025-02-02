package id.ihsan.loanapps.utils

import java.text.DecimalFormat

object FunctionHelper {

    fun convertRupiahFormat(price: Int?): String {
        val formatter = DecimalFormat("#,###")
        return "Rp." + formatter.format(price?.toLong()).replace(",",".")
    }

    fun convertTermToMonths(termInDays: Int): Int {
        return termInDays/30
    }

}