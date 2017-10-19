package com.cac.dsi.dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.cac.dsi.entites.User;
import com.cac.dsi.mail.MailSender;

@RestController
public class UserRestServices {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	@Qualifier("javasampleapproachMailSender")
	public MailSender mailSender;

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public Page<User> GetUsrs(String mc,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size) {
		return userRepository.findUsers("%" + mc + "%", new PageRequest(page,size));
	}

	@RequestMapping(value = "/user/{email}/{pass}", method = RequestMethod.GET)
	public User login(@PathVariable("email") String email,
			@PathVariable("pass") String pass) {
		User user = userRepository.login(email, pass);
		return user;
	}

	@RequestMapping(value = "/userp", method = RequestMethod.POST)
	public String passob(@RequestBody User u) {
		User user = userRepository.Vemail(u.getEmail());
		if (user != null) {
			String from = "appdsicac@gmail.com";
			String to = user.getEmail();
			String subject = "Mot de passe ";
			String body = "Bonjour " + user.getNom()
					+ " votre mot de pass est " + user.getPassword();
			try{
			 mailSender.sendMail(from, to, subject, body);
			}finally{
				
			}
			return "valid";

		} else
			return "nonValid";
	}

	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public String PostUser(@RequestBody User u) {
		User user = userRepository.Vemail(u.getEmail());
		if (user == null) {
			userRepository.save(u);
			return "ajut";
		} else
			return "error";
	}

	@RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
	public User PutUser(@RequestBody User u, @PathVariable Long id) {
		u.setId(id);
		User user = userRepository.findOne(id);
		System.out.print(id);
		u.setPassword(user.getPassword());
		return userRepository.saveAndFlush(u);
	}

	@RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
	public void DeleteUser(@PathVariable("id") Long id) {
		userRepository.delete(id);

	}
}
