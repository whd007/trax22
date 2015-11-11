package trax2;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.border.*;

public class Game extends JPanel {// 800x800 수정요함

	public Game() {
		this.setLayout(null);

		createControlPanel();
		tileInfoClear();
		clearLoopCheck();
	}

	static ImageIcon[] icons = { new ImageIcon("src/trax2/0.jpg"), new ImageIcon("src/trax2/1.jpg"),
		new ImageIcon("src/trax2/2.jpg"), new ImageIcon("src/trax2/3.jpg"), new ImageIcon("src/trax2/4.jpg"),
		new ImageIcon("src/trax2/5.jpg"), new ImageIcon("src/trax2/6.jpg"),

		new ImageIcon("src/trax2/1-1.jpg"), // 7 1
		new ImageIcon("src/trax2/2-1.jpg"), // 8 2
		new ImageIcon("src/trax2/3-1.jpg"), // 9 3
		new ImageIcon("src/trax2/4-1.jpg"), // 10 4
		new ImageIcon("src/trax2/5-1.jpg"), // 11 5
		new ImageIcon("src/trax2/6-1.jpg"), // 12 6
		new ImageIcon("src/trax2/000.jpg")
	}; // icons

	static JButton[][] jbtn = new JButton[128][128]; // jbutton 배열
	static JScrollPane scroll;
	static JScrollBar vscbar;
	static JScrollBar hscbar;
	static JButton selectButton;
	static int gamecount = 0, check = 0, rClickAble[][] = new int[128][128];
	static int tileInfo[][] = new int[128][128];
	public int loopCheck[][] = new int[128][128];
	public Listeners l = new Listeners();
	public TextArea turn = new TextArea("",0,0,TextArea.SCROLLBARS_NONE);
	public static Boolean onoff = false;


	public int tile_list[][][] = new int[][][] { // 위부터 시계방향으로
			{ { 0, 0, 0 }, { 0, 0, 0 }, { 0, 0, 0 }, { 0, 0, 0 } },
			{ { 1, 5, 6 }, { 1, 4, 5 }, { 1, 3, 4 }, { 1, 3, 6 } }, // 1번블록
			{ { 2, 3, 4 }, { 2, 3, 6 }, { 2, 5, 6 }, { 2, 4, 5 } }, // 2번블록
			{ { 1, 5, 6 }, { 1, 4, 5 }, { 2, 5, 6 }, { 2, 4, 5 } }, // 3번블록
			{ { 1, 5, 6 }, { 2, 3, 6 }, { 2, 5, 6 }, { 1, 3, 6 } }, // 4번블록
			{ { 2, 3, 4 }, { 2, 3, 6 }, { 1, 3, 4 }, { 1, 3, 6 } }, // 5번블록
			{ { 2, 3, 4 }, { 1, 4, 5 }, { 1, 3, 4 }, { 2, 4, 5 } } };// 6번 블록


