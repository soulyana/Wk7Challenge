package me.afua.daveslistdemo;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.naming.Binding;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class MainController {

    @Autowired
    RoomRepository rooms;

    @Autowired
    AppUserRepository users;

    @Autowired
    AppRoleRepository roles;

    @GetMapping("/")
    public String showIndex(Model model)
    {

        model.addAttribute("rooms",rooms.findAll());
        return "index";
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
        if(result.hasErrors())
        {
            return "addRoom";
        }
        else
      rooms.save(room);

        return "redirect:/";
    }

    @GetMapping("/updateroom")
    public String updateRoomDetails(HttpServletRequest request, Model model)
    {
        model.addAttribute("aUser",users.findById(new Long(request.getParameter("id"))));
        return "addroom";
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


    @GetMapping("/signup")
    public String signUpNewUser(Model model)
    {

        model.addAttribute("aUser",new AppUser());
        return "signup";
    }

    @PostMapping("/signup")
    public String saveNewUser(@Valid @ModelAttribute("aUser") AppUser user, BindingResult result)
    {
        if(result.hasErrors())
            return "signup";
        else
        {
            users.save(user);

        }
        return "redirect:/";
    }

    @PostConstruct
    public void addUsers()
    {
        //Add users before the application loads without a command line runner.
        AppUser aUser = new AppUser();
        aUser.setUsername("newuser");
        aUser.setPassword("password");
        users.save(aUser);

        AppRole aRole = new AppRole();
        aRole.setName("ADMIN");
        roles.save(aRole);


    }


}
