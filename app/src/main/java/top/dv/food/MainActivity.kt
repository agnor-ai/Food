package top.dv.food

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import top.dv.food.databinding.ActivityMainBinding
import top.dv.food.databinding.DialogAddNewItemBinding
import top.dv.food.databinding.DialogDeleteFoodBinding
import top.dv.food.databinding.DialogUpdateFoodBinding

@Suppress("UNCHECKED_CAST")
class MainActivity: AppCompatActivity(),FoodEvent {
    private lateinit var binding: ActivityMainBinding
    private lateinit var myAdapter: FoodAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val foodList = arrayListOf(
            Food("Vegetable Salad","15","3","Zahedan","https://images.pexels.com/photos/1640777/pexels-photo-1640777.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",20,4.5f),
            Food("Pasta With Tomato","12","6","Tehran","https://images.pexels.com/photos/1279330/pexels-photo-1279330.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",6,5f),
            Food("Burger","16","12","Mashhad","https://images.pexels.com/photos/1199960/pexels-photo-1199960.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",26,2.5f),
            Food("Steak Food","21","3","Esfahan","https://images.pexels.com/photos/769289/pexels-photo-769289.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",35,3.5f),
            Food("Fries With Leaves Dish","20","3","Shiraz","https://images.pexels.com/photos/718742/pexels-photo-718742.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",1,4f),
            Food("Fried Potatoes","6","3","Kerman","https://images.pexels.com/photos/1583884/pexels-photo-1583884.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",48,1.5f),
            Food("Sandwich And Slice Of Lemons","15","3","Tabriz","https://images.pexels.com/photos/1603901/pexels-photo-1603901.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",20,3f),
            Food("Grilled Barbecue","11","10","Yazd","https://images.pexels.com/photos/3186654/pexels-photo-3186654.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",35,2f),
            Food("Macaroni","9","2.5","Karaj","https://images.pexels.com/photos/1437267/pexels-photo-1437267.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",78,5f),
            Food("Vegetable Salad","21","3","Sari","https://images.pexels.com/photos/3026808/pexels-photo-3026808.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",24,4.5f),
        )
        myAdapter = FoodAdapter(foodList.clone() as ArrayList<Food>,this)
        binding.recyclerMain.adapter = myAdapter
        binding.recyclerMain.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        binding.btnAddNewFood.setOnClickListener {
            val dialog = AlertDialog.Builder(this).create()
            val dialogBinding = DialogAddNewItemBinding.inflate(layoutInflater)
            dialog.setView(dialogBinding.root)
            dialog.setCancelable(true)
            dialog.show()
            dialogBinding.btnDialogDone.setOnClickListener {
                if (dialogBinding.dialogEdtFoodName.length() > 0 &&
                    dialogBinding.dialogEdtFoodPrice.length() > 0 &&
                    dialogBinding.dialogEdtFoodDistance.length() > 0 &&
                    dialogBinding.dialogEdtFoodCity.length() > 0
                    ){
                    val txtName = dialogBinding.dialogEdtFoodName.text.toString()
                    val txtPrice = dialogBinding.dialogEdtFoodPrice.text.toString()
                    val txtDistance = dialogBinding.dialogEdtFoodDistance.text.toString()
                    val txtCity = dialogBinding.dialogEdtFoodCity.text.toString()
                    val ratingNumber:Int = (1 until 150).random()
                    val ratingBarStar:Float = (1..5).random().toFloat()
                    val randomForUrl = (1..9).random()
                    val urlPic = foodList[randomForUrl].urlImage

                    val newFood = Food(txtName,txtPrice,txtDistance,txtCity,urlPic,ratingNumber,ratingBarStar)
                    myAdapter.addFood(newFood)
                    binding.recyclerMain.scrollToPosition(0)
                    dialog.dismiss()
                }else{
                    Toast.makeText(this, "لطفا همه مقادیر را پر کنید", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.edtSearch.addTextChangedListener {editTextInput->
            if (editTextInput!!.isNotEmpty()){
                //filter data
                val cloneList = foodList.clone() as ArrayList<Food>
                val filteredList = cloneList.filter { foodItem->
                    foodItem.txtSubject.contains(editTextInput)
                }
                myAdapter.setData(ArrayList(filteredList))
            }else{
                //show all data
                myAdapter.setData(foodList.clone() as ArrayList<Food>)
            }
        }
    }

    override fun onFoodClicked(food: Food,position:Int) {
        val dialog = AlertDialog.Builder(this).create()
        val updateDialogBinding = DialogUpdateFoodBinding.inflate(layoutInflater)
        dialog.setView(updateDialogBinding.root)
        dialog.setCancelable(true)
        dialog.show()
        
        updateDialogBinding.dialogEdtFoodName.setText(food.txtSubject)
        updateDialogBinding.dialogEdtFoodCity.setText(food.txtCity)
        updateDialogBinding.dialogEdtFoodPrice.setText(food.txtPrice)
        updateDialogBinding.dialogEdtFoodDistance.setText(food.txtDistance)
        
        updateDialogBinding.btnDialogEdtCancle.setOnClickListener {
            dialog.dismiss()
        }

        updateDialogBinding.btnDialogEdtDone.setOnClickListener {
            dialog.dismiss()
            if (updateDialogBinding.dialogEdtFoodName.length() > 0 &&
                updateDialogBinding.dialogEdtFoodPrice.length() > 0 &&
                updateDialogBinding.dialogEdtFoodDistance.length() > 0 &&
                updateDialogBinding.dialogEdtFoodCity.length() > 0
            ){val txtName = updateDialogBinding.dialogEdtFoodName.text.toString()
                val txtPrice = updateDialogBinding.dialogEdtFoodPrice.text.toString()
                val txtDistance = updateDialogBinding.dialogEdtFoodDistance.text.toString()
                val txtCity = updateDialogBinding.dialogEdtFoodCity.text.toString()
                //create new food to uptade food
                val newFood = Food(txtName,txtPrice,txtDistance,txtCity,food.urlImage,food.numOfRating,food.rating)
                //update Item
                myAdapter.updateFood(newFood,position)
            }else{
                Toast.makeText(this, "لطفا همه مقادیر را پر کنید", Toast.LENGTH_SHORT).show()
            }
            
        }

    }

    override fun onFoodLongClicked(food: Food,position:Int) {
        val dialog = AlertDialog.Builder(this).create()
        val dialogDeleteBinding= DialogDeleteFoodBinding.inflate(layoutInflater)
        dialog.setView(dialogDeleteBinding.root)
        dialog.setCancelable(true)
        dialog.show()
        dialogDeleteBinding.dialogBtnCancel.setOnClickListener{
            dialog.dismiss()
        }
        dialogDeleteBinding.dialogBtnDelete.setOnClickListener {
            dialog.dismiss()
            myAdapter.removeFood(food,position)
        }
    }
}