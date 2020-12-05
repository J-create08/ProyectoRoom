package com.loguito.clase6.views.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.loguito.clase6.R
import kotlinx.android.synthetic.main.fragment_detail_character.*

class DetailCharacterFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_character, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button.setOnClickListener {

            if (radioGroup.checkedRadioButtonId == R.id.femaleRadioButton) {
                Log.d("RADIOBUTTON", "Femenino seleccionado")
            } else {
                Log.d("RADIOBUTTON", "Masculino seleccionado")
            }
        }
    }
}