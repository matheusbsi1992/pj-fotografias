/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fotografias.util;

import br.com.fotografias.transfer.PessoaTransfer;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Jandisson
 */
public class LoginUsuarios {

    public LoginUsuarios() {

    }

    public static Integer loggedUsersNum = 0;
    private Map<String, PessoaTransfer> usersMap = new HashMap<String, PessoaTransfer>();

    public synchronized void registerLogout(String sessionId) {
        usersMap.remove(sessionId);
    }

    public synchronized Integer getTotalNumberOfSessions() {
        return usersMap.size();
    }

    public synchronized Integer getNumberOfAuthenticatedUsers() {
        int number = 0;
        Set<Map.Entry<String, PessoaTransfer>> entries = usersMap.entrySet();
        for (Map.Entry<String, PessoaTransfer> entry : entries) {
            if (null != entry.getValue()) {
                number++;
            }
        }
        return number;
    }

    public synchronized void registerLogin(String sessionId, PessoaTransfer user) {
        usersMap.put(sessionId, user);
    }
}
