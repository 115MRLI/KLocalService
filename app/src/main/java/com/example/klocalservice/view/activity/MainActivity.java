package com.example.klocalservice.view.activity;

import android.os.Bundle;

import com.example.klocalservice.R;
import com.example.klocalservice.mvp.contract.MainContract;
import com.example.klocalservice.mvp.presenter.MainPresenter;

public class MainActivity extends BaseActivity implements MainContract.View {
    private MainContract.Presenter presenter;

    @Override
    protected void initVariables(Bundle bundle) {
        //绑定P类
        presenter = new MainPresenter(getActivity(), this, bundle);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void loadData() {
    }
}