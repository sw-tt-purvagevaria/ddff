package com.pg.alldemo;

/**
 * Created by test on 12/3/18.
 */
@Dao
public interface OperationManager {

    @Query("SELECT * FROM user")
    List<User> getAllUserData();

    @Insert
    void insertData(List<User> user);

    @Insert
    void insertSingleUserData(User user);

    @Query("SELECT * FROM UserDetail WHERE user_id = :id")
    UserDetail getUserDetail(int id);

    @Insert
    void insertSingleUserDetailData(UserDetail userDetail);
}
