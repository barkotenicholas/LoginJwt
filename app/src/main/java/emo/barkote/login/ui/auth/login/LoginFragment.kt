package emo.barkote.login.ui.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import dagger.hilt.android.AndroidEntryPoint
import emo.barkote.login.R
import emo.barkote.login.databinding.LoginfragmentBinding


@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.loginfragment) {

    private val viewModel: LoginFragmentViewModel by viewModels()
    private lateinit var binding: LoginfragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = LoginfragmentBinding.inflate(inflater, container, false)


        binding.apply {
            emailField.apply {
                addTextChangedListener {
                    viewModel.onEvent(LoginFormEvent.EmailChanged(it.toString()))
                }

            }

            passwordField.apply {
                addTextChangedListener {
                    viewModel.onEvent(LoginFormEvent.PasswordChanged(it.toString()))
                }


            }

            textView3.apply {
                setOnClickListener {
                    viewModel.goToSignUp()
                }
            }

            button.setOnClickListener {
                binding.textInputLayout.error = ""
                binding.textInputLayout2.error = ""

                viewModel.onEvent(LoginFormEvent.Submit)

            }

        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.validationEvents.collect { event ->
                when (event) {
                    LoginFragmentViewModel.ValidationEvent.Success -> {
                        Toast.makeText(
                            context,
                            "Login SuccessFull",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    LoginFragmentViewModel.ValidationEvent.NavigateToLogin -> {
                        findNavController().navigate(R.id.action_loginFragment_to_signupFragment,null, navOptions{
                            anim {
                                enter = android.R.animator.fade_in
                                exit = android.R.animator.fade_out
                            }
                        })
                    }
                    is LoginFragmentViewModel.ValidationEvent.NavigateToHome -> {
                        val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment(event.login)
                        findNavController().navigate(action)
                    }
                    is LoginFragmentViewModel.ValidationEvent.InvalidPassword -> {
                        binding.apply {
                            textInputLayout2.apply {
                                error = event.message
                            }
                        }
                    }
                    is LoginFragmentViewModel.ValidationEvent.Unexpected -> {
                        Toast.makeText(context,event.message,Toast.LENGTH_SHORT).show()
                    }
                    is LoginFragmentViewModel.ValidationEvent.UserNotFound -> {
                        binding.apply {
                            textInputLayout.apply {
                                error = event.message
                            }
                        }
                    }
                    is LoginFragmentViewModel.ValidationEvent.ShowEmailError -> {
                        binding.apply {
                            textInputLayout.apply {
                                error = event.msg
                            }
                        }
                    }
                    is LoginFragmentViewModel.ValidationEvent.ShowPasswordError -> {
                        binding.apply {
                            textInputLayout2.apply {
                                error = event.msg
                            }
                        }
                    }
                }
            }
        }

        return binding.root
    }



}