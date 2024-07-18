package top.dv.food

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import top.dv.food.databinding.ItemBinding

class FoodAdapter(private val data: ArrayList<Food>, private val foodEvent: FoodEvent) :
    RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    inner class FoodViewHolder(private val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val imgMain = binding.imgMain
        private val txtSubject = binding.txtSubject
        private val txtCity = binding.txtCity
        private val txtPrice = binding.txtPrice
        private val txtDistance = binding.txtDistance
        private val ratingBar = binding.ratingBar
        private var txtRating = binding.txtRatings

        @SuppressLint("SetTextI18n")
        fun bindData(position: Int) {
            txtSubject.text = data[position].txtSubject
            txtCity.text = data[position].txtCity
            txtPrice.text = "$" + data[position].txtPrice + " VIP"
            txtDistance.text = data[position].txtDistance + " miles from you"
            ratingBar.rating = data[position].rating
            txtRating.text = "(" + data[position].numOfRating.toString() + " Ratings" + ")"
            Glide.with(binding.root.context)
                .load(data[position].urlImage)
                .transform(RoundedCornersTransformation(16, 4))
                .into(imgMain)

            itemView.setOnClickListener {
               foodEvent.onFoodClicked(data[adapterPosition],adapterPosition)
            }
            itemView.setOnLongClickListener {
                foodEvent.onFoodLongClicked(data[adapterPosition],adapterPosition)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(binding)
    }
    override fun getItemCount(): Int = data.size
    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bindData(position)
    }

    fun addFood(newFood: Food) {
        data.add(0, newFood)
        notifyItemInserted(0)
    }
    fun removeFood(oldFood: Food, oldPositon: Int) {
        data.remove(oldFood)
        notifyItemRemoved(oldPositon)
    }
    fun updateFood(newFood:Food,position: Int){
        //update Item From List
        data[position] = newFood
        notifyItemChanged(position)
    }
    @SuppressLint("NotifyDataSetChanged")
    fun setData(newList: ArrayList<Food>){
        //setNewDataToList:
        data.clear()
        data.addAll(newList)
        notifyDataSetChanged()
    }

}