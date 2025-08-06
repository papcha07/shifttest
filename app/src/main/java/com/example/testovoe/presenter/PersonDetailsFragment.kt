package com.example.testovoe.presenter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.testovoe.R
import com.example.testovoe.databinding.FragmentPersonDetailsBinding
import com.example.testovoe.domain.models.PersonPreview
import org.koin.androidx.viewmodel.ext.android.viewModel

class PersonDetailsFragment : Fragment() {

    private lateinit var binding: FragmentPersonDetailsBinding
    private val personViewModel: PersonViewModel by viewModel()
    private lateinit var personPreview: PersonPreview
    private val args: PersonDetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        personPreview = args.personArg
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPersonDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populateViews(personPreview)
        setupClickListeners(personPreview)
        goBack()
    }

    private fun populateViews(person: PersonPreview) {
        binding.apply {
            Glide.with(requireContext())
                .load(person.thumbnail)
                .circleCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(personThumbnail)

            personFullName.text = "${person.first} ${person.last}"

            personPhone.text = person.phone
            personEmail.text = person.email

            val fullAddress =
                "${person.streetNumber} ${person.streetName}, ${person.city}, ${person.country}"
            personAddress.text = fullAddress
        }
    }


    private fun setupClickListeners(person: PersonPreview) {
        binding.personPhone.setOnClickListener {
            personViewModel.openPhone(person.phone)
        }

        binding.personEmail.setOnClickListener {
            personViewModel.openEmail(person.email)
        }

        binding.personAddress.setOnClickListener {
            val addressString = binding.personAddress.text.toString()
            personViewModel.openMap(addressString)
        }
    }

    private fun goBack() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
    }
}