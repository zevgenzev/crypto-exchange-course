package ndd.com.cryptocurrencyexchange.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ndd.com.cryptocurrencyexchange.R
import ndd.com.cryptocurrencyexchange.databinding.ItemCoinInfoBinding
import ndd.com.cryptocurrencyexchange.pojo.CoinPriceInfo

class CoinInfoAdapter(private val context: Context) :
    RecyclerView.Adapter<CoinInfoAdapter.CoinInfoViewHolder>() {

    var coinInfoList: List<CoinPriceInfo> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var onCoinClickListener: OnCoinClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_coin_info, parent, false)
        return CoinInfoViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val coin = coinInfoList[position]
        val symbolsTemplate = context.resources.getString(R.string.symbols_template)
        val lastUpdateTemplate = context.resources.getString(R.string.last_update_template)
        holder.tvSymbols.text = String.format(symbolsTemplate, coin.fromSymbol, coin.toSymbol)
        holder.tvCurrency.text = coin.price
        holder.tvLastUpdate.text = String.format(lastUpdateTemplate, coin.getFormattedTime())
        Picasso.get().load(coin.getFullImageUrl()).into(holder.ivLogoCoin)
        holder.itemView.setOnClickListener {
            onCoinClickListener?.onCoinClick(coin)
        }
    }

    override fun getItemCount() = coinInfoList.size

    inner class CoinInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemCoinInfoBinding.bind(itemView)
        val ivLogoCoin = binding.ivLogoCoin
        val tvSymbols = binding.tvSymbols
        val tvCurrency = binding.tvCurrency
        val tvLastUpdate = binding.tvLastUpdate
//        val ivLogoCoin: ImageView = itemView.findViewById(R.id.ivLogoCoin)
//        val tvSymbols: TextView = itemView.findViewById(R.id.tvSymbols)
//        val tvCurrency: TextView = itemView.findViewById(R.id.tvCurrency)
//        val tvLastUpdate: TextView = itemView.findViewById(R.id.tvLastUpdate)
    }

    interface OnCoinClickListener {
        fun onCoinClick(coinPriceInfo: CoinPriceInfo)
    }
}