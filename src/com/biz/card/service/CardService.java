package com.biz.card.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.biz.card.vo.CardVO;

public class CardService {

	List<String> CardSet; // 전체 카드를 셔플해서 담은 리스트
	List<CardVO> cList;   // 딜러의 점수와 플레이어의 점수 변수가 담긴 CardVO
	Scanner scan;

	public CardService() {
		// TODO Auto-generated constructor stub
		CardSet = new ArrayList();
		cList = new ArrayList();
		scan = new Scanner(System.in);
	}

	// 전체 카드덱을 쪼개서 리스트에 담고 섞는 method
	public void shuffleCard() {
		// 전체 카드덱을 하나의 배열에 담기
		String[] Card = { "DA", "D2", "D3", "D4", "D5", "D6", "D7", "D8", "D9", "D10", "DJ", "DQ", "DK", "HA", "H2",
				"H3", "H4", "H5", "H6", "H7", "H8", "H9", "H10", "HJ", "HQ", "HK", "SA", "S2", "S3", "S4", "S5", "S6",
				"S7", "S8", "S9", "S10", "SJ", "SQ", "SK", "CA", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "C10",
				"CJ", "CQ", "CK" };
		// 배열 안의 카드를 하나씩 나누어서 CardSet리스트에 차례대로 담기
		for (int i = 0; i < Card.length; i++) {
			String j = Card[i];
			CardSet.add(j);
		}
		// 랜덤 출력을 위한 섞기
		Collections.shuffle(CardSet);

	}

	// 카드 한 장씩 뽑는 method(뽑은 카드를 점수로 환산한 값을 return)
	public int pickCard() {

		int intScore = 0; // 뽑은 카드의 점수를 담을 변수

		for (int i = 0; i < 1; i++) {
			String k = CardSet.get(i); // 카드를 랜덤으로 담아두었던 리스트에서 카드를 한 장만 뽑기

			CardSet.remove(i); // 뽑은 카드는 다시 나오지 않도록 제거
			System.out.println(k);

			String[] letter = k.split(""); // 배열을 ""을 기준으로 잘라서
			String letter1 = letter[0]; // 카드의 글자(D,H,S,C)부분
			String letter2 = letter[1]; // 점수로 환산될 숫자부분

			if (letter2.equals("A"))
				intScore = 1; // A카드는 1점
			else if (letter2.equals("J") || letter2.equals("Q") || letter2.equals("K"))
				intScore = 10;
			// J,Q,K카드는 10점
			else if (letter2.equals("1"))
				intScore = 10;
			// 10카드는 10점
			else {
				intScore = Integer.valueOf(letter2); // 이외의 숫자카드는 숫자 그대로 점수로 환산
			}
		}
		return intScore; // 위의 기준으로 셋팅된 값을 return;
	}

	// 플레이어와 딜러의 기본카드 4장 셋팅하는 method
	public void setBasicCard(CardVO c) {

		int ps = 0; // 플레이어 스코어
		int ds = 0; // 딜러 스코어

		// 카드덱을 담은 리스트에서 4장 뽑기
		for (int i = 0; i < 4; i++) {
			// 첫번째로 뽑은 카드는 플레이어에게
			if (i == 0) {
				System.out.print("Player's Card1 :");
				ps = this.pickCard();
			}
			// 두번째로 뽑은 카드는 딜러에게
			if (i == 1) {
				System.out.print("Dealer's Card1 :");
				ds = this.pickCard();
			}
			// 세번째로 뽑은 카드는 플레이어에게
			if (i == 2) {
				System.out.print("Player's Card2 :");
				ps += this.pickCard();
			}
			// 네번째로 뽑은 카드는 딜러에게 전달
			if (i == 3) {
				System.out.print("Dealer's Card2 :");
				ds += this.pickCard();
			}
		}
		// 각각 2장씩 나눠가진 카드를 점수로 환산하여 첫번째 과정의 점수를 보여준다
		System.out.println(">> Player's Score(Turn1):" + ps);
		System.out.println(">> Dealer's Score(Turn1):" + ds);
		System.out.println("================================================");

		// 1차로 나온 점수 결과를 ScoreVO에 있는 각각의 변수에 저장
		c.setPlayerScore(ps);
		c.setDealerScore(ds);
	}

