package com.adenion.testomdb.homeModule

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

        with(mBinding){
            tvPhoneNum.setOnClickListener {
                dial("+57 3188860026")
            }

            tvEmail.setOnClickListener {
                composeEmail(arrayOf("Ceps32@hotmail.com"), R.string.email_subject)
                //goToWebSite("Ceps32@hotmail.com")
            }

            tvLinkedin.setOnClickListener {
                goToWebSite("https://www.linkedin.com/in/carlos-pineda-s")
            }
        }

    }

    private fun dial (phone:String){
        val callIntent = Intent().apply {
            action = Intent.ACTION_DIAL
            data = Uri.parse("tel: $phone")
        }
        startIntent(callIntent)
    }

    private fun goToWebSite(website: String){
        if (website.isEmpty()) {
            Toast.makeText(requireContext(), R.string.main_error_no_website, Toast.LENGTH_LONG).show()
        } else{
            val websiteIntent =Intent().apply {
                action = Intent.ACTION_VIEW
                data = Uri.parse(website)
            }

            startIntent(websiteIntent)
        }
    }

    fun composeEmail(addresses: Array<String>, subject: Int) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:") // only email apps should handle this
            putExtra(Intent.EXTRA_EMAIL, addresses)
            putExtra(Intent.EXTRA_SUBJECT, subject)
        }
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(intent)
        } else
            Toast.makeText(requireContext(), R.string.main_error_no_resolve, Toast.LENGTH_LONG).show()
    }

    private fun startIntent(intent: Intent){
        if (intent.resolveActivity(requireContext().packageManager) != null)
            startActivity(intent)
        else
            Toast.makeText(requireContext(), R.string.main_error_no_resolve, Toast.LENGTH_LONG).show()
    }

}