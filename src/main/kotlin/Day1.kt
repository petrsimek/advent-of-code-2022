import java.io.File

object Day1 {

    private fun partOne(inputList: List<String>): Long?  {
        return getSums(inputList).maxOrNull()
    }

    private fun partTwo(inputList: List<String>): Long {
        return getSums(inputList).sortedDescending().take(3).sum()
    }

    private fun getSums(inputList: List<String>): List<Long> {
        return inputList.map { it.split("\n").map { it.toLong() }.sum() }.toMutableList()
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val inputList = File("src/main/resources/input1.txt").readText(Charsets.UTF_8).split("\n\n")
        println("Part 1: " + partOne(inputList))
        println("Part 2: " + partTwo(inputList))
    }
}