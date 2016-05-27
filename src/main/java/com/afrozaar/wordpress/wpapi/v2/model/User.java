package com.afrozaar.wordpress.wpapi.v2.model;

import com.afrozaar.wordpress.wpapi.v2.deserializer.StringBooleanMapDeserializer;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.annotation.Generated;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "_links",
        "avatar_urls",
        "capabilities",
        "description",
        "email",
        "first_name",
        "id",
        "last_name",
        "link",
        "name",
        "nickname",
        "registered_date",
        "roles",
        "slug",
        "url"
})
public class User {

    @JsonProperty("_links")
    private Links Links;
    @JsonProperty("avatar_urls")
    private AvatarUrls avatarUrls;
    @JsonProperty("capabilities")
    @JsonDeserialize(using = StringBooleanMapDeserializer.class)
    private Map<String, Boolean> capabilities;
    @JsonProperty("description")
    private String description;
    @JsonProperty("email")
    private String email;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("id")
    private Long id;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("link")
    private String link;
    @JsonProperty("name")
    private String name;
    @JsonProperty("nickname")
    private String nickname;
    @JsonProperty("registered_date")
    private String registeredDate;
    @JsonProperty("roles")
    private List<String> roles = new ArrayList<String>();
    @JsonProperty("slug")
    private String slug;
    @JsonProperty("url")
    private String url;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The Links
     */
    @JsonProperty("_links")
    public Links getLinks() {
        return Links;
    }

    /**
     * @param Links The _links
     */
    @JsonProperty("_links")
    public void setLinks(Links Links) {
        this.Links = Links;
    }

    /**
     * @return The avatarUrls
     */
    @JsonProperty("avatar_urls")
    public AvatarUrls getAvatarUrls() {
        return avatarUrls;
    }

    /**
     * @param avatarUrls The avatar_urls
     */
    @JsonProperty("avatar_urls")
    public void setAvatarUrls(AvatarUrls avatarUrls) {
        this.avatarUrls = avatarUrls;
    }

    /**
     * @return The capabilities
     */
    @JsonProperty("capabilities")
    public Map<String, Boolean> getCapabilities() {
        return capabilities;
    }

    /**
     * @param capabilities The capabilities
     */
    @JsonProperty("capabilities")
    public void setCapabilities(Map<String, Boolean> capabilities) {
        this.capabilities = capabilities;
    }

    /**
     * @return The description
     */
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    /**
     * @param description The description
     */
    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return The email
     */
    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    /**
     * @param email The email
     */
    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return The firstName
     */
    @JsonProperty("first_name")
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName The first_name
     */
    @JsonProperty("first_name")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return The id
     */
    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    /**
     * @param id The id
     */
    @JsonProperty("id")
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return The lastName
     */
    @JsonProperty("last_name")
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName The last_name
     */
    @JsonProperty("last_name")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return The link
     */
    @JsonProperty("link")
    public String getLink() {
        return link;
    }

    /**
     * @param link The link
     */
    @JsonProperty("link")
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * @return The name
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The nickname
     */
    @JsonProperty("nickname")
    public String getNickname() {
        return nickname;
    }

    /**
     * @param nickname The nickname
     */
    @JsonProperty("nickname")
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * @return The registeredDate
     */
    @JsonProperty("registered_date")
    public String getRegisteredDate() {
        return registeredDate;
    }

    /**
     * @param registeredDate The registered_date
     */
    @JsonProperty("registered_date")
    public void setRegisteredDate(String registeredDate) {
        this.registeredDate = registeredDate;
    }

    /**
     * @return The roles
     */
    @JsonProperty("roles")
    public List<String> getRoles() {
        return roles;
    }

    /**
     * @param roles The roles
     */
    @JsonProperty("roles")
    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    /**
     * @return The slug
     */
    @JsonProperty("slug")
    public String getSlug() {
        return slug;
    }

    /**
     * @param slug The slug
     */
    @JsonProperty("slug")
    public void setSlug(String slug) {
        this.slug = slug;
    }

    /**
     * @return The url
     */
    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    /**
     * @param url The url
     */
    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
