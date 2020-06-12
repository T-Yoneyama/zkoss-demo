package local.satoseiki.demo.db;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

import org.apache.commons.dbutils.QueryRunner;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WebApp;

import com.mysql.cj.jdbc.MysqlDataSource;


public interface CommonService {

	/**
	 * QueryRunnerを取得
	 *
	 * @param dbName
	 *            データベース名
	 * @return
	 */
	static QueryRunner getQRun(String dbName) {
		Desktop desktop = Executions.getCurrent().getDesktop();
		WebApp webapp = desktop.getWebApp();

		// 本番用とテスト用のDB切り替え
		String db_url = CommonService.getDatabeseUrl(webapp);
		String db_port = webapp.getInitParameter("dbPort");
		String db_user = webapp.getInitParameter("dbUser");
		String db_pass = webapp.getInitParameter("dbPass");

		// 取得したアドレスが本番用アドレスと違ったら開発環境
		if (!webapp.getInitParameter("dbUrl").equals(db_url)) {
			db_port = webapp.getInitParameter("dbPortDev");
			db_user = webapp.getInitParameter("dbUserDev");
			db_pass = webapp.getInitParameter("dbPassDev");
		}

		// DB名追加
		db_url = db_url + ':' + db_port + '/' + dbName
				+ "?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull";

		// 接続
		MysqlDataSource ds = new MysqlDataSource();
		ds.setUrl(db_url);
		ds.setUser(db_user);
		ds.setPassword(db_pass);

		QueryRunner runner = new QueryRunner(ds);
		return runner;
	}

	/**
	 * 本番用DBと開発用DBのURLの切り替え
	 *
	 * @param webapp
	 * @return
	 */
	static String getDatabeseUrl(WebApp webapp) {

		String dbUrl = webapp.getInitParameter("dbUrl");
		String dbUrlDev = webapp.getInitParameter("dbUrlDev");
		String dbPort = webapp.getInitParameter("dbPort");

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e2) {

		}
		String db = null;

		// アドレス生成
		InetAddress addr = null;
		try {
			addr = InetAddress.getByName("localhost");
		} catch (UnknownHostException e1) {

		}

		int port = Integer.parseInt(dbPort);
		SocketAddress sockaddr = new InetSocketAddress(addr, port);
		@SuppressWarnings("resource")
		Socket sock = new Socket(); // ソケット作成
		try {
			sock.connect(sockaddr, 2000); // 2秒をタイムアウトとして繋いでみる
			db = dbUrl;
		} catch (IOException e) {
			// 本番用への接続が失敗したので開発用へ
			db = dbUrlDev;
		}

		return db;
	}
}