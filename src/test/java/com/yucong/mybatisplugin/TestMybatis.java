package com.yucong.mybatisplugin;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yucong.App;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class TestMybatis {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void test_findAll() {
        List<User> list = userMapper.selectList(null);
        list.forEach(System.out::println);
    }

    @Test
    public void test_findAll1() {
        int update = userMapper.update(new User().setName("tom"), Wrappers.<User>lambdaUpdate().eq(User::getId, 2));
        System.out.println(update);
    }

    @Test
    public void test_page() {

        System.out.println("----- baseMapper 自带分页 ------");
        Page<User> page = new Page<>(1, 5);
        Map<String, Object> params = new HashMap<>();
        params.put("age", 2);
        params.put("name", "tom");
        IPage<User> userIPage = userMapper.selectPage(page, Wrappers.<User>query().allEq(params));
        assertThat(page).isSameAs(userIPage);
        System.out.println("总条数 ------> " + userIPage.getTotal());
        System.out.println("当前页数 ------> " + userIPage.getCurrent());
        System.out.println("当前每页显示数 ------> " + userIPage.getSize());
        System.out.println(userIPage.getRecords());
        System.out.println("----- baseMapper 自带分页 ------");

    }

    @Test
    public void tests() {
        System.out.println("----- 普通查询 ------");
        List<User> plainUsers = userMapper.selectList(new QueryWrapper<User>().eq("role_id", 2L));
        List<User> lambdaUsers = userMapper.selectList(new QueryWrapper<User>().lambda().eq(User::getRoleId, 2L));
        Assert.assertEquals(plainUsers.size(), lambdaUsers.size());
        print(plainUsers);

        System.out.println("----- 带子查询(sql注入) ------");
        List<User> plainUsers2 = userMapper.selectList(new QueryWrapper<User>().inSql("role_id", "select id from role where id = 2"));
        List<User> lambdaUsers2 = userMapper.selectList(new QueryWrapper<User>().lambda().inSql(User::getRoleId, "select id from role where id = 2"));
        Assert.assertEquals(plainUsers2.size(), lambdaUsers2.size());
        print(plainUsers2);

        System.out.println("----- 带嵌套查询 ------");
        List<User> plainUsers3 =
                userMapper.selectList(new QueryWrapper<User>().nested(i -> i.eq("role_id", 2L).or().eq("role_id", 3L)).and(i -> i.ge("age", 20)));
        List<User> lambdaUsers3 = userMapper.selectList(
                new QueryWrapper<User>().lambda().nested(i -> i.eq(User::getRoleId, 2L).or().eq(User::getRoleId, 3L)).and(i -> i.ge(User::getAge, 20)));
        Assert.assertEquals(plainUsers3.size(), lambdaUsers3.size());
        print(plainUsers3);

        System.out.println("----- 自定义(sql注入) ------");
        List<User> plainUsers4 = userMapper.selectList(new QueryWrapper<User>().apply("role_id = 2"));
        print(plainUsers4);

        UpdateWrapper<User> uw = new UpdateWrapper<>();
        uw.set("email", null);
        uw.eq("id", 4);
        userMapper.update(new User(), uw);
        User u4 = userMapper.selectById(4);
        Assert.assertNull(u4.getEmail());
    }

    @Test
    public void lambdaQueryWrapper() {
        System.out.println("----- 普通查询 ------");
        List<User> plainUsers = userMapper.selectList(new LambdaQueryWrapper<User>().eq(User::getRoleId, 2L));
        List<User> lambdaUsers = userMapper.selectList(new QueryWrapper<User>().lambda().eq(User::getRoleId, 2L));
        Assert.assertEquals(plainUsers.size(), lambdaUsers.size());
        print(plainUsers);

        System.out.println("----- 带子查询(sql注入) ------");
        List<User> plainUsers2 = userMapper.selectList(new LambdaQueryWrapper<User>().inSql(User::getRoleId, "select id from role where id = 2"));
        List<User> lambdaUsers2 = userMapper.selectList(new QueryWrapper<User>().lambda().inSql(User::getRoleId, "select id from role where id = 2"));
        Assert.assertEquals(plainUsers2.size(), lambdaUsers2.size());
        print(plainUsers2);

        System.out.println("----- 带嵌套查询 ------");
        List<User> plainUsers3 = userMapper.selectList(
                new LambdaQueryWrapper<User>().nested(i -> i.eq(User::getRoleId, 2L).or().eq(User::getRoleId, 3L)).and(i -> i.ge(User::getAge, 20)));
        List<User> lambdaUsers3 = userMapper.selectList(
                new QueryWrapper<User>().lambda().nested(i -> i.eq(User::getRoleId, 2L).or().eq(User::getRoleId, 3L)).and(i -> i.ge(User::getAge, 20)));
        Assert.assertEquals(plainUsers3.size(), lambdaUsers3.size());
        print(plainUsers3);

        System.out.println("----- 自定义(sql注入) ------");
        List<User> plainUsers4 = userMapper.selectList(new QueryWrapper<User>().apply("role_id = 2"));
        print(plainUsers4);

        UpdateWrapper<User> uw = new UpdateWrapper<>();
        uw.set("email", null);
        uw.eq("id", 4);
        userMapper.update(new User(), uw);
        User u4 = userMapper.selectById(4);
        Assert.assertNull(u4.getEmail());
    }

    private <T> void print(List<T> list) {
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(System.out::println);
        }
    }

}
