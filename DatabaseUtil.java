package practice_webapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * データベース接続を管理するユーティリティクラスにして
 * この共通化したクラスを使い回すことで、他のクラスから簡単にデータベースへ接続できる。
 *
 * 使い方の例:
 *   Connection conn = DatabaseUtil.getConnection();
 */
public class DatabaseUtil {

    private static final String URL = "jdbc:sqlserver://localhost\\SQLEXPRESS;database=practice01;"
            + "enxrypt=true;" // おそらくencryptしたかった？
            + "trustServerCertificate=true;"
            + "integratedSecurity=false;"
            + "user=sa;"
            + "password=SQLPassword1234"; // 本当はよくない

    static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("JDBC Driver not found", e);
        }
    }

    /**
     * データベースへの接続（Connectionオブジェクト）を取得するメソッド。
     * 使い終わったらconn.close() で接続を閉じる
     * このケースではtry-with-resourcesしているので終了時に自動でcloseされる
     *
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}
