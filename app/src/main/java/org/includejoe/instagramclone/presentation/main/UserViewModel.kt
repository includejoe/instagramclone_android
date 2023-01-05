package org.includejoe.instagramclone.presentation.main

import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.includejoe.instagramclone.domain.model.User
import org.includejoe.instagramclone.domain.use_cases.user.UserUseCases
import org.includejoe.instagramclone.util.Response
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val userUseCases: UserUseCases
): ViewModel() {
    private val userId = auth.currentUser?.uid

    private val _getUserDetails = mutableStateOf<Response<User?>>(Response.Success(null))
    val getUserDetails: State<Response<User?>> = _getUserDetails

    private val _setUserDetails = mutableStateOf<Response<Boolean>>(Response.Success(false))
    val setUserDetails: State<Response<Boolean>> = _setUserDetails

    fun getUserInfo() {
        if(userId != null) {
            viewModelScope.launch {
                userUseCases.getUserDetails(userId = userId).collect {
                    _getUserDetails.value = it
                }
            }
        }
    }

    fun setUserInfo(
        name: String,
        username: String,
        bio: String,
        url: String
    ) {
        if(userId != null) {
            viewModelScope.launch {
                userUseCases.setUserDetails(
                    userId = userId,
                    name = name,
                    username = username,
                    bio = bio,
                    url = url
                ).collect {
                    _setUserDetails.value = it
                }
            }
        }
    }
}