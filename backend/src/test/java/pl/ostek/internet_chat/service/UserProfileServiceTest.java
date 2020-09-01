package pl.ostek.internet_chat.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.ostek.internet_chat.exception.ProfileAlreadyExistsException;
import pl.ostek.internet_chat.exception.UserNotFoundException;
import pl.ostek.internet_chat.mapper.UserProfileMapper;
import pl.ostek.internet_chat.model.entity.Gender;
import pl.ostek.internet_chat.model.entity.User;
import pl.ostek.internet_chat.model.entity.UserProfile;
import pl.ostek.internet_chat.repository.UserProfileRepository;
import pl.ostek.internet_chat.repository.UserRepository;
import pl.ostek.internet_chat.validator.UserProfileValidator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserProfileServiceTest {

    @InjectMocks
    UserProfileService userProfileService;
    @Mock
    UserProfileRepository userProfileRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    UserProfileValidator userProfileValidator;
    @Mock
    UserProfileMapper userProfileMapper;

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
        }).isInstanceOf(ProfileAlreadyExistsException.class)
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
        }).isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User adam not found!");
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
    }

}
