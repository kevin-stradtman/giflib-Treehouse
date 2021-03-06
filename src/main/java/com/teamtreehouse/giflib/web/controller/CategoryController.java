package com.teamtreehouse.giflib.web.controller;

import com.teamtreehouse.giflib.model.Category;
import com.teamtreehouse.giflib.service.CategoryService;
import com.teamtreehouse.giflib.web.Color;
import com.teamtreehouse.giflib.web.FlashMessage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/categories")
    public String listCategories(Model model) {
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "category/index";
    }

    @RequestMapping("/categories/{categoryId}")
    public String category(@PathVariable Long categoryId, Model model) {
        Category category = null;

        model.addAttribute("category", category);
        return "category/details";
    }

    @RequestMapping("categories/add")
    public String formNewCategory(Model model) {
        if(!model.containsAttribute("category")) {
            model.addAttribute("category", new Category());
        }
        model.addAttribute("colors", Color.values());
        return "category/form";
    }

    @RequestMapping("categories/{categoryId}/edit")
    public String formEditCategory(@PathVariable Long categoryId, Model model) {

        return "category/form";
    }

    @RequestMapping(value = "/categories/{categoryId}", method = RequestMethod.POST)
    public String updateCategory() {
        return null;
    }

    @RequestMapping(value = "/categories", method = RequestMethod.POST)
    public String addCategory(@Valid Category category, BindingResult result, RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.category", result);
            redirectAttributes.addFlashAttribute("category", category);
            return "redirect:/categories/add";
        }
        categoryService.save(category);
        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Category successfully added", FlashMessage.Status.SUCCESS));
        return "redirect:/categories";
    }

    @RequestMapping(value = "/categories/{categoryId}/delete", method = RequestMethod.POST)
    public String deleteCategory(@PathVariable Long categoryId) {
        return null;
    }
}
