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
import java.util.logging.Logger;

@Service
public class UserService {
    private static final Logger logger = Logger.getLogger(UserService.class.getName());

    private final UserRepository userRepository;
    private final String UPLOAD_DIR = "./uploads/avatars/";

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        try {
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
                logger.info("Создана директория для загрузки аватаров: " + uploadPath.toAbsolutePath());
            }
        } catch (IOException e) {
            logger.severe("Не удалось создать директорию для загрузки: " + e.getMessage());
            throw new RuntimeException("Не удалось создать директорию для загрузки: " + UPLOAD_DIR, e);
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

        User savedUser = userRepository.save(user);
        logger.info("Создан новый пользователь с ID: " + savedUser.getId());
        return savedUser;
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public String uploadAvatar(AvatarUploadDto avatarUploadDto) throws IOException {
        if (avatarUploadDto == null || avatarUploadDto.getAvatar() == null || avatarUploadDto.getUserId() == null) {
            logger.warning("Получены некорректные данные для загрузки аватара");
            throw new IllegalArgumentException("Отсутствуют необходимые данные для загрузки аватара");
        }

        MultipartFile file = avatarUploadDto.getAvatar();

        if (file.isEmpty()) {
            logger.warning("Загружаемый файл пустой");
            throw new IllegalArgumentException("Загружаемый файл не может быть пустым");
        }

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || !isImageFile(originalFilename)) {
            logger.warning("Загружаемый файл не является изображением: " + originalFilename);
            throw new IllegalArgumentException("Пожалуйста, загрузите файл изображения (jpg, jpeg, png, gif)");
        }

        String filename = UUID.randomUUID() + "_" + originalFilename;
        Path filePath = Paths.get(UPLOAD_DIR + filename);

        try {
            Files.write(filePath, file.getBytes());
            logger.info("Файл сохранен по пути: " + filePath.toAbsolutePath());
        } catch (IOException e) {
            logger.severe("Ошибка при сохранении файла: " + e.getMessage());
            throw new IOException("Ошибка при сохранении файла: " + e.getMessage(), e);
        }

        Optional<User> optionalUser = userRepository.findById(avatarUploadDto.getUserId());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            String oldAvatar = user.getAvatar();
            if (oldAvatar != null && !oldAvatar.equals("default_avatar.png")) {
                try {
                    Path oldFilePath = Paths.get(UPLOAD_DIR + oldAvatar);
                    if (Files.exists(oldFilePath)) {
                        Files.delete(oldFilePath);
                        logger.info("Удален старый аватар: " + oldFilePath.toAbsolutePath());
                    }
                } catch (IOException e) {
                    logger.warning("Не удалось удалить старый аватар: " + e.getMessage());
                }
            }

            user.setAvatar(filename);
            userRepository.save(user);
            logger.info("Обновлен аватар пользователя с ID: " + user.getId());
            return filename;
        } else {
            try {
                Files.delete(filePath);
                logger.warning("Удален файл, так как пользователь не найден: " + filePath.toAbsolutePath());
            } catch (IOException e) {
                logger.warning("Не удалось удалить файл после ошибки: " + e.getMessage());
            }
            logger.warning("Пользователь с ID " + avatarUploadDto.getUserId() + " не найден");
            return null;
        }
    }

    private boolean isImageFile(String filename) {
        String lowercaseFilename = filename.toLowerCase();
        return lowercaseFilename.endsWith(".jpg") ||
                lowercaseFilename.endsWith(".jpeg") ||
                lowercaseFilename.endsWith(".png") ||
                lowercaseFilename.endsWith(".gif");
    }
}