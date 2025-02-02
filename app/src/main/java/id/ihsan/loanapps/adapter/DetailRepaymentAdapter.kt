package id.ihsan.loanapps.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.ihsan.loanapps.databinding.ItemRowDetailOrderBinding
import id.ihsan.loanapps.model.InstallmentsItem
import id.ihsan.loanapps.utils.FunctionHelper.convertRupiahFormat

class DetailRepaymentAdapter: RecyclerView.Adapter<DetailRepaymentAdapter.DetailHistoryViewHolder>() {

    private val diffCallBack = object  : DiffUtil.ItemCallback<InstallmentsItem>(){
        override fun areItemsTheSame(oldItem: InstallmentsItem, newItem: InstallmentsItem): Boolean {
            return oldItem.dueDate == newItem.dueDate
        }

        override fun areContentsTheSame(oldItem: InstallmentsItem, newItem: InstallmentsItem): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this,diffCallBack)
    private var dataItem: List<InstallmentsItem?>? = listOf()

    inner class DetailHistoryViewHolder(private val binding: ItemRowDetailOrderBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(item: InstallmentsItem){
            binding.apply {
                tvDueDate.text = item.dueDate
                tvAmountDue.text = convertRupiahFormat(item.amountDue)
//                tvHargaTiket.text = FunctionHelper.rupiahFormat(item..toString())
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DetailRepaymentAdapter.DetailHistoryViewHolder {
        return DetailHistoryViewHolder(ItemRowDetailOrderBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(
        holder: DetailRepaymentAdapter.DetailHistoryViewHolder,
        position: Int
    ) {
        dataItem?.get(position)?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return dataItem?.size ?: 0
    }

    fun setData(list: List<InstallmentsItem?>?){
        differ.submitList(list)
        dataItem = list
        notifyDataSetChanged()
    }

}