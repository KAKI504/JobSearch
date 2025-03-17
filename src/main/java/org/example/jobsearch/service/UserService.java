package org.example.jobsearch.service;

import org.example.jobsearch.dto.AvatarUploadDto;
import org.example.jobsearch.dto.UserDto;
import org.example.jobsearch.model.User;
import org.example.jobsearch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final String UPLOAD_DIR = "./uploads/avatars/";

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        try {
            Files.createDirectories(Paths.get(UPLOAD_DIR));
        } catch (IOException e) {
            throw new RuntimeException("Не удалось создать директорию для загрузки", e);
        }
    }

    public User createUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setAge(userDto.getAge());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setAccountType(userDto.getAccountType());
        user.setAvatar("default_avatar.png");

        return userRepository.save(user);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public String uploadAvatar(AvatarUploadDto avatarUploadDto) throws IOException {
        MultipartFile file = avatarUploadDto.getAvatar();
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIR + filename);

        Files.write(filePath, file.getBytes());

        Optional<User> optionalUser = userRepository.findById(avatarUploadDto.getUserId());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setAvatar(filename);
            userRepository.save(user);
            return filename;
        }

        return null;
    }
}