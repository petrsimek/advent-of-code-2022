import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonArray
import java.io.File
import kotlin.math.max

/**
 * Advent of Code 2022 - Day 13 - https://adventofcode.com/2022/day/13
 */
object Day13 {

    data class Paket(val data: String) : Comparable<Paket> {
        override fun compareTo(other: Paket): Int {
            return compare(Json.parseToJsonElement(this.data).jsonArray, Json.parseToJsonElement(other.data).jsonArray)
        }
    }

    val dividers = listOf(Paket("[[2]]"), Paket("[[6]]"))
    var input = File("src/main/resources/input13.txt").readText(Charsets.UTF_8)
    var pairs = input.split("\n\n").map {
        val lines = it.lines()
        Pair(lines[0], lines[1])
    }

    private fun compare(first: JsonArray, second: JsonArray): Int {
        val maxSize = max(first.size, second.size)
        var res = 0
        loop@ for (i in 0 until maxSize) {
            if (i == first.size && i < second.size) {
                res = -1
                break@loop
            } else if (i < first.size && i == second.size) {
                res = 1
                break@loop
            }
            if (first[i] is JsonArray && second[i] is JsonArray) {
                val c = compare(first[i].jsonArray, second[i].jsonArray)
                if (c != 0) {
                    res = c
                    break@loop
                }
            } else if (first[i] is JsonPrimitive && second[i] is JsonPrimitive) {
                if (first[i].toString().toInt() < second[i].toString().toInt()) {
                    res = -1
                    break@loop
                } else if (first[i].toString().toInt() > second[i].toString().toInt()) {
                    res = 1
                    break@loop
                }
            } else if (first[i] is JsonPrimitive) {
                val c = compare(JsonArray(listOf(first[i])), second[i].jsonArray)
                if (c != 0) {
                    res = c
                    break@loop
                }
            } else if (second[i] is JsonPrimitive) {
                val c = compare(first[i].jsonArray, JsonArray(listOf(second[i])))
                if (c != 0) {
                    res = c
                    break@loop
                }
            }
        }
        return res
    }

    private fun partOne(): Int {
        return pairs.mapIndexed { index, pair ->
            if (Paket(pair.first) < Paket(pair.second)) index + 1 else 0
        }.sum()
    }

    private fun partTwo(): Int {
        return pairs.map {
            listOf(Paket(it.first), Paket(it.second))
        }.flatten().toMutableList().plus(dividers).sorted().mapIndexedNotNull { index, paket ->
            if (dividers.contains(paket)) index + 1 else null
        }.reduce(Int::times)
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println("Part 1: " + partOne())
        println("Part 2: " + partTwo())
    }
}