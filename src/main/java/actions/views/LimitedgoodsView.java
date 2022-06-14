package actions.views;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * イベントごと商品テーブルについて画面の入力値・出力値を扱うViewモデル
 *
 */
@Getter //全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter //全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor //引数なしコンストラクタを自動生成する(Lombok)
@AllArgsConstructor //全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する(Lombok)

public class LimitedgoodsView {

    /**
     * id
     */
    private Integer id;

    /**
     * イベントid
     */
    private EventView event;

    /**
     *商品id
     */
    private GoodsView goods;

    /**
     * イベントごと並び順
     */
    private String evorder;
    /**
     * イベントごと価格
     */
    private String lm_sellingprice;
    /**
     * イベントごと販売数
     */
    private String soldgoods;

    /**
     *登録日時
     */
    private LocalDateTime createdAt;

    /**
     * 更新日時
     */
    private LocalDateTime updatedAt;

    /**
     * 削除されたかどうか（有効：0、削除済み：1）
     */
    private Integer deleteFlag;

}
