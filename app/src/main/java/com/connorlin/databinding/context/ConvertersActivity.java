package com.connorlin.databinding.context;

import android.databinding.BindingConversion;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableBoolean;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import com.connorlin.databinding.R;
import com.connorlin.databinding.databinding.ActivityConvertersBinding;
import com.connorlin.databinding.model.User;

import java.util.ArrayList;
import java.util.List;

public class ConvertersActivity extends BaseActivity {

    private ObservableBoolean mIsError = new ObservableBoolean();

    ActivityConvertersBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_converters);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_converters);

        List<User> usrList = new ArrayList<>();
        usrList.add(new User("Connor", "Lin", 28));
        mBinding.setList(usrList);

        mIsError.set(true);
        mBinding.setIsError(mIsError);
    }

    public void onToggle(View view) {
        mIsError.set(!mIsError.get());
    }

    // 方法名可自定义，只需关心参数 User
    @BindingConversion
    public static String convertUserToCharSequence(User user) {
        return user.getFullName();
    }

    @BindingConversion
    public static ColorDrawable convertColorToDrawable(int color) {
        return new ColorDrawable(color);
    }
}
