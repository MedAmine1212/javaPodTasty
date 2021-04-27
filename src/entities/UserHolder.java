/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author Douiri Amine
 */
public final class UserHolder {
     private User user;
  private final static UserHolder INSTANCE = new UserHolder();
  
  private UserHolder() {}
  
  
  public void setUser(User u) {
    this.user = u;
  }
  
  public User getUser() {
    return this.user;
  }
   public static UserHolder  getInstance() {
    return INSTANCE;
  }
}
