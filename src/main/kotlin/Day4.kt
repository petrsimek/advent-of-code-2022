import java.io.File

/**
 * Advent of Code 2022 - Day 4 - https://adventofcode.com/2022/day/4
 */
object Day4 {

    val inputList: List<String> = File("src/main/resources/input4.txt").readText(Charsets.UTF_8).split("\n")

    private fun toRanges(inputList: List<String>): List<Pair<IntRange, IntRange>> {
        return inputList.map {
            val (first1, first2, second1, second2) = Regex("""(\d+)-(\d+),(\d+)-(\d+)""").find(it)!!.destructured
            Pair(IntRange(first1.toInt(), first2.toInt()), IntRange(second1.toInt(), second2.toInt()))
        }
    }

    private fun partOne(inputList: List<String>): Int {
        return toRanges(inputList).map {
            if (it.first.first >= it.second.first && it.first.last <= it.second.last
                || it.second.first >= it.first.first && it.second.last <= it.first.last
            ) 1 else 0
        }.sum()
    }

    private fun partTwo(inputList: List<String>): Int {
        return toRanges(inputList).map {
            if (it.first.intersect(it.second).isNotEmpty()) 1 else 0
        }.sum()
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println("Part 1: " + partOne(inputList))
        println("Part 2: " + partTwo(inputList))
    }
}