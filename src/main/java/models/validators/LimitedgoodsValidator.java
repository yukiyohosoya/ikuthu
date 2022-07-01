package models.validators;

import java.util.ArrayList;
import java.util.List;

import constants.MessageConst;

/**
 * lmevリストに設定されている値のバリデーションを行うクラス
 *
 */
public class LimitedgoodsValidator {
    /**
     * lmevリストの各項目についてバリデーションを行う
     * @param gsid_list チェックボックスから拾ったグッズインスタンスのidリスト(String型)
     * @param sellingprice_list チェックボックスから拾ったイベントごとグッズ価格のリスト
     * @param soldgoods_list チェックボックスから拾ったイベントごとグッズ販売数のリスト
     * @return  エラーのリスト
     */

    public static List<String> Validate(String[] gsid_list,String[]sellingprice_list,String[]soldgoods_list){
        List<String> errors =new ArrayList<String>();

        //配列自体のnullのチェック
        String nullError = validateNull(gsid_list,sellingprice_list,soldgoods_list);
        if(!nullError.equals("")) {
            errors.add(nullError);
        }

        return errors;

    }

    /**
     * lmevリストのデリートバリデーションを行う
     * @param gsid_list チェックボックスから拾ったグッズインスタンスのidリスト(String型)
     * @return  エラーのリスト
     */

    public static List<String> deleteValidate(String[] gsid_list){
        List<String> errors =new ArrayList<String>();

        //配列自体のnullのチェック
        String nullError = validategsidlistNull(gsid_list);
        if(!nullError.equals("")) {
            errors.add(nullError);
        }

        return errors;

    }

    /**
     * タイトルに入力値があるかをチェックし、入力値がなければエラーメッセージを返却。
     * さらに中の配列もチェックしてnullがないかチェック
     * @param gsid_list グッズID配列
     * @param sellingprice_list 価格配列
     * @return エラーメッセージ
     */
    private static String validateNull(String[] gsid_list,String[]sellingprice_list,String[]soldgoods_list){
        if(gsid_list==null||sellingprice_list==null||soldgoods_list==null) {
            return MessageConst.U_LEGSNULL.getMessage();
        }
        //配列がnullじゃなかったら中身をチェック
        for (int i = 0; i < sellingprice_list.length; i++){
            //空じゃない？
            if(sellingprice_list[i]==null||sellingprice_list[i].equals("")||
                    sellingprice_list[i]==null||sellingprice_list[i].equals("")||
                            soldgoods_list[i]==null||soldgoods_list[i].equals("")) {
                return MessageConst.U_LEGSNOPRICE.getMessage();
            }
            //数字？
            boolean sl = sellingprice_list[i].chars().allMatch( Character::isDigit );
            boolean so = soldgoods_list[i].chars().allMatch( Character::isDigit );
            if(!sl||!so) {
                return MessageConst.U_LEGS_PRNOIN.getMessage();
            }
            System.out.println(sellingprice_list[i]);
        }

        //入力値がある場合は空文字を返却
        return "";
    }

    /**
     * タイトルに入力値があるかをチェックし、入力値がなければエラーメッセージを返却。
     * さらに中の配列もチェックしてnullがないかチェック
     * @param gsid_list グッズID配列
     * @return エラーメッセージ
     */
    private static String validategsidlistNull(String[] gsid_list){
        if(gsid_list==null) {
            return MessageConst.U_LEGSNULL.getMessage();
        }

        //入力値がある場合は空文字を返却
        return "";
    }

}
