package com.connorlin.databinding.context;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.connorlin.databinding.R;
import com.connorlin.databinding.databinding.ActivityCombineBinding;
import com.connorlin.databinding.handler.ContextHandler;
import com.connorlin.databinding.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CombineActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_combine);

        ActivityCombineBinding baseBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_combine);

        User user = new User("connor", "lin", 28);
        baseBinding.setUser(user);

        baseBinding.setTest("Java lang");
        baseBinding.setNum(1);

        // 容器类
        List<User> mUserList = new ArrayList<>();
        mUserList.add(user);
        mUserList.add(user);
        mUserList.add(user);
        baseBinding.setUserList(mUserList);

        Map<String, String> map = new HashMap<>();
        map.put("1", "map1");
        map.put("2", "map2");
        map.put("3", "map3");
        baseBinding.setMap(map);

        // Context
        ContextHandler handler = new ContextHandler();
        baseBinding.setHandler(handler);
    }
}
