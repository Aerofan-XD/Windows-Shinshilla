package com.example.shinshilla.controllers;

import com.example.shinshilla.models.Review;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
public class MainController {
    private final ObjectMapper mapper = new ObjectMapper();
    private final File reviewsFile = new File("reviews.json");

    private List<Review> loadReviews() {
        if (reviewsFile.exists()) {
            try {
                return mapper.readValue(reviewsFile, new TypeReference<List<Review>>() {});
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<>();
    }

    private void saveReviews(List<Review> reviews) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(reviewsFile, reviews);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/")
    public String index() { return "index"; }

    @GetMapping("/archive") public String archive(){ return "history"; }
    @GetMapping("/photos") public String photos(){ return "photos"; }
    @GetMapping("/instrs") public String instrs(){ return "instrs"; }
    @GetMapping("/wallpapers") public String wallpapers(){ return "wallpapers"; }
    @GetMapping("/download") public String download(){ return "download"; }

    @GetMapping("/otzivy")
    public String otzivy(Model model) {
        model.addAttribute("reviews", loadReviews());
        return "otzivy";
    }

    @PostMapping("/otzivy")
    public String addReview(@RequestParam(defaultValue = "Аноним") String name,
                            @RequestParam(defaultValue = "5") int rating,
                            @RequestParam String comment) {
        if (!comment.isBlank()) {
            List<Review> reviews = loadReviews();
            String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
            reviews.add(new Review(name, rating, comment, date));
            saveReviews(reviews);
        }
        return "redirect:/otzivy";
    }

    @GetMapping("/404")
    public String error() { return "404"; }
}
