package org.isk.kategory

data class Morphism(
    val objects: Pair<Object, Object>
) {
    val id = getID.incrementAndGet()
    fun domain(): Object = objects.first
    fun codomain(): Object = objects.second

    fun isIdentity(): Boolean = objects.second == objects.first


    fun isDistinct(other: Morphism): Boolean = this.objects.takeIf {
        it.first in other.objects && it.second in other.objects
    } == null

    operator fun contains(obj: Object): Boolean = obj in this.objects

    fun isParallelWith(other: Morphism): Boolean = this.objects.first in other && this.objects.second in other

    fun isLeaving(other: Object): Boolean = this.domain() == other
    fun isEntering(other: Object): Boolean = this.codomain() == other

    fun isCompatibleWith(other: Morphism): Boolean = this.domain() == other.codomain()

}

operator fun <A> Pair<A, A>.contains(second: A): Boolean = this.first == second || this.second == second
infix fun Set<Morphism>.compositionsOf(objects: Collection<Object>): Set<Morphism> =
    objects.map { obj ->
        val coDomains = this.filter {
            obj == it.domain() && obj != it.codomain()
        }.map { it.codomain() }

        val domains = this.filter {
            obj == it.codomain() && obj != it.domain()
        }.map { it.domain() }

        domains.flatMap { obj ->
            List(coDomains.size) { obj }.zip(coDomains)
        }.map {
            Morphism(it)
        }
    }.flatten().filter {
        !it.isIdentity()
    }.toSet()


