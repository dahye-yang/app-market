package org.dadak.market.dao;

import java.util.List;

import org.dadak.market.model.OwnerTargetPick;
import org.dadak.market.model.Pick;
import org.dadak.market.model.PickAccount;

public interface PickDao {
	
	public int savePick(Pick one);
	
	//유저아이디와 상품아이디로
	public int deleteByOwnerAndTarget(OwnerTargetPick one);
	
	public int deleteById(int id);
	
	//상품의 찜회수
	public int countByTarget(int targetProductId);
	
	//찜하기 체크여부확인 (count)
	public int findByOwnerAndTarget(OwnerTargetPick one);
	
	public List<PickAccount> findAllByOwnerId(String id);
}
