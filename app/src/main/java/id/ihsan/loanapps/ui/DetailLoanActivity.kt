package id.ihsan.loanapps.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import id.ihsan.loanapps.adapter.DetailRepaymentAdapter
import id.ihsan.loanapps.adapter.DocumentAdapter
import id.ihsan.loanapps.adapter.LoanAdapter
import id.ihsan.loanapps.databinding.ActivityDetailLoanBinding
import id.ihsan.loanapps.model.ResponseLoanItem
import id.ihsan.loanapps.utils.FunctionHelper.convertRupiahFormat

class DetailLoanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailLoanBinding

    private val detailRepaymentAdapter by lazy { DetailRepaymentAdapter() }

    private val documentAdapter by lazy { DocumentAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailLoanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val loanData = intent?.extras?.getParcelable<ResponseLoanItem>(EXTRA_LOAN)

        binding.toolBar.setOnClickListener {
            finish()
        }

        //display loanData pada ui
        binding.apply {
            tvEmail.text = loanData?.borrower?.email
            tvName.text = loanData?.borrower?.name
            tvCreditScore.text = loanData?.borrower?.creditScore.toString()
            tvCollacteralType.text = loanData?.collateral?.type
            tvCollacteralValue.text = convertRupiahFormat(loanData?.collateral?.value)
        }

        binding.rvDetailOrder.apply {
            adapter = detailRepaymentAdapter
            layoutManager = LinearLayoutManager(this@DetailLoanActivity)

        }

        detailRepaymentAdapter.setData(loanData?.repaymentSchedule?.installments)

        if (loanData?.documents?.isEmpty() == true){
            handleUiEmptyDocument(
                textEmpty = true,
                recyclerView = false
            )

        }else{

            documentAdapter.setData(loanData?.documents)

            binding.rvDocument.apply {
                adapter = documentAdapter
                layoutManager = LinearLayoutManager(this@DetailLoanActivity)
            }
        }

    }

    companion object{
        const val EXTRA_LOAN = "loan"
    }

    private fun handleUiEmptyDocument(
        textEmpty:Boolean,
        recyclerView:Boolean
    ){
        binding.apply {

            tvEmpty.isVisible = textEmpty
            rvDocument.isVisible = recyclerView

        }
    }

}