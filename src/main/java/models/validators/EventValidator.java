package models.validators;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.Time;

import constants.MessageConst;
import services.ShopService;
import actions.views.EventView;
import actions.views.ShopView;

/**
 * 日報インスタンスに設定されている値のバリデーションを行うクラス
 *
 */
public class EventValidator {
    /**
     * 日報インスタンスの各項目についてバリデーションを行う
     * @param rv 日報インスタンス
     * @return エラーのリスト
     */

    public static List<String> Validate(EventView ev){
        List<String> errors =new ArrayList<String>();

        //タイトルのチェック
        String titleError = validateTitle(ev.getName());
        if(!titleError.equals("")) {
            errors.add(titleError);
        }

        //内容のチェック
        String contentError = validateDay(ev.getEventday());
        if(!contentError.equals("")) {
            errors.add(contentError);
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
            return MessageConst.U_EVENAME.getMessage();
        }

        //入力値がある場合は空文字を返却
        return "";
    }

    /**
     * 内容に入力値があるかをチェックし。、その後日付が入力されているのか。なければエラーメッセージを返却
     * @param day 内容
     * @return エラーメッセージ
     */
    private static String validateDay(String day) {
        if(day==null||day.equals("")) {
            return MessageConst.U_EVEDAY.getMessage();
        }
        Pattern p = Pattern.compile("^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$");
        System.out.println(day);
        Matcher m = p.matcher(day);
        if(!m.find()) {
            return MessageConst.U_EVEDAY_NO.getMessage();
        }

        //入力値がある場合は空文字を返却
        return "";
    }


}
