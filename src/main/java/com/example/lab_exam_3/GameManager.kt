package com.example.lab_exam_3

import kotlin.random.Random

class GameManager {

    private var lettersUsed: String = ""
    private lateinit var underscoreWord: String
    private lateinit var wordToGuess: String
    private val maxTries = 7
    private var currentTries = 0
    private var drawable: Int = R.drawable.hang1

    //reset game state variables
    fun startNewGame(): GameState {
        lettersUsed = ""
        currentTries = 0
        drawable = R.drawable.hang8
        //selects random word from the gameConstants predefined list
        val randomIndex = Random.nextInt(0, GameConstants.words.size)
        wordToGuess = GameConstants.words[randomIndex]
        generateUnderscores(wordToGuess)
        return getGameState()
    }

    //replace letters with underscores and keep special characters unchanged
    fun generateUnderscores(word: String) {
        val sb = StringBuilder()
        word.forEach { char ->
            if (char == '/') {
                sb.append('/')
            } else {
                sb.append("_")
            }
        }
        underscoreWord = sb.toString()
    }

    fun play(letter: Char): GameState {
        if (lettersUsed.contains(letter)) {
            return GameState.Running(lettersUsed, underscoreWord, drawable)
        }
        //add the letter to the list of used letters
        lettersUsed += letter

        //find indexes of entered letter in the word to guess
        val indexes = mutableListOf<Int>()
        wordToGuess.forEachIndexed { index, char ->
            if (char.equals(letter, true)) {
                indexes.add(index)
            }
        }

        //update the underscore word with the guessed letter
        var finalUnderscoreWord = "" + underscoreWord
        indexes.forEach { index ->
            val sb = StringBuilder(finalUnderscoreWord).also { it.setCharAt(index, letter) }
            finalUnderscoreWord = sb.toString()
        }

        if (indexes.isEmpty()) {
            currentTries++
        }

        //update the underscore word and rturn the current game state
        underscoreWord = finalUnderscoreWord
        return getGameState()
    }


    //determine game state: won,lost, return corresponding GameState object
    private fun getGameState(): GameState {
        if (underscoreWord.equals(wordToGuess, true)) {
            return GameState.Won(wordToGuess)
        }

        if (currentTries == maxTries) {
            return GameState.Lost(wordToGuess)
        }

        drawable = getHangmanDrawable()
        return GameState.Running(lettersUsed, underscoreWord, drawable)
    }

    //function to determine drawables with no. of tries
    private fun getHangmanDrawable(): Int {
        return when (currentTries) {
            0 -> R.drawable.hang1
            1 -> R.drawable.hang2
            2 -> R.drawable.hang3
            3 -> R.drawable.hang4
            4 -> R.drawable.hang5
            5 -> R.drawable.hang6
            6 -> R.drawable.hang7
            7 -> R.drawable.hang8
            else -> R.drawable.hang8
        }
    }
}
