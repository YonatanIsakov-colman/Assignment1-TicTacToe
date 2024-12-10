package com.idz.assignment1_tictactoe

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

private const val BOARD_SIZE:Int = 3;

class MainActivity : AppCompatActivity() {
    private var currentPlayer:String = "X";
    private lateinit var playerTurnText:TextView
    private var gameActive:Boolean = true;
    private lateinit var gameButtons: Array<Array<Button>>
    private val gameBoard = Array(BOARD_SIZE){
        Array(BOARD_SIZE){""}

    }
    private lateinit var playAgain:Button;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        gameButtons = arrayOf(
            arrayOf(
                findViewById(R.id.activity_main_button00),
                findViewById(R.id.activity_main_button01),
                findViewById(R.id.activity_main_button02)),
            arrayOf(
                findViewById(R.id.activity_main_button10),
                findViewById(R.id.activity_main_button11),
                findViewById(R.id.activity_main_button12)),
            arrayOf(
                findViewById(R.id.activity_main_button20),
                findViewById(R.id.activity_main_button21),
                findViewById(R.id.activity_main_button22))
        )
        // Set click listeners for each button
        for (row in 0 until BOARD_SIZE) {
            for (col in 0 until BOARD_SIZE) {
                gameButtons[row][col].setOnClickListener {
                    onCellClick(row, col)
                }
            }
        }

        playerTurnText = findViewById(R.id.activity_main_text_player_turn);
        playerTurnText.text = "X Player Turn"

        playAgain=findViewById(R.id.activity_main_play_again_button);
        playAgain.setOnClickListener{
            resetGame()};
    }



    private fun onCellClick(row: Int, col: Int) {
        if(gameBoard[row][col]=="" && gameActive) {
            gameButtons[row][col].text = currentPlayer;
            gameBoard[row][col] = currentPlayer;
            if (isWin()) {
                gameActive = false;
                playerTurnText.text = "Player " + currentPlayer + " is the WINNER";
                playAgain.visibility = Button.VISIBLE;
            }
            else if (isBoardFull()){
                gameActive = false;
                playerTurnText.text = " its a TIE ";
                playAgain.visibility = Button.VISIBLE;
            }
            else {
                currentPlayer = if (currentPlayer == "X")
                    "O";
                else
                    "X";
                playerTurnText.text = currentPlayer + " Player Turn";
            }
        }
    }

    private fun isWin():Boolean {
        // Check rows, columns, and diagonals
        for (i in 0 until BOARD_SIZE) {
            if (gameBoard[i][0] == gameBoard[i][1] && gameBoard[i][1]  == gameBoard[i][2]
                && gameBoard[i][1] != "")
                return true
            if (gameBoard[0][i] == gameBoard[1][i] && gameBoard[1][i]== gameBoard[2][i]
                && gameBoard[1][i]!= "")
                return true
        }
        if (gameBoard[0][0] == gameBoard[1][1] && gameBoard[1][1] == gameBoard[2][2]
            && gameBoard[1][1]!= "")
            return true
        if (gameBoard[0][2] == gameBoard[2][0] && gameBoard[2][0] == gameBoard[1][1]
            && gameBoard[2][0]!= "" )
            return true
        return false
    }
    private fun isBoardFull():Boolean {
        for (row in 0 until BOARD_SIZE) {
            for(col in 0 until BOARD_SIZE){
                if(gameBoard[row][col] == "")
                return false;
            }
        }
        return true;
    }


    private fun resetGame() {
        for (row in 0 until BOARD_SIZE) {
            for (col in 0 until BOARD_SIZE) {
                gameBoard[row][col] =""
                gameButtons[row][col].text = ""
            }
        }
        currentPlayer = "X";
        gameActive = true;
        playerTurnText.text = "X Player Turn";
        playAgain.visibility = Button.GONE
    }
}
