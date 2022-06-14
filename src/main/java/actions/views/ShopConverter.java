package actions.views;

import java.util.ArrayList;
import java.util.List;

import constants.AttributeConst;
import constants.JpaConst;

import models.Shop;

/**
 * ユーザーデータのDTOモデル⇔Viewモデルの変換を行うクラス
 *
 */

public class ShopConverter {
    /**
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param sv ShopViewのインスタンス
     * @return Shopのインスタンス
     */
    public static Shop toModel(ShopView sv) {

        return new Shop(
                sv.getId(),
                UserConverter.toModel(sv.getUser()),
                sv.getName(),
                sv.getPriorityflag(),
                sv.getDeleteFlag()== null
                ? null
                : sv.getDeleteFlag() == AttributeConst.DEL_FLAG_TRUE.getIntegerValue()
                        ? JpaConst.ALL_DEL_TRUE
                        : JpaConst.ALL_DEL_FALSE);
    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param u Shopのインスタンス
     * @return ShopViewのインスタンス
     */
    public static ShopView toView(Shop s) {

        if(s == null) {
            return null;
        }

        return new ShopView(
                s.getId(),
                UserConverter.toView(s.getUser()),
                s.getName(),
                s.getPriorityflag(),
                s.getDeleteFlag()== null
                ? null
                : s.getDeleteFlag() == AttributeConst.DEL_FLAG_TRUE.getIntegerValue()
                        ? JpaConst.ALL_DEL_TRUE
                        : JpaConst.ALL_DEL_FALSE
                );
     }

    /**
     * DTOモデルのリストからViewモデルのリストを作成する
     * @param list DTOモデルのリスト
     * @return Viewモデルのリスト
     */
    public static List<ShopView> toViewList(List<Shop> list) {
        List<ShopView> svl = new ArrayList<>();

        for (Shop s : list) {
            svl.add(toView(s));
        }

        return svl;
    }

    /**
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param e DTOモデル(コピー先)
     * @param sv Viewモデル(コピー元)
     */
    public static void copyViewToModel(Shop s, ShopView sv) {
        s.setId(sv.getId());
        s.setUser(UserConverter.toModel(sv.getUser()));
        s.setName(sv.getName());
        s.setPriorityflag(sv.getPriorityflag());
        s.setDeleteFlag(sv.getDeleteFlag());


    }

}