package com.parakoder.panggilbridge.daoimpl;

import java.io.Closeable;
import java.sql.Connection;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.parakoder.panggilbridge.dao.AntrianPanggil;

public class AntrianPanggilImpl implements AntrianPanggil{
	JdbcTemplate jdbcTemplate;
	
	public AntrianPanggilImpl(DataSource dataSource) {
		// TODO Auto-generated constructor stub
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public void update(Integer id, boolean status) {
		// TODO Auto-generated method stub
		String sql = "UPDATE public.antrian_panggil SET status = "+status+" WHERE pelayanan = "+id;
		jdbcTemplate.update(sql);
		
	}

	@Override
	public Integer cekStatus() {
		// TODO Auto-generated method stub
		String sql = "SELECT pelayanan FROM public.antrian_panggil where status = true";
		try {
			return jdbcTemplate.queryForObject(sql, Integer.class);
		} catch (EmptyResultDataAccessException e) {
			// TODO: handle exception
			return null;
		}
		
		
	}

}
