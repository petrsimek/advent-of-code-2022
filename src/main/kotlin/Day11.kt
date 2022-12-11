import java.io.File

/**
 * Advent of Code 2022 - Day 11 - https://adventofcode.com/2022/day/11
 */
object Day11 {

    class Monkey(
        val items: MutableList<Long>,
        val operation: Pair<String, Int>,
        val test: Int,
        val ifTrue: Int,
        val ifFalse: Int,
        var inspections: Long
    )

    val inputList = File("src/main/resources/input11-test.txt").readText(Charsets.UTF_8)
    var monkeys = inputList.split("\n\n").map {
        val rows = it.split("\n")
        val items = rows[1].split(": ").last().split(", ").map { it.toLong() }
        val op = rows[2].split(" = ").last().split(" ")
        val operation = op[op.size - 2]
        val number = if (op.last() == "old") 0 else op.last().toInt()
        val test = rows[3].split("by ").last().toInt()
        val ifTrue = rows[4].split("monkey ").last().toInt()
        val ifFalse = rows[5].split("monkey ").last().toInt()
        Monkey(items.toMutableList(), Pair(operation, number), test, ifTrue, ifFalse, 0)
    }

    private fun doMonkeyBusiness(rounds: Int, divide: Boolean): Long {
        repeat(rounds) {
            monkeys = oneRound(monkeys, divide)
        }
        return monkeys.map { it.inspections }.sortedDescending().take(2).reduce(Long::times)
    }


    private fun oneRound(monkeys: List<Monkey>, divide: Boolean): List<Monkey> {
        val greatestCommonDivisor = monkeys.map { it.test }.reduce(Int::times)
        monkeys.forEachIndexed { i, monkey ->
            monkey.items.forEach {
                val addition = if (monkey.operation.first == "*") {
                    if (monkey.operation.second == 0) it * it else it * monkey.operation.second.toLong()
                } else it + monkey.operation.second.toLong()
                val div = if (divide) Math.floor(addition / 3.0).toLong() else addition % greatestCommonDivisor
                if (div % monkey.test == 0L) {
                    monkeys[monkey.ifTrue].items.add(div)
                } else {
                    monkeys[monkey.ifFalse].items.add(div)
                }
                monkey.inspections++
            }
            monkey.items.clear()

        }
        return monkeys
    }

    private fun partOne(): Long {
        return doMonkeyBusiness(20, true)
    }

    private fun partTwo(): Long {
        return doMonkeyBusiness(10000, false)
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println("Part 1: " + partOne())
        println("Part 2: " + partTwo())
    }
}