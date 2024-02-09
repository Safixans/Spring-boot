package uz.pdp.SpringBootDemoApplication.domains;


import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Store {
    private Long id;
    private String name;
    private String desc;



}
