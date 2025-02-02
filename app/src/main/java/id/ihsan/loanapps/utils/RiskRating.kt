package id.ihsan.loanapps.utils

import android.graphics.Color

enum class RiskRating(val label: String, val color: Int) {
    A("A", Color.RED),
    B("B", Color.YELLOW),
    C("C", Color.GREEN),
    UNKNOWN("N/A", Color.BLACK); // Default jika tidak dikenali

    companion object {
        fun from(value: String?): RiskRating {
            return values().find { it.label == value } ?: UNKNOWN
        }
    }
}