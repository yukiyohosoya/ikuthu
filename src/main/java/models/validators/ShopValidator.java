package models.validators;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import actions.views.ShopView;
import constants.MessageConst;

/**
 * Shopインスタンスに設定されている値のバリデーションを行うクラス
 *
 */
public class ShopValidator {

    /**
     * ショップインスタンスの各項目についてバリデーションを行う
     * @param sv ショップインスタンス
     * @return エラーのリスト
     */
    public static List<String> Validate(ShopView sv){
        List<String> errors =new ArrayList<String>();

        //タイトルのチェック
        String titleError = validateName(sv.getName());
        if(!titleError.equals("")) {
            errors.add(titleError);
        }



        return errors;

    }

    /**
     * ショップ名に入力値があるかをチェックし、入力値がなければエラーメッセージを返却
     * @param name 名前
     * @return エラーメッセージ
     */
    private static String validateName(String name) {
        if(name==null||name.equals("")) {
            return MessageConst.U_SHOPNAME.getMessage();
        }
        //名前が60文字以内か
        Pattern n = Pattern.compile("{1,60}");
        Matcher m = n.matcher(name);
        if(!m.find()) {
            return MessageConst.U_SHOPNAME_L.getMessage();
        }


        //入力値がある場合は空文字を返却
        return "";
    }

}
