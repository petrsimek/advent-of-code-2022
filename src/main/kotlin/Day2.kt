import java.io.File

object Day2 {

    val inputList: List<String> = File("src/main/resources/input2.txt").readText(Charsets.UTF_8).split("\n")

    val values = mapOf("A" to 1, "B" to 2, "C" to 3, "X" to 1, "Y" to 2, "Z" to 3)

    private fun buildInputMap(): List<Pair<Int, Int>> {
        return inputList.map {
            val (opp, me) = Regex("""(\w+) (\w+)""").find(it)!!.destructured
            //1 rock, 2 paper, 3 scissors
            Pair(values[opp]!!, values[me]!!)
        }
    }

    private fun partOne(inputList: List<Pair<Int, Int>>): Int {
        val points = mapOf(
            Pair(1, 1) to 3, Pair(1, 2) to 6, Pair(1, 3) to 0,
            Pair(2, 1) to 0, Pair(2, 2) to 3, Pair(2, 3) to 6,
            Pair(3, 1) to 6, Pair(3, 2) to 0, Pair(3, 3) to 3
        )

        return inputList.map {
            it.second + points[it]!!
        }.toList().sum()
    }

    private fun partTwo(inputList: List<Pair<Int, Int>>): Int {
        val points = mapOf(
            Pair(1, 1) to 3, Pair(1, 2) to 4, Pair(1, 3) to 8,
            Pair(2, 1) to 1, Pair(2, 2) to 5, Pair(2, 3) to 9,
            Pair(3, 1) to 2, Pair(3, 2) to 6, Pair(3, 3) to 7
        )

        return inputList.map {
            points[it]!!
        }.toList().sum()
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println("Part 1: " + partOne(buildInputMap()))
        println("Part 2: " + partTwo(buildInputMap()))
    }
}