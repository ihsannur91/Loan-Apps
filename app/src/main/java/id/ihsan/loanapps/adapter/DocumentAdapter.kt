package id.ihsan.loanapps.adapter

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import id.ihsan.loanapps.R
import id.ihsan.loanapps.databinding.ItemRowDetailOrderBinding
import id.ihsan.loanapps.databinding.ItemRowDocumentBinding
import id.ihsan.loanapps.model.DocumentsItem
import id.ihsan.loanapps.utils.FunctionHelper.convertRupiahFormat

class DocumentAdapter: RecyclerView.Adapter<DocumentAdapter.DocumentViewHolder>() {

    private val diffCallBack = object  : DiffUtil.ItemCallback<DocumentsItem>(){
        override fun areItemsTheSame(oldItem: DocumentsItem, newItem: DocumentsItem): Boolean {
            return oldItem.type == newItem.type
        }

        override fun areContentsTheSame(oldItem: DocumentsItem, newItem: DocumentsItem): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this,diffCallBack)
    private var dataItem: List<DocumentsItem?>? = listOf()

    inner class DocumentViewHolder(private val binding: ItemRowDocumentBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DocumentsItem){
            binding.apply {
                tvType.text = item.type
                Glide.with(imgContent)
                    .load(item.url)
                    .centerCrop()
                    .apply(
                        RequestOptions()
                            .placeholder(R.drawable.empty_box)
                            .error(R.drawable.empty_box))
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: com.bumptech.glide.request.target.Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            Log.d("failedLoadImage","===${e}")
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: com.bumptech.glide.request.target.Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            return false
                        }
                    })
                    .into(imgContent)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DocumentAdapter.DocumentViewHolder {
        return DocumentViewHolder(ItemRowDocumentBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(
        holder: DocumentAdapter.DocumentViewHolder,
        position: Int
    ) {
        dataItem?.get(position)?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return dataItem?.size ?: 0
    }

    fun setData(list: List<DocumentsItem?>?){
        differ.submitList(list)
        dataItem = list
        notifyDataSetChanged()
    }

}