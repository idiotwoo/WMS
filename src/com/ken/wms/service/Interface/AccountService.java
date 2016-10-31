package com.ken.wms.service.Interface;

import com.ken.wms.domain.User;

public interface AccountService {

	public void signIn(User user, String checkCode);
}
