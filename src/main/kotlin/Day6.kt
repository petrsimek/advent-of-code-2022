import java.io.File

/**
 * Advent of Code 2022 - Day 6 - https://adventofcode.com/2022/day/6
 */
object Day6 {
    val inputList = File("src/main/resources/input6.txt").readText(Charsets.UTF_8).toList()

    private fun calculate(inputList: List<Char>, packetSize: Int): Int {
        var cnt = 0
        for (i in packetSize until inputList.size) {
            if (inputList.subList(i - packetSize, i).groupingBy { it }.eachCount().filter { it.value > 1 }.count() == 0) {
                cnt = i
                break
            }
        }
        return cnt
    }

    private fun partOne(inputList: List<Char>): Int {
        return calculate(inputList, 4)
    }

    private fun partTwo(inputList: List<Char>): Int {
        return calculate(inputList, 14)
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println("Part 1: " + partOne(inputList))
        println("Part 2: " + partTwo(inputList))
    }
}