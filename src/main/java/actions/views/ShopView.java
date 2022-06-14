package actions.views;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import models.User;


/**
 * ユーザー情報について画面の入力値・出力値を扱うViewモデル
 *
 */
@Getter //全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter //全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor //引数なしコンストラクタを自動生成する(Lombok)
@AllArgsConstructor //全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する(Lombok)

public class ShopView {

    /**
     * id
     */
    private Integer id;

    /**
     * ショップを登録したユーザー
     */
    private UserView user;

    /**
     * ショップ名
     */
    private String name;

    /**
     * ショップ優先フラグ
     */
    private Integer priorityflag;

    /**
     * 削除されたかどうか（有効：0、削除済み：1）
     */
    private Integer deleteFlag;



}
