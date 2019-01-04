package com.biz.card.exec;

import com.biz.card.service.CardService;
import com.biz.card.vo.CardVO;

public class CardExec {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CardService cs = new CardService();
		CardVO c = new CardVO();
		
		cs.shuffleCard();
		cs.setBasicCard(c);
		cs.turn1Results(c);
		cs.drawPlayersCard(c);
		
	}

}
