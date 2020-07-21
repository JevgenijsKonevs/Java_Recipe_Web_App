package com.bootcamp.nomnom.service;

import com.bootcamp.nomnom.entity.Recipe;
import com.bootcamp.nomnom.entity.User;
import com.bootcamp.nomnom.repository.UserRepository;
import com.bootcamp.nomnom.util.StringGenerator;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class UserService implements UserDetailsService {

    private static Path fileUploadDirectory = Paths.get("./src/main/uploads/images/user");

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User saveUserRegister(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setFileName("default.png");
        userRepository.save(user);
        return user;
    }

    public boolean userExists(String username) {
        return userRepository.existsByUsername(username);
    }

    public User saveProfilePhoto(User user, MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            user.setFileName("default.png");
            userRepository.save(user);
            return user;
        } else {
            if (ImageIO.read(file.getInputStream()) != null) {
                String toDelete = user.getFileName();
                String fileName = StringGenerator.getRandomFilename(file);
                Path filePath = Paths.get(fileUploadDirectory + "/" + fileName);
                try {
                    Files.write(filePath, file.getBytes());
                    user.setFileName(fileName);
                    userRepository.save(user);
                    if (!("default.png".equals(toDelete))) {
                        Path pathToDelete = Paths.get(fileUploadDirectory + "/" + toDelete);
                        Files.delete(pathToDelete);
                    }
                    return user;
                } catch (Exception e) {
                    // need proper handling...
                    e.printStackTrace();
                }
            }
        }

        return user;
    }

    public User deleteUserPicture(User user) throws IOException {
        if (!("default.png").equals(user.getFileName())) {
            Path deletePath = Paths.get(fileUploadDirectory + "/" + user.getFileName());
            Files.delete(deletePath);
            user.setFileName("default.png");
        }

        return user;
    }
}
