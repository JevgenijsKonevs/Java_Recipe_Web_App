package com.bootcamp.nomnom.service;

import com.bootcamp.nomnom.entity.User;
import com.bootcamp.nomnom.repository.UserRepository;
import com.bootcamp.nomnom.util.StringGenerator;
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
        userRepository.save(user);
        return user;
    }

    //Based on the RecipeService saveRecipe method..
    public User saveProfilePhoto(User user, MultipartFile file) throws IOException {
        String dir = UserService.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "../resources";
        if (ImageIO.read(file.getInputStream()) != null) {
            String fileName = StringGenerator.getRandomFilename(file);
            Path filePath = Paths.get(dir + "/user-profile_photos/" + fileName);
            try {
                Files.write(filePath, file.getBytes());
                user.setFileName(fileName);
                userRepository.save(user);
                return user;
            } catch (Exception e) {
                // need proper handling...
                e.printStackTrace();
            }

        } else {
            return null;
        }
        return user;
    }


}
