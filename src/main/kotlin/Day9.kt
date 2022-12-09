import java.io.File
import java.lang.Math.abs

/**
 * Advent of Code 2022 - Day 9 - https://adventofcode.com/2022/day/9
 */
object Day9 {

    val inputList = File("src/main/resources/input9.txt").readText(Charsets.UTF_8)
    val commands = inputList.split("\n").map {
        val (direction, steps) = it.split(" ")
        Pair(direction, steps.toInt())
    }

    private fun run(rope: MutableList<Pair<Int, Int>>): Int {
        val alltails = mutableSetOf<Pair<Int, Int>>()
        commands.forEach { comm ->
            for (s in 0 until comm.second) {
                for (ropeIndex in 0 until rope.size - 1) {
                    if (ropeIndex == 0) {
                        rope[ropeIndex] = calculateHead(comm.first, rope[ropeIndex])
                    }
                    rope[ropeIndex + 1] = calculateTail(rope[ropeIndex], rope[ropeIndex + 1])
                }
                alltails.add(rope.last())
            }
        }

        return alltails.count()
    }

    private fun calculateHead(dir: String, h: Pair<Int, Int>): Pair<Int, Int> {
        var headX = h.first
        var headY = h.second

        when (dir) {
            "R" -> headY += 1
            "L" -> headY -= 1
            "U" -> headX += 1
            "D" -> headX -= 1
        }

        return Pair(headX, headY)
    }


    private fun calculateTail(head: Pair<Int, Int>, tail: Pair<Int, Int>): Pair<Int, Int> {
        val dX = if (head.first > tail.first) 1 else if (head.first < tail.first) -1 else 0
        val dY = if (head.second > tail.second) 1 else if (head.second < tail.second) -1 else 0

        val diffX = abs(head.first - tail.first)
        val diffY = abs(head.second - tail.second)

        var newX = tail.first
        var newY = tail.second

        if (diffX > 1) {
            newX += dX
            if (diffY > 1) {
                newY += dY
            } else if (diffY > 0) {
                newY += dY
            }
        } else if (diffX > 0) {
            if (diffY > 1) {
                newX += dX
                newY += dY
            }
        } else {
            if (diffY > 1) {
                newY += dY
            }
        }

        return Pair(newX, newY)
    }

    private fun partOne(): Int {
        return run(mutableListOf(Pair(0, 0), Pair(0, 0)))
    }

    private fun partTwo(): Int {
        return run(
            mutableListOf(
                Pair(0, 0),
                Pair(0, 0),
                Pair(0, 0),
                Pair(0, 0),
                Pair(0, 0),
                Pair(0, 0),
                Pair(0, 0),
                Pair(0, 0),
                Pair(0, 0),
                Pair(0, 0)
            )
        )
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println("Part 1: " + partOne())
        println("Part 2: " + partTwo())
    }
}