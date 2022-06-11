package actions.views;

import java.util.ArrayList;
import java.util.List;


import models.Goods;

/**
 * イベントデータのDTOモデル⇔Viewモデルの変換を行うクラス
 * Limitedgoods
 */

public class GoodsConverter {
    /**
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param sv GoodsViewのインスタンス
     * @return Goodsのインスタンス
     */
    public static Goods toModel(GoodsView gv) {

        return new Goods(
                gv.getId(),
                ShopConverter.toModel(gv.getShopid()),
                gv.getName(),
                gv.getSellingprice(),
                gv.getPurchaseprice(),
                gv.getCreate_day(),
                gv.getCreatedAt(),
                gv.getUpdatedAt(),
                gv.getPicture()
                );
    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param u Goodsのインスタンス
     * @return GoodsViewのインスタンス
     */
    public static GoodsView toView(Goods g) {

        if(g == null) {
            return null;
        }

        return new GoodsView(
                g.getId(),
                ShopConverter.toView(g.getShopid()),
                g.getName(),
                g.getSellingprice(),
                g.getPurchaseprice(),
                g.getCreate_day(),
                g.getCreatedAt(),
                g.getUpdatedAt(),
                g.getPicture()
                );
     }

    /**
     * DTOモデルのリストからViewモデルのリストを作成する
     * @param list DTOモデルのリスト
     * @return Viewモデルのリスト
     */
    public static List<GoodsView> toViewList(List<Goods> list) {
        List<GoodsView> gvl = new ArrayList<>();

        for (Goods g : list) {
            gvl.add(toView(g));
        }

        return gvl;
    }

    /**
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param g DTOモデル(コピー先)
     * @param sv Viewモデル(コピー元)
     */
    public static void copyViewToModel(Goods g, GoodsView gv) {
        g.setId(gv.getId());
        g.setShopid(ShopConverter.toModel(gv.getShopid()));
        g.setName(gv.getName());
        g.setSellingprice(gv.getSellingprice());
        g.setPurchaseprice(gv.getPurchaseprice());
        g.setCreate_day( gv.getCreate_day());
        g.setCreatedAt(gv.getCreatedAt());
        g.setUpdatedAt(gv.getUpdatedAt());
        g.setPicture(gv.getPicture());

    }

}