/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import at.favre.lib.crypto.bcrypt.BCrypt;

/**
 *
 * @author Douiri Amine
 */
public class UpdatableBCrypt {
    String password = "1234";
String bcryptHashString = BCrypt.withDefaults().hashToString(12, password.toCharArray());
BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), bcryptHashString);

}
