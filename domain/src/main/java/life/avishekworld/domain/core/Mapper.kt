package life.avishekworld.domain.core

interface Mapper<In, Out> {
    fun map(input : In) : Out
}