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
	
	/* preference 테이블에 존재하고, rate 혹은 totalAmount 둘 중 하나가 null, 0이 아닐 경우 (맞춤형 추천에서 사용) */
	public long findPreference2(long memberId, long alcoholId) {
		String query = "SELECT preference_id FROM preference "
				+ "WHERE (rate > 0 or totalAmount != 0) AND member_id = ? and alcohol_id = ?";
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
		Object[] param;
		
		if (rate == 0) {
			param = new Object[] { null, preferenceId };
		} else {
			param = new Object[] { rate, preferenceId };
		}
			
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
	
	/* 리뷰 삭제 시 사용 (totalAmount가 0 이하일 땐 삭제) */
	public int deletePreference(long memberId, long alcoholId) {
		String query = "delete from preference where totalAmount <= 0 AND member_id = ? and alcohol_id = ?";
		jdbcUtil.setSqlAndParameters(query, new Object[] { memberId, alcoholId });
		int result = 0;
		try {
			result = jdbcUtil.executeUpdate();
			
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
			jdbcUtil.close();
		}	
		return result;
	}
	
	public List<Long> findAllPreference(long id) {
		String query = "Select preference_id from preference where member_id = ?";
		jdbcUtil.setSqlAndParameters(query, new Object[] {id});
		ResultSet rs = null;
		List<Long> preferenceIdList = null;
		try {
			rs = jdbcUtil.executeQuery();
			
			preferenceIdList = new ArrayList<Long>();
			
			while (rs.next()) {
				preferenceIdList.add(rs.getLong("preference_id"));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close(); // resource 반환
		}
		return preferenceIdList;
	}
	
	public void deleteAllPreference(long id) {
		try {
			String query = "DELETE FROM preference WHERE member_id = ?";
			jdbcUtil.setSqlAndParameters(query, new Object[] { id });
			jdbcUtil.executeUpdate();
			
			jdbcUtil.commit();
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		} finally {
			jdbcUtil.close(); // resource 반환
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
