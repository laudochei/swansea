/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reg.data;

import com.reg.model.Student;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Me
 */
@Repository
public class StudentDaoImpl implements StudentDao {
   
    NamedParameterJdbcTemplate namedparameterjdbctemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    
        
    @Autowired
    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) throws DataAccessException {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;      
    }
        
        
        DataSource dataSource;
        @Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
                
	}

        
        
        @Override
        public  List<Student> findAll(){
            String slq = "Select * from Student";
            List<Student>  result = namedParameterJdbcTemplate.query(slq, new StudentDaoImpl.StudentMapper());
            return result;
        }
        
        
        
        
        
        
        // finding a specific student detail
        @Override
        public  Student findNo(Integer id){

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("id", id);
            String sql = "select * from student WHERE id=:id";
            Student result = null;
            try {
		result = namedParameterJdbcTemplate.queryForObject(sql, params, new StudentMapper());
            } catch (EmptyResultDataAccessException e) {
			// do nothing, return null
            }
            return result;
            
        }
          
        
        
        @Override
        public  void save(Student student){
            KeyHolder  keyHolder = new GeneratedKeyHolder();
           String sql = "INSERT INTO Student (id, firstname, lastname, email) "
				+ "VALUES ( :id, :firstname, :lastname, :email)";
           namedParameterJdbcTemplate.update(sql, getSqlParameterByModel(student), keyHolder, new String[]{"id"});
           student.setId(keyHolder.getKey().intValue());
        }
        
        
        
        @Override
        public  void update(Student student){
            String sql = "UPDATE Student SET id=:id, firstname=:firstname, lastname=:lastname email=:email WHERE id=:id";
            namedParameterJdbcTemplate.update(sql, getSqlParameterByModel(student)); 
        }
        
        
        
        
       
        @Override
	public void delete(Integer id) {
            String sql = "DElete from student where id= :id";
            namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource("id", id));
	}
        
        
        
        
        
        private SqlParameterSource getSqlParameterByModel(Student student) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
                paramSource.addValue("id", student.getId());
		paramSource.addValue("firstname", student.getFirstName());
                paramSource.addValue("lastname", student.getLastName());
                paramSource.addValue("email", student.getEmailId());
                return paramSource;
	}
        
        
    private static final class StudentMapper implements RowMapper<Student> {
        public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
			Student student = new Student();
                        student.setId(rs.getInt("id"));  
                        student.setFirstName(rs.getString("firstname"));    
                        student.setLastName(rs.getString("lastname"));
                        student.setEmailId(rs.getString("email"));
                        return student;

		}
	}   
        
        
    
}
