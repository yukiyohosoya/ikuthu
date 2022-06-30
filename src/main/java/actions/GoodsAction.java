package actions;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;

import javax.servlet.http.Part;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import actions.views.UserView;
import actions.views.GoodsView;
import actions.views.ShopView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;
import services.EventService;
import services.GoodsService;
import services.ShopService;
import services.UserService;

@MultipartConfig

/**
 * ショップに関する処理を行うActionクラス
 *
 */

public class GoodsAction extends ActionBase {

    private ShopService shop_service;
    private UserService user_service;
    private EventService event_service;
    private GoodsService goods_service;

    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {
        shop_service = new ShopService();
        user_service = new UserService();
        event_service = new EventService();
        goods_service = new GoodsService();


        //メソッドを実行
        invoke();

        shop_service.close();
        user_service.close();
        event_service.close();
        goods_service.close();
    }

    /**
     * ログインしているユーザーのショップ一覧画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void index() throws ServletException, IOException {

        //セッションからショップ情報を取得
         ShopView select_shop = (ShopView) getSessionScope(AttributeConst.SELECT_SH);
         String picpath =context.getRealPath("/WEB-INF/uploaded") + "/";

         //指定されたページ数の一覧画面に表示するデータを取得
         int page = getPage();

         List<GoodsView> index_goods = goods_service.getMinePer_index(select_shop);

         //選択中ショップのEvent件数を取得
         long myGoodsCount = goods_service.countAllMine(select_shop);

          putRequestScope(AttributeConst.GOODSS,index_goods); //index用に取得したイベント
          putRequestScope(AttributeConst.GS_COUNT,myGoodsCount); //Goods全件数
          putRequestScope(AttributeConst.PAGE, page); //ページ数
          putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1ページに表示するレコードの数
          putRequestScope(AttributeConst.PICPATH, picpath);

          //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
          String flush = getSessionScope(AttributeConst.FLUSH);
          if (flush != null) {
              putRequestScope(AttributeConst.FLUSH, flush);
              removeSessionScope(AttributeConst.FLUSH);
          }
        //一覧画面を表示
        forward(ForwardConst.FW_GS_INDEX);
    }

    /**
     * 新規登録画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void entryNew() throws ServletException, IOException {

        putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン

        //新規登録画面を表示
        forward(ForwardConst.FW_GS_NEW);
        }
    /**
     * 新規登録を行う
     * @throws ServletException
     * @throws IOException
     */
    public void create() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if(checkToken()) {

            //セッションから選択中のショップ情報を取得
            ShopView sv = (ShopView)getSessionScope(AttributeConst.SELECT_SH);

            String day=getRequestParam(AttributeConst.GS_CREATEDAY).replace("/", "-");

            //inputからfileピクチャを取得
            Part part = request.getPart(AttributeConst.GS_PICTURE.getValue());
            //セットされてなかった場合のフラグをオンにしておき、noimageのパスをpictureに入れておく。
            boolean notsetfile =true;
            String picture = "noimage.jpg";

            //取得したpartオブジェクトが空じゃなかった場合。
            if(!(part.getSize()==0)) {
              //取得したファイルから名前を取得
              picture = this.getFileName(part);
              //拡張子だけを取得
              String extension = picture.substring(picture.lastIndexOf("."));
              //既存のグッズ数をカウントし
              long number = goods_service.countAll();
              //今までの物をセットし、新しい名前にする。
              picture =String.valueOf(sv.getId())+"_"+String.valueOf(number+1) + extension ;
              //フラグをfalseにしておく
              notsetfile =false;
            }

            //パラメータの値を元にショップ情報のインスタンスを作成する
            GoodsView gv = new GoodsView(
                    null,
                    sv,//セッションにあるショップ
                    getRequestParam(AttributeConst.GS_NAME),
                    getRequestParam(AttributeConst.GS_SELLINGPRICE),
                    getRequestParam(AttributeConst.GS_PURCHASEPRICE),
                    getRequestParam(AttributeConst.GS_STOCK),
                    day,
                    picture,
                    null,
                    null,
                    AttributeConst.DEL_FLAG_FALSE.getIntegerValue());

            //グッズ情報登録
            List<String> errors = goods_service.create(gv,part);

            if (errors.size() > 0) {
                //登録中にエラーがあった場合

                //セットしたpictureをnullにして返す
                gv.setPicture(null);
                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.GOODS, gv); //入力されたイベント情報
                putRequestScope(AttributeConst.ERR, errors); //エラーのリスト

                System.out.println(errors);
                //新規登録画面を再表示
                forward(ForwardConst.FW_GS_NEW);

            } else {
                //登録中にエラーがなかった場合
                //この段階で画像は書き込み。パートファイルが正常だったら。
                if(!notsetfile) {
                    String filePath = context.getRealPath("/uploaded") + "/" + picture;
                    part.write(filePath);
                    s3up(filePath,picture);
                }

                //セッションに登録完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_REGISTERED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_SHOP, ForwardConst.CMD_INDEX);
            }

        }
    }

    /**
     * 詳細画面を表示する
     * @throws ServletException
     * @throws IOException
     */

    public void show() throws ServletException, IOException {

        //idを条件にショップデータを取得
        GoodsView gv = goods_service.findOne(toNumber(getRequestParam(AttributeConst.GS_ID)));

        if(gv==null) {
            //該当のショップデータが存在しない場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);
            return;
        }else{
            putRequestScope(AttributeConst.GOODS,gv);//取得したショップデータ
            putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
            //詳細画面を表示
            forward(ForwardConst.FW_GS_SHOW);

        }
    }

    /**
     * 編集画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void edit() throws ServletException, IOException {
        System.out.println("きてるよ～");
        //セッションからショップ情報を取得
        ShopView sv = (ShopView) getSessionScope(AttributeConst.SELECT_SH);
        //idを条件にショップデータを取得
        GoodsView gv = goods_service.findOne(toNumber(getRequestParam(AttributeConst.GS_ID)));

        if (sv == null || gv.getShop().getId() != sv.getId()) {
            //該当のショップデータが存在しない、また作成したグッズの管理ショップ作者でない場合はエラー画面を表示
            System.out.println(gv.getShop().getId()+"と"+sv.getId());
            forward(ForwardConst.FW_ERR_UNKNOWN);

        }else {

            putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
            putRequestScope(AttributeConst.GOODS, gv); //取得したショップ情報

            //編集画面を表示する
            forward(ForwardConst.FW_GS_EDIT);
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

            //セッションからショップ情報を取得
            ShopView sv = (ShopView) getSessionScope(AttributeConst.SELECT_SH);
            //idを条件に日報データを取得
            GoodsView gv = goods_service.findOne(toNumber(getRequestParam(AttributeConst.GS_ID)));

            //入力されたグッズ内容を設定する
            gv.setCreate_day(getRequestParam(AttributeConst.GS_CREATEDAY).replace("/", "-"));
            gv.setName(getRequestParam(AttributeConst.GS_NAME));
            gv.setSellingprice(getRequestParam(AttributeConst.GS_SELLINGPRICE));
            gv.setPurchaseprice(getRequestParam(AttributeConst.GS_PURCHASEPRICE));
            gv.setStock(getRequestParam(AttributeConst.GS_STOCK));


            //inputからfileピクチャを取得
            Part part = request.getPart(AttributeConst.GS_PICTURE.getValue());
            //セットされてなかった場合のフラグをオンにしておき、元々の指定ファイルを入れておく。
            boolean notsetfile =true;
            String picture =gv.getPicture();
            String oldpicture =gv.getPicture();

            //取得したpartオブジェクトが空じゃなかった場合。
            if(!(part.getSize()==0)) {
              //取得したファイルから名前を取得
              picture = this.getFileName(part);
              //拡張子だけを取得
              String extension = picture.substring(picture.lastIndexOf("."));
              //もうIDを獲得してるので、IDを新しい名前にする。
              picture =String.valueOf(sv.getId())+"_"+ gv.getId() + extension ;
              gv.setPicture(picture);
              //フラグをfalseにしておく
              notsetfile =false;
            }

            //グッズ情報登録
            List<String> errors = goods_service.update(gv,part);

            if (errors.size() > 0) {
                //登録中にエラーがあった場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.GOODS, gv); //入力されたイベント情報
                putRequestScope(AttributeConst.ERR, errors); //エラーのリスト

                System.out.println(errors);
                //新規登録画面を再表示
                forward(ForwardConst.FW_GS_EDIT);

            } else {

                //登録中にエラーがなかった場合
                //この段階で画像は書き込み。パートファイルが正常だったら。
                if(!notsetfile) {
                    String filePath = context.getRealPath("/uploaded") + "/" + picture;
                    part.write(filePath);
                    s3dl(oldpicture);
                    s3up(filePath,picture);
                }

                //セッションに登録完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_UPDATED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_SHOP, ForwardConst.CMD_INDEX);
            }

        }
    }

    /**
     * ショップのログイン処理を行う
     * @throws ServletException
     * @throws IOException
     */
    public void select() throws ServletException, IOException {

        //作成ユーザー以外がショップにログインしてはまずいので、承認を行う。
        //リクエストスコープに入っているショップidを条件にショップデータを取得
        //セッションからログイン中のユーザー情報を取得
        //トップへリダイレクト

        UserView uv = (UserView)getSessionScope(AttributeConst.LOGIN_US);
        ShopView sv = shop_service.findOne(toNumber(getRequestParam(AttributeConst.SH_ID)));

               //セッションにログインしたユーザーを設定
               putSessionScope(AttributeConst.SELECT_SH,sv);
               //トップへリダイレクト
               redirect(ForwardConst.ACT_SHOP,ForwardConst.CMD_INDEX);

    }

    /**
     * 論理削除を行う
     * @throws ServletException
     * @throws IOException
     */
    public void destroy() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if(checkToken()) {

            //idを条件にユーザーデータを論理削除する
            goods_service.destroy(toNumber(getRequestParam(AttributeConst.GS_ID)));
            System.out.println(getRequestParam(AttributeConst.GS_ID));

            //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
            String flush = getSessionScope(AttributeConst.FLUSH);
            if (flush != null) {
                putRequestScope(AttributeConst.FLUSH, flush);
                removeSessionScope(AttributeConst.FLUSH);
            }
            //セッションに削除完了のフラッシュメッセージ
            putSessionScope(AttributeConst.FLUSH, MessageConst.I_DELETED.getMessage());

            //一覧画面にリダイレクト
            redirect(ForwardConst.ACT_SHOP, ForwardConst.CMD_INDEX);
        }
    }

    private String getFileName(Part part) {
    String name = null;
      for (String dispotion : part.getHeader("Content-Disposition").split(";")) {
          if (dispotion.trim().startsWith("filename")) {
             name = dispotion.substring(dispotion.indexOf("=") + 1).replace("\"", "").trim();
             name = name.substring(name.lastIndexOf("\\") + 1);
              break;
                  }
             }
              return name;
         }

    private void s3up(String filePath,String picture) {
        try {
            /* S3 */
            String region = (String) context.getAttribute("region");
            String awsAccessKey = (String) context.getAttribute("awsAccessKey");
            String awsSecretKey = (String) context.getAttribute("awsSecretKey");
            String bucketName = (String) context.getAttribute("bucketName");
            // 認証情報を用意
            AWSCredentials credentials = new BasicAWSCredentials(
                    // アクセスキー
                    awsAccessKey,
                    // シークレットキー
                    awsSecretKey);
            // クライアントを生成
            AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                    // 認証情報を設定
                    .withCredentials(new AWSStaticCredentialsProvider(credentials))
                    // リージョンを AP_NORTHEAST_1(東京) に設定
                    .withRegion(region).build();
            // === ファイルから直接アップロードする場合 ===
            // アップロードするファイル
            File file = new File(filePath);
            // ファイルをアップロード
            s3.putObject(
                    // アップロード先バケット名
                    bucketName,
                    // アップロード後のキー名
                    "uploaded/" + picture,
                    // ファイルの実体
                    file);
        } catch (Exception e) {
            System.out.println("S3失敗");

        }
    }

    private void s3dl(String picture) {
        try {
            /* S3 */
            String region = (String) context.getAttribute("region");
            String awsAccessKey = (String) context.getAttribute("awsAccessKey");
            String awsSecretKey = (String) context.getAttribute("awsSecretKey");
            String bucketName = (String) context.getAttribute("bucketName");
            // 認証情報を用意
            AWSCredentials credentials = new BasicAWSCredentials(
                    // アクセスキー
                    awsAccessKey,
                    // シークレットキー
                    awsSecretKey);
            // クライアントを生成
            AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                    // 認証情報を設定
                    .withCredentials(new AWSStaticCredentialsProvider(credentials))
                    // リージョンを AP_NORTHEAST_1(東京) に設定
                    .withRegion(region).build();
            // === ファイルから直接アップロードする場合 ===
            // ファイルをアップロード
            s3.deleteObject(
                    // アップロード先バケット名
                    bucketName,
                    // アップロード後のキー名
                    "uploaded/" + picture);
        } catch (Exception e) {
            System.out.println("デリート失敗");

        }
    }

}


