package uz.pdp.SpringBootDemoApplication;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class HomeController {

//    private final MailConfigs mailConfigs;
    private final JdbcTemplate jdbcTemplate;
    private final BookDao dao;

    public HomeController(JdbcTemplate jdbcTemplate, BookDao dao) {
//        this.mailConfigs = mailConfigs;
        this.jdbcTemplate = jdbcTemplate;
        this.dao = dao;
    }

    @GetMapping("/")
    public String homePage(Model model) {
        String sql = "select * from book order by id";
        List<Book> books = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Book.class));
        model.addAttribute("books", books);

//        System.out.println(mailConfigs.toString());
        return "home";
    }


    @GetMapping("/book/create")
    public String bookCreatePage() {
        return "book_create";
    }

    @PostMapping("/book/create")
    public String bookCreate(@ModelAttribute BookCreateDTO dto) {
        // save
        String sql = "insert into book(title,description,author,price) values (?,?,?,?)";
        jdbcTemplate.update(sql, dto.getTitle(),dto.getDescription(), dto.getAuthor(),dto.getPrice());
        return "redirect:/";
    }
    @GetMapping("/book/update")
    public String bookUpdatePage() {
        return "book_update";
    }

    @PostMapping("/book/update")
    public String bookUpdate(@ModelAttribute BookCreateDTO dto) {
        String sql = "update book set title=?,description=?,author=?,price=? where id=?";
        jdbcTemplate.update(sql, dto.getTitle(),dto.getDescription(), dto.getAuthor(),dto.getPrice(),dto.getId());
        return "redirect:/";
    }
    @GetMapping("/book/delete")
    public String bookDeletePage() {
        return "book_delete";
    }

    @PostMapping("/book/delete")
    public String bookDelete(@ModelAttribute BookCreateDTO dto) {
        String sql = "delete from book where id=?";
        jdbcTemplate.update(sql,dto.getId());
        return "redirect:/";
    }

    @GetMapping("/book/search")
    public String searchBooks(@RequestParam("query") String query,Model model){
        List<Book> books=dao.findByTitleContainingOrDescriptionContainingOrAuthorContaining(query);
        model.addAttribute("books",books);
        return "book_search";
    }


   /* public List<Book> findByTitleContainingOrDescriptionContainingOrAuthorContaining(String query) {
        String sql = "SELECT * FROM book WHERE title = ? OR description = ? OR author = ?";
//        String searchParam = "%" + query + "%";
        return jdbcTemplate.query(sql, new Object[]{query, query, query},BeanPropertyRowMapper.newInstance(Book.class));
    }*/
}
