package com.parakoder.panggilbridge;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.parakoder.panggilbridge.dao.AntrianPanggil;
import com.parakoder.panggilbridge.daoimpl.AntrianPanggilImpl;

@Configuration
@PropertySource({
    "file:src/main/resources/antrianbridge_application.properties" 
})
public class DBConfig {
	@Autowired
    Environment env;
	
	@Bean()
    public DataSource getDataSource() throws SQLException {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("DB_DRIVER"));
        dataSource.setUrl(env.getProperty("DB_URL"));
        dataSource.setUsername(env.getProperty("DB_USERNAME"));
        dataSource.setPassword(env.getProperty("DB_PASSWORD"));
        dataSource.getConnection().close();
        return dataSource;
        
    }
//	@Bean()
//    public DetailDAO getDetailDAO() {
//    	return new DetailDAOimpl(getDataSource2());
//    }
	@Bean
	public AntrianPanggil getAntrianPanggil() throws SQLException {
		return new AntrianPanggilImpl(getDataSource());
	}
}
