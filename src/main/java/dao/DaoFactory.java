package dao;

import Entity.*;

public class DaoFactory {
    private DaoFactory() {

    }
    public static CrudDAO<Users> getUsersDao(){
        return new UsersDAO();
    }
    public static CrudDAO<Planning> getPlanningDao(){
        return new PlanningDAO();
    }
    public static CrudDAO<Task> getTaskDao(){
        return new TaskDAO();
    }
    public static CrudDAO<Action> getActionDao(){
        return new ActionDAO();
    }
    public static CrudDAO<Address> getAddressDao(){
        return new AddressDAO();
    }
    public static CrudDAO<Event> getEventDao(){
        return new EventDAO();
    }
    public static CrudDAO<Role> getRoleDao(){return new RoleDAO();}
    public static CrudDAO<Share> getShareDao(){return new ShareDAO();}

}
