package model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.*;

public class RecommendDao {
	private JDBCUtil jdbcUtil = null;	// JDBCUtil 참조 변수 선언
	
	public RecommendDao() {	// 생성자
		jdbcUtil = new JDBCUtil();		// JDBCUtil 객체 생성 및 활용
	}
	
	public long findPreference(long memberId, long alcoholId) {
		String query = "SELECT preference_id FROM preference WHERE member_id = ? and alcohol_id = ?";
		Object[] param = new Object[] { memberId, alcoholId };
		
		jdbcUtil.setSqlAndParameters(query, param);
		
		ResultSet rs = null;
		
		long preferenceId = -1;
		
		try {
			rs = jdbcUtil.executeQuery();
			
			if (rs.next()) {
				preferenceId = rs.getInt("preference_id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {	
		}	
		
		return preferenceId;
	}
	
	
	public long createPreferenceByRate(long memberId, long alcoholId, float rate) {
		String query = "INSERT INTO preference (preference_id, member_id, alcohol_id, rate) "
				+ "values (preference_id_seq.nextval, ?, ?, ?)";

		Object[] param = new Object[] { memberId, alcoholId, rate };
		
		String key[] = { "preference_id" };
		
		// memberId, alcoholId가 존재하는지 MemberDao, AlcoholDao로 확인??
		
		ResultSet rs = null;
		long pId = -1;
		
		try {
			jdbcUtil.setSqlAndParameters(query, param);
				
			jdbcUtil.executeUpdate(key);
			rs = jdbcUtil.getGeneratedKeys();
			if (rs.next()) {
				pId = rs.getInt(1);
			}
			
			jdbcUtil.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			jdbcUtil.rollback();
			e.printStackTrace();
		} catch (Exception e) {
			jdbcUtil.rollback();
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		}
		
		return pId;
	}
	
	public long createPreferenceByAmount(long memberId, long alcoholId, float amount) {
		String query = "INSERT INTO preference (preference_id, member_id, alcohol_id, totalAmount) "
				+ "values (preference_id_seq.nextval, ?, ?, ?)";
			
		Object[] param = new Object[] { memberId, alcoholId, amount };
		
		String key[] = { "preference_id" };
		
		// memberId, alcoholId가 존재하는지 MemberDao, AlcoholDao로 확인??
		
		ResultSet rs = null;
		long pId = -1;
			
		try {
			jdbcUtil.setSqlAndParameters(query, param);
				
			jdbcUtil.executeUpdate(key);
			rs = jdbcUtil.getGeneratedKeys();
			if (rs.next()) {
				pId = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			jdbcUtil.rollback();
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			jdbcUtil.rollback();
			e.printStackTrace();
		} finally {	
		}	
		return pId;
	}
		
	public void updatePreferenceByRate(long preferenceId, float rate) {
		String query = "UPDATE preference SET rate = ? "
				+ "WHERE preference_id = ?";
			
		Object[] param = new Object[] { rate, preferenceId };
			
		try {
			jdbcUtil.setSqlAndParameters(query, param);
				
			jdbcUtil.executeUpdate();
			
			jdbcUtil.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			jdbcUtil.rollback();
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			jdbcUtil.rollback();
			e.printStackTrace();
		} finally {	
		}	
	}
		
	public void updatePreferenceByAmount(long preferenceId, float amount) {
		String query = "UPDATE preference SET totalamount=totalamount+? "
				+ "WHERE preference_id = ?";
			
		Object[] param = new Object[] { amount, preferenceId };
			
		try {
			jdbcUtil.setSqlAndParameters(query, param);
				
			jdbcUtil.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			jdbcUtil.rollback();
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			jdbcUtil.rollback();
			e.printStackTrace();
		} finally {	
		}	
	}
	
	// 최근 언급량 증가 랭킹
	public List<Rank> rankByRecentIncrease() {
		String query = "SELECT name, image, count(drink.alcohol_id) AS \"numOfAlcohol\", taste, flavor, corps "
				+ "from diary, drink, alcohol "
				+ "where diary.diary_id = drink.diary_id "
				+ "and drink.alcohol_id = alcohol.alcohol_id "
				+ "and MONTHS_BETWEEN(SYSDATE, drinking_date) <= 1 "
				+ "group by name, image, taste, flavor, corps "
				+ "order by count(drink.alcohol_id) DESC";
		jdbcUtil.setSqlAndParameters(query, null);
		ResultSet rs = null;
		ArrayList<Rank> rankList = null;
		int count = 1;
		
		try {
			rs = jdbcUtil.executeQuery();
			rankList = new ArrayList<Rank>();
			
			while (rs.next() && count <= 5) {
				String name = rs.getString("name");
				String imageUrl = rs.getString("image");
				int taste = rs.getInt("taste");
				int flavor = rs.getInt("flavor");
				int corps = rs.getInt("corps");
				int numberOfMention = rs.getInt("numOfAlcohol");
				
				Rank rank = new Rank(count, name, imageUrl, taste, flavor, corps, numberOfMention);
				rankList.add(rank);
				
				count++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {	
			jdbcUtil.close();
		}	
    	return rankList;
	}
	
	// 꾸준히 인기많은
	public List<Rank> rankByPopularity() {
		String query = "SELECT name, image, count(drink.alcohol_id) AS \"numOfAlcohol\", taste, flavor, corps "
				+ "from diary, drink, alcohol "
				+ "where diary.diary_id = drink.diary_id "
				+ "and drink.alcohol_id = alcohol.alcohol_id "
				+ "group by name, image, taste, flavor, corps "
				+ "order by count(drink.alcohol_id) DESC";
		jdbcUtil.setSqlAndParameters(query, null);
		ResultSet rs = null;
		ArrayList<Rank> rankList = null;
		int count = 1;
		
		try {
			rs = jdbcUtil.executeQuery();
			rankList = new ArrayList<Rank>();
			
			while (rs.next() && count <= 5) {
				String name = rs.getString("name");
				String imageUrl = rs.getString("image");
				int taste = rs.getInt("taste");
				int flavor = rs.getInt("flavor");
				int corps = rs.getInt("corps");
				int numberOfMention = rs.getInt("numOfAlcohol");
				
				Rank rank = new Rank(count, name, imageUrl, taste, flavor, corps, numberOfMention);
				rankList.add(rank);
				
				count++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {	
			jdbcUtil.close();
		}	
    	return rankList;
	}
	
	// 주종별 랭킹
	public List<Rank> rankByType(String type) {
		String query = "SELECT name, image, count(drink.alcohol_id) AS \"numOfAlcohol\", taste, flavor, corps "
				+ "from diary, drink, alcohol "
				+ "where diary.diary_id = drink.diary_id "
				+ "and drink.alcohol_id = alcohol.alcohol_id "
				+ "and alcohol.type = ?"
				+ "group by name, image, taste, flavor, corps "
				+ "order by count(drink.alcohol_id) DESC";
		Object[] param = new Object[] { type };
		jdbcUtil.setSqlAndParameters(query, param);
		ResultSet rs = null;
		ArrayList<Rank> rankList = null;
		int count = 1;
		
		try {
			rs = jdbcUtil.executeQuery();
			rankList = new ArrayList<Rank>();
			
			while (rs.next() && count <= 5) {
				String name = rs.getString("name");
				String imageUrl = rs.getString("image");
				int taste = rs.getInt("taste");
				int flavor = rs.getInt("flavor");
				int corps = rs.getInt("corps");
				int numberOfMention = rs.getInt("numOfAlcohol");
				
				Rank rank = new Rank(count, name, imageUrl, taste, flavor, corps, numberOfMention);
				
				rankList.add(rank);
				
				count++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {	
			jdbcUtil.close();
		}	
    	return rankList;
	}
	
}
