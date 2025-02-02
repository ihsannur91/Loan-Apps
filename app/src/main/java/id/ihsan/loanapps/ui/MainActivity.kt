package id.ihsan.loanapps.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import id.ihsan.loanapps.R
import id.ihsan.loanapps.adapter.LoanAdapter
import id.ihsan.loanapps.databinding.ActivityMainBinding
import id.ihsan.loanapps.model.ResponseLoanItem
import id.ihsan.loanapps.utils.NetworkResult
import id.ihsan.loanapps.viewmodel.LoanViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    private val loanViewModel by viewModels<LoanViewModel>()

    private val loanAdapter by lazy { LoanAdapter() }

    private var loanList = listOf<ResponseLoanItem>()

    private var selectedRisk: String = "All"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestApiData()

        setupRecyclerView()

        setupSearchFeature()

        setupFilterFeature()

        setupSwipeRefresh()


    }

    private fun setupSwipeRefresh() {
        binding.layoutSwipeRefresh.setOnRefreshListener {
            requestApiData() // Request ulang data saat di-refresh
        }
    }

    private fun requestApiData() {
        loanViewModel.fetchLoanData(this)
        loanViewModel.loanData.observe(this) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    handleShimmerEffect(false)
                    response.data.let {
                        loanAdapter.setData(it)
                        if (it != null) {
                            loanList= it
                        }
                    }

                    //mengirim data ke detail loan
                    loanAdapter.setOnItemClickCallback(object : LoanAdapter.IOnItemCallBack{
                        override fun onItemClickCallback(data: ResponseLoanItem) {
                            val intent = Intent(this@MainActivity, DetailLoanActivity::class.java).apply {
                                putExtra(DetailLoanActivity.EXTRA_LOAN, data)
                            }
                            startActivity(intent)
                        }

                    })

                    handleUiEmptyData(imageEmpty = false,textEmpty = false, recyclerView = true)

                }
                is NetworkResult.Error -> {
                    handleShimmerEffect(false)
                    Toast.makeText(this, response.errorMessage.toString(), Toast.LENGTH_SHORT)
                        .show()
                    handleUiEmptyData(imageEmpty = true,textEmpty = true, recyclerView = false)
                }
                is NetworkResult.Loading -> {
                    handleShimmerEffect(true)
                    handleUiEmptyData(imageEmpty = false,textEmpty = false, recyclerView = false)
                }
            }

            binding.layoutSwipeRefresh.isRefreshing = false

        }
    }

    private fun setupRecyclerView() {
        binding.rvLoan.apply {
            adapter = loanAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            handleShimmerEffect(true)
        }
    }

    private fun setupSearchFeature() {
        binding.tfSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterLoanData(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setupFilterFeature() {
        binding.imgFilter.setOnClickListener {
            showFilterBottomSheet()
        }
    }

    private fun showFilterBottomSheet() {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottom_sheet_filter, null)
        dialog.setContentView(view)

        view.findViewById<View>(R.id.tv_default).setOnClickListener {
            selectedRisk = "All"
            filterLoanData(binding.tfSearch.text.toString())
            dialog.dismiss()
        }
        view.findViewById<View>(R.id.tv_a).setOnClickListener {
            selectedRisk = "A"
            filterLoanData(binding.tfSearch.text.toString())
            dialog.dismiss()
        }
        view.findViewById<View>(R.id.tv_b).setOnClickListener {
            selectedRisk = "B"
            filterLoanData(binding.tfSearch.text.toString())
            dialog.dismiss()
        }
        view.findViewById<View>(R.id.tv_c).setOnClickListener {
            selectedRisk = "C"
            filterLoanData(binding.tfSearch.text.toString())
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun filterLoanData(query: String) {
        val filteredList = loanList.filter {
            (selectedRisk == "All" || it.riskRating == selectedRisk) &&
                    (query.isEmpty() || it.borrower?.name?.contains(query, ignoreCase = true) ?: false)
        }
        loanAdapter.setData(filteredList)
        handleUiEmptyData(imageEmpty = filteredList.isEmpty(), textEmpty = filteredList.isEmpty(), recyclerView = filteredList.isNotEmpty())
    }



    private fun handleShimmerEffect(active: Boolean) {
        if (active) binding.rvLoan.showShimmer() else binding.rvLoan.hideShimmer()
    }

    private fun handleUiEmptyData(
        imageEmpty:Boolean,
        textEmpty:Boolean,
        recyclerView:Boolean
    ){
        binding.apply {
            imgEmptyBox.isVisible = imageEmpty
            tvEmpty.isVisible = textEmpty
            rvLoan.isVisible = recyclerView
        }
    }



}