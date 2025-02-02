package id.ihsan.loanapps.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.ihsan.loanapps.databinding.ItemRowLoanBinding
import id.ihsan.loanapps.utils.FunctionHelper.convertRupiahFormat
import id.ihsan.loanapps.utils.FunctionHelper.convertTermToMonths
import id.ihsan.loanapps.model.ResponseLoanItem
import id.ihsan.loanapps.utils.RiskRating

class LoanAdapter : RecyclerView.Adapter<LoanAdapter.LoanViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<ResponseLoanItem>() {
        override fun areItemsTheSame(oldItem: ResponseLoanItem, newItem: ResponseLoanItem): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ResponseLoanItem, newItem: ResponseLoanItem): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    private lateinit var onItemCallBack: IOnItemCallBack


    inner class LoanViewHolder(private val binding: ItemRowLoanBinding):
        RecyclerView.ViewHolder(binding.root){

            fun bind(result : ResponseLoanItem){
                binding.apply {
                    val risk = RiskRating.from(result.riskRating)

                    tvName.text = result.borrower?.name
                    tvTerm.text = result.term?.let { "${convertTermToMonths(it)} month" } ?: "N/A"
                    tvPurpose.text = result.purpose
                    tvLoan.text = convertRupiahFormat(result.amount)
                    tvInterestRate.text = "${result.interestRate}% (Interest Rate)"

                    tvRisk.text = risk.label // Gunakan label dari enum agar sesuai
                    tvRisk.setTextColor(risk.color) // Set warna berdasarkan enum

                    itemView.setOnClickListener {
                        onItemCallBack.onItemClickCallback(result)
                    }

                }
            }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoanViewHolder {
        return LoanViewHolder(ItemRowLoanBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: LoanViewHolder, position: Int) {
            holder.bind(differ.currentList[position])
    }

    fun setData(loan: List<ResponseLoanItem?>?){
        differ.submitList(loan)
    }

    fun setOnItemClickCallback(action: IOnItemCallBack) {
        this.onItemCallBack = action
    }
    interface IOnItemCallBack {
        fun onItemClickCallback(data: ResponseLoanItem)
    }

}