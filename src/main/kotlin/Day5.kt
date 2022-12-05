import java.io.File

/**
 * Advent of Code 2022 - Day 5 - https://adventofcode.com/2022/day/5
 */
object Day5 {
    val inputList = File("src/main/resources/input5.txt").readText(Charsets.UTF_8).split("\n\n")

    private fun toCrates(inputList: List<String>): List<MutableList<Char>> {
        val block = inputList.get(0).split("\n")
        val max = block.last().split("  ").map { it.trim().toInt() }.last()
        val prepared = block.subList(0, block.size - 1).map {
            it.padEnd(max * 4).chunked(4).map { it.get(1) }
        }.reversed()
        val cratePiles = mutableListOf<MutableList<Char>>()
        for (i in 0..max - 1) {
            val cratePile = mutableListOf<Char>()
            for (j in 0..prepared.size - 1) {
                if (prepared[j][i] != ' ') {
                    cratePile.add(prepared[j][i])
                }
            }
            cratePiles.add(cratePile)
        }
        return cratePiles
    }

    private fun toCommands(inputList: List<String>): List<Triple<Int, Int, Int>> {
        return inputList.get(1).split("\n").map {
            val (what, from, to) = Regex("""move (\d+) from (\d+) to (\d+)""").find(it)!!.destructured
            Triple(what.toInt(), from.toInt(), to.toInt())
        }
    }

    private fun doWorkAndGetResult(crates: List<MutableList<Char>>, commands: List<Triple<Int, Int, Int>>, crane9001: Boolean): String {
        commands.forEach {
            val size = crates[it.second - 1].size
            val subList = crates[it.second - 1].subList(size - it.first, size)
            if (crane9001) {
                crates[it.third - 1].addAll(subList)
            } else {
                subList.asReversed().forEach { it2 ->
                    crates[it.third - 1].add(it2)
                }
            }
            subList.clear()
        }

        return crates.map {
            it.last()
        }.joinToString("")
    }

    private fun partOne(inputList: List<String>): String {
        return doWorkAndGetResult(toCrates(inputList), toCommands(inputList), false)
    }

    private fun partTwo(inputList: List<String>): String {
        return doWorkAndGetResult(toCrates(inputList), toCommands(inputList), true)
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println("Part 1: " + partOne(inputList))
        println("Part 2: " + partTwo(inputList))
    }
}