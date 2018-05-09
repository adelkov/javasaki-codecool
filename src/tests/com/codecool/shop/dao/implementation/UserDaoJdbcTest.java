package com.codecool.shop.dao.implementation;

import com.codecool.shop.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoJdbcTest {

    UserDaoJdbc userDaoJdbc;

    @BeforeEach
    void setUp() {
        User user1 = new User(
                "Dzsoli",
                "dzsoli@répa.com",
                "Jelszó"
        );
        User user2 = new User(
                "Juli",
                "juli@répa.com",
                "fffrrr"
        );
        userDaoJdbc = new UserDaoJdbc();
        userDaoJdbc.add(user1);
        userDaoJdbc.add(user2);
    }

    @AfterEach
    void tearDown(){
        userDaoJdbc.removeAll();
    }

    @Test
    void add_when_userAdded_then_allUsersIsOneItemLonger() {
        int beforSize = userDaoJdbc.getAll().size();
        userDaoJdbc.add(new User("tomika", "toma_email@ff", "fff"));
        int afterSize = userDaoJdbc.getAll().size();
        assertEquals(1, afterSize - beforSize);
    }

    @Test
    void remove_userRemoved_then_allUserIsOneItemShorter(){
        int beforSize = userDaoJdbc.getAll().size();
        userDaoJdbc.remove(userDaoJdbc.getLastId());
        int afterSize = userDaoJdbc.getAll().size();
        assertEquals(1,  beforSize - afterSize);
    }


}