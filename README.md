Neutreeko
=========
Neutreeko is a turn-based board game played on a 5 by 5 grid.
The 2 players each have 3 pieces, which can move horizontally, vertically, or diagonally until it no longer can.
A player wins if 3 of his/her/its pieces are aligned horizontally, vertically, or diagonally in a row.
More about Neutreeko can be found [here](http://www.neutreeko.net/neutreeko.htm).

This is a GUI implemented version of the game using the LWJGL library.
RED goes first.
A piece of the current turn's player can be selected with the mouse cursor and moved using the keyboard.
A piece, in a single turn, can move in 8 directions, unless there is a wall or another piece in that direction.
Centered on the 'S' key, its 8 neighboring keys (W, E, D, C, X, Z, A, Q) represents the directions.

There are plans to implement the AI using the minimax algorithm and alpha-beta pruning.
But, for now, the 'AI' does not make any logical moves; it selects a random piece and direction to make the move.
