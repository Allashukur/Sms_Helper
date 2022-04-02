package com.example.smshelper.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.smshelper.R
import com.example.smshelper.adapter.Adapter_Date
import com.example.smshelper.data_base_room.database.AppDataBase
import com.example.smshelper.databinding.FragmentDateBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DateFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DateFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var binding: FragmentDateBinding
    lateinit var adapter: Adapter_Date
    lateinit var appDataBase: AppDataBase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDateBinding.inflate(inflater, container, false)

        appDataBase = AppDataBase.getInstanse(requireContext())
        adapter = Adapter_Date(object : Adapter_Date.OnClickListenerDate {
            override fun onClickDate(dateId: Int) {
                var bundle = Bundle()
                bundle.putInt("date", dateId)
                Navigation.findNavController(requireView()).navigate(R.id.provinceFragment, bundle)
            }
        })

        appDataBase.getDateDao().allDate()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                adapter.submitList(it)
            })
        binding.rv.adapter = adapter




        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DateFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DateFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}