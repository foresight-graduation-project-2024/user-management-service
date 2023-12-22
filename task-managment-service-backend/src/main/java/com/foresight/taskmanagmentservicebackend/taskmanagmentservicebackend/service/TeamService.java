package com.foresight.taskmanagmentservicebackend.taskmanagmentservicebackend.service;
import com.foresight.taskmanagmentservicebackend.taskmanagmentservicebackend.collection.TeamCollection;
import com.foresight.taskmanagmentservicebackend.taskmanagmentservicebackend.collection.User;
import com.foresight.taskmanagmentservicebackend.taskmanagmentservicebackend.dto.CreateTeamRequest;
import com.foresight.taskmanagmentservicebackend.taskmanagmentservicebackend.dto.TeamSearchCriteria;
import com.foresight.taskmanagmentservicebackend.taskmanagmentservicebackend.dto.TeamSummary;
import com.foresight.taskmanagmentservicebackend.taskmanagmentservicebackend.dto.UpdateTeamRequest;
import com.foresight.taskmanagmentservicebackend.taskmanagmentservicebackend.entity.Member;
import com.foresight.taskmanagmentservicebackend.taskmanagmentservicebackend.entity.Task;
import com.foresight.taskmanagmentservicebackend.taskmanagmentservicebackend.mapper.MapperClass;
import com.foresight.taskmanagmentservicebackend.taskmanagmentservicebackend.mapper.TeamMapper;
import com.foresight.taskmanagmentservicebackend.taskmanagmentservicebackend.repo.TeamCollectionRepo;
import com.mongodb.BasicDBObject;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
@AllArgsConstructor
public class TeamService {
   private TeamMapper mapper;
    private MongoTemplate template;
    private TeamCollectionRepo teamCollectionRepo;
    private UserService userService;
    public void createTeam(CreateTeamRequest request){
        TeamCollection team = teamCollectionRepo.save(mapper.createTeamRequestToTeamCollection(request));
        userService.addOrUpdateUsersTeams(team);

    }

    public Page<TeamCollection> getAll(Pageable page) {
        return teamCollectionRepo.findAll(page);
    }

    public TeamCollection getTeam(String id) {
        return teamCollectionRepo.findById(id).orElseThrow(()->new RuntimeException("Team not found"));
    }

    public void deleteTeam(String teamId){
        userService.deleteUserTeam(teamId);
        teamCollectionRepo.deleteById(teamId);
    }
    public void updateTeam(TeamCollection team){
        TeamCollection oldTeam= teamCollectionRepo.findById(team.getTeamId()).orElseThrow(()->new RuntimeException("Team not found"));
        userService.addOrUpdateUsersTeams(team);
        BeanUtils.copyProperties(team, oldTeam, "teamId");
        teamCollectionRepo.save(oldTeam);
    }

    public Page<TeamSummary> getTeamsSummary(Pageable pageable) {

        List<TeamSummary> summaries= template.find(new Query().with(pageable),TeamSummary.class,"team");
        long count =template.count(new Query().with(pageable),TeamSummary.class,"team");
        return new PageImpl<>(summaries,pageable, count);

    }

    public Page<TeamSummary> searchSummaries(Pageable pageable, TeamSearchCriteria criteria) {
        Query q=new Query();
        if(criteria.getName()!=null && !criteria.getName().isEmpty())
            q.addCriteria(Criteria.where("name").is(criteria.getName()));
        if( (criteria.getLeaderFirstname()!=null && !criteria.getLeaderFirstname().isEmpty()) && (criteria.getLeaderLastname()!=null && !criteria.getLeaderLastname().isEmpty()) )
            q.addCriteria(Criteria.where("teamLeader.firstname").is(criteria.getLeaderFirstname()).andOperator(Criteria.where("teamLeader.lastname").is(criteria.getLeaderLastname())));
        if(criteria.getLeaderRole()!=null && !criteria.getLeaderRole().isEmpty())
            q.addCriteria(Criteria.where("teamLeader.role").is(criteria.getLeaderRole()));
        q.with(pageable);
        List<TeamSummary> summaries= template.find(q,TeamSummary.class,"team");
        long count =template.count(q,TeamSummary.class,"team");
        return new PageImpl<>(summaries,pageable, count);
    }


    public void addTask(Task task1, String teamId) {
        TeamCollection team= teamCollectionRepo.findById(teamId).orElseThrow(()->new RuntimeException("Team not found"));
        team.getTeamTasks().add(task1);
        teamCollectionRepo.save(team);
    }

    public void editTask(Task task, String teamId) {
        TeamCollection team= teamCollectionRepo.findById(teamId).orElseThrow(()->new RuntimeException("Team not found"));
        List<Task> tasks= team.getTeamTasks().stream().map(t -> t.getTaskId().equals(task.getTaskId()) ? task : t).toList();
        team.setTeamTasks(tasks);
        teamCollectionRepo.save(team);
    }

    public void deleteTeamTask(String teamId, String taskId) {
        TeamCollection team= teamCollectionRepo.findById(teamId).orElseThrow(()->new RuntimeException("Team not found"));
        for(Task task:team.getTeamTasks()){
            if(task.getTaskId().equals(taskId))
                team.getTeamTasks().remove(task);
        }
        teamCollectionRepo.save(team);
    }
}
