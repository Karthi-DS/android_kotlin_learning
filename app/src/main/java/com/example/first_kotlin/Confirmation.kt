package com.example.first_kotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.activityViewModels
import com.example.first_kotlin.db.DatabaseHelper
import com.example.first_kotlin.sharedModel.SharedViewModel



// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Confirmation.newInstance] factory method to
 * create an instance of this fragment.
 */
class Confirmation : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val viewModel: SharedViewModel by activityViewModels()

    private lateinit var db : DatabaseHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_confirmation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.emailC).text = "Your Email: " + viewModel.formData.email
        view.findViewById<TextView>(R.id.nameC).text = "Your Name: " + viewModel.formData.name
        view.findViewById<TextView>(R.id.passwordC).text = "Your Password: " + viewModel.formData.password
        view.findViewById<TextView>(R.id.genderC).text = "Your Gender: " + viewModel.formData.gender
        view.findViewById<TextView>(R.id.cityC).text = "Your City: " + viewModel.formData.city
        view.findViewById<TextView>(R.id.paymentC).text = "Your Payment Method: " + viewModel.formData.payment
        view.findViewById<TextView>(R.id.bankC).text = "Your Bank Name: " + viewModel.formData.bank

        val submit = view.findViewById<Button>(R.id.submit)

        db = DatabaseHelper(requireContext())

        submit.setOnClickListener {
            db.insertUserData(viewModel.formData)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Confirmation.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Confirmation().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}