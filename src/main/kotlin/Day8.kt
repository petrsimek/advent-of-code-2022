import java.io.File

/**
 * Advent of Code 2022 - Day 8 - https://adventofcode.com/2022/day/8
 */
object Day8 {

    val inputList = File("src/main/resources/input8.txt").readText(Charsets.UTF_8)

    private fun getGrid(input: String): List<Triple<Int, Int, Char>> {
        return input.split("\n").mapIndexed { row, s ->
            s.toList().mapIndexed { col, valu ->
                Triple(row, col, valu)
            }
        }.flatten()
    }


    private fun partOne(input: String): Int {
        val dots = getGrid(input)
        val maxRow = dots.map { it.first }.max()
        val maxCol = dots.map { it.second }.max()
        return dots.map {
            val borderVisible = it.first == 0 || it.second == 0 || it.first == maxRow || it.second == maxCol
            val rightVisible = dots.filter { it2 -> it2.first == it.first && it2.second > it.second && it2.third >= it.third }.isEmpty()
            val leftVisible = dots.filter { it2 -> it2.first == it.first && it2.second < it.second && it2.third >= it.third }.isEmpty()
            val topVisible = dots.filter { it2 -> it2.first < it.first && it2.second == it.second && it2.third >= it.third }.isEmpty()
            val bottomVisible = dots.filter { it2 -> it2.first > it.first && it2.second == it.second && it2.third >= it.third }.isEmpty()

            if (borderVisible || rightVisible || leftVisible || topVisible || bottomVisible) 1 else 0
        }.sum()
    }

    private fun partTwo(input: String): Int {
        val dots = getGrid(input)
        val maxRow = dots.map { it.first }.max()
        val maxCol = dots.map { it.second }.max()
        return dots
            .filter {
                !(it.first == 0 || it.second == 0 || it.first == maxRow || it.second == maxCol)
            }
            .map {
                val bottomIoF = dots.filter { it2 -> it2.first > it.first && it2.second == it.second }.sortedBy { it.first }
                    .indexOfFirst { it2 -> it2.third >= it.third }
                val bottomTrees =
                    if (bottomIoF < 0) dots.filter { it2 -> it2.first > it.first && it2.second == it.second }.count() else bottomIoF + 1
                val topIoF = dots.filter { it2 -> it2.first < it.first && it2.second == it.second }.sortedBy { it.first }.reversed()
                    .indexOfFirst { it2 -> it2.third >= it.third }
                val topTrees =
                    if (topIoF < 0) dots.filter { it2 -> it2.first < it.first && it2.second == it.second }.count() else topIoF + 1
                val rightIoF = dots.filter { it2 -> it2.first == it.first && it2.second > it.second }.sortedBy { it.second }
                    .indexOfFirst { it2 -> it2.third >= it.third }
                val rightTrees =
                    if (rightIoF < 0) dots.filter { it2 -> it2.first == it.first && it2.second > it.second }.count() else rightIoF + 1
                val leftIoF = dots.filter { it2 -> it2.first == it.first && it2.second < it.second }.sortedBy { it.second }.reversed()
                    .indexOfFirst { it2 -> it2.third >= it.third }
                val leftTrees =
                    if (leftIoF < 0) dots.filter { it2 -> it2.first == it.first && it2.second < it.second }.count() else leftIoF + 1

                topTrees * bottomTrees * leftTrees * rightTrees
            }.max()
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println("Part 1: " + partOne(inputList))
        println("Part 2: " + partTwo(inputList))
    }
}