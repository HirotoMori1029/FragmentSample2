package com.websarva.wings.android.fragmentsample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MenuListFragment extends Fragment {
    //大画面かどうかの判定フラグ
    private boolean _isLayoutXLarge = true;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        //親クラスメソッドの呼び出し
        super.onActivityCreated(savedInstanceState);
        //このフラグメントが所属するアクティビティオブジェクトを取得
        Activity parentActivity = getActivity();
        //自分が所属するアクティビティからmenuThanksFrameを取得
        View menuThanksFrame = parentActivity.findViewById(R.id.menuThanksFrame);
        //menuThanksFrameがnullなら
        if (menuThanksFrame == null) {
            _isLayoutXLarge = false;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //このフラグメントが所属するアクティビティオブジェクトを取得
        Activity parentActivity = getActivity();
        //フラグメントで表示する画面をXMLファイルからインフレートする
        View view = inflater.inflate(R.layout.fragment_menu_list, container, false);
        //画面部品Listviewを取得
        ListView lvMenu = view.findViewById(R.id.lvMenu);
        //SimpleAdapterで使用するListオブジェクトを用意
        List<Map<String, String>> menuList = new ArrayList<>();
        //menuListデータ生成処理
        Map<String, String> menu = new HashMap<>();
        menu.put("name", "からあげ定食");
        menu.put("price", "800円");
        menuList.add(menu);

        menu = new HashMap<>();
        menu.put("name", "ハンバーグ定食");
        menu.put("price", "850円");
        menuList.add(menu);

        for (int i=3; i < 18; i++) {
            menu = new HashMap<>();
            menu.put("name", i +"番目定食");
            menu.put("price", i*100 + "円");
            menuList.add(menu);
        }

        //SimpleAdapter用の4,5データを用意
        String[] from = {"name", "price"};
        int[] to = {android.R.id.text1, android.R.id.text2};
        //アダプター
        SimpleAdapter adapter = new SimpleAdapter(parentActivity, menuList,android.R.layout.simple_list_item_2, from, to);
        lvMenu.setAdapter(adapter);
        //リスナの登録
        lvMenu.setOnItemClickListener(new ListItemClickListener());
        //inflateされた画面を戻り値として返す
        return view;
    }

    private class ListItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Map<String, String> item = (Map<String, String>) parent.getItemAtPosition(position);
            String menuName = item.get("name");
            String menuPrice = item.get("price");
            //このフラグメントが所属するアクティビティオブジェクトを取得
            Activity parentActivity = getActivity();
            //引継ぎデータをまとめて格納できるBundleオブジェクトを生成
            Bundle bundle = new Bundle();
            //Bunedleオブジェクトに引継ぎデータを格納
            bundle.putString("menuName", menuName);
            bundle.putString("menuPrice", menuPrice);

            //大画面の場合。
            if(_isLayoutXLarge) {
                //FragmentManagerの取得
                FragmentManager manager = getFragmentManager();
                //FragmentTransaction Start
                FragmentTransaction transaction = manager.beginTransaction();
                //注文完了フラグメントを生成
                MenuThanksFragment menuThanksFragment = new MenuThanksFragment();
                //引継ぎデータを注文完了フラグメントに格納
                menuThanksFragment.setArguments(bundle);
                //生成した注文完了フラグメントをmenuThanksLayout部品に追加
                transaction.replace(R.id.menuThanksFrame, menuThanksFragment);
                //フラグメントトランザクションのコミット
                transaction.commit();
            }
            //通常の場合
            else {
                //Intentオブジェクトを生成
                Intent intent = new Intent(parentActivity, MenuThanksActivity.class);
                //第二画面に送るデータを格納
                intent.putExtras(bundle);
                //第二画面の起動
                startActivity(intent);
            }

        }
    }


}