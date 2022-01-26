package com.common.manager.test;

import com.common.manager.common.ExistExcelUtil;
import com.common.manager.service.common.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class main {
    @Autowired
    IUserService userService;
    @Test
    public void test(){
        userService.get(1);
    }
}
