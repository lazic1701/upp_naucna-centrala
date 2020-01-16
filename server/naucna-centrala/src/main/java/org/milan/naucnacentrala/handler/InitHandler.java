package org.milan.naucnacentrala.handler;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InitHandler  {

    @Autowired
    IdentityService identityService;

    @PostConstruct
    public void init() {
        Group admini = identityService.createGroupQuery().groupId("administratori").singleResult();
        Group urednici = identityService.createGroupQuery().groupId("urednici").singleResult();
        Group recenzenti = identityService.createGroupQuery().groupId("recenzenti").singleResult();
        Group autori = identityService.createGroupQuery().groupId("autori").singleResult();

        if (admini == null) {
            admini = identityService.newGroup("administratori");
            admini.setName("Administratori");
            identityService.saveGroup(admini);
        }

        if (urednici == null) {
            urednici = identityService.newGroup("urednici");
            urednici.setName("Urednici");
            identityService.saveGroup(urednici);
        }

        if (recenzenti == null) {
            recenzenti = identityService.newGroup("recenzenti");
            recenzenti.setName("Recenzenti");
            identityService.saveGroup(recenzenti);
        }

        if (autori == null) {
            autori = identityService.newGroup("autori");
            autori.setName("Autori");
            identityService.saveGroup(autori);
        }

        User demo = identityService.createUserQuery().userId("demo").singleResult();

        if (demo == null) {
            demo = identityService.newUser("demo");
            demo.setEmail("lazic1701@gmail.com");
            demo.setPassword("demo");
            identityService.saveUser(demo);
            identityService.createMembership(demo.getId(), "administratori");
        }

    }
}
