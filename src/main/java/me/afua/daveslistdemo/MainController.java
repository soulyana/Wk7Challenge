package me.afua.daveslistdemo;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class MainController {

    @Autowired
    RoomRepository rooms;

    @GetMapping("/")
    public String showIndex(Model model)
    {

        model.addAttribute("rooms",rooms.findAll());
        return "oindex";
    }

    @GetMapping("/addroom")
    public String addRoom(Model model)
    {
        model.addAttribute("aRoom",new Room());
        return "addRoom";
    }

    @PostMapping("/addroom")
    public String addRoom(@Valid @ModelAttribute("aRoom") Room room, BindingResult result)
    {
        System.out.println(result.toString());
        if(result.hasErrors())
        {
            return "addRoom";
        }
        else
      rooms.save(room);

        return "redirect:/";
    }

    @GetMapping("/viewrooms")
    public String viewRooms(Authentication auth, Model model)
    {
        if (auth == null) {
            //User is not
            model.addAttribute("rooms",rooms.findAllByPrivateListing(false));
        }
        else
        {
            //User is authenticated
            model.addAttribute("rooms",rooms.findAll());
        }
        return "listrooms";
    }

    @GetMapping("/newindex")
    public String showOtherIndex()
    {
        return "index";
    }
}
