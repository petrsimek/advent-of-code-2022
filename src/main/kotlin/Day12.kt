import utils.Coordinate
import java.io.File

/**
 * Advent of Code 2022 - Day 12 - https://adventofcode.com/2022/day/12
 */
object Day12 {
    lateinit var start: Coordinate
    lateinit var end: Coordinate
    val grid = File("src/main/resources/input12.txt").readText(Charsets.UTF_8).split("\n")
        .flatMapIndexed { y, row ->
            row.mapIndexed { x, c ->
                if (c == 'S') start = Coordinate(y, x)
                if (c == 'E') end = Coordinate(y, x)
                Coordinate(y, x) to if (c == 'E') 'z' else if (c == 'S') 'a' else c
            }
        }.toMap()
    val maxX = grid.keys.map { it.x }.max()
    val maxY = grid.keys.map { it.y }.max()

    private fun calculateMap(): MutableMap<Coordinate, Int> {
        var count = 1
        var candidateList = setOf(end)
        val counts = mutableMapOf<Coordinate, Int>()
        while (candidateList.isNotEmpty()) {
            candidateList = candidateList.map { candidate ->
                val value = grid[candidate]!!
                candidate.adjacent(true).entries
                    .filter { it.value.x >= 0 && it.value.y >= 0 && it.value.x <= maxX && it.value.y <= maxY }
                    .filter { (value.code - grid[it.value]!!.code <= 1) }
                    .filter { counts[it.value] == null }
                    .map {
                        counts.put(it.value, count)
                        it.value
                    }
            }.flatten().toSet()

            count++
        }
        return counts
    }

    private fun partOne(): Int {
        return calculateMap()[start]!!
    }

    private fun partTwo(): Int {
        return calculateMap().filterKeys { grid.filter { it.value == 'a' }.keys.contains(it) }.minOf { it.value }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println("Part 1: " + partOne())
        println("Part 2: " + partTwo())
    }
}