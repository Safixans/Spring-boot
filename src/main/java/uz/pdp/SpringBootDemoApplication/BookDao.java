package uz.pdp.SpringBootDemoApplication;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class BookDao {
    private final JdbcTemplate jdbcTemplate;

    public BookDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> findByTitleContainingOrDescriptionContainingOrAuthorContaining(String query) {
        String sql = "SELECT * FROM book WHERE title LIKE ? OR description LIKE ? OR author LIKE ?";
        String searchParam = "%" + query + "%";
        return jdbcTemplate.query(sql, new Object[]{searchParam, searchParam, searchParam},BeanPropertyRowMapper.newInstance(Book.class));
    }

}

