package com.example.cattlelog.herd_member_details

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.example.cattlelog.R
import com.example.cattlelog.model.database.CattlelogDatabase

/**
 * A simple [Fragment] subclass.
 */
class OverviewFragment : Fragment() {

    private var herdMemberTagNumber: Int = -1
    private var herdMemberBirthDate: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        herdMemberTagNumber = (activity as HerdMemberDetails).getTagNumber()
        herdMemberBirthDate = (activity as HerdMemberDetails).getBirthDate()
        Toast.makeText(context, "OverviewFragment received: $herdMemberTagNumber", Toast.LENGTH_LONG).show()


        // Use of "activity!!" is required to guarantee that "activity" will never be null. Calling with "activity" does not work.
        AsyncTask.execute {
            Log.d(
                "TEST QUERY IN FRAGMENT",
                "OVERVIEW Test Query: " + CattlelogDatabase.getDatabase(activity!!).cattleDao().getUniqueHerdMember(
                    herdMemberTagNumber, herdMemberBirthDate
                )
            )
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_overview, container, false)
    }
}