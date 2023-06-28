package com.wild.kotlin.cinema

fun main() {
    println("Cinema:")
    println("Enter the number of rows:")
    val numberOfRows = readln().toInt()
    println("Enter the number of seats in each row:")
    val numberOfSeatsPerRow = readln().toInt()
    val seats = MutableList(numberOfRows) { MutableList(numberOfSeatsPerRow) { 'S' } }
    val totalSeats = numberOfRows * numberOfSeatsPerRow
    val totalIncome = calculateTotalIncome(numberOfRows, numberOfSeatsPerRow)
    var purchasedTickets = 0
    var currentIncome = 0
    while (true) {
        println("\n1. Show the seats")
        println("2. Buy a ticket")
        println("3. Statistics")
        println("0. Exit")
        print("Enter your choice: ")
        when (readln().toInt()) {
            1 -> showSeats(seats)
            2 -> {
                val selectedSeat = buyTicket(seats)
                val (selectedRow, _) = selectedSeat
                purchasedTickets++
                currentIncome += calculateTicketPrice(numberOfRows, numberOfSeatsPerRow, selectedRow)
            }
            3 -> showStatistics(purchasedTickets, totalSeats, currentIncome, totalIncome)
            0 -> return
            else -> println("Invalid choice. Please try again.")
        }
    }
}

fun showSeats(seats: List<List<Char>>) {
    println("\nCinema:")
    for (i in seats.indices) {
        if (i == 0) {
            print("  ")
            for (j in seats[i].indices) {
                print("${j + 1} ")
            }
            println()
        }
        print("${i + 1} ")
        for (j in seats[i].indices) {
            print("${seats[i][j]} ")
        }
        println()
    }
}

fun buyTicket(seats: MutableList<MutableList<Char>>): Pair<Int, Int> {
    while (true) {
        println("\nEnter a row number:")
        val selectedRow = readln().toInt()
        println("Enter a seat number in that row:")
        val selectedSeat = readln().toInt()
        val numRows = seats.size
        val numSeatsPerRow = seats[0].size
        if (selectedRow !in 1..numRows || selectedSeat !in 1..numSeatsPerRow) {
            println("Wrong input!")
            continue
        }
        if (seats[selectedRow - 1][selectedSeat - 1] == 'B') {
            println("That ticket has already been purchased!")
            continue
        }
        val ticketPrice = calculateTicketPrice(numRows, numSeatsPerRow, selectedRow)
        println("Ticket price: $$ticketPrice")
        seats[selectedRow - 1][selectedSeat - 1] = 'B'
        return selectedRow to selectedSeat
    }
}

fun calculateTicketPrice(numberOfRows: Int, numberOfSeatsPerRow: Int, selectedRow: Int): Int {
    val totalSeats = numberOfRows * numberOfSeatsPerRow
    return if (totalSeats <= 60)
        10
    else {
        val frontHalf = numberOfRows / 2
        if (selectedRow <= frontHalf) {
            10
        } else {
            8
        }
    }
}

fun calculateTotalIncome(numberOfRows: Int, numberOfSeatsPerRow: Int): Int {
    val totalSeats = numberOfRows * numberOfSeatsPerRow
    return if (totalSeats <= 60)
        totalSeats * 10
    else {
        val frontHalf = numberOfRows / 2
        val backHalf = numberOfRows - frontHalf
        (frontHalf * numberOfSeatsPerRow * 10) + (backHalf * numberOfSeatsPerRow * 8)
    }
}

fun showStatistics(purchasedTickets: Int, totalSeats: Int, currentIncome: Int, totalIncome: Int) {
    val percentage = (purchasedTickets.toDouble() / totalSeats.toDouble()) * 100
    val formatPercentage = "%.2f".format(percentage)
    println("\nNumber of purchased tickets: $purchasedTickets")
    println("Percentage: $formatPercentage%")
    println("Current income: $$currentIncome")
    println("Total income: $$totalIncome")
}