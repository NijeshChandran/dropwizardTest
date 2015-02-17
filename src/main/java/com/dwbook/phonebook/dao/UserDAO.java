package com.dwbook.phonebook.dao;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;

public interface UserDAO {
	
	@SqlQuery("select count(*) from users where username = :userName and password = :password")
	int getUsers(@Bind("userName") String userName, @Bind("password") String password);
}
