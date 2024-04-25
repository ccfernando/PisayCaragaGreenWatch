package Constants;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SpeciesConstants {
    public static final Map<String, String> SPECIES_MAP;
    
    // Static initializer block to initialize the map
    static {
        Map<String, String> map = new HashMap<>();
        map.put("Banaba", "Lagerstroemia speciosa");
        map.put("Narra", "Pterocarpus indicus");
        map.put("PalawanCherry", "Cassia nodosa");
        map.put("Rosewood", "Petersianthus quadrialatus");
        map.put("Talisay", "Terminalia catappa");
        map.put("Unknown", "Unknown");
        
        SPECIES_MAP = Collections.unmodifiableMap(map);
    }
}