package global.sunil.covidupdates.repositories.impl;

import global.sunil.covidupdates.lib.mailclient.MailSender;
import global.sunil.covidupdates.lib.utils.HelperUtils;
import global.sunil.covidupdates.repositories.ExceptionManager;
import global.sunil.covidupdates.repositories.UserRepository;
import global.sunil.covidupdates.repositories.constraints.AppConstraints;
import global.sunil.covidupdates.repositories.dao.UserDao;
import global.sunil.covidupdates.repositories.dao.entites.UserEntity;
import global.sunil.covidupdates.repositories.dao.entites.UserFilterCriteria;
import global.sunil.covidupdates.repositories.dtos.SendEmailRequest;
import global.sunil.covidupdates.repositories.dtos.SendEmailResponse;
import global.sunil.covidupdates.repositories.dtos.UserEmail;
import global.sunil.covidupdates.repositories.dtos.UserInfo;

import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.MessagingException;
import javax.ws.rs.POST;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author Sunil on 2021-05-28 - १३:२७
 */
@Named
public class UserRepositoryImpl implements UserRepository {

    MailSender mailSender;
    UserDao userDao;

    @Inject
    public UserRepositoryImpl(MailSender mailSender, UserDao userDao) {
        this.mailSender = mailSender;
        this.userDao = userDao;
    }

    private Long generateId() {
        return new Date().getTime();
    }

    @Override
    @POST
    public UserInfo addUser(UserEmail userEmail) {

        this.validateUserForAdd(userEmail);
        UserInfo userInfo = this.convertToUserInfo(userEmail);
        userInfo.setEmailValid(isEmailValid(userInfo.getEmail()));
        userInfo.setId(this.generateId());
        Optional<UserEntity> userEntityOptional = this.userDao.add(this.convertToUserEntity(userInfo));
        if (userEntityOptional.isEmpty()) {
            ExceptionManager.throwException(ExceptionManager.UserError.COULD_NOT_ADD_USER);
        }
        return userInfo;
    }

    void validateUserForAdd(UserEmail userEmail) {
        if (HelperUtils.isBlankOrNull(userEmail.getEmail())) {
            ExceptionManager.throwException(ExceptionManager.UserError.EMAIL_IS_MISSING);
        }

        if (!this.isEmailValid(userEmail.getEmail())) {
            ExceptionManager.throwException(ExceptionManager.UserError.EMAIL_IS_INVALID);
        }

        if (HelperUtils.isBlankOrNull(userEmail.getName())) {
            ExceptionManager.throwException(ExceptionManager.UserError.NAME_IS_MISSING);
        }
    }

    @Override
    public List<UserInfo> getUsers() {

        List<UserEntity> userEntities = userDao.getAll();
        return userEntities.stream().map(this::convertToUserInfo)
                .collect(Collectors.toList());
    }

    @Override
    public SendEmailResponse sendEmails(SendEmailRequest request) {

        this.validateSendEmailRequest(request);

        UserFilterCriteria userFilterCriteria = prepareUserFilterCriteria(request);
        List<UserEntity> userEntities = userDao.search(userFilterCriteria);
        if (userEntities == null || userEntities.size() <= 0) {
            ExceptionManager.throwException(ExceptionManager.UserError.COULD_NOT_FOUND_USERS);
        }

        List<UserEntity> userEntitiesWithValidEmail = new ArrayList<>();
        List<UserEntity> userEntitiesWithInvalidEmail = new ArrayList<>();
        for (UserEntity userEntity : userEntities) {
            if (this.isEmailValid(userEntity.getEmail())) {
                userEntitiesWithValidEmail.add(userEntity);
            } else {
                userEntitiesWithInvalidEmail.add(userEntity);
            }
        }

        List<String> recipients = userEntitiesWithValidEmail.stream().map(UserEntity::getEmail)
                .collect(Collectors.toList());
        String subject = request.getSubject();
        String message = request.getMessage();

        try {
            mailSender.send(recipients, subject, message);
        } catch (MessagingException e) {
            ExceptionManager.throwException(ExceptionManager.UserError.COULD_NOT_SEND_EMAIL);
        }

        userEntitiesWithInvalidEmail.forEach(userEntity -> {
            userEntity.setEmailValid(false);
            userDao.update(userEntity);
        });

        List<UserEmail> usersWithValidEmail = userEntitiesWithValidEmail.stream()
                .map(this::convertToUserEmail).collect(Collectors.toList());
        List<UserEmail> usersWithInvalidEmail = userEntitiesWithInvalidEmail.stream()
                .map(this::convertToUserEmail).collect(Collectors.toList());

        return new SendEmailResponse(usersWithValidEmail, usersWithInvalidEmail);
    }

    void validateSendEmailRequest(SendEmailRequest request) {
        if (HelperUtils.isBlankOrNull(request.getSubject())) {
            ExceptionManager.throwException(ExceptionManager.UserError.EMAIL_SUBJECT_IS_MISSING);
        }

        if (HelperUtils.isBlankOrNull(request.getMessage())) {
            ExceptionManager.throwException(ExceptionManager.UserError.MESSAGE_IS_MISSING);
        }

        if(request.getCount() != null && AppConstraints.MAX_EMAIL_RECIPIENTS_NUMBERS < request.getCount()){
            ExceptionManager.throwException(ExceptionManager.UserError.COUNT_EXCEEDED_MAX_LIMIT);
        }

        if(request.getCount() != null && AppConstraints.MAX_EMAIL_RECIPIENTS_NUMBERS < request.getCount()){
            ExceptionManager.throwException(ExceptionManager.UserError.COUNT_EXCEEDED_MAX_LIMIT);
        }

        if(!HelperUtils.isBlankOrNull(request.getSubject()) && AppConstraints.MAX_EMAIL_SUBJECT_LENGTH < request.getSubject().length()){
            ExceptionManager.throwException(ExceptionManager.UserError.EMAIL_SUBJECT_EXCEEDED_MAX_LENGTH_LIMIT);
        }

        if(!HelperUtils.isBlankOrNull(request.getMessage()) && AppConstraints.MAX_EMAIL_BODY_LENGTH < request.getMessage().length()){
            ExceptionManager.throwException(ExceptionManager.UserError.MESSAGE_EXCEEDED_MAX_LENGTH_LIMIT);
        }
    }

    UserFilterCriteria prepareUserFilterCriteria(SendEmailRequest request){
        UserFilterCriteria filterCriteria = new UserFilterCriteria();
        filterCriteria.setSize(request.getCount());
        return filterCriteria;
    }

    UserInfo convertToUserInfo(UserEmail userEmail) {
        UserInfo userInfo = new UserInfo();
        userInfo.setName(userEmail.getName());
        userInfo.setEmail(userEmail.getEmail());
        return userInfo;
    }

    UserEntity convertToUserEntity(UserInfo userInfo) {
        return new UserEntity(userInfo.getId(), userInfo.getName(), userInfo.getEmail(), userInfo.getEmailValid());
    }

    UserInfo convertToUserInfo(UserEntity entity) {
        return new UserInfo(entity.getId(), entity.getName(), entity.getEmail(), entity.getEmailValid());
    }

    UserEmail convertToUserEmail(UserEntity entity) {
        return new UserEmail(entity.getName(), entity.getEmail());
    }

    boolean isEmailValid(String emailToValidate) {
        Pattern regexPattern = Pattern.compile(AppConstraints.VALID_EMAIL_ADDRESS_REGEX, Pattern.CASE_INSENSITIVE);
        Matcher matcher = regexPattern.matcher(emailToValidate);
        return matcher.find();
    }
}
