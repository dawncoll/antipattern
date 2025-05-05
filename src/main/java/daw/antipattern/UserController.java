package daw.antipattern;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

	private List<User> users = new ArrayList<>();

    public UserController() {
        users.add(new User("Alice", 17));
        users.add(new User("Bob", 20));
        users.add(new User("Charlie", 16));
        users.add(new User("Diana", 22));
        users.add(new User("Edward", 19));
        users.add(new User("Fiona", 21));
    }
	
	 @GetMapping("/adultos")
	 public String users(Model model) {
		 List<User> adults = new ArrayList<>();
		 List<User> minors = new ArrayList<>();
		 List<User> seniors = new ArrayList<>();
		 for (User user : users) {
			 if (user.getAge() >= 18 && user.getAge() <= 25) {
				 adults.add(user);
			 }else {
				 seniors.add(user);
			 }
		 }
		 model.addAttribute("adults", adults);
		 model.addAttribute("seniors", seniors);
		 model.addAttribute("totalAdults", adults.size());
		 model.addAttribute("totalMinors", minors.size());
		 model.addAttribute("totalSeniors", seniors.size());
		 model.addAttribute("totalUsers", users.size());
		 return "users";
	 }
}
