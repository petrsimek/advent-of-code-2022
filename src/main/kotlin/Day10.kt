import java.io.File

/**
 * Advent of Code 2022 - Day 10 - https://adventofcode.com/2022/day/10
 */
object Day10 {

    val inputList = File("src/main/resources/input10.txt").readText(Charsets.UTF_8)
    var reg = 1
    var cycle = 1
    var sum = 0
    var pixels = mutableListOf<Pair<Int, Int>>()

    private fun cycle() {
        val x = (cycle - 1) % 40
        val y = (cycle - 1) / 40
        if (x >= reg - 1 && x <= reg + 1) {
            pixels.add(Pair(x, y))
        }
        if (cycle % 40 == 20) sum += cycle * reg
        cycle++
    }

    private fun execute() {
        inputList.split("\n")
            .forEachIndexed { index, it ->
                if (it == "noop") {
                    cycle()
                } else {
                    cycle()
                    cycle()
                    reg += it.split(" ").get(1).toInt()
                }
            }
    }

    private fun partOne(): Int {
        execute()
        return sum
    }

    private fun partTwo() {
        execute()
        pixels.groupBy { it.second }
            .forEach {
                for (i in 0..39) {
                    if (it.value.map { it2 -> it2.first }.contains(i)) print("#") else print(".")
                }
                println()
            }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println("Part 1: " + partOne())
        partTwo()
    }
}