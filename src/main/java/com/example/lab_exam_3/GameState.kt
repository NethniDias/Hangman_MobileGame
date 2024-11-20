package com.example.lab_exam_3

//represents the states of game when running, losing and winning
sealed class GameState {

    class Running (
            val lettersUsed: String,
            val underscoreWord: String,
            val drawable: Int
    ): GameState()

    class Lost(
        val wordToGuess: String
    ): GameState()

    class Won(
        val wordToGuess: String
    ): GameState()

}