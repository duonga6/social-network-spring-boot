package com.socialnetwork.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User extends BaseEntity implements UserDetails {

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, columnDefinition = "VARCHAR(100)")
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    private String avatarUrl;
    private Date dateOfBirth;
    private String address;
    private String phoneNumber;
    private String job;

    @OneToMany(mappedBy = "user")
    private Set<Comment> comments;

    @OneToMany(mappedBy = "fromUser")
    private Set<Friend> requestsSend;

    @OneToMany(mappedBy = "toUser")
    private Set<Friend> requestsReceived;

    @OneToMany(mappedBy = "user")
    private Set<PostLike> likedPost;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<UserRole> listRoles;

    public User(UUID uuid, String firstName, String lastName, String password, String email, String avatarUrl, Date dateOfBirth, String address, String job, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.avatarUrl = avatarUrl;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.job = job;
        this.phoneNumber = phoneNumber;
        this.setId(uuid);
    }

    public User(String firstName, String lastName, String password, String email, String avatarUrl, Date dateOfBirth, String address, String job, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.avatarUrl = avatarUrl;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.job = job;
        this.phoneNumber = phoneNumber;
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return  listRoles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole().getRoleName().name()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