	private void createControlPanel() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(128, 128, 0, 0));

		for (int i = 0; i < 128; i++)
			for (int j = 0; j < 128; j++) {
				buttonPanel.add(jbtn[i][j] = new JButton(icons[13]));
				jbtn[i][j].setBorderPainted(false);// 테두리 없애기
				jbtn[i][j].setMargin(new Insets(0, 0, 0, 0));// 여백없애기
				jbtn[i][j].setContentAreaFilled(false);// 버튼의 바탕 없애기
				jbtn[i][j].addMouseListener(l);

			}
		buttonPanel.setSize(new Dimension(300, 300));

		add(buttonPanel, BorderLayout.CENTER);

		scroll = new JScrollPane();
		scroll.getViewport().add(buttonPanel);
		scroll.getHorizontalScrollBar().setValue(scroll.getHorizontalScrollBar().getMaximum() / 2);// 이상하게
		// 2번
		// 해야
		// 된다.
		scroll.getHorizontalScrollBar().setValue(scroll.getHorizontalScrollBar().getMaximum() / 2);// 가로
		// 스크롤
		// 위치
		// 설정
		scroll.getVerticalScrollBar().setValue(scroll.getVerticalScrollBar().getMaximum() / 2);// 세로
		// 스크롤
		// 위치
		// 설정

		scroll.setBounds(0, 100, 780, 650);// 게임판 크기 및 위치

		add(scroll);


		turn.setBounds(0,16,100,80);
		turn.setText("BlackTurn");
		add(turn);
	}

	private class Listeners implements MouseListener {

		public void mouseEntered(MouseEvent e) {// 마우스가 버튼 위로 갔을때 이벤트
			int r = 0;
			int c = 0;

			r = e.getComponent().getX() / 86;
			c = e.getComponent().getY() / 86;

			jbtn[c][r].setBorderPainted(true);

			LineBorder b = new LineBorder(Color.black, 3);// 테두리 색깔 및 두께
			jbtn[c][r].setBorder(b);

		}

		public void mouseExited(MouseEvent e) {// 마우스가 버튼 밖으로 빠질때 이벤트
			int r = 0;
			int c = 0;

			r = e.getComponent().getX() / 86;
			c = e.getComponent().getY() / 86;

			jbtn[c][r].setBorderPainted(false);

		}

		public void mouseReleased(MouseEvent e) {

		}

		public void mousePressed(MouseEvent e) {

		}

		public void mouseClicked(MouseEvent e) {
			int r = 0;
			int c = 0;
			r = e.getComponent().getX() / 86;
			c = e.getComponent().getY() / 86;

			int ableTile[] = new int[3];// 한 타일에 이어질 수 있는 타일의 종류는 최대 3개이다.

			if (gamecount == 0 && onoff==true) {// 첫 타일이 놓일 이벤트

				if (e.getButton() == MouseEvent.BUTTON1) // 좌클릭시
				{
					System.out.println(r + "," + c + "에서 좌클릭이벤트!");
					rClickAble[67][67] = 1;
					if (check == 0) {
						check = 1;
						jbtn[67][67].setIcon(icons[7]);
						System.out.println(e.getSource());
					} else {
						check = 0;
						jbtn[67][67].setIcon(icons[9]);
					}
				}

				else if ((e.getButton() == MouseEvent.BUTTON3)) // 좌클릭 후 우클릭시
				{
					System.out.println(r + "," + c + "에서 우클릭이벤트!");
					if (rClickAble[67][67] != 0) {
						rClickAble[67][67] = 0;
						if (check == 0) {

							jbtn[67][67].setIcon(icons[3]);
							if(gamecount%2 == 0){
								Menu.writeLog(gamecount + ". Black : "+"67" + ", " + "67" +"\n");
								turn.setText("WhiteTurn");

							}
							else{
								Menu.writeLog(gamecount + ". White : "+"67" + ", " + "67" + "\n");
								turn.setText("BlackTurn");
							}
							gamecount++;
							tileInfo[67][67] = 3;
						} else {
							if(gamecount%2 == 0){
								Menu.writeLog(gamecount + ". Black : "+"67" + ", " + "67" + "\n");
								turn.setText("WhiteTurn");
							}
							else{
								Menu.writeLog(gamecount + ". White : "+"67" + ", " + "67" + "\n");
								turn.setText("BlackTurn");
							}

							jbtn[67][67].setIcon(icons[1]);
							gamecount++;
							tileInfo[67][67] = 1;
							check = 0;


						}
					}
				}
			} else {// 첫타일 이후의 이벤트
				if (e.getButton() == MouseEvent.BUTTON1 && tileInfo[c][r] == 0 && isAble(r, c)) {
					// 타일이 놓을 수 있는 위치인지 받아오는 함수 isAble(r, c) true면 가능 false면 불가능
					System.out.println(r + "," + c + "에서 좌클릭이벤트!");
					for (int x = 0; x < 128; x++)
						for (int y = 0; y < 128; y++) {// 어딘가의 rClickAble이 1이라면,
							// 즉 어딘가서 좌클릭을 했다면
							if (rClickAble[x][y] == 1) {
								if (!(x == c && y == r)) {
									rClickAble[x][y] = 0;// 그 위치의 rClickAble을
									// 0으로 바꿔
									// 우클릭 불가능으로
									jbtn[x][y].setIcon(icons[0]);// 그 위치의 아이콘을
									// 기본
									// 아이콘으로
									check = 0;
								}
							}
						}
					rClickAble[c][r] = 1;
					// ableTile[]에 들어갈 수 있는 타일 3개를 받아온다.
					ableTile = ableTiles(r, c);
					if (ableTile[check] != 0)
						jbtn[c][r].setIcon(icons[ableTile[check] + 6]);
					else {
						check = 0;
						jbtn[c][r].setIcon(icons[ableTile[check] + 6]);
					}
					check++;
					if (check == 3)
						check = 0;

				} else if (e.getButton() == MouseEvent.BUTTON3 && rClickAble[c][r] != 0) {// 우클릭시
					System.out.println(r + "," + c + "에서 우클릭이벤트!");
					rClickAble[c][r] = 0;
					ableTile = ableTiles(r, c);
					switch (check) {
					case 0:
						jbtn[c][r].setIcon(icons[ableTile[2]]);
						tileInfo[c][r] = ableTile[2];
						if(gamecount%2 == 0){
							Menu.writeLog(gamecount + ". Black : "+r + ", " + c + "\n");
							turn.setText("WhiteTurn");
						}
						else{
							Menu.writeLog(gamecount + ". White : "+r + ", " + c + "\n");
							turn.setText("BlackTurn");
						}

						isWin(r,c);// 승리조건 검사
						forcedPlay(r, c);// 자동완성 검사
						gamecount++;

						break;
					case 1:
						jbtn[c][r].setIcon(icons[ableTile[0]]);
						tileInfo[c][r] = ableTile[0];
						if(gamecount%2 == 0){
							Menu.writeLog(gamecount + ". Black : "+r + ", " + c + "\n");
							turn.setText("WhiteTurn");
						}
						else{
							Menu.writeLog(gamecount + ". White : "+r + ", " + c + "\n");
							turn.setText("BlackTurn");
						}
						isWin(r,c);// 승리조건 검사
						forcedPlay(r, c);// 자동완성 검사
						gamecount++;
						break;
					case 2:
						jbtn[c][r].setIcon(icons[ableTile[1]]);
						tileInfo[c][r] = ableTile[1];
						if(gamecount%2 == 0){
							Menu.writeLog(gamecount + ". Black : "+r + ", " + c + "\n");
							turn.setText("WhiteTurn");
						}
						else{
							Menu.writeLog(gamecount + ". White : "+r + ", " + c + "\n");
							turn.setText("BlackTurn");
						}
						isWin(r,c);// 승리조건 검사
						forcedPlay(r, c);// 자동완성 검사
						gamecount++;
						break;
					}

				}
			}

		}

	}

	public void tileInfoClear() {
		for (int x = 0; x < 128; x++)
			for (int y = 0; y < 128; y++) {
				tileInfo[x][y] = 0;
				rClickAble[x][y] = 0;
			}

	}

	public boolean isAble(int r, int c) {// 첫 타일 이후의 타일들의 놓일 수 있는 자리 판별 함수
		if (c > 0 && c < 127 && r > 0 && r < 127) {
			if (tileInfo[c - 1][r] != 0 || tileInfo[c + 1][r] != 0 || tileInfo[c][r - 1] != 0
					|| tileInfo[c][r + 1] != 0) {
				return true;// 만일 상하좌우에 타일이 하나라도 있다면 놓을 수 있음
			} else
				return false;// 상하좌우에 타일이 하나도 없다면 놓을 수 없음.
		} else if (c == 0) {
			if (r == 0) {
				if (tileInfo[c + 1][r] != 0 || tileInfo[c][r + 1] != 0)
					return true;
				else
					return false;
			} else if (r == 127) {
				if (tileInfo[c + 1][r] != 0 || tileInfo[c][r - 1] != 0)
					return true;
				else
					return false;
			} else {
				if (tileInfo[c + 1][r] != 0 || tileInfo[c][r - 1] != 0 || tileInfo[c][r + 1] != 0)
					return true;
				else
					return false;
			}
		} else if (c == 127) {
			if (r == 0) {
				if (tileInfo[c - 1][r] != 0 || tileInfo[c][r + 1] != 0)
					return true;
				else
					return false;
			} else if (r == 127) {
				if (tileInfo[c - 1][r] != 0 || tileInfo[c][r - 1] != 0)
					return true;
				else
					return false;
			} else {
				if (tileInfo[c - 1][r] != 0 || tileInfo[c][r - 1] != 0 || tileInfo[c][r + 1] != 0)
					return true;
				else
					return false;
			}
		} else if (r == 0) {
			if (tileInfo[c - 1][r] != 0 || tileInfo[c + 1][r] != 0 || tileInfo[c][r + 1] != 0)
				return true;
			else
				return false;
		} else if (r == 127) {
			if (tileInfo[c - 1][r] != 0 || tileInfo[c + 1][r] != 0 || tileInfo[c][r - 1] != 0)
				return true;
			else
				return false;
		}
		return false;
	}

	public int[] ableTiles(int r, int c) {
		int itIsAble[] = new int[3];
		int upTile[] = new int[3], rightTile[] = new int[3], downTile[] = new int[3], leftTile[] = new int[3];
		if (c > 0 && c < 127 && r > 0 && r < 127) {
			upTile = tile_list[tileInfo[c - 1][r]][2];
			rightTile = tile_list[tileInfo[c][r + 1]][3];
			downTile = tile_list[tileInfo[c + 1][r]][0];
			leftTile = tile_list[tileInfo[c][r - 1]][1];
		} else if (c == 0) {
			if (r == 0) {
				rightTile = tile_list[tileInfo[c][r + 1]][3];
				downTile = tile_list[tileInfo[c + 1][r]][0];
				upTile = new int[] { 0, 0, 0 };
				leftTile = new int[] { 0, 0, 0 };
			} else if (r == 127) {
				downTile = tile_list[tileInfo[c + 1][r]][0];
				leftTile = tile_list[tileInfo[c][r - 1]][1];
				rightTile = new int[] { 0, 0, 0 };
				upTile = new int[] { 0, 0, 0 };
			} else {
				rightTile = tile_list[tileInfo[c][r + 1]][3];
				downTile = tile_list[tileInfo[c + 1][r]][0];
				leftTile = tile_list[tileInfo[c][r - 1]][1];
				upTile = new int[] { 0, 0, 0 };
			}
		} else if (c == 127) {
			if (r == 0) {
				upTile = tile_list[tileInfo[c - 1][r]][2];
				rightTile = tile_list[tileInfo[c][r + 1]][3];
				leftTile = new int[] { 0, 0, 0 };
				downTile = new int[] { 0, 0, 0 };
			} else if (r == 127) {
				upTile = tile_list[tileInfo[c - 1][r]][2];
				leftTile = tile_list[tileInfo[c][r - 1]][1];
				downTile = new int[] { 0, 0, 0 };
				rightTile = new int[] { 0, 0, 0 };
			} else {
				upTile = tile_list[tileInfo[c - 1][r]][2];
				rightTile = tile_list[tileInfo[c][r + 1]][3];
				leftTile = tile_list[tileInfo[c][r - 1]][1];
				downTile = new int[] { 0, 0, 0 };
			}
		} else if (r == 0) {
			upTile = tile_list[tileInfo[c - 1][r]][2];
			rightTile = tile_list[tileInfo[c][r + 1]][3];
			downTile = tile_list[tileInfo[c + 1][r]][0];
			leftTile = new int[] { 0, 0, 0 };
		} else if (r == 127) {
			upTile = tile_list[tileInfo[c - 1][r]][2];
			downTile = tile_list[tileInfo[c + 1][r]][0];
			leftTile = tile_list[tileInfo[c][r - 1]][1];
			rightTile = new int[] { 0, 0, 0 };
		}

		int tmp1[] = new int[3];
		int tmp2[] = new int[3];
		int i, j, k = 0;
		if (upTile[0] != 0) {
			if (rightTile[0] != 0) {
				for (i = 0; i < 3; i++)
					for (j = 0; j < 3; j++)
						if (upTile[i] == rightTile[j]) {
							tmp1[k] = upTile[i];
							k++;
						}
			} else
				for (i = 0; i < 3; i++)
					tmp1[i] = upTile[i];
		} else
			for (i = 0; i < 3; i++)
				tmp1[i] = rightTile[i];
		k = 0;
		if (downTile[0] != 0) {
			if (leftTile[0] != 0) {
				for (i = 0; i < 3; i++)
					for (j = 0; j < 3; j++)
						if (downTile[i] == leftTile[j]) {
							tmp2[k] = downTile[i];
							k++;
						}
			} else
				for (i = 0; i < 3; i++)
					tmp2[i] = downTile[i];
		} else
			for (i = 0; i < 3; i++)
				tmp2[i] = leftTile[i];
		k = 0;
		if (tmp1[0] != 0) {
			if (tmp2[0] != 0) {
				for (i = 0; i < 3; i++)
					for (j = 0; j < 3; j++)
						if (tmp1[i] == tmp2[j] && tmp1[i] != 0) {
							itIsAble[k] = tmp1[i];
							k++;
						}
			} else
				for (i = 0; i < 3; i++)
					itIsAble[i] = tmp1[i];
		} else
			for (i = 0; i < 3; i++)
				itIsAble[i] = tmp2[i];

		return itIsAble;
	}

	public void forcedPlay(int r, int c) {// 자동완성
		if (tileInfo[c][r+1] == 0) {
			int rightAble[] = ableTiles(r + 1, c);// 우측타일의 가능 타일
			if (rightAble[1] == 0) {// 즉, 가능한 타일이 한개인 경우
				jbtn[c][r + 1].setIcon(icons[rightAble[0]]);
				tileInfo[c][r + 1] = rightAble[0];
				Menu.writeLog("Auto : "+r + ", " + c + "\n");
				isWin(r+1, c);
				forcedPlay(r + 1, c);
			}
		}
		if (tileInfo[c][r-1] == 0) {
			int leftAble[] = ableTiles(r - 1, c);// 좌측타일의 가능 타일
			if (leftAble[1] == 0) {// 즉, 가능한 타일이 한개인 경우
				jbtn[c][r - 1].setIcon(icons[leftAble[0]]);
				tileInfo[c][r - 1] = leftAble[0];
				Menu.writeLog("Auto : "+r + ", " + c + "\n");
				isWin(r-1,c);
				forcedPlay(r - 1, c);
			}
		}
		if (tileInfo[c-1][r] == 0) {
			int upAble[] = ableTiles(r, c - 1);// 상측타일의 가능 타일
			if (upAble[1] == 0) {// 즉, 가능한 타일이 한개인 경우
				jbtn[c - 1][r].setIcon(icons[upAble[0]]);
				tileInfo[c - 1][r] = upAble[0];
				Menu.writeLog("Auto : "+r + ", " + c + "\n");
				isWin(r,c-1);
				forcedPlay(r, c - 1);
			}
		}
		if (tileInfo[c+1][r] == 0) {
			int downAble[] = ableTiles(r, c + 1);// 하측타일의 가능 타일
			if (downAble[1] == 0) {// 즉, 가능한 타일이 한개인 경우
				jbtn[c + 1][r].setIcon(icons[downAble[0]]);
				tileInfo[c + 1][r] = downAble[0];
				Menu.writeLog("Auto : "+r + ", " + c + "\n");
				isWin(r,c+1);
				forcedPlay(r, c + 1);
			}
		}
	}
	public void isWin(int r, int c){
		black_loop(r, c);
		white_loop(r, c);

	}
	public void black_loop(int r, int c){
		int x = r; int y = c;
		boolean loop = true;
		int count = 0;
		int xCount = 0; int yCount = 0;

		while(loop){
			loopCheck[y][x]++;
			System.out.println(x+","+y);
			if(xCount > 6 || xCount < -6 || yCount > 6 || yCount < -6){
				for(int i=0;i<128;i++)
					for(int j=0;j<128;j++)
						if(loopCheck[i][j] == 1)
							jbtn[i][j].setIcon(icons[tileInfo[i][j]+6]);
				Menu.writeLog("black win");
				for (int i = 0; i < 128; i++)
					for (int j = 0; j < 128; j++)
						jbtn[i][j].removeMouseListener(l);

				break;

			}
			switch(tileInfo[y][x]){
			case 1:
				if(count == 0){
					if(tileInfo[y+1][x] != 0){
						y++;
						yCount++;
					}
					else if(tileInfo[y-1][x] != 0){
						y--;
						yCount--;
					}
				}
				else if(loopCheck[y-1][x] == 1 && tileInfo[y+1][x] != 0){
					y++;
					yCount++;
				}
				else if(loopCheck[y+1][x] >0 && tileInfo[y-1][x] != 0){
					y--;
					yCount--;
				}
				else{
					//블랙 루프 아님
					loop = false;
				}
				break;
			case 2:
				if(count == 0){
					if(tileInfo[y][x+1] != 0){
						x++;
						xCount++;
					}
					else if(tileInfo[y][x-1] != 0){
						x--;
						xCount--;
					}
				}
				else if(loopCheck[y][x-1] == 1 && tileInfo[y][x+1] != 0){
					x++;
					xCount++;
				}
				else if(loopCheck[y][x+1] >0 && tileInfo[y][x-1] != 0){
					x--;
					xCount--;
				}

				else{
					//블랙 루프 아님
					loop = false;
				}
				break;
			case 3:
				System.out.println("3");
				System.out.println(loopCheck[66][67]);
				if(count == 0){
					if(tileInfo[y][x-1] != 0){
						x--;
						xCount--;
					}
					else if(tileInfo[y-1][x] != 0){
						y--;
						yCount--;
					}
				}

				else if(loopCheck[y-1][x] == 1 && tileInfo[y][x-1] != 0){
					x--;
					xCount--;

				}
				else if(loopCheck[y][x-1] >0 && tileInfo[y-1][x] != 0){
					y--;
					yCount--;
				}
				else{
					//블랙 루프 아님
					loop = false;
				}
				break;
			case 4:
				System.out.println("4");
				if(count == 0){
					if(tileInfo[y][x+1] != 0){
						x++;
						xCount++;
					}
					else if(tileInfo[y-1][x] != 0){
						y--;
						yCount--;
					}
				}

				else if(loopCheck[y][x+1] == 1 && tileInfo[y-1][x] != 0){
					y--;
					yCount--;
				}
				else if(loopCheck[y-1][x] >0 && tileInfo[y][x+1] != 0){
					x++;
					xCount++;
				}
				else{
					//블랙 루프 아님
					loop = false;
				}
				break;
			case 5:
				System.out.println("5");
				if(count == 0){
					if(tileInfo[y+1][x] != 0){
						y++;
						yCount++;
					}
					else if(tileInfo[y][x+1] != 0){
						x++;
						xCount++;
					}
				}

				else if(loopCheck[y+1][x] == 1 && tileInfo[y][x+1] != 0){
					x++;
					xCount++;
				}
				else if(loopCheck[y][x+1] >0 && tileInfo[y+1][x] != 0){
					y++;
					yCount++;
				}
				else{
					//블랙 루프 아님
					loop = false;
				}
				break;
			case 6:
				if(count == 0){
					if(tileInfo[y+1][x] != 0){
						y++;
						yCount++;
					}
					else if(tileInfo[y][x-1] != 0){
						x--;
						xCount--;
					}
				}
				else if(loopCheck[y][x-1] == 1 && tileInfo[y+1][x] != 0){
					y++;
					yCount++;
				}
				else if(loopCheck[y+1][x] >0 && tileInfo[y][x-1] != 0){
					x--;
					xCount--;
				}
				else{
					//블랙 루프 아님
					loop = false;
				}
				break;
			}

			if(x == r && y ==c &&count>2){
				//블랙 루프이므로 블랙 승리
				for(int  i=0;i<128;i++)
					for(int j=0;j<128;j++){
						if(loopCheck[i][j] > 0)
							jbtn[i][j].setIcon(icons[tileInfo[i][j]+6]);

						jbtn[i][j].removeMouseListener(l);

					}
				Menu.writeLog("black win");


				break;
			}

			count++;
		}
		clearLoopCheck();
	}
	public void white_loop(int r, int c){
		int x = r; int y = c;
		boolean loop = true;
		int count = 0;
		int xCount = 0; int yCount = 0;

		while(loop){
			loopCheck[y][x]++;
			if(xCount > 6 || xCount < -6 || yCount > 6 || yCount < -6){
				for(int i=0;i<128;i++)
					for(int j=0;j<128;j++)
						if(loopCheck[i][j] == 1)
							jbtn[i][j].setIcon(icons[tileInfo[i][j]+6]);
				Menu.writeLog("white win");
				for (int i = 0; i < 128; i++)
					for (int j = 0; j < 128; j++)
						jbtn[i][j].removeMouseListener(l);

				break;

			}
			switch(tileInfo[y][x]){
			case 1:
				if(count == 0){
					if(tileInfo[y][x+1] != 0){
						x++;
						xCount++;
					}
					else if(tileInfo[y][x-1] != 0){
						x--;
						xCount--;
					}
				}

				else if(loopCheck[y][x+1] ==1 && tileInfo[y][x-1] != 0){
					x--;
					xCount--;
				}
				else if(loopCheck[y][x-1] >0 && tileInfo[y][x+1] != 0){
					x++;
					xCount++;
				}
				else{
					//화이트 루프 아님
					loop = false;
				}
				break;
			case 2:
				if(count == 0){
					if(tileInfo[y+1][x] != 0){
						y++;
						yCount++;
					}
					else if(tileInfo[y-1][x] != 0){
						y--;
						yCount--;
					}
				}

				else if(loopCheck[y+1][x] ==1 && tileInfo[y-1][x] != 0){
					y--;
					yCount--;
				}
				else if(loopCheck[y-1][x] >0 && tileInfo[y+1][x] != 0){
					y++;
					yCount++;
				}
				else{
					// 루프 아님
					loop = false;
				}
				break;
			case 3:
				System.out.println("3");
				if(count == 0){
					if(tileInfo[y][x+1] != 0){
						x++;
						xCount++;
					}
					else if(tileInfo[y+1][x] != 0){
						y++;
						yCount++;
					}
				}
				else if(loopCheck[y+1][x] == 1 && tileInfo[y][x+1] != 0){
					x++;
					xCount++;
				}
				else if(loopCheck[y][x+1] >0 && tileInfo[y+1][x] != 0){
					y++;
					yCount++;
				}
				else{
					// 루프 아님
					loop = false;
				}
				break;
			case 4:
				System.out.println("4");
				if(count == 0){
					if(tileInfo[y][x-1] != 0){
						x--;
						xCount--;
					}
					else if(tileInfo[y+1][x] != 0){
						y++;
						yCount++;
					}
				}
				else if(loopCheck[y+1][x] ==1 && tileInfo[y][x-1] != 0){
					x--;
					xCount--;
				}
				else if(loopCheck[y][x-1] >0 && tileInfo[y+1][x] != 0){
					y++;
					yCount++;
				}

				else{
					// 루프 아님
					loop = false;
				}
				break;
			case 5:
				System.out.println("5");
				if(count == 0){
					if(tileInfo[y-1][x] != 0){
						y--;
						yCount--;
					}
					else if(tileInfo[y][x-1] != 0){
						x--;
						xCount--;
					}
				}
				else if(loopCheck[y][x-1] ==1 && tileInfo[y-1][x] != 0){
					y--;
					yCount--;
				}
				else if(loopCheck[y-1][x] >0 && tileInfo[y][x-1] != 0){
					x--;
					xCount--;
				}


				else{
					// 루프 아님
					loop = false;
				}
				break;
			case 6:
				System.out.println("6");
				if(count == 0){
					if(tileInfo[y-1][x] != 0){
						y--;
						yCount--;
					}
					else if(tileInfo[y][x+1] != 0){
						x++;
						xCount++;
					}
				}

				else if(loopCheck[y-1][x] ==1 && tileInfo[y][x+1] != 0){
					x++;
					xCount++;
				}
				else if(loopCheck[y][x+1] >0 && tileInfo[y-1][x] != 0){
					y--;
					yCount--;
				}
				else{
					// 루프 아님
					loop = false;
				}
				break;
			}

			if(x == r && y == c &&count>2){
				//화이트 루프이므로 화이트 승리
				for(int  i=0;i<128;i++)
					for(int j=0;j<128;j++){
						if(loopCheck[i][j] > 0)
							jbtn[i][j].setIcon(icons[tileInfo[i][j]+6]);

						jbtn[i][j].removeMouseListener(l);

					}
				Menu.writeLog("white win");


				break;
			}

			count++;
		}
		clearLoopCheck();
	}

	public void clearLoopCheck(){
		for(int i=0;i<128;i++)
			for(int j=0;j<128;j++)
				loopCheck[i][j] = 0;
	}

	public static void startgame(){                           
		for(int i=0;i<128;i++)
			for(int j=0;j<128;j++){
				jbtn[i][j].setIcon(icons[0]);  //스타트버튼 눌릴시 타일 변경
			}
		onoff=true;			//눌릴수있게
	}

}
