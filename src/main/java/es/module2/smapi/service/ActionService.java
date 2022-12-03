package es.module2.smapi.service;

import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.module2.smapi.model.Action;
import es.module2.smapi.repository.ActionRepository;

@Service
public class ActionService {
    private static final Logger log = LoggerFactory.getLogger(ActionService.class);

    @Autowired
    private ActionRepository actionRepository;

    public List<Action> getAllActions(){
        log.info("Getting All Actions");

        return actionRepository.findAll();
    }

    public Action createAction(String admin, String action_type, String entity_type, String entity_id){
        log.info("Creating Action");

        Action action = new Action();
        action.setAdmin(admin);
        action.setAction_type(action_type);
        action.setEntity_type(entity_type);
        action.setEntity_id(entity_id);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        action.setDate(timestamp.toString());

        action = actionRepository.saveAndFlush(action);

        return action;
    }
}
