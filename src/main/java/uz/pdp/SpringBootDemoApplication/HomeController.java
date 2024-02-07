package uz.pdp.SpringBootDemoApplication;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class HomeController {

    private final MailConfigs mailConfigs;
    private final JdbcTemplate jdbcTemplate;
    @Value("${welcome.message}")
    private String message;

    public HomeController(MailConfigs mailConfigs, JdbcTemplate jdbcTemplate) {
        this.mailConfigs = mailConfigs;
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("message", message);
        String sql = "select * from book";
        List<Book> books = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Book.class));
        model.addAttribute("books", books);

        System.out.println(mailConfigs.toString());
        return "home";
    }


    @GetMapping("/book/create")
    public String bookCreatePage() {
        return "book_create";
    }

    @PostMapping("/book/create")
    public String bookCreate(@ModelAttribute BookCreateDTO dto) {
        // save
        String sql = "insert into book(name,author) values (?,?)";
        jdbcTemplate.update(sql, dto.getName(), dto.getAuthor());
        return "redirect:/";
    }
}
