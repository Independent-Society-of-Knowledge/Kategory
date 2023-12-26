package org.isk.kategory

data class Category(
    val objects: Set<Object>,
    val initialMorphisms: Set<Morphism>
) {
    private val morphismsByRank: MutableList<Set<Morphism>> = mutableListOf(initialMorphisms)
    private var currentMorphisms: Set<Morphism> = initialMorphisms

    init {
        morphismsByRank.add(initialMorphisms)
    }

    fun getNextCompositions(): Unit {
        val newMorphisms: Set<Morphism> = currentMorphisms compositionsOf objects
        morphismsByRank.add(newMorphisms)
        currentMorphisms += newMorphisms
    }

    fun getMorphismsOfObject(obj: Object): Set<Morphism> {
         return currentMorphisms.filter {
            obj in it.objects
        }.toSet()
    }
    fun getCurrentMorphisms(): Set<Morphism> = currentMorphisms
    fun getMorphismsByRank(rank: Int): Set<Morphism> = morphismsByRank[rank]

}

fun main() {
    val a = Object()
    val b = Object()
    val c = Object()
    val d = Object()

    Category(
        setOf(a, b, c), setOf(
            Morphism(a to b),
            Morphism(b to c),
            Morphism(a to d),
            Morphism(c to d)
        )
    )
}

// 
