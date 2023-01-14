package com.dochien0204.codeproject.cronjob.user;

import com.dochien0204.codeproject.entities.User;
import com.dochien0204.codeproject.repositories.UserRepository;
import com.dochien0204.codeproject.services.MailService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Component
public class UserCronjob {

    /**
     * This method is used to send mail to happy birthday for user
     */
    private final UserRepository userRepository;
    private final MailService mailService;

    public UserCronjob(UserRepository userRepository, MailService mailService) {
        this.userRepository = userRepository;
        this.mailService = mailService;
    }

    @Scheduled(cron = "0 13 22 * * ?")
    public void sendMailToHappyBirthdayToUser() {
        //get list user
        List<User> users = userRepository.findAll();
        //check day and month of user's birthday
        List<String> emails = new ArrayList<>();
        users.forEach(user -> {
            if(checkUserBirthday(user)) {
                emails.add(user.getEmail());
            }
        });
        mailService.sendMailToHappyBirthday(emails.toArray(String[]::new));
    }

    public boolean checkUserBirthday(User user) {
        //get current time
        Calendar calendar = Calendar.getInstance();
        //get month of current time (Calender: 0-January ... 11-December)
        int month = calendar.get(Calendar.MONTH);
        //get day of current time
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        //get date of birth user (format: dd-mm-yyyy)
        String dateOfBirth = user.getDateOfBirth();
        String[] numbers = dateOfBirth.split("-");
        if(month - (Integer.valueOf(numbers[1]) - 1) == 0 && (day - Integer.valueOf(numbers[0]) == 0)) {
            return true;
        }
        return false;

    }
}
