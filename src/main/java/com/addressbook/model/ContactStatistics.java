package com.addressbook.model;

import java.time.LocalDateTime;

public class ContactStatistics {
    private int totalContacts;
    private int contactsThisWeek;
    private int modifiedThisWeek;
    private LocalDateTime oldestContact;
    private LocalDateTime newestContact;
    
    // Getters and setters
    public int getTotalContacts() { return totalContacts; }
    public void setTotalContacts(int totalContacts) { this.totalContacts = totalContacts; }
    
    public int getContactsThisWeek() { return contactsThisWeek; }
    public void setContactsThisWeek(int contactsThisWeek) { this.contactsThisWeek = contactsThisWeek; }
    
    public int getModifiedThisWeek() { return modifiedThisWeek; }
    public void setModifiedThisWeek(int modifiedThisWeek) { this.modifiedThisWeek = modifiedThisWeek; }
    
    public LocalDateTime getOldestContact() { return oldestContact; }
    public void setOldestContact(LocalDateTime oldestContact) { this.oldestContact = oldestContact; }
    
    public LocalDateTime getNewestContact() { return newestContact; }
    public void setNewestContact(LocalDateTime newestContact) { this.newestContact = newestContact; }
} 