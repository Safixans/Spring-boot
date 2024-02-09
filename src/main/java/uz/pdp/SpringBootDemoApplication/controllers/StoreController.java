package uz.pdp.SpringBootDemoApplication.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.SpringBootDemoApplication.domains.Item;
import uz.pdp.SpringBootDemoApplication.domains.Store;
import uz.pdp.SpringBootDemoApplication.exceptionHandlers.StoreNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/store")
public class StoreController {
    static AtomicInteger count = new AtomicInteger(1);
    private final List<Store> stores = new ArrayList<>() {{
        add(new Store((long) count.getAndIncrement(), "Korzinka.uz", "Popular with assortiment"));
        add(new Store((long) count.getAndIncrement(), "Makro.uz", "Popular with fresh products"));
    }};

    @GetMapping("/getall")
    public ResponseEntity<List<Store>> getAll() {
        return ResponseEntity.ok(stores);
    }

    @PostMapping("/create")
    public ResponseEntity<Store> createStore(@RequestBody Store store) {
        store.setId((long) count.getAndIncrement());
        stores.add(store);
        return ResponseEntity.status(HttpStatus.CREATED).body(store);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Store> updateStore(@PathVariable Long id, @RequestBody Store updatedStore) {
        Store existingStore = stores.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst()
                .orElseThrow(
                        () -> new StoreNotFoundException("Update is NOT completed well !")
                );
        existingStore.setName(updatedStore.getName());
        existingStore.setDesc(updatedStore.getDesc());
        return ResponseEntity.status(HttpStatus.OK).body(existingStore);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Store> deleteStore(@PathVariable Long id) {
        Store existingStore = stores.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new StoreNotFoundException("Cannot DELETE Cause Not FOUND"));
        stores.remove(existingStore);
        return ResponseEntity.status(HttpStatus.OK).body(existingStore);
    }

}
