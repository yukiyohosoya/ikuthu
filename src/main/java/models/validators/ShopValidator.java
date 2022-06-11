package models.validators;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import actions.views.ShopView;
import constants.MessageConst;
import services.ShopService;

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
    public static List<String> Validate(ShopView sv,ShopService service){
        List<String> errors =new ArrayList<String>();

        //タイトルのチェック
        String titleError = validateName(sv,service);
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
    private static String validateName(ShopView sv,ShopService service) {
        String name = sv.getName();
        if(name==null||name.equals("")) {
            return MessageConst.U_SHOPNAME.getMessage();
        }
        //名前が60文字以内か
        Pattern n = Pattern.compile("{1,60}");
        Matcher m = n.matcher(name);
        if(!m.find()) {
            return MessageConst.U_SHOPNAME_L.getMessage();
        }

        Long shopnameCount = isDuplicateShopname(sv,service);
        System.out.println("shopnameCount数は"+ shopnameCount);
        //同一社員番号がすでに登録されている場合はエラーメッセージを返却
        if(shopnameCount > 0) {
            return MessageConst.U_SHOPNAME_TRUE.getMessage();
        }


        //入力値がある場合は空文字を返却
        return "";
    }

    /**
     * @param service ShopServiceのインスタンス
     * @param name　ショップ名
     * @return ショップテーブルに登録されている同一名のデータの件数
     *r
     */

    private static Long isDuplicateShopname(ShopView sv,ShopService service) {

        long employeeCount=service.countByShop(sv.getUser(),sv.getName());
        return employeeCount;
    }

}
