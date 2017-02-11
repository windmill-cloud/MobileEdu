package edu.ucsb.cs.cs185.xuanwangreminder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ReminderContent {
    public static RecyclerView.Adapter<ReminderListActivity.SimpleItemRecyclerViewAdapter.ViewHolder> adapter;
    public static final List<Reminder> ITEMS = new ArrayList<Reminder>();
    public static final Map<String, Reminder> ITEM_MAP = new HashMap<String, Reminder>();
    public static int MONDAY = 128;
    public static int TUESDAY = 32;
    public static int WEDNESDAY = 16;
    public static int THURSDAY = 8;
    public static int FRIDAY = 4;
    public static int SATURDAY = 2;
    public static int SUNDAY = 1;

    /**
     * Add a reminder to the list
     * @param item
     */

    public static void addItemInMem(Reminder item){
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
        adapter.notifyDataSetChanged();
    }
    public static void addItem(Reminder item, Context context) {
        ReminderDBHelper dbOperator = DatabaseOperatorSingleton.getInstance(context).getDBOperator();
        dbOperator.insertReminder(item);
        addItemInMem(item);
    }

    public static void updateItem(Reminder item, Context context){
        ReminderDBHelper dbOperator = DatabaseOperatorSingleton.getInstance(context).getDBOperator();
        dbOperator.updateReminder(item);
        adapter.notifyDataSetChanged();
    }

    /**
     * Remove a reminder from the list
     * @param item
     */
    public static void removeItem(Reminder item, Context context) {
        ReminderDBHelper dbOperator = DatabaseOperatorSingleton.getInstance(context).getDBOperator();
        dbOperator.deleteReminder(item);
        ITEMS.remove(item);
        ITEM_MAP.remove(item.id);
        adapter.notifyDataSetChanged();
    }

    public static Reminder getItem(String id) {
        if(ITEM_MAP.containsKey(id)){
            return ITEM_MAP.get(id);
        }
        return null;
    }

    public static void populateReminders(Context context){
        ReminderDBHelper dbOperator = DatabaseOperatorSingleton.getInstance(context).getDBOperator();
        dbOperator.populateReminders(context);
    }

    /**
     * The reminder object (add fields as necessary)
     */
    public static class Reminder {
        public final String id;
        public String title;
        public String details;
        public Integer hour;
        public Integer minute;
        public int days;
        //public static int count = 0;

        public Reminder(String title, int days, Integer hour, Integer minute, String details) {
            id  = UUID.randomUUID().toString();
            this.title = title;
            this.days = days;
            this.hour = hour;
            this.minute = minute;
            this.details = details;
        }

        public Reminder(String uuid, String title, int days, Integer hour, Integer minute, String details) {
            id  = uuid;
            this.title = title;
            this.days = days;
            this.hour = hour;
            this.minute = minute;
            this.details = details;
        }
    }

}
