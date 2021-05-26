package life.avishekworld.domain.usecase

interface UseCase

interface SuspendUseCase<In,Out> : UseCase {
    suspend fun run(param : In) : Out
}