package com.capsulecrm.rest;

import java.util.List;

/**
 * @author Mathias Bogaert
 */
public interface HasContacts {
    List<CContact> getContacts();
    
    void setContacts(List<CContact> contacts);
}
