package actions.views;

import java.util.ArrayList;
import java.util.List;


import models.Limitedgoods;

/**
 * イベントデータのDTOモデル⇔Viewモデルの変換を行うクラス
 * Limitedgoods
 */

public class LimitedgoodsConverter {
    /**
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param sv LimitedgoodsViewのインスタンス
     * @return Limitedgoodsのインスタンス
     */
    public static Limitedgoods toModel(LimitedgoodsView lgv) {

        return new Limitedgoods(
                lgv.getId(),
                EventConverter.toModel(lgv.getEvent()),
                GoodsConverter.toModel(lgv.getGoods()),
                lgv.getEvorder(),
                lgv.getLm_sellingprice(),
                lgv.getSoldgoods(),
                lgv.getCreatedAt(),
                lgv.getUpdatedAt()
                );
    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param u Limitedgoodsのインスタンス
     * @return LimitedgoodsViewのインスタンス
     */
    public static LimitedgoodsView toView(Limitedgoods lg) {

        if(lg == null) {
            return null;
        }

        return new LimitedgoodsView(
                lg.getId(),
                EventConverter.toView(lg.getEvent()),
                GoodsConverter.toView(lg.getGoods()),
                lg.getEvorder(),
                lg.getLm_sellingprice(),
                lg.getSoldgoods(),
                lg.getCreatedAt(),
                lg.getUpdatedAt()
                );
     }

    /**
     * DTOモデルのリストからViewモデルのリストを作成する
     * @param list DTOモデルのリスト
     * @return Viewモデルのリスト
     */
    public static List<LimitedgoodsView> toViewList(List<Limitedgoods> list) {
        List<LimitedgoodsView> gvl = new ArrayList<>();

        for (Limitedgoods g : list) {
            gvl.add(toView(g));
        }

        return gvl;
    }

    /**
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param lg DTOモデル(コピー先)
     * @param sv Viewモデル(コピー元)
     */
    public static void copyViewToModel(Limitedgoods lg, LimitedgoodsView lgv) {
        lg.setId(lgv.getId());
        lg.setEvent(EventConverter.toModel(lgv.getEvent()));
        lg.setGoods(GoodsConverter.toModel(lgv.getGoods()));
        lg.setEvorder(lgv.getEvorder());
        lg.setLm_sellingprice(lgv.getLm_sellingprice());
        lg.setSoldgoods(lgv.getSoldgoods());
        lg.setCreatedAt(lgv.getCreatedAt());
        lg.setUpdatedAt(lgv.getUpdatedAt());

    }

}