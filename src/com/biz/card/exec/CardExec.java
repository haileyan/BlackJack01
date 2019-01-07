package com.biz.card.exec;

import com.biz.card.service.CardService;
import com.biz.card.vo.CardVO;

public class CardExec {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CardService cs = new CardService();
		CardVO c = new CardVO();
		
		cs.shuffleCard();	// 전체 카드덱을 쪼개서 리스트에 담고 섞는 method
		cs.setBasicCard(c);	// 플레이어와 딜러의 기본카드 4장 셋팅하는 method
		cs.turn1Results(c); // 기본 2장씩 카드 분배후 딜러가 카드를 추가로 뽑을 것인지 결정하는 method
		cs.drawPlayersCard(c); // 플레이어가 카드를 추가로 뽑는 method
	}
}
