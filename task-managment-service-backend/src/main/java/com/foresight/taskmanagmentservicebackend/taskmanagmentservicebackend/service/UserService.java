package com.foresight.taskmanagmentservicebackend.taskmanagmentservicebackend.service;

import com.foresight.taskmanagmentservicebackend.taskmanagmentservicebackend.collection.TeamCollection;
import com.foresight.taskmanagmentservicebackend.taskmanagmentservicebackend.collection.User;
import com.foresight.taskmanagmentservicebackend.taskmanagmentservicebackend.dto.TaskSummary;
import com.foresight.taskmanagmentservicebackend.taskmanagmentservicebackend.entity.Member;
import com.foresight.taskmanagmentservicebackend.taskmanagmentservicebackend.entity.Task;
import com.foresight.taskmanagmentservicebackend.taskmanagmentservicebackend.entity.Team;
import com.foresight.taskmanagmentservicebackend.taskmanagmentservicebackend.mapper.MapperClass;
import com.foresight.taskmanagmentservicebackend.taskmanagmentservicebackend.mapper.TeamMapper;
import com.foresight.taskmanagmentservicebackend.taskmanagmentservicebackend.repo.UserRepo;
import com.mongodb.BasicDBObject;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepo userRepo;
    private MongoTemplate mongoTemplate;
    private TeamMapper teamMapper;
    public void addOrUpdateUsersTeams(TeamCollection teamCollection){
        List<Member> members =teamCollection.getMembers();
        Team team= teamMapper.teamCollectionToTeam(teamCollection);
        BulkOperations bulkOps = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, User.class);
        for (Member member: members) {
            Query query = new Query(Criteria.where("userId").is(member.getMemberId()));
            Update update = new Update().addToSet("teams", team);
            bulkOps.upsert(query, update);
        }
        bulkOps.execute();
    }

    public void deleteUserTeam(String teamId) {
        Query query = new Query(Criteria.where("teams.teamId").is(teamId));
        Update update = new Update().pull("teams", new BasicDBObject("teamId", teamId));
        mongoTemplate.updateMulti(query, update, User.class);
    }
    public void deleteUserFromTeam(String teamId, String memberId){
        Query query = new Query(Criteria.where("userId").is(memberId));
        Update update = new Update().pull("teams", new BasicDBObject("teamId", teamId));
        mongoTemplate.updateFirst(query, update, User.class);
    }

    public void addTask(Task task, String memberId) {
        User user =userRepo.findById(memberId).orElseThrow(()->new RuntimeException("user is not found"));
        user.getTasks().add(task);
        userRepo.save(user);
    }

    public void editTask(Task task) {
        User user=userRepo.findById(task.getAssignee().getMemberId()).orElseThrow(()->new RuntimeException("user is not found"));
        List<Task> userTasks=user.getTasks().stream()
                .map(t -> t.getTaskId().equals(task.getTaskId()) ? task : t)
                .toList();
        user.setTasks(userTasks);
        userRepo.save(user);
    }

    public void deleteTask(String memberId, String taskId) {
        User user=userRepo.findById(memberId).orElseThrow(()->new RuntimeException("user is not found"));
        //user.getTasks().removeIf(task -> task.getTaskId() == taskId);
        for (Task task:user.getTasks()){
            if(task.getTaskId().equals(taskId)){
                user.getTasks().remove(task);
            }
        }
        userRepo.save(user);
    }

    public User getUser(String id) {
        return userRepo.findById(id).orElseThrow(()->new RuntimeException("user is not found"));
    }

    public Page<Team> getUserTeams(Pageable pageable, String userId) {
        Query query= new Query(Criteria.where("userId").is(userId));
        query.fields().exclude("_id").include("teams").slice("teams", (int)pageable.getOffset(), pageable.getPageSize());
        long count = mongoTemplate.count(query, Team.class, "user");
        List<Team>teams=mongoTemplate.find(query,Team.class,"user");
        return new PageImpl<>(teams,pageable,count);
    }

    public Page<Task> getUserTasks(Pageable pageable, String userId) {
        Query query= new Query(Criteria.where("userId").is(userId));
        query.fields().exclude("_id").include("tasks").slice("tasks", (int)pageable.getOffset(), pageable.getPageSize());
        long count = mongoTemplate.count(query, Task.class, "user");
        List<Task>tasks=mongoTemplate.find(query,Task.class,"user");
        return new PageImpl<>(tasks,pageable,count);
    }
}
