package uz.pdp.SpringBootDemoApplication.daos;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;
import uz.pdp.SpringBootDemoApplication.domains.Upload;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Objects;

@Component
public class UploadDao {


    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public UploadDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }


    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5433/springdata");
        dataSource.setUsername("postgres");
        dataSource.setPassword("123");
        dataSource.setSchema("spring_jdbc");
//spring.datasource.jdbc.driver
        return dataSource;
    }

    public void save(Upload upload) {
        String sql = "insert into uploads(originalName,generatedName,size,mimeType,UploadedPath) values(:originalName,:generatedName,:size,mimeType,:UploadedPath)";
        var params = new BeanPropertySqlParameterSource(upload);
        namedParameterJdbcTemplate.update(sql, params);
    }

    public Upload findByGeneratedName(String fileName) {
        String sql = "select * from uploads where generatedName=:fileName";
        Map<String, Object> generatedName = Map.of("generatedName", fileName);
        return namedParameterJdbcTemplate.queryForObject(sql, generatedName, BeanPropertyRowMapper.newInstance(Upload.class));
    }

}
