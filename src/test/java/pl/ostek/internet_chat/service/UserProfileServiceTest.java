package pl.ostek.internet_chat.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pl.ostek.internet_chat.exception.InvalidProfileException;
import pl.ostek.internet_chat.exception.ProfileDoesNotExistsException;
import pl.ostek.internet_chat.exception.ProfileExistsException;
import pl.ostek.internet_chat.exception.SuchUserDoesNotExistsException;
import pl.ostek.internet_chat.model.Gender;
import pl.ostek.internet_chat.model.User;
import pl.ostek.internet_chat.model.UserProfile;
import pl.ostek.internet_chat.repository.UserProfileRepository;
import pl.ostek.internet_chat.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class UserProfileServiceTest {

    private UserProfileService userProfileService;
    private UserProfileRepository userProfileRepository;
    private UserRepository userRepository;

    @BeforeEach
    public void initEach() {
        userProfileRepository = mock(UserProfileRepository.class);
        userRepository = mock(UserRepository.class);
        userProfileService = new UserProfileService(userProfileRepository, userRepository);
    }

    @Test
    void createProfile_UserWithoutProfile_ProfileCreated() {
        //given
        UserProfile userProfile = UserProfile.builder().description("123").build();
        User user = User.builder().username("adam").build();
        given(userRepository.findByUsername("adam")).willReturn(user);
        //when
        userProfileService.createProfile(userProfile, "adam");
        //then
        assertThat(userProfile.getUser()).isEqualTo(user);
        verify(userRepository).findByUsername("adam");
        verify(userProfileRepository).save(userProfile);
    }

    @Test
    void createProfile_UserHasProfile_ExceptionThrown() {
        //given
        User user = User.builder().username("adam").build();
        UserProfile userProfile = UserProfile.builder().user(user).description("123").build();
        user.setUserProfile(userProfile);
        given(userRepository.findByUsername("adam")).willReturn(user);
        //expected
        assertThatThrownBy(() -> {
            userProfileService.createProfile(userProfile, "adam");
        }).isInstanceOf(ProfileExistsException.class)
                .hasMessageContaining("adam has already created a profile!");
    }

    @Test
    void createProfile_WrongUsername_ExceptionThrown() {
        //given
        UserProfile userProfile = UserProfile.builder().description("123").build();
        given(userRepository.findByUsername("adam")).willReturn(null);
        //expected
        assertThatThrownBy(() -> {
            userProfileService.createProfile(userProfile, "adam");
        }).isInstanceOf(SuchUserDoesNotExistsException.class)
                .hasMessageContaining("User adam does not exists!");
    }

    @Test
    void updateProfile_UserHasProfile_ProfileSaved() {
        //given
        User user = User.builder().username("adam").build();
        UserProfile oldProfile = UserProfile.builder().user(user).gender(Gender.FEMALE).description("old").build();
        UserProfile newProfile = UserProfile.builder().gender(Gender.MALE).description("new").build();
        user.setUserProfile(oldProfile);
        given(userProfileRepository.findByUsername("adam")).willReturn(oldProfile);
        //when
        userProfileService.updateProfile(newProfile, "adam");
        //then
        assertThat(oldProfile.getDescription()).isEqualTo("new");
        assertThat(oldProfile.getGender()).isEqualTo(Gender.MALE);
        verify(userProfileRepository).save(oldProfile);
    }

    @ParameterizedTest(name = "Set gender={0} and description={1} was successful.")
    @CsvSource(value = {
            "MALE,aaa123", ",@#$~", "FEMALE,",
    })
    void partialUpdateProfile_UserHasProfile_ProfileSaved(Gender gender, String description) {
        //given
        User user = User.builder().username("adam").build();
        UserProfile oldProfile = UserProfile.builder().user(user).gender(Gender.MALE).description("old").build();
        UserProfile newProfile = UserProfile.builder().gender(gender).description(description).build();
        user.setUserProfile(oldProfile);
        given(userProfileRepository.findByUsername("adam")).willReturn(oldProfile);
        //when
        userProfileService.updateProfile(newProfile, "adam");
        //then
        assertThat(oldProfile.getGender()).isEqualTo(gender);
        assertThat(oldProfile.getDescription()).isEqualTo(description);
        verify(userProfileRepository).save(oldProfile);
    }

    @Test
    void getUserProfile_ProfileExists_ProfileReturned() {
        //given
        UserProfile userProfile = UserProfile.builder().description("123").build();
        given(userProfileRepository.findByUsername("adam")).willReturn(userProfile);
        //when
        UserProfile returned = userProfileService.getUserProfile("adam");
        //then
        assertThat(returned).isEqualTo(userProfile);
        verify(userProfileRepository).findByUsername("adam");
    }

    @Test
    void getUserProfile_ProfileDoesNotExists_ExceptionThrown() {
        //given
        given(userProfileRepository.findByUsername("adam")).willReturn(null);
        //expected
        assertThatThrownBy(() -> {
            userProfileService.getUserProfile("adam");
        }).isInstanceOf(ProfileDoesNotExistsException.class)
                .hasMessageContaining("Profile that belongs to adam does not exists!");
    }

    @Test
    void checkIfProfileIsCorrect_NullProfile_ExceptionThrown() {
        assertThatThrownBy(() -> {
            userProfileService.checkIfProfileIsCorrect(null);
        }).isInstanceOf(InvalidProfileException.class)
                .hasMessageContaining("UserProfile should not be null!");
    }

    @Test
    void checkIfProfileIsCorrect_Length256Description_ExceptionThrown() {
        //given
        String description = RandomStringUtils.random(256, true, true);
        UserProfile userProfile = UserProfile.builder().description(description).build();
        //expected
        assertThatThrownBy(() -> {
            userProfileService.checkIfProfileIsCorrect(userProfile);
        }).isInstanceOf(InvalidProfileException.class)
                .hasMessageContaining("Description should be shorter than 256 characters!");
    }

}
