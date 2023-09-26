package com.basinda.config;

import com.basinda.models.eUserType;
import com.basinda.models.entity.AdminUser;
import com.basinda.models.entity.Profession;
import com.basinda.models.entity.Properties;
import com.basinda.contants.PropertiesConstants;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;
import com.basinda.repositories.AdminUserRepository;
import com.basinda.repositories.ProfessionRepository;
import com.basinda.repositories.PropertiesRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {
    private final AdminUserRepository adminUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final PropertiesRepository propertiesRepository;
    private final ProfessionRepository professionRepository;

    public CommandLineAppStartupRunner(AdminUserRepository adminUserRepository, PasswordEncoder passwordEncoder, PropertiesRepository propertiesRepository, ProfessionRepository professionRepository) {
        this.adminUserRepository = adminUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.propertiesRepository = propertiesRepository;
        this.professionRepository = professionRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        AdminUser adminUser = new AdminUser();
        adminUser.setName("Admin");
        adminUser.setPhone("01315656967");
        adminUser.setEmail("admin@gmail.com");
        adminUser.setRole(eUserType.eSuperAdmin);
        adminUser.setPassword(passwordEncoder.encode("12345"));
        adminUserRepository.save(adminUser);

        Properties properties = new Properties();
        properties.setProperty(PropertiesConstants.twoFactorEnabled);
        properties.setValue("False");
        propertiesRepository.save(properties);

        Profession profession1 = new Profession();
        profession1.setName("Student");
        professionRepository.save(profession1);

        Profession profession2 = new Profession();
        profession2.setName("Govt Job");
        professionRepository.save(profession2);

        Profession profession3 = new Profession();
        profession3.setName("Private Job");
        professionRepository.save(profession3);

        Profession profession4 = new Profession();
        profession4.setName("Business");
        professionRepository.save(profession4);

        Profession profession5 = new Profession();
        profession5.setName("Hocker");
        professionRepository.save(profession5);

        Profession profession6 = new Profession();
        profession6.setName("Unemployed");
        professionRepository.save(profession6);
    }
}
