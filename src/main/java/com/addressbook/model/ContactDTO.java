package com.addressbook.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ContactDTO {

    private int cid;
    private String firstName;
    private String lastName;
    private String location;
    private String phone;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Date formatter for display
    private static final DateTimeFormatter DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm");
    private static final DateTimeFormatter DATE_ONLY_FORMATTER = DateTimeFormatter.ofPattern("MMM dd, yyyy");

    // Getters and setters
    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    // Convenience methods for formatted date display
    public String getCreatedAtDisplay() {
        return createdAt != null ? createdAt.format(DISPLAY_FORMATTER) : "N/A";
    }
    
    public String getUpdatedAtDisplay() {
        return updatedAt != null ? updatedAt.format(DISPLAY_FORMATTER) : "N/A";
    }
    
    public String getCreatedAtDateOnly() {
        return createdAt != null ? createdAt.format(DATE_ONLY_FORMATTER) : "N/A";
    }
    
    public String getUpdatedAtDateOnly() {
        return updatedAt != null ? updatedAt.format(DATE_ONLY_FORMATTER) : "N/A";
    }
    
    // Method to check if contact was recently modified (within last 7 days)
    public boolean isRecentlyModified() {
        if (updatedAt == null) return false;
        LocalDateTime weekAgo = LocalDateTime.now().minusDays(7);
        return updatedAt.isAfter(weekAgo);
    }
    
    // Method to get age of contact in days
    public long getAgeInDays() {
        if (createdAt == null) return 0;
        return java.time.Duration.between(createdAt, LocalDateTime.now()).toDays();
    }
}
