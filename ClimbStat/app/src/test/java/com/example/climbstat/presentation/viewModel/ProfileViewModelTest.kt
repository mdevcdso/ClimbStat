package com.example.climbstat.presentation.viewModel

import com.example.climbstat.domain.model.ClimbingGym
import com.example.climbstat.domain.model.DifficultyTier
import com.example.climbstat.domain.model.Topo
import com.example.climbstat.domain.repository.ClimbingGymRepository
import com.example.climbstat.domain.repository.TopoRepository
import com.example.climbstat.domain.usecase.state.ProfileUiState
import com.example.climbstat.utils.TokenManagerUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class ProfileViewModelTest {

    private lateinit var tokenManager: TokenManagerUtils
    private lateinit var topoRepository: TopoRepository
    private lateinit var climbingGymRepository: ClimbingGymRepository
    private lateinit var viewModel: ProfileViewModel

    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        tokenManager = mock()
        topoRepository = mock()
        climbingGymRepository = mock()
        viewModel = ProfileViewModel(tokenManager, topoRepository, climbingGymRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadProfile sets Error when userName is null`() = runTest {
        whenever(tokenManager.getUserName()).thenReturn(null)

        viewModel.loadProfile()
        advanceUntilIdle()

        val state = viewModel.profileUiState.value
        assertTrue(state is ProfileUiState.Error)
        assertEquals("Utilisateur non connecté", (state as ProfileUiState.Error).message)
    }

    @Test
    fun `loadProfile sets Error when repository fails`() = runTest {
        whenever(tokenManager.getUserName()).thenReturn("Marion")
        whenever(topoRepository.getUserTopos())
            .thenReturn(Result.failure(RuntimeException("network down")))

        viewModel.loadProfile()
        advanceUntilIdle()

        val state = viewModel.profileUiState.value
        assertTrue(state is ProfileUiState.Error)
        assertEquals("network down", (state as ProfileUiState.Error).message)
    }

    @Test
    fun `loadProfile computes sessions count from distinct dates`() = runTest {
        whenever(tokenManager.getUserName()).thenReturn("Marion")
        whenever(topoRepository.getUserTopos()).thenReturn(
            Result.success(
                listOf(
                    topo(attemptDate = "2026-04-15T10:00:00Z"),
                    topo(attemptDate = "2026-04-15T18:30:00Z"),
                    topo(attemptDate = "2026-04-16T09:00:00Z")
                )
            )
        )
        whenever(climbingGymRepository.fetchClimbingGyms())
            .thenReturn(Result.success(emptyList()))

        viewModel.loadProfile()
        advanceUntilIdle()

        val state = viewModel.profileUiState.value as ProfileUiState.Success
        assertEquals(2, state.sessionsCount)
    }

    @Test
    fun `loadProfile buckets tops by tier from boulder color`() = runTest {
        whenever(tokenManager.getUserName()).thenReturn("Marion")
        whenever(topoRepository.getUserTopos()).thenReturn(
            Result.success(
                listOf(
                    topo(boulderDifficulty = "jaune"),
                    topo(boulderDifficulty = "vert"),
                    topo(boulderDifficulty = "bleu"),
                    topo(boulderDifficulty = "noir"),
                    topo(boulderDifficulty = "noir")
                )
            )
        )
        whenever(climbingGymRepository.fetchClimbingGyms())
            .thenReturn(Result.success(emptyList()))

        viewModel.loadProfile()
        advanceUntilIdle()

        val state = viewModel.profileUiState.value as ProfileUiState.Success
        assertEquals(2, state.topsByTier[DifficultyTier.EASY])
        assertEquals(1, state.topsByTier[DifficultyTier.MEDIUM])
        assertEquals(2, state.topsByTier[DifficultyTier.HARD])
    }

    @Test
    fun `loadProfile keeps only gyms present in user topos`() = runTest {
        whenever(tokenManager.getUserName()).thenReturn("Marion")
        whenever(topoRepository.getUserTopos()).thenReturn(
            Result.success(
                listOf(
                    topo(boulderGymId = "gym1"),
                    topo(boulderGymId = "gym2"),
                    topo(boulderGymId = null)
                )
            )
        )
        whenever(climbingGymRepository.fetchClimbingGyms()).thenReturn(
            Result.success(
                listOf(gym("gym1"), gym("gym2"), gym("gym3"))
            )
        )

        viewModel.loadProfile()
        advanceUntilIdle()

        val state = viewModel.profileUiState.value as ProfileUiState.Success
        assertEquals(listOf("gym1", "gym2"), state.visitedGyms.map { it.id })
    }

    @Test
    fun `logout clears token`() {
        viewModel.logout()
        verify(tokenManager).clearToken()
    }

    private fun topo(
        id: String = "t1",
        attemptDate: String = "2026-04-15T10:00:00Z",
        boulderDifficulty: String? = "jaune",
        boulderGymId: String? = null
    ) = Topo(
        id = id,
        userId = "u1",
        userName = "Marion",
        attemptDate = attemptDate,
        idBoulder = "b1",
        boulderDifficulty = boulderDifficulty,
        boulderGymId = boulderGymId,
        isFlash = false,
        nbAttempts = 1,
        comment = ""
    )

    private fun gym(id: String) = ClimbingGym(
        id = id,
        address = "",
        clossingHours = "",
        cotationType = "",
        createdAt = "",
        description = "",
        franchise = "",
        image = "",
        location = "",
        name = "Gym $id",
        openingHours = "",
        tags = emptyList(),
        updatedAt = ""
    )
}
