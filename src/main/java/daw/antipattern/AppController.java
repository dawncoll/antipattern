package daw.antipattern;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AppController {

	private List<Message> messages = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    private int messageCounter = 0;

    public AppController() {
        users.add(new User("Alice", 17));
        users.add(new User("Bob", 20));
        users.add(new User("Charlie", 16));
        users.add(new User("Diana", 22));
        users.add(new User("Edward", 19));
        users.add(new User("Fiona", 21));
    }

    @GetMapping("/")
    public String home(Model model) {
        try {
            List<String> sortedMessages = messages.stream()
                    .sorted(Comparator.comparing(Message::getTimestamp).reversed())
                    .map(Message::toString)
                    .collect(Collectors.toList());
            String serverStatus = "Servidor activo desde: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
            model.addAttribute("messages", sortedMessages);
            model.addAttribute("total", messages.size());
            model.addAttribute("latestMessage", messages.isEmpty() ? "No hay mensajes" : messages.get(messages.size() - 1).getText());
            model.addAttribute("serverStatus", serverStatus);
            model.addAttribute("adultsCount", users.stream().filter(u -> u.getAge() >= 18).count());
            model.addAttribute("minorCount", users.stream().filter(u -> u.getAge() < 18).count());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "home";
    }

    @PostMapping("/add")
    public String addMessage(@RequestParam("message") String message, @RequestParam("autor") String autor, Model model) {
        if (message != null && !message.trim().isEmpty()) {
        	if(autor != null && !autor.trim().isEmpty()) {
	        	if(!users.isEmpty()) {
		        	 
	        		User autorFound = null;
	        		Iterator<User> userIterator = users.iterator();
		            while (userIterator.hasNext()) {
		                 User user = userIterator.next();
		                 if(autor.equalsIgnoreCase(user.getName())) {
		                	 autorFound = user;
		                	 break;
		                 }
		            }
		            if(autorFound!=null) {
		            	String processedMessage = processMessage(message);
		            	messages.add(new Message(processedMessage, new Date(), autorFound));
		            	messageCounter++;
		            }
	             }
        	}
        }
        if (message.length() > 200) {
            System.out.println("Mensaje muy largo: " + message);
        }
        return home(model);
    }

    private String processMessage(String message) {
        String processed = message.trim().toUpperCase();
        processed = processed + " (" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + ")";
        if (processed.length() > 100) {
            processed = processed.substring(0, 100);
        }
        return processed;
    }

    @GetMapping("/about")
    public String about(Model model) {
        try {
            model.addAttribute("info", "Esta es una simple aplicación sin separación de capas. Mensajes actuales: " + messageCounter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "about";
    }

    @GetMapping("/users")
    public String users(Model model) {
        List<User> adults = new ArrayList<>();
        List<User> minors = new ArrayList<>();
        List<User> seniors = new ArrayList<>();
        for (User user : users) {
            if (user.getAge() >= 18 && user.getAge() <= 25) {
                adults.add(user);
            } else if (user.getAge() < 18) {
                minors.add(user);
            } else {
                seniors.add(user);
            }
        }
        model.addAttribute("adults", adults);
        model.addAttribute("minors", minors);
        model.addAttribute("seniors", seniors);
        model.addAttribute("totalAdults", adults.size());
        model.addAttribute("totalMinors", minors.size());
        model.addAttribute("totalSeniors", seniors.size());
        model.addAttribute("totalUsers", users.size());
        return "users";
    }

    @GetMapping("/cleanup")
    public String cleanupInactiveUsers(Model model) {
        Iterator<User> userIterator = users.iterator();
        while (userIterator.hasNext()) {
            User user = userIterator.next();
            boolean found = false;
            for (Message message : messages) {
                if (message.getAuthor().getName().equalsIgnoreCase(user.getName())) {
                	long months = ChronoUnit.MONTHS.between(message.getTimestamp().toInstant(), LocalDateTime.now());
                	if (months <= 6) {
                		found = true;
                        break;
                    }
                }
            }
            if (!found) {
            	if (user.getAge() > 30) {
            		if (user.getName().startsWith("A")) {
            			if (user.getName().length() > 5) {
            				userIterator.remove();
            			}else {
            				if (user.getName().length() <= 5) {
            					System.out.println("Usuario " + user.getName() + " no eliminado porque su nombre es corto");
                            }
                        }
                    }else {
                        userIterator.remove();
                    }
                }else {
                    userIterator.remove();
                }
            }
        }
        model.addAttribute("totalUsersAfterCleanup", users.size());
        return "cleanup";
    }
    
}

