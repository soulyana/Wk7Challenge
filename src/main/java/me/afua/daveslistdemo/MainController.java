package me.afua.daveslistdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.PostConstruct;
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
        model.addAttribute("usersregistered",users.count());
        model.addAttribute("rented",rooms.countAllByRented(true));
        model.addAttribute("unrented",rooms.countAllByRented(false));
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

    @GetMapping("/updateroom/{id}")
    public String updateRoomDetails(@PathVariable("id") long id, HttpServletRequest request, Model model)
    {
        model.addAttribute("aRoom",rooms.findById(id).get());
        return "editRoom";
    }

    @GetMapping("/showprivate")
    public String showPrivateListings(Model model)
    {
        model.addAttribute("rooms",rooms.findAllByPrivateListing(true));
        System.out.println("Listing private rooms");
        model.addAttribute("title", "Dave's Privately Listed Rooms");
        return "listrooms";
    }
    @GetMapping("/view/{id}")
    public String viewRoomDetails(@PathVariable("id") long id, HttpServletRequest request, Model model)
    {
        model.addAttribute("aRoom",rooms.findById(id).get());
        return "showroomdetails";
    }
    @GetMapping("/viewrooms")
    public String viewRooms(Authentication auth, Model model)
    {
        if (auth==null) {
            //User is not authenticated
            model.addAttribute("rooms",rooms.findAllByPrivateListing(false));
            model.addAttribute("title", "Dave's Publicly Rooms");
            System.out.println("Listing public rooms");
        }
        else
        {
            //User is authenticated
            model.addAttribute("rooms",rooms.findAll());
            model.addAttribute("title", "Dave's Listed Rooms");
            System.out.println("Listing all rooms");
        }

        return "listrooms";
    }

    @GetMapping("/changerented/{id}")
    public String changeRented(@PathVariable("id") long id)
    {
        Room thisRoom= rooms.findById(id).get();
        thisRoom.setRented(!thisRoom.isRented());
        rooms.save(thisRoom);
        return "redirect:/viewrooms";
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

    @GetMapping("/pkindex")
    public String indexThis()
    {
        return "pkindex";
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

        Room aRoom = new Room();
        aRoom.setAddress("123 Main Street");
        aRoom.setCableType("basic");
        aRoom.setCity("New York");
        aRoom.setDescription("A private room preset by app ");
        rooms.save(aRoom);

        aRoom = new Room();
        aRoom.setAddress("124 Main Street");
        aRoom.setCableType("basic");
        aRoom.setCity("New York");
        aRoom.setDescription("A public room preset by app ");
        aRoom.setPrivateListing(false);
        rooms.save(aRoom);




    }


}
