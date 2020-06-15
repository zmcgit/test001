package cn.admin.workPre.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AppService {

	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	@Autowired
	private SqlSession sqlSession;
	@Autowired
	private DataSource dataSource;
	

	/**
	 * 提交数据
	 * @param list
	 * @param year
	 * @throws RuntimeException
	 */
	@Transactional
	public void insertinto() throws RuntimeException {
		System.out.println("【开始写数据：】"+new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
		SqlSession batchSqlSession = null;
		try{
		    batchSqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
		    int batchCount = 500;
		    Map<String, Object> pMap = new HashMap<String, Object>();
		    for(int index = 0; index < 10000;index++){
		    	pMap.put("periodId", "20");
		    	pMap.put("name", "小红");
			    batchSqlSession.insert("workLibraryDao.addWork",pMap);
		        if(index != 0 && index % batchCount == 0){
		            batchSqlSession.commit();
		        }
		    }
		    batchSqlSession.commit();
		}finally {
		    if(batchSqlSession != null){
		        batchSqlSession.close();
		    }
		}
		System.out.println("【结束写数据：】"+new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
	}
	
	public boolean insertinto2() {
		System.out.println("【开始写数据：】"+new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		boolean result;
		try {
			connection = dataSource.getConnection();
			connection.setAutoCommit(false);
			String sql = "insert into work(periodId,name) values(?,?)";
			preparedStatement = connection.prepareStatement(sql);
			int batchCount = 5;
		    for(int index = 0; index < 10000;index++){
		    	preparedStatement.setString(1, "30");
		    	preparedStatement.setString(2, "小红");
		    	preparedStatement.addBatch();
		    	if((index+1) % batchCount == 0) {
		    		preparedStatement.executeBatch();
		    		preparedStatement.clearBatch();
		    	}
		    }
		    preparedStatement.executeBatch();
		    preparedStatement.clearBatch();
		    preparedStatement.close();
		    connection.commit();
		    result = true;
		} catch (Exception e) {
			result = false;
			if(connection != null) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		} finally {
			if(connection != null) {
				try {
					connection.setAutoCommit(true);
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("【结束写数据：】"+new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
		return result;
	}
	
	
}
