package com.example.testfirebase

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.testfirebase.ListView.Companion.data
import com.example.testfirebase.data.contact
import com.example.testfirebase.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var db: FirebaseFirestore
    lateinit var process: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        process = ProgressDialog(this)
        process.setCancelable(false)
        process.setMessage("Adding Data..")


//======================================================================================
        db = Firebase.firestore
//======================================================================================
        val i = Intent(this, ListView::class.java)
//======================================================================================
        binding.show.setOnClickListener {
            startActivity(i)
        }
//======================================================================================
        binding.save.setOnClickListener {
            val name = binding.Name.text.toString()
            val number = binding.Number.text.toString()
            val address = binding.Address.text.toString()
            if (name.isEmpty() || number.isEmpty() || address.isEmpty()) {
                Toast.makeText(this, "FILL DATA !!", Toast.LENGTH_SHORT).show()
            } else {
                addToDB(name, number, address)
                process.show()
                binding.Name.text.clear()
                binding.Number.text.clear()
                binding.Address.text.clear()
            }
        }
    }


    fun addToDB(name: String, number: String, address: String) {
        val user = hashMapOf("name" to name, "number" to number, "address" to address)

        db.collection("users").add(user).addOnSuccessListener {
            Toast.makeText(this, "sucsess ${it.id}", Toast.LENGTH_SHORT).show()
            if (process.isShowing) {
                process.dismiss()
            }
            Log.e("Erorr", "sucsess")
        }.addOnFailureListener {
            Toast.makeText(this, "faild ${it.message}", Toast.LENGTH_SHORT).show()
            Log.e("zzzzz", "faild ${it.message}")
        }
    }


}