package grokswell.summoner;

import org.bukkit.block.Block;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.Metadatable;
import org.bukkit.plugin.Plugin;


//THIS CLASS THANKS TO AmShaegar on BukkitDev
public class MetaUtil {
 
public static void setMetadata(Metadatable obj, String key, Object value, Plugin plugin) {
obj.setMetadata(key,new FixedMetadataValue(plugin, value));
}
 
public static MetadataValue getMetadata(Metadatable obj, String key, Plugin plugin){
for(MetadataValue value : obj.getMetadata(key)){
if(value.getOwningPlugin().getDescription().getName().equals(plugin.getDescription().getName())){
return value;
}
}
return null;
}
 
public static void removeMetadata(Metadatable obj, String key, Plugin plugin) {
obj.removeMetadata(key, plugin);
}
 
public static void protect(Block block, Plugin plugin) {
setMetadata(block, "protected", true, plugin);
}
 
public static void unprotect(Block block, Plugin plugin) {
removeMetadata(block, "protected", plugin);
}
 
public static boolean isProtected(Block block, Plugin plugin) {
return getMetadata(block, "protected", plugin) == null ? false : getMetadata(block, "protected", plugin).asBoolean();
}
 
}