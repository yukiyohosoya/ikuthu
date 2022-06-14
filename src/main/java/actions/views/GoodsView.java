package actions.views;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;

import constants.JpaConst;
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
    private ShopView shop;

    /**
     * 商品の名前
     */
    private String name;

    /**
     * 販売価格
     */
    private String sellingprice;
    /**
     * 仕入れ価格
     */
    private String purchaseprice;

    /**
     * 在庫数
     */
    private String stock;

    /**
     * 商品作成日
     */
    private String create_day;

    /**
     *商品画像ファイル名
     */
    private String picture;

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
