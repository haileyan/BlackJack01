package com.biz.card.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.biz.card.vo.CardVO;

public class CardService {

	List<String> CardSet; // 전체 카드를 셔플해서 담은 리스트
	List<String> BasicCard; // 
	List<String> PlayerCard;
	List<String> DealerCard;
	List<CardVO> cList;
	Scanner scan;
	
	
	public CardService() {
		// TODO Auto-generated constructor stub
		CardSet = new ArrayList();
		BasicCard = new ArrayList();
		PlayerCard = new ArrayList();
		DealerCard = new ArrayList();
		cList = new ArrayList();
		scan = new Scanner(System.in);
	}
	
	// 카드 뽑기
	public void shuffleCard() {

		String[] Card = {"DA", "D2", "D3", "D4", "D5", "D6", "D7", "D8", "D9","D10","DJ","DQ","DK",
					"HA", "H2", "H3", "H4", "H5", "H6", "H7","H8", "H9","H10","HJ","HQ","HK",
					"SA","S2", "S3", "S4", "S5", "S6", "S7", "S8", "S9","S10","SJ","SQ","SK",
					"CA","C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9","C10","CJ","CQ","CK"};
		for(int i = 0; i<Card.length; i++) {
			String j = Card[i];
			CardSet.add(j);
		}
		
		Collections.shuffle(CardSet);
	
	}
	
	// 카드 한장씩 뽑기(뽑은 카드를 점수로 환산한 값을 return)
	public int pickCard() {
		
		int intScore = 0;
		
		for(int i = 0; i<1; i++) {
			String k = CardSet.get(i);
		
			CardSet.remove(i);
			System.out.println(k);
			
			String[] letter = k.split("");
			String letter1 = letter[0];
			String letter2 = letter[1];
			
			if(letter2.equals("A")) intScore = 1;
			else if(letter2.equals("J") || letter2.equals("Q") || letter2.equals("K")) intScore = 10;
			else if(letter2.equals("1")) intScore = 10;
			else {
				intScore = Integer.valueOf(letter2);
			} 
		}
		return intScore;
	}
	
	// 기본 카드 4장 셋팅(처음으로 ds,ps 셋팅)
	public void setBasicCard(CardVO c) {
		
		int ps = 0;
		int ds = 0;
		
		for(int i = 0; i<4; i++) {
			if(i == 0) {
				System.out.print("Player's Card1 :");
				ps = this.pickCard(); 
			}
			if(i == 1) {
				System.out.print("Dealer's Card1 :");
				ds = this.pickCard();
			}
			if(i == 2) {
				System.out.print("Player's Card2 :");
				ps += this.pickCard();
			}
			if(i == 3) {
				System.out.print("Dealer's Card2 :");
				ds += this.pickCard();	
			}
		}
		System.out.println(">> Player's Score(Turn1):" + ps);
		System.out.println(">> Dealer's Score(Turn1):" + ds);
		System.out.println("================================================");
		c.setPlayerScore(ps);
		c.setDealerScore(ds);
	}

	// 기본 2장씩 카드 분배후 플레이어가 추가로 뽑을 것인지 선택
	public void turn1Results(CardVO c) {
		
			int ps = c.getPlayerScore();
			int ds = c.getDealerScore();
			
			if(ds <= 16) {
				System.out.print("Dealer's Final Card:");
				int ds2 = this.pickCard();
				ds += ds2;
				System.out.println("Dealer's Final Score:" + ds);
				c.setDealerScore(ds2);
				cList.add(c);
			} else {
				System.out.println(">> Dealer's Turn is Over <<");
				this.drawPlayersCard(c);
			}
	}
	
	// 플레이어 추가 카드뽑기
	public void drawPlayersCard(CardVO c) {
		
		int ps = c.getPlayerScore();
		
		while(true) {
			System.out.println("================================================");
			System.out.println("!Player, Let's start a Next Turn!");
			System.out.println("DRAW or STOP");
			System.out.print("YOUR CHOICE >>");
			String strC = scan.nextLine();
			
			if(strC.equals("DRAW")) {
				System.out.print("Player's Next Card:");
				int ps2 = this.pickCard();
				ps += ps2;
				System.out.println("Player's Next Score:" + ps); 
				c.setPlayerScore(ps);
				cList.add(c);
				if(ps >21) {
					System.out.println("Player's Lose");
					this.decideVictory(c);
					break;
				} else continue;	
			} 
			if(strC.equals("STOP")) {
				System.out.println(">> Player's Turn is Over <<");
				this.decideVictory(c);
				break;
			}
		} 
		System.out.println("GAME OVER");
	}

	// 승패결정
	public void decideVictory(CardVO c) {
		
		int ps = c.getPlayerScore();
		int ds = c.getDealerScore();
		
			System.out.println("---------------------------------------");
			System.out.println("★ Player's final Score:" + ps + " ★");
			System.out.println("★ Dealer's final Score:" + ds + " ★");
			System.out.println("---------------------------------------");
			
			if(ps > ds && ps < 21) {
				System.out.println("*****Player's WIN******");
				return;
			} else if (ps < ds && ds < 21) {
				System.out.println("*****Dealer's WIN******");
				return;
			} else if (ps == ds) {
				System.out.println("Scores are TIED");
			} else return;
			
		}
	
}
