import java.io.FileReader;
import java.util.*;
import com.google.gson.*;

public class RoomLoader {
    public Map<String, Room> loadRooms(String filePath) {
        Map<String, Room> rooms = new HashMap<>();
        try {
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(new FileReader(filePath), JsonObject.class);

            for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                String roomId = entry.getKey();
                JsonObject roomData = entry.getValue().getAsJsonObject();

                String name = roomData.get("name").getAsString();
                String description = roomData.get("description").getAsString();

                Map<String, String> exits = new HashMap<>();
                JsonObject exitsJson = roomData.getAsJsonObject("exits");
                for (Map.Entry<String, JsonElement> exit : exitsJson.entrySet()) {
                    exits.put(exit.getKey(), exit.getValue().getAsString());
                }

                Map<String, String> lockedExits = new HashMap<>();
                if (roomData.has("lockedExits")) {
                    JsonObject lockedExitsJson = roomData.getAsJsonObject("lockedExits");
                    for (Map.Entry<String, JsonElement> lock : lockedExitsJson.entrySet()) {
                        lockedExits.put(lock.getKey(), lock.getValue().getAsString());
                    }
                }

                List<Item> items = new ArrayList<>();
                JsonArray itemsJson = roomData.getAsJsonArray("items");
                for (JsonElement itemElement : itemsJson) {
                    JsonObject itemObj = itemElement.getAsJsonObject();
                    String itemId = itemObj.get("id").getAsString();
                    String itemName = itemObj.get("name").getAsString();
                    String itemDescription = itemObj.get("description").getAsString();
                    String itemUse = itemObj.get("use").getAsString();
                    int itemWeight = itemObj.get("weight").getAsInt();
                    if (itemUse.equalsIgnoreCase("weapon")) {
                        int itemDmg = itemObj.get("dmg").getAsInt();
                        items.add(new Weapon(itemId, itemName, itemDescription, itemUse, itemWeight, itemDmg));
                    } else if (itemUse.equalsIgnoreCase("heal")) {
                        int healAmt = itemObj.get("healAmt").getAsInt();
                        items.add(new Healable(itemId, itemName, itemDescription, itemUse, itemWeight, healAmt));
                    } else {
                        items.add(new Item(itemId, itemName, itemDescription, itemUse, itemWeight));
                    }
                }

                List<String> tags = new ArrayList<>();
                JsonArray tagsJson = roomData.getAsJsonArray("tags");
                for (JsonElement tagElement : tagsJson) {
                    String tag = tagElement.getAsString();
                    tags.add(tag);
                }

                List<Monster> monsters = new ArrayList<>();
                JsonArray monsterJson = roomData.getAsJsonArray("monsters");
                for (JsonElement monsterElement : monsterJson) {
                    JsonObject monsterObj = monsterElement.getAsJsonObject();
                    String monsterName = monsterObj.get("name").getAsString();
                    String monsterDesc = monsterObj.get("description").getAsString();
                    int hp = monsterObj.get("hp").getAsInt();
                    int dmg = monsterObj.get("dmg").getAsInt();
                    List<Item> inventory = new ArrayList<>();
                    JsonArray invJson = monsterObj.getAsJsonArray("inventory");
                    for (JsonElement monsterItem : invJson) {
                        JsonObject itemObj = monsterItem.getAsJsonObject();
                        String itemId = itemObj.get("id").getAsString();
                        String itemName = itemObj.get("name").getAsString();
                        String itemDescription = itemObj.get("description").getAsString();
                        String itemUse = itemObj.get("use").getAsString();
                        int itemWeight = itemObj.get("weight").getAsInt();
                         if (itemUse.equalsIgnoreCase("weapon")) {
                            int itemDmg = itemObj.get("dmg").getAsInt();
                            inventory.add(new Weapon(itemId, itemName, itemDescription, itemUse, itemWeight, itemDmg));
                        } else if (itemUse.equalsIgnoreCase("heal")) {
                            int healAmt = itemObj.get("healAmt").getAsInt();
                            inventory.add(new Healable(itemId, itemName, itemDescription, itemUse, itemWeight, healAmt));
                        } else {
                            inventory.add(new Item(itemId, itemName, itemDescription, itemUse, itemWeight));
                        }
                    }
                    monsters.add(new Monster(monsterName, monsterDesc, hp, dmg, inventory));
                }

                Room room = new Room(roomId, name, description, exits, lockedExits, items, tags, monsters);
                rooms.put(roomId, room);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rooms;
    }
}
