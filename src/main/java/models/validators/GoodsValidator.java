package models.validators;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.Time;

import constants.MessageConst;
import services.ShopService;
import actions.views.EventView;
import actions.views.GoodsView;
import actions.views.ShopView;

import javax.servlet.http.Part;

/**
 * 日報インスタンスに設定されている値のバリデーションを行うクラス
 *
 */
public class GoodsValidator {
    /**
     * 日報インスタンスの各項目についてバリデーションを行う
     * @param rv 日報インスタンス
     * @return エラーのリスト
     */

    public static List<String> Validate(GoodsView gv,Part part){
        List<String> errors =new ArrayList<String>();

        //タイトルのチェック
        String titleError = validateTitle(gv.getName());
        if(!titleError.equals("")) {
            errors.add(titleError);
        }

        //仕入れ日のチェック
        String dayError = validateDay(gv.getCreate_day());
        if(!dayError.equals("")) {
            errors.add(dayError);
        }

        //価格のチェック
        String priceError = validatePrice(gv.getSellingprice(),gv.getPurchaseprice());
        if(!priceError.equals("")) {
            errors.add(priceError);
        }

        //在庫のチェック
        String stockError = validatestock(gv.getStock());
        if(!stockError.equals("")) {
            errors.add(stockError);
        }

        //画像のチェック
        String pictureError = validatepicture(gv.getPicture(),part);
        if(!pictureError.equals("")) {
            errors.add(pictureError);
        }

        return errors;

    }

    /**
     * タイトルに入力値があるかをチェックし、入力値がなければエラーメッセージを返却
     * @param title タイトル
     * @return エラーメッセージ
     */
    private static String validateTitle(String title) {
        if(title==null||title.equals("")) {
            return MessageConst.U_GSNAME.getMessage();
        }

      //エラーがない場合空文字を返却
        return "";
    }

    /**
     * 内容に入力値があるかをチェックし。、その後日付が入力されているのか。なければエラーメッセージを返却
     * @param day 内容
     * @return エラーメッセージ
     */
    private static String validateDay(String day) {
        if(day==null||day.equals("")) {
            return MessageConst.U_GSCRDAY.getMessage();
        }
        Pattern p = Pattern.compile("^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$");
        System.out.println(day);
        Matcher m = p.matcher(day);
        if(!m.find()) {
            return MessageConst.U_GSCRDAY_NO.getMessage();
        }

      //エラーがない場合空文字を返却
        return "";
    }

    /**
     * 価格関係の内容に入力値があるかをチェック。数字や入力がなければエラーメッセージを返却
     * @param sellingprice 内容
     * @param purchaseprice 内容
     * @return エラーメッセージ
     */
    private static String validatePrice(String sellingprice,String purchaseprice) {
        if(sellingprice==null||sellingprice.equals("")||purchaseprice==null||purchaseprice.equals("")) {
            return MessageConst.U_GS_PRNO.getMessage();
        }

        boolean sl = sellingprice.chars().allMatch( Character::isDigit );
        boolean pp = purchaseprice.chars().allMatch( Character::isDigit );
        if(!sl||!pp) {
            return MessageConst.U_GS_PRNOIN.getMessage();
        }

      //エラーがない場合空文字を返却
        return "";
    }
    /**
     * 在庫の内容に入力値があるかをチェック。数字や入力がなければエラーメッセージを返却
     * @param stock 内容
     * @return エラーメッセージ
     */
    private static String validatestock(String stock) {
        if(stock==null||stock.equals("")) {
            return MessageConst.U_GS_STOCKNO.getMessage();
        }

        boolean st = stock.chars().allMatch( Character::isDigit );
        if(!st) {
            return MessageConst.U_GS_STOCKNOIN.getMessage();
        }

      //エラーがない場合空文字を返却
        return "";
    }

    /**
     * 画像に関してのチェック。エーらがあればエラーメッセージを返却
     * @param part 内容
     * @return エラーメッセージ
     */
    private static String validatepicture(String file,Part part) {
        //パートオブジェクトが空じゃなかった場合実行
        if(!(part.getSize()==0)) {
            //サイズ1mb以下チェック
            if(part.getSize()>1048577) {
                return MessageConst.U_GS_PICTURESIZE.getMessage();
            }
            //拡張子だけを取得。拡張子がpngかjpgか
            String extension =file.substring(file.lastIndexOf("."));
            System.out.println(extension);

            if(!(".jpeg".equals(extension)||".png".equals(extension)||".jpg".equals(extension))) {
                return MessageConst.U_GS_NOPICTURE.getMessage();
            }
        }

        //エラーがない場合空文字を返却
        return "";
    }

}
