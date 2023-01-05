package org.includejoe.instagramclone.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.includejoe.instagramclone.data.AuthenticationRepositoryImpl
import org.includejoe.instagramclone.data.UserRepositoryImpl
import org.includejoe.instagramclone.domain.repository.AuthenticationRepository
import org.includejoe.instagramclone.domain.repository.UserRepository
import org.includejoe.instagramclone.domain.use_cases.*
import org.includejoe.instagramclone.domain.use_cases.authentication.*
import org.includejoe.instagramclone.domain.use_cases.user.GetUserDetails
import org.includejoe.instagramclone.domain.use_cases.user.SetUserDetails
import org.includejoe.instagramclone.domain.use_cases.user.UserUseCases
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InstagramCloneModule {

    @Singleton
    @Provides
    fun provideFirebaseAuthentication():FirebaseAuth{
        return FirebaseAuth.getInstance()
    }

    @Singleton
    @Provides
    fun provideFirebaseFireStore():FirebaseFirestore{
        return FirebaseFirestore.getInstance()
    }

    @Singleton
    @Provides
    fun provideFirebaseStorage():FirebaseStorage{
        return FirebaseStorage.getInstance()
    }

    @Singleton
    @Provides
    fun provideAuthenticationRepository(
        auth: FirebaseAuth,
        firestore: FirebaseFirestore
    ):AuthenticationRepository {
        return AuthenticationRepositoryImpl(auth = auth, firestore = firestore)
    }

    @Singleton
    @Provides
    fun provideAuthUseCases(repository: AuthenticationRepository) = AuthenticationUseCases(
        isUserAuthenticated = IsUserAuthenticated(repository = repository),
        firebaseAuthState = FirebaseAuthState(repository = repository),
        firebaseSignOut = FirebaseSignOut(repository = repository),
        firebaseSignIn = FirebaseSignIn(repository = repository),
        firebaseSignUp = FirebaseSignUp(repository = repository)
    )

    @Singleton
    @Provides
    fun provideUserRepository(
        firebaseFirestore: FirebaseFirestore
    ): UserRepository {
        return UserRepositoryImpl(firebaseFirestore = firebaseFirestore)
    }

    @Singleton
    @Provides
    fun provideUserUseCases(repository: UserRepository) = UserUseCases(
        getUserDetails = GetUserDetails(repository = repository),
        setUserDetails = SetUserDetails(repository = repository)
    )
}