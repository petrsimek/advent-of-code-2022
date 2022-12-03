import java.io.File

object Day3 {

    val inputList: List<String> = File("src/main/resources/input3.txt").readText(Charsets.UTF_8).split("\n")

    private fun toPriorities(rucksacks: List<String>): List<List<Int>> {
        return rucksacks.map {
            it.trim().toList().map { if (it.isLowerCase()) it.code - 96 else it.code - 38 }
        }.toList()
    }

    private fun partOne(inputList: List<String>): Int {
        return toPriorities(inputList).map {
            val halves = it.chunked(it.size / 2)
            halves[0].intersect(halves[1]).first()
        }.toList().sum()
    }

    private fun partTwo(inputList: List<String>): Int {
        return toPriorities(inputList).withIndex().groupBy { it.index / 3 }.values.map {
            it[0].value.toList().intersect(it[1].value.toList()).intersect(it[2].value.toList()).first()
        }.sum()
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println("Part 1: " + partOne(inputList))
        println("Part 2: " + partTwo(inputList))
    }
}