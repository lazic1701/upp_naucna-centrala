package org.milan.naucnacentrala.handler;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.User;
import org.milan.naucnacentrala.repository.IUserRepository;
import org.milan.naucnacentrala.repository_es.UserRepositoryES;
import org.milan.naucnacentrala.service.UserService;
import org.milan.naucnacentrala.service_es.UserServiceES;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class InitHandler  {

    @Autowired
    IdentityService identityService;

    @Autowired
    IUserRepository userRepository;


    @Autowired
    UserServiceES userServiceES;

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
            demo.setEmail("sep.bbf@gmail.com");
            demo.setPassword("demo");
            identityService.saveUser(demo);
            identityService.createMembership(demo.getId(), "administratori");
        }


        User lepi = identityService.createUserQuery().userId("lepi").singleResult();

        if (lepi == null) {
            lepi = identityService.newUser("lepi");
            lepi.setEmail("zokofausti@gmail.com");
            lepi.setPassword("mile");
            identityService.saveUser(lepi);
            identityService.createMembership(lepi.getId(), "urednici");
        }

        User lazoni = identityService.createUserQuery().userId("lazoni").singleResult();

        if (lazoni == null) {
            lazoni = identityService.newUser("lazoni");
            lazoni.setEmail("lazoni.milancello@google.com");
            lazoni.setPassword("mile");
            identityService.saveUser(lazoni);
            identityService.createMembership(lazoni.getId(), "urednici");
        }

        User perun = identityService.createUserQuery().userId("perun").singleResult();

        if (perun == null) {
            perun = identityService.newUser("perun");
            perun.setEmail("perunpere@yahoo.com");
            perun.setPassword("mile");
            identityService.saveUser(perun);
            identityService.createMembership(perun.getId(), "urednici");
        }


        User racko = identityService.createUserQuery().userId("racko").singleResult();

        if (racko == null) {
            racko = identityService.newUser("racko");
            racko.setEmail("lazm1701@yahoo.com");
            racko.setPassword("mile");
            identityService.saveUser(racko);
            identityService.createMembership(racko.getId(), "recenzenti");
        }

        User karapandza = identityService.createUserQuery().userId("karapandza").singleResult();

        if (karapandza == null) {
            karapandza = identityService.newUser("karapandza");
            karapandza.setEmail("milanquedinho@gmail.com");
            karapandza.setPassword("mile");
            identityService.saveUser(karapandza);
            identityService.createMembership(karapandza.getId(), "recenzenti");
        }

        User recen = identityService.createUserQuery().userId("recen").singleResult();

        if (recen == null) {
            recen = identityService.newUser("recen");
            recen.setEmail("lazoni.milancello@gmail.com");
            recen.setPassword("recen");
            identityService.saveUser(recen);
            identityService.createMembership(recen.getId(), "recenzenti");
        }

        User lebron = identityService.createUserQuery().userId("lebron").singleResult();

        if (lebron == null) {
            lebron = identityService.newUser("lebron");
            lebron.setEmail("lazicy@gmail.com");
            lebron.setPassword("lebron");
            identityService.saveUser(lebron);
            identityService.createMembership(lebron.getId(), "recenzenti");
        }

        User otore = identityService.createUserQuery().userId("otore").singleResult();

        if (otore == null) {
            otore = identityService.newUser("otore");
            otore.setEmail("lazic1701@gmail.com");
            otore.setPassword("otore");
            identityService.saveUser(otore);
            identityService.createMembership(otore.getId(), "autori");
        }

        List<org.milan.naucnacentrala.model.User> allUsers = userRepository.findAll();

        for (org.milan.naucnacentrala.model.User u: allUsers) {
            try {
                userServiceES.saveUser(u);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }



    }
}
