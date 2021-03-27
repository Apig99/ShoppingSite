package apig.aramian.shoppingcart.controllers;

import apig.aramian.shoppingcart.model.PageRepository;
import apig.aramian.shoppingcart.model.data.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin/pages")
public class AdminPagesController {

    @Autowired
    private PageRepository repository;


    @GetMapping
    public String index(Model model) {
        List<Page> pages = repository.findAllByOrderBySortingAsc();
        model.addAttribute("pages", pages);
        return "admin/pages/index";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("page") Page page) {
        return "admin/pages/add";
    }

    @PostMapping("/add")
    public String add(@Valid Page page, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "admin/pages/add";
        }
        redirectAttributes.addFlashAttribute("message", "Page added");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        String slug = page.getSlug().equals("") ? page.getTitle().toLowerCase().replace(" ", "-") :
                page.getSlug().toLowerCase().replace(" ", "-");

        Page slugExists = repository.findBySlug(slug);
        if (slugExists != null) {
            redirectAttributes.addFlashAttribute("message", "Slug exists, choose another");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("page", page);

        } else {
            page.setSlug(slug);
            page.setSorting(100);
            repository.save(page);
        }
        return "redirect:/admin/pages/add";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model) {
        Page page = repository.getOne(id);

        model.addAttribute("page", page);

        return "admin/pages/edit";
    }

    @PostMapping("/edit")
    public String edit(@Valid Page page, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        Page pageCurrent = repository.getOne(page.getId());

        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", pageCurrent.getTitle());
            return "admin/pages/edit";
        }
        redirectAttributes.addFlashAttribute("message", "Page edited");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        String slug = page.getSlug().equals("") ? page.getTitle().toLowerCase().replace(" ", "-") :
                page.getSlug().toLowerCase().replace(" ", "-");

        Page slugExists = repository.findBySlugAndIdNot(slug, page.getId());
        if (slugExists != null) {
            redirectAttributes.addFlashAttribute("message", "Slug exists, choose another");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("page", page);
        } else {
            page.setSlug(slug);
            repository.save(page);
        }
        return "redirect:/admin/pages/edit/" + page.getId();
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id, RedirectAttributes redirectAttributes) {
        repository.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Page Deleted");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        return "redirect:/admin/pages";
    }

    @PostMapping("/reorder")
    public @ResponseBody String reorder(@RequestParam("id[]") int[] id) {
        int count = 1;
        Page page;

        for (int pageId : id) {
            page = repository.getOne(pageId);
            page.setSorting(count);
            repository.save(page);
            count++;
        }
        return "OK";
    }
}
