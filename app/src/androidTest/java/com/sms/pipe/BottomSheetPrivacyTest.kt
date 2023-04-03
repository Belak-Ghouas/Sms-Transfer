package com.sms.pipe

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sms.pipe.domain.usecases.*
import com.sms.pipe.view.MainActivityViewModel
import com.sms.pipe.view.login.PrivacyPoliciesBottomSheet
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module


@RunWith(AndroidJUnit4::class)
class BottomSheetPrivacyTest {

    @MockK
    lateinit var getLoggedUserUseCase: GetLoggedUserUseCase

    @MockK
    lateinit var refreshUserDataUseCase: RefreshUserDataUseCase

    @MockK
    lateinit var deleteAppletUseCase: DeleteAppletUseCase

    @MockK
    lateinit var getAppletsUseCase: GetAppletsUseCase

    @MockK
    lateinit var getUserToken: GetUserTokenUseCase

    @MockK
    lateinit var didUserReadPrivacyPolicyUseCase: DidUserReadPrivacyPolicyUseCase

    @MockK
    lateinit var onTermsAcceptedUseCase: OnTermsAcceptedUseCase

    @MockK
    lateinit var needToShowInAppReview: NeedToShowInAppReview

    @MockK
    lateinit var onReviewSuccessUseCase: OnReviewSuccessUseCase

    private val mainActivityViewModel: MainActivityViewModel by lazy {
        spyk(
            MainActivityViewModel(
                getLoggedUserUseCase,
                refreshUserDataUseCase,
                deleteAppletUseCase,
                getAppletsUseCase,
                getUserToken,
                didUserReadPrivacyPolicyUseCase,
                onTermsAcceptedUseCase,
                needToShowInAppReview,
                onReviewSuccessUseCase
            )
        )
    }
    private val mainModule = module {
        viewModel { mainActivityViewModel }
    }

    init {
        MockKAnnotations.init(this)
    }

    @Before
    fun setup() = runBlocking {
        loadKoinModules(mainModule)
    }

    @Test
    fun testNoVehicleDashboard() {
        launchFragmentInContainer(themeResId = R.style.AppBottomSheetDialogTheme) {
            PrivacyPoliciesBottomSheet()
        }.use {
            it.moveToState(Lifecycle.State.CREATED)
            it.moveToState(Lifecycle.State.RESUMED)
            assert(true)
        }
    }
}