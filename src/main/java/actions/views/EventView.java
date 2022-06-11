package actions.views;

import java.time.LocalDate;
import java.time.LocalDateTime;

import constants.JpaConst;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import models.Shop;


/**
 * イベント情報について画面の入力値・出力値を扱うViewモデル
 *
 */
@Getter //全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter //全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor //引数なしコンストラクタを自動生成する(Lombok)
@AllArgsConstructor //全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する(Lombok)

public class EventView {

    /**
     * id
     */
    private Integer id;

    /**
     * イベントを作成したショップ
     */
    private ShopView shop;

    /**
     *イベントの名前
     */
    private String name;

    /**
     * イベント日付
     */
    private LocalDate eventday;

    /**
     *登録日時
     */
    private LocalDateTime createdAt;

    /**
     * 更新日時
     */
    private LocalDateTime updatedAt;

}
