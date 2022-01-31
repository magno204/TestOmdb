package com.adenion.testomdb.homeModule

import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adenion.testomdb.R
import com.adenion.testomdb.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var mBinding: FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.tvAbout.text = Html.fromHtml("<span styles=\"color:black\">Programador de software con 10 años en </span><b>backend C#</b> usando frameworks como <b>.NET 5</b> y bases de datos SQL Server.<br>" +
                "He construido API’s que funcionan en Frontend de <b>Android, Winforms, iOS y Angular.</b>" +
                "Tengo experiencia en <b>desarrollo Android</b> con lenguaje <b>Kotlin</b>, <b>iOS</b> con <b>Swift</b>, <b>Angular 8+</b> y <b>Typescript</b>." +
                "Realicé proyectos para optimizar los procesos de calidad de producto en una empresa dedicada a la fabricación de empaques, he enseñado programación en lenguaje C# tanto a empresas como a particulares. Tengo el deseo de seguir creciendo como desarrollador, especialmente en el campo del Blockchain y desarrollo móvil.")
    }

}