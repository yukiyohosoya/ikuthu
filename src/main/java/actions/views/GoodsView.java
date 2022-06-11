package actions.views;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * イベント情報について画面の入力値・出力値を扱うViewモデル
 *
 */
@Getter //全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter //全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor //引数なしコンストラクタを自動生成する(Lombok)
@AllArgsConstructor //全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する(Lombok)

public class GoodsView {
    /**
     * id
     */
    private Integer id;

    /**
     * 商品を作成したショップid
     */
    private ShopView shopid;

    /**
     * 商品の名前
     */
    private String name;

    /**
     * 販売価格
     */
    private Integer sellingprice;
    /**
     * 仕入れ価格
     */
    private Integer purchaseprice;

    /**
     * 商品作成日
     */
    private LocalDate create_day;

    /**
     *登録日時
     */
    private LocalDateTime createdAt;

    /**
     * 更新日時
     */
    private LocalDateTime updatedAt;

    /**
     *商品画像ファイル名
     */
    private String picture;

}
