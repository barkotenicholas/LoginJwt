package emo.barkote.login.ui.auth.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import emo.barkote.login.R
import emo.barkote.login.databinding.SignupfragmentBinding

@AndroidEntryPoint
class SignupFragment : Fragment() {

    private lateinit var binding: SignupfragmentBinding
    private val viewModel :SignupViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SignupfragmentBinding.inflate(layoutInflater,container,false)
        val items = listOf("admin","user","moderator")
        val adapters = ArrayAdapter(requireContext(), R.layout.list_item, items)


        binding.apply {

            email.apply {
                addTextChangedListener {
                    viewModel.onEvent(SignupFormEvent.EmailChanged(it.toString()))
                }
            }
            password.apply {
                addTextChangedListener {
                    viewModel.onEvent(SignupFormEvent.PasswordChanged(it.toString()))
                }
            }
            username.apply {
                addTextChangedListener {
                    viewModel.onEvent(SignupFormEvent.UserNameChanged(it.toString()))
                }
            }
            role.apply {
                addTextChangedListener {
                    viewModel.onEvent(SignupFormEvent.RoleChanged(it.toString()))
                }
                setAdapter(adapters)
                threshold = 1
            }

            signUp.apply {
                setOnClickListener {
                    viewModel.onEvent(SignupFormEvent.Submit)
                }
            }

        }


        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.validationEvents.collect{ event->
                when(event){
                    is SignupViewModel.ValidationEvent.ShowEmailError -> {
                        binding.apply {
                            textInputLayout3.apply {
                                error = event.msg
                            }
                        }
                    }
                    is SignupViewModel.ValidationEvent.ShowPasswordError -> {
                        binding.apply {
                            textInputLayout4.apply {
                                error = event.msg
                            }
                        }
                    }
                    is SignupViewModel.ValidationEvent.ShowRole -> {
                        binding.apply {
                            textInputLayout6.apply {
                                error = event.msg
                            }
                        }
                    }
                    is SignupViewModel.ValidationEvent.ShowUserNameError -> {
                        binding.apply {
                            textInputLayout5.apply {
                                error = event.msg
                            }
                        }
                    }
                    is SignupViewModel.ValidationEvent.Success -> {
                        Toast.makeText(context,event.msg,Toast.LENGTH_LONG).show()
                    }
                    is SignupViewModel.ValidationEvent.ShowSignUpError -> {
                        Toast.makeText(context,event.msg,Toast.LENGTH_SHORT).show()
                    }
                    SignupViewModel.ValidationEvent.SuccessGoToLogin -> {
                        val action = SignupFragmentDirections.actionSignupFragmentToLoginFragment()
                        findNavController().navigate(action)
                    }
                }
            }
        }

        return binding.root
    }

}