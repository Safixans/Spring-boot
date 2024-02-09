package uz.pdp.SpringBootDemoApplication.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.SpringBootDemoApplication.exceptionHandlers.ItemNotFoundException;
import uz.pdp.SpringBootDemoApplication.domains.Item;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/item")
public class ItemController {
    static AtomicInteger count = new AtomicInteger(1);
    private final Path rootPath = Path.of("/Users/safixonabdusattorov/Documents/Upload/");
   private final List<Item> items = new ArrayList<>() {{
        add(new Item((long) count.getAndIncrement(), "Water", "very good for health", 12, rootPath.toString()));
        add(new Item((long) count.getAndIncrement(), "IWatch", "MultiFunctional", 4000, rootPath.toString()));
    }};


    @GetMapping("/getall")
    public ResponseEntity<List<Item>> getAll() {
        return ResponseEntity.ok(items);
    }

    @PostMapping("/create")
    public ResponseEntity<Item> create(@RequestBody Item item) {
        item.setId((long) count.getAndIncrement());
        items.add(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(item);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable Long id, @RequestBody Item updatedItem) {
        Item existingItem = items.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ItemNotFoundException("Item not found with given id " + id));
        existingItem.setName(updatedItem.getName());
        existingItem.setDescription(updatedItem.getDescription());
        existingItem.setPrice(updatedItem.getPrice());
        existingItem.setPath(existingItem.getPath());
        return ResponseEntity.status(HttpStatus.OK).body(existingItem);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(items.stream().
                filter(t -> t.getId().equals(id))
                .findFirst()
                .orElseThrow(
                        () -> new ItemNotFoundException("Item not Found to show you by given id" + id))
        );
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Item> deleteItem(@PathVariable Long id){
        Item item=items.stream()
                .filter(t->t.getId().equals(id))
                .findFirst()
                .orElseThrow(()->new ItemNotFoundException("Cannot DELETE ITEM Not FOUND BY GIVEN ID"));

        items.remove(item);
        return ResponseEntity.status(HttpStatus.OK).body(item);

    }


}
