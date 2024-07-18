package top.dv.food

interface FoodEvent {
    fun onFoodClicked(food: Food,position:Int)
    fun onFoodLongClicked(food: Food,position:Int)
}