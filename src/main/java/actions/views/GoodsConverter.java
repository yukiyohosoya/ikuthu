package actions.views;

import java.util.ArrayList;
import java.util.List;

import constants.AttributeConst;
import constants.JpaConst;
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
                ShopConverter.toModel(gv.getShop()),
                gv.getName(),
                gv.getSellingprice(),
                gv.getPurchaseprice(),
                gv.getStock(),
                gv.getCreate_day(),
                gv.getPicture(),
                gv.getCreatedAt(),
                gv.getUpdatedAt(),
                gv.getDeleteFlag()== null
                ? null
                : gv.getDeleteFlag() == AttributeConst.DEL_FLAG_TRUE.getIntegerValue()
                        ? JpaConst.ALL_DEL_TRUE
                        : JpaConst.ALL_DEL_FALSE);
    }

    /**
     * イベントごとグッズViewモデルリストのインスタンスからDTOモデルのインスタンスを作成する
     * @param gv GoodsViewのインスタンスのList
     * @return GoodsのインスタンスのList
     */
    public static List<Goods>  toModelList(List<LimitedgoodsView> list){
        List<Goods> gl = new ArrayList<>();

        for (LimitedgoodsView g : list) {
            gl.add(toModel(g.getGoods()));
        }

        return gl;
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
                ShopConverter.toView(g.getShop()),
                g.getName(),
                g.getSellingprice(),
                g.getPurchaseprice(),
                g.getStock(),
                g.getCreate_day(),
                g.getPicture(),
                g.getCreatedAt(),
                g.getUpdatedAt(),
                g.getDeleteFlag()== null
                ? null
                : g.getDeleteFlag() == AttributeConst.DEL_FLAG_TRUE.getIntegerValue()
                        ? JpaConst.ALL_DEL_TRUE
                        : JpaConst.ALL_DEL_FALSE
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
        g.setShop(ShopConverter.toModel(gv.getShop()));
        g.setName(gv.getName());
        g.setSellingprice(gv.getSellingprice());
        g.setPurchaseprice(gv.getPurchaseprice());
        g.setStock(gv.getStock());
        g.setCreate_day( gv.getCreate_day());
        g.setPicture(gv.getPicture());
        g.setCreatedAt(gv.getCreatedAt());
        g.setUpdatedAt(gv.getUpdatedAt());
        g.setDeleteFlag(gv.getDeleteFlag());

    }

}