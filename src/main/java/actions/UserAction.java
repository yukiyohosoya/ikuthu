package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.UserView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;
import constants.PropertyConst;
import services.UserService;


public class UserAction extends ActionBase{

        private UserService service;

        /**
         * メソッドを実行する
         */
        @Override
        public void process() throws ServletException, IOException {

            service = new UserService();

            //メソッドを実行
            invoke();

            service.close();
        }

        /**
         * 一覧画面を表示する
         * @throws ServletException
         * @throws IOException
         */
        public void index() throws ServletException, IOException {

            //指定されたページ数の一覧画面に表示するデータを取得
            int page = getPage();
            List<UserView> users = service.getPerPage(page);

            //全てのユーザーデータの件数を取得
            long userCount = service.countAll();

            putRequestScope(AttributeConst.USERS, users); //取得したユーザーデータ
            putRequestScope(AttributeConst.US_COUNT, userCount); //全てのユーザーデータの件数
            putRequestScope(AttributeConst.PAGE, page); //ページ数
            putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1ページに表示するレコードの数

            //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
            String flush = getSessionScope(AttributeConst.FLUSH);
            if (flush != null) {
                putRequestScope(AttributeConst.FLUSH, flush);
                removeSessionScope(AttributeConst.FLUSH);
            }

            //一覧画面を表示
            forward(ForwardConst.FW_US_INDEX);

        }


        /**
         * 新規登録画面を表示する
         * @throws ServletException
         * @throws IOException
         */
        public void entryNew() throws ServletException, IOException {

            putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
            putRequestScope(AttributeConst.USER, new UserView()); //空のユーザーインスタンス

            //新規登録画面を表示
            forward(ForwardConst.FW_US_NEW);
           }



        /**
         * 新規登録を行う
         * @throws ServletException
         * @throws IOException
         */
        public void create() throws ServletException, IOException {


                //パラメータの値を元にユーザー情報のインスタンスを作成する
                UserView uv = new UserView(
                        null,
                        getRequestParam(AttributeConst.US_NAME),
                        getRequestParam(AttributeConst.US_MAIL),
                        getRequestParam(AttributeConst.US_PASS),
                        null,
                        null,
                        AttributeConst.DEL_FLAG_FALSE.getIntegerValue());

                String pass_k =getRequestParam(AttributeConst.US_PASS_K);
                //アプリケーションスコープからpepper文字列を取得
                String pepper = getContextScope(PropertyConst.PEPPER);

                //ユーザー情報登録
                List<String> errors = service.create(uv, pepper,pass_k);

                if (errors.size() > 0) {
                    //登録中にエラーがあった場合

                    putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                    putRequestScope(AttributeConst.USER, uv); //入力されたユーザー情報
                    putRequestScope(AttributeConst.ERR, errors); //エラーのリスト

                    //新規登録画面を再表示
                    forward(ForwardConst.FW_US_NEW);

                } else {
                    //登録中にエラーがなかった場合


                    //セッションに登録完了のフラッシュメッセージを設定
                    putSessionScope(AttributeConst.FLUSH, MessageConst.I_REGISTERED.getMessage());

                    //エラーメッセージが何もしないとtrueになってしまうためfalseに。
                    putRequestScope(AttributeConst.LOGIN_ERR,false);


                    //一覧画面にリダイレクト
                    redirect(ForwardConst.ACT_AUTH, ForwardConst.CMD_SHOW_LOGIN);//※改変。インデックス画面からログイン画面に
                }

           }
        /**
         * 更新を行う
         * @throws ServletException
         * @throws IOException
         */
        public void update() throws ServletException, IOException {

            //CSRF対策 tokenのチェック
            if(checkToken()) {
                //パラメータの値を元にユーザー情報のインスタンスを作成する
                UserView uv = new UserView(
                        toNumber(getRequestParam(AttributeConst.US_ID)),
                        getRequestParam(AttributeConst.US_NAME),
                        getRequestParam(AttributeConst.US_MAIL),
                        getRequestParam(AttributeConst.US_PASS),
                        null,
                        null,
                        AttributeConst.DEL_FLAG_FALSE.getIntegerValue());

                //アプリケーションスコープからpepper文字列を取得
                String pepper = getContextScope(PropertyConst.PEPPER);
                String pass_k =getRequestParam(AttributeConst.US_PASS_K);

                //従業員情報更新
                List<String> errors = service.update(uv, pepper,pass_k);

                if (errors.size() > 0) {
                    //更新中にエラーが発生した場合

                    putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                    putRequestScope(AttributeConst.USER, uv); //入力されたユーザー情報
                    putRequestScope(AttributeConst.ERR, errors); //エラーのリスト

                    //編集画面を再表示
                    forward(ForwardConst.FW_US_EDIT);
                } else {
                    //更新中にエラーがなかった場合

                    //セッションに更新完了のフラッシュメッセージを設定
                    putSessionScope(AttributeConst.FLUSH, MessageConst.I_UPDATED.getMessage());

                    //一覧画面にリダイレクト
                    redirect(ForwardConst.ACT_SHOP, ForwardConst.CMD_INDEX);
                }
            }
        }

        /**
         * 論理削除を行う
         * @throws ServletException
         * @throws IOException
         */
        public void destroy() throws ServletException, IOException {

            //CSRF対策 tokenのチェック
            if(checkToken()) {
                System.out.println(getRequestParam(AttributeConst.US_ID));

                //セッションからログイン中ユーザーのパラメータを削除
                removeSessionScope(AttributeConst.LOGIN_US);
                //選択してる店舗も消す
                removeSessionScope(AttributeConst.SELECT_SH);
                //idを条件にユーザーデータを論理削除する
                service.destroy(toNumber(getRequestParam(AttributeConst.US_ID)));

                //セッションに削除完了のフラッシュメッセージ
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_DELETED.getMessage());

                //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
                String flush = getSessionScope(AttributeConst.FLUSH);
                if (flush != null) {
                    putRequestScope(AttributeConst.FLUSH, flush);
                    removeSessionScope(AttributeConst.FLUSH);
                }

                //一覧画面を表示
                forward(ForwardConst.FW_LOGIN);
            }
        }
        /**
         * 編集画面を表示する
         * @throws ServletException
         * @throws IOException
         */
        public void edit() throws ServletException, IOException {

            //セッションからログイン中のユーザー情報を取得
            UserView uv = (UserView)getSessionScope(AttributeConst.LOGIN_US);

            if (uv == null || uv.getDeleteFlag() == AttributeConst.DEL_FLAG_TRUE.getIntegerValue()) {

                //データが取得できなかった、または論理削除されている場合はエラー画面を表示
                forward(ForwardConst.FW_ERR_UNKNOWN);
                return;
            }

            putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
            putRequestScope(AttributeConst.USER, uv); //取得した従業員情報

            //編集画面を表示する
            forward(ForwardConst.FW_US_EDIT);

        }



    }