	// 기본 2장씩 카드 분배후 딜러가 카드를 추가로 뽑을 것인지 결정하는 method
	public void turn1Results(CardVO c) {

		int ps = c.getPlayerScore();
		int ds = c.getDealerScore();

		// 2장을 뽑아 그 점수가 16점을 넘지 않으면 무조건 추가로 한장을 뽑는다
		if (ds <= 16) {
			System.out.print("Dealer's Final Card:");
			int ds2 = this.pickCard();
			ds += ds2; // 기존 스코어에 추가로 뽑은 카드의 점수를 더해서
			System.out.println("Dealer's Final Score:" + ds);
			c.setDealerScore(ds); // 다시 ScoreVO에 딜러점수를 설정해준다
			cList.add(c);

		} else {
			System.out.println(">> Dealer's Turn is Over <<");
			this.drawPlayersCard(c);
		}
	}

	// 플레이어가 카드를 추가로 뽑는 method
	public void drawPlayersCard(CardVO c) {

		int ps = c.getPlayerScore();

		while (true) {
			System.out.println("================================================");
			System.out.println("!Player, Let's start a Next Turn!");
			System.out.println("DRAW or STOP");
			System.out.print("YOUR CHOICE >>");
			String strC = scan.nextLine(); // 추가로 뽑을 것인지(DRAW) 그만 멈출 것인지(STOP) 스캐너로 입력

			// 추가로 뽑는 것을 선택할 경우
			if (strC.equals("DRAW")) {
				System.out.print("Player's Next Card:");
				int ps2 = this.pickCard();
				ps += ps2;
				System.out.println("Player's Next Score:" + ps);
				c.setPlayerScore(ps); // 새롭게 더해진 스코어를 다시 ScoreVO에 플레이어 스코어로 셋팅
				cList.add(c);

				// 추가로 DRAW를 선택한 경우 21점을 넘으면 바로 딜러의 승리를 콘솔에 띄우고, 파이널 스코어 출력
				if (ps >= 21) {
					System.out.println(">> Player's score is over 21 <<");
					this.decideVictory(c);
					break;
					// 아닐 경우에는 계속
				} else
					continue;
			}
			// 추가로 카드 DRAW를 선택하지 않는 경우에는 바로 승패결정
			if (strC.equals("STOP")) {
				System.out.println(">> Player's Turn is Over <<");
				this.decideVictory(c);
				break;
			} else
				break;
		}
		System.out.println("GAME OVER");
	}

	// 최종 스코어를 보여주고 승패를 결정하는 method
	public void decideVictory(CardVO c) {

		int ps = c.getPlayerScore();
		int ds = c.getDealerScore();

		System.out.println("---------------------------------------");
		System.out.println("★ Player's final Score:" + ps + " ★");
		System.out.println("★ Dealer's final Score:" + ds + " ★");
		System.out.println("---------------------------------------");

		if (ps >= 21 && ds >= 21) {
			System.out.println("*****Dealer's WIN******");
			return;
		} else if (ps >= 21 && ds < 21) {
			System.out.println("*****Dealer's WIN******");
			return;
		} else if (ds >= 21 && ps < 21) {
			System.out.println("*****Player's WIN******");
			return;
		} else if (ps > ds && ps < 21) {
			System.out.println("*****Player's WIN******");
			return;
		} else if (ps < ds && ds < 21) {
			System.out.println("*****Dealer's WIN******");
			return;
		} else if (ps == ds) {
			System.out.println("Scores are TIED");
			return;
		}
	}

}
