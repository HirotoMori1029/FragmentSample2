package com.websarva.wings.android.fragmentsample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class MenuThanksFragment extends Fragment {

    private boolean _isLayoutXlarge = true;
    @Override
    public void onCreate(Bundle savedInstaceState) {
        //親クラスのonCreateの呼び出し。
        super.onCreate(savedInstaceState);
        //フラグメントマネージャーを作成
        FragmentManager manager = getFragmentManager();
        //フラグメントマネージャーからメニューリストフラグメントを取得
        MenuListFragment menuListFragment = (MenuListFragment) manager.findFragmentById(R.id.fragmentMenuList);
        //menuListFragmentがnullなら
        if (menuListFragment == null) {
            //画面判定フラグを通常画面とする
            _isLayoutXlarge = false;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //このフラグメントが所属しているアクティビティオブジェクトを取得
        Activity parentActivity = getActivity();
        //このフラグメントで表示する画面をXMLファイルからインフレートする
        View view = inflater.inflate(R.layout.fragment_menu_thanks, container, false);

        Bundle extras;
        //大画面の場合
        if (_isLayoutXlarge) {
            //フラグメントマネージャーを取得
            extras = getArguments();
        }
        //通常の場合
        else {
            Intent intent = parentActivity.getIntent();
            //インテントから引継ぎデータをまとめたもの(Bundleオブジェクト)を取得
            extras = intent.getExtras();
        }


        //注文した定食名と金額変数を用意
        String menuName = "";
        String menuPrice = "";
        //引継ぎデータ、Bundleオブジェクトが存在すれば、
        if (extras != null) {
            menuName = extras.getString("menuName");
            menuPrice = extras.getString("menuPrice");
        }
        //定食名と金額を表示させるtextviewを取得
        TextView tvMenuName = view.findViewById(R.id.tvMenuName);
        TextView tvMenuPrice = view.findViewById(R.id.tvMenuPrice);

        //Textviewに定食名と金額を表示
        tvMenuName.setText(menuName);
        tvMenuPrice.setText(menuPrice);

        //戻るボタンを取得
        Button btBackButton = view.findViewById(R.id.btBackButton);
        //戻るボタンにリスナを登録
        btBackButton.setOnClickListener(new ButtonClickListener());
        //インフレートした画面を戻り値として返す
        return view;
    }

    //ボタンが押されたときの処理が記述されたメンバクラス
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (_isLayoutXlarge) {
                //フラグメントマネージャーを取得
                FragmentManager manager = getFragmentManager();
                //フラグメントトランザクションを開始
                FragmentTransaction transaction = manager.beginTransaction();
                //自分自身を削除
                transaction.remove(MenuThanksFragment.this);
                //フラグメントトランザクションのコミット
                transaction.commit();
            }
            else {
                //このフラグメントが所属するアクティビティオブジェクトを取得
                Activity parentActivity = getActivity();
                //自分が所属するアクティビティを終了。
                parentActivity.finish();
            }
        }
    }








}