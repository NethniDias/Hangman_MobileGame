package com.example.lab_exam_3

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.children


class GameActivity : AppCompatActivity() {

    private val gameManager = GameManager()

    private lateinit var wordTextView: TextView
    private lateinit var lettersUsedTextView: TextView
    private lateinit var imageView: ImageView
    private lateinit var gameLostTextView: TextView
    private lateinit var gameWonTextView: TextView
    private lateinit var newGameButton: Button
    private lateinit var backButton: Button
    private lateinit var scoreTextView: TextView //score
    private lateinit var sharedPrefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        imageView = findViewById(R.id.imageView)
        wordTextView = findViewById(R.id.wordTextView)
        lettersUsedTextView = findViewById(R.id.lettersUsedTextView)
        gameLostTextView = findViewById(R.id.gameLostTextView)
        gameWonTextView = findViewById(R.id.gameWonTextView)
        newGameButton = findViewById(R.id.newGameButton)
        backButton = findViewById(R.id.backButton)
        sharedPrefs = getSharedPreferences("MyPrefs", MODE_PRIVATE)


        newGameButton.setOnClickListener {
            startNewGame()
        }

        backButton.setOnClickListener {
            navigateToMainPage()
        }

        val firstLineLayout = findViewById<LinearLayout>(R.id.firstLineLettersLayout)
        val secondLineLayout = findViewById<LinearLayout>(R.id.secondLineLettersLayout)
        val thirdLineLayout = findViewById<LinearLayout>(R.id.thirdLineLettersLayout)

        // Iterate over the children of the first line layout
        firstLineLayout.children.forEach { letterView ->
            if (letterView is TextView) {
                letterView.setOnClickListener {
                    val gameState = gameManager.play((letterView).text[0])
                    updateUI(gameState)
                    letterView.visibility = View.GONE
                }
            }
        }

        // Iterate over the children of the second line layout
        secondLineLayout.children.forEach { letterView ->
            if (letterView is TextView) {
                letterView.setOnClickListener {
                    val gameState = gameManager.play((letterView).text[0])
                    updateUI(gameState)
                    letterView.visibility = View.GONE
                }
            }
        }

        // Iterate over the children of the third line layout
        thirdLineLayout.children.forEach { letterView ->
            if (letterView is TextView) {
                letterView.setOnClickListener {
                    val gameState = gameManager.play((letterView).text[0])
                    updateUI(gameState)
                    letterView.visibility = View.GONE
                }
            }
        }
    }

    private fun navigateToMainPage() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    //update UI according to game state
    private fun updateUI(gameState: GameState) {
        when (gameState) {
            is GameState.Lost -> showGameLost(gameState.wordToGuess)
            is GameState.Running -> {
                wordTextView.text = gameState.underscoreWord
                lettersUsedTextView.text = "Letters used: ${gameState.lettersUsed}"
                imageView.setImageDrawable(ContextCompat.getDrawable(this, gameState.drawable))
            }
            is GameState.Won -> showGameWon(gameState.wordToGuess)
        }
    }
    //Game Lost function
    private fun showGameLost(wordToGuess: String) {
        wordTextView.text = wordToGuess
        gameLostTextView.visibility = View.VISIBLE
        imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.hang8))
        // Change visibility of relevant layout
        findViewById<LinearLayout>(R.id.firstLineLettersLayout).visibility = View.GONE
        findViewById<LinearLayout>(R.id.secondLineLettersLayout).visibility = View.GONE
        findViewById<LinearLayout>(R.id.thirdLineLettersLayout).visibility = View.GONE
    }
    //Game win function
    private fun showGameWon(wordToGuess: String) {
        wordTextView.text = wordToGuess
        gameWonTextView.visibility = View.VISIBLE
        // Change visibility of relevant layout
        findViewById<LinearLayout>(R.id.firstLineLettersLayout).visibility = View.GONE
        findViewById<LinearLayout>(R.id.secondLineLettersLayout).visibility = View.GONE
        findViewById<LinearLayout>(R.id.thirdLineLettersLayout).visibility = View.GONE
    }
    //New game function
    private fun startNewGame() {
        gameLostTextView.visibility = View.GONE
        gameWonTextView.visibility = View.GONE
        val gameState = gameManager.startNewGame()
        // Change visibility of relevant layout
        findViewById<LinearLayout>(R.id.firstLineLettersLayout).visibility = View.VISIBLE
        findViewById<LinearLayout>(R.id.secondLineLettersLayout).visibility = View.VISIBLE
        findViewById<LinearLayout>(R.id.thirdLineLettersLayout).visibility = View.VISIBLE

        // Reset visibility of all letter views
        findViewById<LinearLayout>(R.id.firstLineLettersLayout).children.forEach { letterView ->
            letterView.visibility = View.VISIBLE
        }
        findViewById<LinearLayout>(R.id.secondLineLettersLayout).children.forEach { letterView ->
            letterView.visibility = View.VISIBLE
        }
        findViewById<LinearLayout>(R.id.thirdLineLettersLayout).children.forEach { letterView ->
            letterView.visibility = View.VISIBLE
        }

        updateUI(gameState)
    }

}
