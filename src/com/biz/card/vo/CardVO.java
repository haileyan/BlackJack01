package com.biz.card.vo;

public class CardVO {

	int playerScore;  // 플레이어의 점수
	int dealerScore;  // 딜러의 점수
	
	public int getPlayerScore() {
		return playerScore;
	}

	public void setPlayerScore(int playerScore) {
		this.playerScore = playerScore;
	}

	public int getDealerScore() {
		return dealerScore;
	}

	public void setDealerScore(int dealerScore) {
		this.dealerScore = dealerScore;
	}

	@Override
	public String toString() {
		return "CardVO [playerScore=" + playerScore + ", dealerScore=" + dealerScore + "]";
	}
	
}
