package model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import model.*;
import model.service.UserManager;

public class DiaryDAO {
	private JDBCUtil jdbcUtil = null;

	public DiaryDAO() {
		jdbcUtil = new JDBCUtil(); // JDBCUtil 객체 생성
	}

	// diary 조회
	/*
	 * date는 '20221101'(YYMMDD) 형식으로 넘어옴. 조회 시작 날짜와 끝 날짜를 받아, diary 리스트를 반환하거나 조회하고자
	 * 하는 날짜 하나만을 넘겨 받아 diary 객체를 반환함.
	 */
	public List<Diary> getDiaryListByDate(long memberId, String... date) throws SQLException {
		// 기간 조회 (조회 시작 날짜와 끝 날짜)
		if (date.length == 2) {
			String sql = "SELECT di.diary_id, drinking_date, condition, content, a.alcohol_id, name, type, amount "
					+ "FROM diary di JOIN drink dr ON di.diary_id = dr.diary_id JOIN alcohol a ON dr.alcohol_id = a.alcohol_id "
					+ "WHERE member_id=? and drinking_date >= TO_DATE(?) and drinking_date < TO_DATE(?) "
					+ "ORDER BY di.diary_id ";

			jdbcUtil.setSqlAndParameters(sql, new Object[] { memberId, date[0], date[1] }); // JDBCUtil에 query문과 매개 변수
																							// 설정
		}

		// 일별 조회
		if (date.length == 1) {
			String sql = "SELECT di.diary_id, drinking_date, condition, content, a.alcohol_id, name, type, amount "
					+ "FROM diary di JOIN drink dr ON di.diary_id = dr.diary_id JOIN alcohol a ON dr.alcohol_id = a.alcohol_id "
					+ "WHERE member_id=? and TO_CHAR(drinking_date, 'YYYYMMDD')=? ";
				
			jdbcUtil.setSqlAndParameters(sql, new Object[] { memberId, date[0] }); // JDBCUtil에 query문과 매개 변수
																								// 설정
		}

		// diary id를 기준으로 drinkingList에 정보 저장 -> diary.setDrinkingList() 호출 ->
		// diaryList에 diary 저장
		List<Diary> diaryList = new ArrayList<Diary>();
		List<Drink> drinkingList = new ArrayList<Drink>();
		Diary diary = null;
		Alcohol alcohol = null;
		Drink drink = null;
		long diaryId = -1;
		try {
			ResultSet rs = jdbcUtil.executeQuery(); // query 실행
			while (rs.next()) {
				if (diaryId != rs.getLong("diary_id")) { // 새로운 diaryId가 왔을 떄, drinkingList에 drink 추가해야 함
					if (diary != null) { // 새로운 diaryId가 왔을 떄, 그 전 diaryId에 해당하는 diary 정보를 저장함
						diary.setDrinkingList(drinkingList);
						diaryList.add(diary);
						diary = null;
						drinkingList = new ArrayList<Drink>();
					}

					if (diary == null) {
						diaryId = rs.getLong("diary_id");
						diary = new Diary();
						diary.setDiaryId(diaryId);
						Member member = new Member();
						member.setId(memberId);
						diary.setMember(member);
						diary.setDrinkingDate(new java.util.Date(rs.getDate("drinking_date").getTime()));
						diary.setCondition(rs.getInt("condition"));
						diary.setContent(rs.getString("content"));
					}
					alcohol = new Alcohol();
					alcohol.setAlcoholId(rs.getLong("alcohol_id"));
					alcohol.setName(rs.getString("name"));
					alcohol.setType(rs.getString("type"));

					drink = new Drink();
					drink.setAlcohol(alcohol);
					drink.setAmount(rs.getInt("amount"));

					drinkingList.add(drink);
				} else { // diaryId 같을 떄.. drinkingList에 drink 추가
					alcohol = new Alcohol();
					alcohol.setAlcoholId(rs.getLong("alcohol_id"));
					alcohol.setName(rs.getString("name"));
					alcohol.setType(rs.getString("type"));

					drink = new Drink();
					drink.setAlcohol(alcohol);
					drink.setAmount(rs.getInt("amount"));
					drinkingList.add(drink);
				}
			}
		
			if (diary != null) { // 그 전의 diaryId에 해당하는 diary 정보를 저장함
				diary.setDrinkingList(drinkingList);
				diaryList.add(diary);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close(); // resource 반환
		}
		
		return diaryList;
	}
	
	public List<Diary> getDiaryListByMemberId(long memberId) throws SQLException {
		
		String sql = "SELECT di.diary_id, drinking_date, condition, content, a.alcohol_id, name, type, amount "
					+ "FROM diary di JOIN drink dr ON di.diary_id = dr.diary_id JOIN alcohol a ON dr.alcohol_id = a.alcohol_id "
					+ "WHERE member_id=? "
					+ "ORDER BY di.diary_id ";

		jdbcUtil.setSqlAndParameters(sql, new Object[] { memberId }); // JDBCUtil에 query문과 매개 변수 설정
		
		// diary id를 기준으로 drinkingList에 정보 저장 -> diary.setDrinkingList() 호출 ->
		// diaryList에 diary 저장
		List<Diary> diaryList = new ArrayList<Diary>();
		List<Drink> drinkingList = new ArrayList<Drink>();
		Diary diary = null;
		Alcohol alcohol = null;
		Drink drink = null;
		long diaryId = -1;
		try {
			ResultSet rs = jdbcUtil.executeQuery(); // query 실행
			while (rs.next()) {
				if (diaryId != rs.getLong("diary_id")) { // 새로운 diaryId가 왔을 떄, drinkingList에 drink 추가해야 함
					if (diary != null) { // 새로운 diaryId가 왔을 떄, 그 전 diaryId에 해당하는 diary 정보를 저장함
						diary.setDrinkingList(drinkingList);
						diaryList.add(diary);
						diary = null;
						drinkingList = new ArrayList<Drink>();
					}

					if (diary == null) {
						diaryId = rs.getLong("diary_id");
						diary = new Diary();
						diary.setDiaryId(diaryId);
						Member member = new Member();
						member.setId(memberId);
						diary.setMember(member);
						diary.setDrinkingDate(new java.util.Date(rs.getDate("drinking_date").getTime()));
						diary.setCondition(rs.getInt("condition"));
						diary.setContent(rs.getString("content"));
					}
					alcohol = new Alcohol();
					alcohol.setAlcoholId(rs.getLong("alcohol_id"));
					alcohol.setName(rs.getString("name"));
					alcohol.setType(rs.getString("type"));

					drink = new Drink();
					drink.setAlcohol(alcohol);
					drink.setAmount(rs.getInt("amount"));

					drinkingList.add(drink);
				} else { // diaryId 같을 떄.. drinkingList에 drink 추가
					alcohol = new Alcohol();
					alcohol.setAlcoholId(rs.getLong("alcohol_id"));
					alcohol.setName(rs.getString("name"));
					alcohol.setType(rs.getString("type"));

					drink = new Drink();
					drink.setAlcohol(alcohol);
					drink.setAmount(rs.getInt("amount"));
					drinkingList.add(drink);
				}
			}
		
			if (diary != null) { // 그 전의 diaryId에 해당하는 diary 정보를 저장함
				diary.setDrinkingList(drinkingList);
				diaryList.add(diary);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close(); // resource 반환
		}
		
		return diaryList;
	}

	public Diary getDiary(long diaryId) throws SQLException {
		String sql = "SELECT di.diary_id, drinking_date, member_id, condition, content, a.alcohol_id, name, type, amount "
				+ "FROM diary di JOIN drink dr ON di.diary_id = dr.diary_id JOIN alcohol a ON dr.alcohol_id = a.alcohol_id "
				+ "WHERE di.diary_id = ?";
		jdbcUtil.setSqlAndParameters(sql, new Object[] { diaryId }); // JDBCUtil에 query문과 매개 변수 설정

		// diary id를 기준으로 drinkingList에 정보 저장 -> diary.setDrinkingList() 호출 ->
		List<Drink> drinkingList = new ArrayList<Drink>();
		Diary diary = null;
		Alcohol alcohol = null;
		Drink drink = null;
		try {
			ResultSet rs = jdbcUtil.executeQuery(); // query 실행
			while (rs.next()) {
				if (diary == null) { // 새로운 diaryId가 왔을 떄, drinkingList에 drink 추가해야 함
					diaryId = rs.getLong("diary_id");
					diary = new Diary();
					diary.setDiaryId(diaryId);
				    java.sql.Date drinkingDate = rs.getDate("drinking_date");  // DATE 타입 컬럼 --> java.sql.Date
				    java.util.Date utilDate = new java.util.Date(drinkingDate.getTime());   // java.sql.Date --> java.util.Date                        
				    diary.setDrinkingDate(utilDate); // DTO 필드에 저장 
					diary.setCondition(rs.getInt("condition"));
					diary.setContent(rs.getString("content"));

					Member member = new Member();
					member.setId(rs.getLong("member_id"));
					diary.setMember(member);
					
					alcohol = new Alcohol();
					alcohol.setAlcoholId(rs.getLong("alcohol_id"));
					alcohol.setName(rs.getString("name"));
					alcohol.setType(rs.getString("type"));

					drink = new Drink();
					drink.setAlcohol(alcohol);
					drink.setAmount(rs.getInt("amount"));

					drinkingList.add(drink);
				} else { // diaryId 같을 떄.. drinkingList에 drink 추가
					alcohol = new Alcohol();
					alcohol.setAlcoholId(rs.getLong("alcohol_id"));
					alcohol.setName(rs.getString("name"));
					alcohol.setType(rs.getString("type"));

					drink = new Drink();
					drink.setAlcohol(alcohol);
					drink.setAmount(rs.getInt("amount"));
					drinkingList.add(drink);
				}
			}

			if (diary != null) { // 그 전의 diaryId에 해당하는 diary 정보를 저장함
				diary.setDrinkingList(drinkingList);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close(); // resource 반환
		}

		return diary;
	}
	
	// diary 추가
	public Diary createDiary(Diary diary) throws SQLException { // 추가한 diary 객체 반환
		String key1[] = { "diary_id" }; // PK 컬럼의 이름
		try {
			// Diary 테이블에 추가
			String sql1 = "INSERT INTO Diary VALUES (diary_id_seq.nextval, ?, ?, ?, ?)";
			Object[] param1 = new Object[] { diary.getMember().getId(), new java.sql.Date(diary.getDrinkingDate().getTime()), diary.getCondition(),
					diary.getContent() };
			jdbcUtil.setSqlAndParameters(sql1, param1); // JDBCUtil 에 insert문과 매개 변수 설정
			jdbcUtil.executeUpdate(key1); // insert 문 실행
			jdbcUtil.commit();
			
			ResultSet rs = jdbcUtil.getGeneratedKeys();
			if (rs.next()) {
				int generatedKey = rs.getInt(1); // 생성된 PK 값
				diary.setDiaryId(generatedKey); // id필드에 저장
				System.out.println("생성된 키 " + generatedKey);
			}
			
			// Drink 테이블에 마신 술 내역 추가
			String key2[] = { "drink_id" };
			RecommendDao rcd = new RecommendDao();
			for (Drink dr : diary.getDrinkingList()) {
				String sql2 = "INSERT INTO Drink VALUES (drink_id_seq.nextval, ?, ?, ?)";
				Object[] param2 = new Object[] { diary.getDiaryId(), dr.getAlcohol().getAlcoholId(), dr.getAmount() };
				jdbcUtil.setSqlAndParameters(sql2, param2); // JDBCUtil 에 insert문과 매개 변수 설정
				jdbcUtil.executeUpdate(key2); // insert 문 실행

				// Preference 테이블에 total_amount 변화 (테이블 없을 시 생성, 있을 시 수정)
				long preferenceId = rcd.findPreference(diary.getMember().getId(), dr.getAlcohol().getAlcoholId());
				if (preferenceId == -1) { // 테이블 존재 X
					rcd.createPreferenceByAmount(diary.getMember().getId(), dr.getAlcohol().getAlcoholId(),
							dr.getAmount());
				} else {
					rcd.updatePreferenceByAmount(preferenceId, dr.getAmount());
				}
				
			}
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		} finally {
			jdbcUtil.commit();
			jdbcUtil.close(); // resource 반환
		}
		return diary;
	}

	// diary 수정
	public int updateDiary(Diary diary) throws SQLException {
		try {
			// diary 테이블 수정
			String sql1 = "UPDATE Diary " + "SET drinking_date=?, condition=?, content=? " + "WHERE diary_id=?";
			Object[] param1 = new Object[] { new java.sql.Date(diary.getDrinkingDate().getTime()), diary.getCondition(), diary.getContent(), diary.getDiaryId() };
			jdbcUtil.setSqlAndParameters(sql1, param1); // JDBCUtil에 update문과 매개 변수 설정
			int result = jdbcUtil.executeUpdate(); // update 문 실행

			
			// drink 삭제 전 preference 테이블 해당 양만큼 감소
			RecommendDao rcd = new RecommendDao();
			long preferenceId = -1;
			for (Drink dr : diary.getDrinkingList()) {
				int amount = dr.getAmount() * -1; // 아래 메소드의 쿼리문이 총 amount = 총 amount + amount이므로 음수로 넘김
				preferenceId = rcd.findPreference(diary.getMember().getId(), dr.getAlcohol().getAlcoholId());
				rcd.updatePreferenceByAmount(preferenceId, amount);
			}


			// Drink 테이블에 마신 술 내역 추가
			String key[] = { "drink_id" };
			for (Drink dr : diary.getDrinkingList()) {
				String sql3 = "INSERT INTO Drink VALUES (drink_id_seq.nextval, ?, ?, ?)";
				Object[] param3 = new Object[] { diary.getDiaryId(), dr.getAlcohol().getAlcoholId(), dr.getAmount() };
				jdbcUtil.setSqlAndParameters(sql3, param3); // JDBCUtil 에 insert문과 매개 변수 설정
				jdbcUtil.executeUpdate(key); // insert 문 실행

				// preference에 다시 추가
				preferenceId = rcd.findPreference(diary.getMember().getId(), dr.getAlcohol().getAlcoholId());
				rcd.updatePreferenceByAmount(preferenceId, dr.getAmount());
			}
			
			
			 
			return result;
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		} finally {
			jdbcUtil.commit();
			jdbcUtil.close(); // resource 반환
		}
		return 0;
	}

	// diary 삭제
	public int removeDiary(Diary diary) throws SQLException { // 삭제한 레코드 개수 반환
		try {
			// Drink 삭제 전 Preference 테이블에 해당 양만큼 감소
			RecommendDao rcd = new RecommendDao();
			for (Drink dr : diary.getDrinkingList()) {
				int amount = dr.getAmount() * -1;
				long preferenceId = rcd.findPreference(diary.getMember().getId(), dr.getAlcohol().getAlcoholId());
				rcd.updatePreferenceByAmount(preferenceId, amount);
			}

			String sql = "DELETE FROM Diary WHERE diary_id=?";
			jdbcUtil.setSqlAndParameters(sql, new Object[] { diary.getDiaryId() }); // JDBCUtil에 delete문과 매개 변수 설정
			int result = jdbcUtil.executeUpdate(); // delete 문 실행

			return result;
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		} finally {
			jdbcUtil.commit();
			jdbcUtil.close(); // resource 반환
		}
		return 0;
	}

	public List<Long> diaryAllList(long id) {
		String query = "Select diary_id from diary where member_id = ?";
		jdbcUtil.setSqlAndParameters(query, new Object[] {id});
		ResultSet rs = null;
		List<Long> diaryIdList = null;
		try {
			rs = jdbcUtil.executeQuery();
			
			diaryIdList = new ArrayList<Long>();
			
			while (rs.next()) {
				diaryIdList.add(rs.getLong("diary_id"));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close(); // resource 반환
		}
		return diaryIdList;
	}
	
	public void deleteAllDiary(long diaryId) {
		try {
			String query = "DELETE FROM drink WHERE diary_id = ?";
			jdbcUtil.setSqlAndParameters(query, new Object[] { diaryId });
			jdbcUtil.executeUpdate();
				
			String query2 = "DELETE FROM diary WHERE diary_id = ?";
			jdbcUtil.setSqlAndParameters(query2, new Object[] { diaryId });
			jdbcUtil.executeUpdate();
				
			jdbcUtil.commit();
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		} finally {
			jdbcUtil.close(); // resource 반환
		}
	}
}
