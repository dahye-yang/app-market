package org.dadak.market.dao;

import org.dadak.market.model.Account;

public interface AccountDao {
	
	public int saveAccount(Account account);
	
	public int update(Account account);

	public Account findByAccountId(String id);
	
}
