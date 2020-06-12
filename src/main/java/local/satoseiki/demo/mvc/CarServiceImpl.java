package local.satoseiki.demo.mvc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.zkoss.zul.Messagebox;

import local.satoseiki.demo.db.CommonService;

public class CarServiceImpl implements CarService {

	// data model
	private List<Car> carList = new LinkedList<Car>();
	private int id = 1;

	protected final static String DB_NAME = "pm";
	
	// initialize book data
	public CarServiceImpl() {
		int result = 0;

		// DB接続
		QueryRunner qr = null;
		Connection con = null;

		

		ResultSetHandler rsh = new MapListHandler();

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM `");
		sb.append("注文情報");
		sb.append("`");
		sb.append(" LIMIT 1000;");
		String sql = sb.toString();

		// SQL実行
		try {
			qr = CommonService.getQRun(DB_NAME);
			con = qr.getDataSource().getConnection();
			List<Map> list = (List<Map>)qr.query(con, sql, rsh);
			DbUtils.closeQuietly(con);
			
			// 以下、結果出力
			int cnt = 0;
			for( Map<String, String> map : list) {
			    cnt++;
			    // 1行目のみカラム名も出力する
			    /*if( cnt == 1 ) {
			        for( String key : map.keySet() ) {
			            System.out.print(key + " | ");
			        }
			        System.out.println();
			    }*/
			    // 値を出力する
			    /*for( String key : map.keySet() ) {
			        System.out.print(String.valueOf(map.get(key)) + " | ");
			    }
			    System.out.println();*/
			    
		        carList.add(new Car(id++, String.valueOf(map.get("品番")), "Nissan",
						"The Nissan Primera was produced between 2002 and 2008. It was available as a 4-door sedan or a 5-door hatchback or estate."
								+ " The entry-level 1.6-liter petrol feels underpowered for a large car. The 1.8-liter petrol is keen, the refined 2.0-liter unit is the star performer. An improved 2.2-liter turbodiesel performs well, but is only relatively economical, as it's competitors in this class with similar characteristics offer even lower fuel consumption.",
						"/widgets/getting_started/img/car1.png", 23320));
			}
		    System.out.println("データベースアクセス");
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		

		
		
		carList.add(new Car(id++, "Primera", "Nissan",
				"The Nissan Primera was produced between 2002 and 2008. It was available as a 4-door sedan or a 5-door hatchback or estate."
						+ " The entry-level 1.6-liter petrol feels underpowered for a large car. The 1.8-liter petrol is keen, the refined 2.0-liter unit is the star performer. An improved 2.2-liter turbodiesel performs well, but is only relatively economical, as it's competitors in this class with similar characteristics offer even lower fuel consumption.",
				"/widgets/getting_started/img/car1.png", 23320));
	}

	public List<Car> findAll() {
	    System.out.println("データベースアクセス2");
		return carList;
	}

	public List<Car> search(String keyword) {
		List<Car> result = new LinkedList<Car>();
		carList.add(new Car(id++, "Primera", "Toyota",
				"The Nissan Primera was produced between 2002 and 2008. It was available as a 4-door sedan or a 5-door hatchback or estate."
						+ " The entry-level 1.6-liter petrol feels underpowered for a large car. The 1.8-liter petrol is keen, the refined 2.0-liter unit is the star performer. An improved 2.2-liter turbodiesel performs well, but is only relatively economical, as it's competitors in this class with similar characteristics offer even lower fuel consumption.",
				"/widgets/getting_started/img/car1.png", 23320));
		if (keyword == null || "".equals(keyword)) {
			result = carList;
		} else {
			for (Car c : carList) {
				if (c.getModel().toLowerCase().contains(keyword.toLowerCase())
						|| c.getMake().toLowerCase().contains(keyword.toLowerCase())) {
					result.add(c);
				}
			}
		}
	    System.out.println("データベースアクセス3");

		return result;
	}
}
