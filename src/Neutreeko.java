import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class Neutreeko {

	public int turn; // 1 is human (RED), 2 is computer (BLUE)
	public int winner;
	public int[] selected;

	public int Rpieces[][];
	public int Bpieces[][];

	public static void main(String[] args) {

		Neutreeko game = new Neutreeko();
		game.start();
	}

	public void start() {
		try {
			Display.setDisplayMode(new DisplayMode(280, 280));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}

		init();

		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, 280, 0, 280, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		drawGrid();
		drawPieces();
		Display.update();

		while (!Display.isCloseRequested()) {
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

			loop();

			Display.update();
		}

		Display.destroy();
	}

	private void loop() {
		if (winner == 0) {

			if (turn == 1) {
				human();
			} else {
				computer();
			}

			while (Keyboard.next()) {
				if (Keyboard.getEventKeyState()) {
					if (Keyboard.getEventKey() == Keyboard.KEY_1) {
						winner = 1;
					}
					if (Keyboard.getEventKey() == Keyboard.KEY_2) {
						winner = 2;
					}
				}
			}

			drawGrid();
			drawPieces();
		} else {

			drawGrid();
			drawPieces();

			if (winner == 1) {
				GL11.glColor4f(1.0f, 0f, 0f, 0.45f);
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glVertex2f(0, 0);
				GL11.glVertex2f(0, 280);
				GL11.glVertex2f(280, 280);
				GL11.glVertex2f(280, 0);
				GL11.glEnd();
				System.out.println("Player1 WON!");
			} else if (winner == 2) {
				GL11.glColor4f(0.0f, 0f, 1.0f, 0.45f);
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glVertex2f(0, 0);
				GL11.glVertex2f(0, 280);
				GL11.glVertex2f(280, 280);
				GL11.glVertex2f(280, 0);
				GL11.glEnd();
				System.out.println("Player2 WON!");
			}
			while (Keyboard.next()) {
				if (Keyboard.getEventKeyState()) {
					if (Keyboard.getEventKey() == Keyboard.KEY_SPACE) {
						init();
						winner = 0;
					}
				}
			}
		}

		// pollInput();
	}

	private void computer() {
		try {
			Thread.sleep(00);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		/***** Calculate best move *****/
		int i = (int) (Math.random() * 3);
		int d = (int) (Math.random() * 8);
		/***** Calculate best move *****/

		System.out.print("CPU - ");

		if (move(Bpieces[i], d)) {
			turn = 1;
		} else {
			System.out.print(" INVALID MOVE ");
		}

	}

	private void human() {

		int x = 0;
		int y = 0;

		int cx = 0;
		int cy = 0;

		if (Mouse.isButtonDown(0)) {
			x = Mouse.getX();
			y = Mouse.getY();

			if (x > 5 + 55 * Rpieces[0][0] & x < 55 + 55 * Rpieces[0][0]
					& y > 5 + 55 * Rpieces[0][1] & y < 55 + 55 * Rpieces[0][1]) {
				cx = Rpieces[0][0];
				cy = Rpieces[0][1];
			} else if (x > 5 + 55 * Rpieces[1][0] & x < 55 + 55 * Rpieces[1][0]
					& y > 5 + 55 * Rpieces[1][1] & y < 55 + 55 * Rpieces[1][1]) {
				cx = Rpieces[1][0];
				cy = Rpieces[1][1];
			} else if (x > 5 + 55 * Rpieces[2][0] & x < 55 + 55 * Rpieces[2][0]
					& y > 5 + 55 * Rpieces[2][1] & y < 55 + 55 * Rpieces[2][1]) {
				cx = Rpieces[2][0];
				cy = Rpieces[2][1];
			} else {
				return;
			}

			if (selected[0] != cx | selected[1] != cy) {
				selected = new int[] { cx, cy };
				System.out.printf("[%d, %d] - ", cx, cy);
			}
		}

		if (selected[0] != -1) {
			int dir = -1;
			while (Keyboard.next()) {
				if (Keyboard.getEventKeyState()) {
					if (Keyboard.getEventKey() == Keyboard.KEY_W) {
						System.out.print("UP");
						dir = 0;
					} else if (Keyboard.getEventKey() == Keyboard.KEY_E) {
						System.out.print("UP-RIGHT");
						dir = 1;
					} else if (Keyboard.getEventKey() == Keyboard.KEY_D) {
						System.out.print("RIGHT");
						dir = 2;
					} else if (Keyboard.getEventKey() == Keyboard.KEY_C) {
						System.out.print("DOWN-RIGHT");
						dir = 3;
					} else if (Keyboard.getEventKey() == Keyboard.KEY_X) {
						System.out.print("DOWN");
						dir = 4;
					} else if (Keyboard.getEventKey() == Keyboard.KEY_Z) {
						System.out.print("DOWN-LEFT");
						dir = 5;
					} else if (Keyboard.getEventKey() == Keyboard.KEY_A) {
						System.out.print("LEFT");
						dir = 6;
					} else if (Keyboard.getEventKey() == Keyboard.KEY_Q) {
						System.out.print("UP-LEFT");
						dir = 7;
					}
					if (move(selected, dir)) {
						selected = new int[] { -1, -1 };
						turn = 2;

					} else {
						System.out.print(" INVALID MOVE ");
					}
				}
			}
		}
	}

	private boolean move(int[] selected, int dir) {
		int x = selected[0];
		int y = selected[1];
		int xo = x;
		int yo = y;

		if (!valid(x, y, dir)) {
			return false;
		}

		while (valid(x, y, dir)) {
			if (dir == 0) {
				y++;
			} else if (dir == 1) {
				x++;
				y++;
			} else if (dir == 2) {
				x++;
			} else if (dir == 3) {
				x++;
				y--;
			} else if (dir == 4) {
				y--;
			} else if (dir == 5) {
				y--;
				x--;
			} else if (dir == 6) {
				x--;
			} else if (dir == 7) {
				x--;
				y++;
			}
		}

		for (int i = 0; i < 3; i++) {
			if (Rpieces[i][0] == xo & Rpieces[i][1] == yo) {
				Rpieces[i][0] = x;
				Rpieces[i][1] = y;
			}
			if (Bpieces[i][0] == xo & Bpieces[i][1] == yo) {
				Bpieces[i][0] = x;
				Bpieces[i][1] = y;
			}
		}

		System.out.println(" MOVED");
		checkWon();
		return true;
	}

	private void checkWon() {
		Rpieces = sort(Rpieces);
		Bpieces = sort(Bpieces);

		if (Rpieces[0][0] == Rpieces[1][0] && Rpieces[0][0] == Rpieces[2][0]) {
			if (Rpieces[0][1] + 1 == Rpieces[1][1]
					&& Rpieces[1][1] + 1 == Rpieces[2][1])
				winner = 1;
		} else if (Rpieces[0][1] == Rpieces[1][1]
				&& Rpieces[0][1] == Rpieces[2][1]) {
			if (Rpieces[0][0] + 1 == Rpieces[1][0]
					&& Rpieces[1][0] + 1 == Rpieces[2][0])
				winner = 1;
		} else if (Rpieces[0][1] + 1 == Rpieces[1][1]
				&& Rpieces[1][1] + 1 == Rpieces[2][1]) {
			if (Rpieces[0][0] + 1 == Rpieces[1][0]
					&& Rpieces[1][0] + 1 == Rpieces[2][0])
				winner = 1;
		} else if (Rpieces[0][0] + 1 == Rpieces[1][0]
				&& Rpieces[1][0] + 1 == Rpieces[2][0]) {
			if (Rpieces[0][1] - 1 == Rpieces[1][1]
					&& Rpieces[1][1] - 1 == Rpieces[2][1])
				winner = 1;
		}

		if (Bpieces[0][0] == Bpieces[1][0] && Bpieces[0][0] == Bpieces[2][0]) {
			if (Bpieces[0][1] + 1 == Bpieces[1][1]
					&& Bpieces[1][1] + 1 == Bpieces[2][1])
				winner = 2;
		} else if (Bpieces[0][1] == Bpieces[1][1]
				&& Bpieces[0][1] == Bpieces[2][1]) {
			if (Bpieces[0][0] + 1 == Bpieces[1][0]
					&& Bpieces[1][0] + 1 == Bpieces[2][0])
				winner = 2;
		} else if (Bpieces[0][1] + 1 == Bpieces[1][1]
				&& Bpieces[1][1] + 1 == Bpieces[2][1]) {
			if (Bpieces[0][0] + 1 == Bpieces[1][0]
					&& Bpieces[1][0] + 1 == Bpieces[2][0])
				winner = 2;
		} else if (Bpieces[0][0] + 1 == Bpieces[1][0]
				&& Bpieces[1][0] + 1 == Bpieces[2][0]) {
			if (Bpieces[0][1] - 1 == Bpieces[1][1]
					&& Bpieces[1][1] - 1 == Bpieces[2][1])
				winner = 2;
		}
	}

	private int[][] sort(int[][] pieces) {
		int[][] sort = pieces;
		java.util.Arrays.sort(sort, new java.util.Comparator<int[]>() {
			public int compare(int[] a, int[] b) {
				return a[0] - b[0];
			}
		});

		if (sort[0][0] == sort[1][0] && sort[0][0] == sort[2][0]) {
			java.util.Arrays.sort(sort, new java.util.Comparator<int[]>() {
				public int compare(int[] a, int[] b) {
					return a[1] - b[1];
				}
			});
		} else if (sort[0][0] == sort[1][0]) {
			if (sort[0][1] > sort[1][1]) {
				int a = sort[0][1];
				sort[0][1] = sort[1][1];
				sort[1][1] = a;
			}
		} else if (sort[2][0] == sort[1][0]) {
			if (sort[2][1] < sort[1][1]) {
				int a = sort[2][1];
				sort[2][1] = sort[1][1];
				sort[1][1] = a;
			}
		}

		System.out.printf("before:[%d,%d]-[%d,%d]-[%d,%d]\n", pieces[0][0],
				pieces[0][1], pieces[1][0], pieces[1][1], pieces[2][0],
				pieces[2][1]);
		System.out.printf("after: [%d,%d]-[%d,%d]-[%d,%d]\n", sort[0][0],
				sort[0][1], sort[1][0], sort[1][1], sort[2][0], sort[2][1]);

		return sort;
	}

	private boolean valid(int x, int y, int dir) {
		boolean v = true;

		if (dir == 0) {
			y++;
		} else if (dir == 1) {
			x++;
			y++;
		} else if (dir == 2) {
			x++;
		} else if (dir == 3) {
			x++;
			y--;
		} else if (dir == 4) {
			y--;
		} else if (dir == 5) {
			y--;
			x--;
		} else if (dir == 6) {
			x--;
		} else if (dir == 7) {
			x--;
			y++;
		}

		if (x < 0 | y < 0 | x > 4 | y > 4)
			return false;

		for (int i = 0; i < 3; i++) {
			if (Rpieces[i][0] == x & Rpieces[i][1] == y) {
				v = false;
			}
			if (Bpieces[i][0] == x & Bpieces[i][1] == y) {
				v = false;
			}
		}

		return v;
	}

	private void init() {
		turn = 1; // 1 is human (RED), 2 is computer (BLUE)
		winner = 0;

		selected = new int[] { -1, -1 };

		Rpieces = new int[3][2];
		Rpieces[0] = new int[] { 1, 0 };
		Rpieces[1] = new int[] { 3, 0 };
		Rpieces[2] = new int[] { 2, 3 };

		Bpieces = new int[3][2];
		Bpieces[0] = new int[] { 2, 1 };
		Bpieces[1] = new int[] { 1, 4 };
		Bpieces[2] = new int[] { 3, 4 };
	}

	private void drawPieces() {
		GL11.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
		drawP(Rpieces[0][0], Rpieces[0][1]);
		drawP(Rpieces[1][0], Rpieces[1][1]);
		drawP(Rpieces[2][0], Rpieces[2][1]);

		GL11.glColor4f(0.0f, 0.0f, 1.0f, 1.0f);
		drawP(Bpieces[0][0], Bpieces[0][1]);
		drawP(Bpieces[1][0], Bpieces[1][1]);
		drawP(Bpieces[2][0], Bpieces[2][1]);
	}

	private void drawGrid() {
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glVertex2f(5 + i * 55, 5 + j * 55);
				GL11.glVertex2f(5 + i * 55 + 50, 5 + j * 55);
				GL11.glVertex2f(5 + i * 55 + 50, 5 + j * 55 + 50);
				GL11.glVertex2f(5 + i * 55, 5 + j * 55 + 50);
				GL11.glEnd();
			}
		}
		int k = selected[0];
		int l = selected[1];
		if (k != -1) {
			GL11.glColor4f(1.0f, 0f, 0f, 0.45f);
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(5 + k * 55, 5 + l * 55);
			GL11.glVertex2f(5 + k * 55 + 50, 5 + l * 55);
			GL11.glVertex2f(5 + k * 55 + 50, 5 + l * 55 + 50);
			GL11.glVertex2f(5 + k * 55, 5 + l * 55 + 50);
			GL11.glEnd();
		}
	}

	private void drawP(int x, int y) {
		x = x * 55 + 5 + 15;
		y = y * 55 + 5 + 15;
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(x, y);
		GL11.glVertex2f(x + 20, y);
		GL11.glVertex2f(x + 20, y + 20);
		GL11.glVertex2f(x, y + 20);
		GL11.glEnd();
	}

	public void pollInput() {

		if (Mouse.isButtonDown(0)) {
			int x = Mouse.getX();
			int y = Mouse.getY();

			System.out.println("MOUSE DOWN @ X: " + x + " Y: " + y);
		}

		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				if (Keyboard.getEventKey() == Keyboard.KEY_A) {
					System.out.println("A Key Pressed");
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_S) {
					System.out.println("S Key Pressed");
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_D) {
					System.out.println("D Key Pressed");
				}
			} else {
				if (Keyboard.getEventKey() == Keyboard.KEY_A) {
					System.out.println("A Key Released");
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_S) {
					System.out.println("S Key Released");
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_D) {
					System.out.println("D Key Released");
				}
			}
		}
	}
}